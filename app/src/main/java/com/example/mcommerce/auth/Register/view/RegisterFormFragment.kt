
package com.example.mcommerce.auth.Register.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.mcommerce.HomeActivity
import com.example.mcommerce.R
import com.example.mcommerce.auth.Register.viewModel.RegisterViewModel
import com.example.mcommerce.auth.Register.viewModel.RegisterViewModelFactory
import com.example.mcommerce.auth.login.view.LoginFormFragment
import com.example.mcommerce.auth.model.*
import com.example.mcommerce.model.Repository
import com.example.mcommerce.network.AppClient
import com.example.mcommerce.network.CheckInternetConnectionFirstTime
import com.example.mcommerce.shopping_cart.view.ShoppingCartFragment
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register_form.*


class RegisterFormFragment : Fragment() {
   lateinit var btnSkip:Button
   lateinit var txtLogin:TextView
   lateinit var edtFName:EditText
   lateinit var edtLName:EditText
   lateinit var edtEmail:EditText
   lateinit var edtPassword:EditText
   lateinit var edtConfirmPASS:EditText
    lateinit var myEdtPhone:EditText
   lateinit var btnRegister:Button
   lateinit var registerViewModel:RegisterViewModel
   lateinit var registerViewModelFactory:RegisterViewModelFactory
   var sharedPreferences: SharedPreferences? = null
    lateinit var registerFName:String
    lateinit var registerLName:String
    lateinit var registerEmail:String
    lateinit var registerPassword:String
    lateinit var registerConfirmPassword:String
    lateinit var registerPhone:String
    lateinit var registerProgressbar:ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       var view= inflater.inflate(R.layout.fragment_register_form, container, false)
        registerProgressbar=view.findViewById(R.id.registerProgressBar)
        btnSkip=view.findViewById(R.id.btnSkip)
        txtLogin=view.findViewById(R.id.txtHaveAcc)
        edtFName=view.findViewById(R.id.edtFName)
        edtLName=view.findViewById(R.id.edtLName)
        edtEmail=view.findViewById(R.id.edtRegisterEmail)
        edtPassword=view.findViewById(R.id.edtRegisterPassword)
        edtConfirmPASS=view.findViewById(R.id.edtConfirmPass)
        myEdtPhone=view.findViewById(R.id.edtPhone)
        btnRegister=view.findViewById(R.id.btnRegister)
        registerViewModelFactory = RegisterViewModelFactory(Repository.getInstance(AppClient.getInstance(), requireContext()))
        registerViewModel = ViewModelProvider(this, registerViewModelFactory).get(RegisterViewModel::class.java)

        txtLogin.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, LoginFormFragment())
            transaction.commit()
        }
        btnSkip.setOnClickListener {
            startActivity(Intent(requireContext(),HomeActivity::class.java))
        }


        btnRegister.setOnClickListener {
            if (CheckInternetConnectionFirstTime.checkForInternet(requireContext())) {
                registerFName = edtFName.text.toString()
                registerLName = edtLName.text.toString()
                registerEmail = edtEmail.text.toString()
                registerPassword = edtPassword.text.toString()
                registerConfirmPassword = edtConfirmPASS.text.toString()
                registerPhone = myEdtPhone.text.toString()
                if (!registerFName.isEmpty() && !registerLName.isEmpty() && !registerPhone.isEmpty() && !registerEmail.isEmpty() &&
                    !registerPassword.isEmpty() && !registerConfirmPassword.isEmpty() && registerConfirmPassword == registerPassword && registerPassword.length >= 6 &&
                    Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()
                ) {
                    registerProgressbar.visibility = View.VISIBLE
                    var customer = CustomerX()
                    customer.first_name = registerFName
                    customer.last_name = registerLName
                    customer.email = registerEmail
                    customer.verified_email = true
                    var phoneNumber: String = registerPhone
                    customer.phone = phoneNumber
                    customer.tags = registerPassword

                    var customDetai = CustomerDetail(customer)
                    registerViewModel.postCustomer(customDetai)
                    registerViewModel.customer.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            registerProgressbar.visibility = View.INVISIBLE
                            val snack =
                                Snackbar.make(it, "Register Succefully", Snackbar.LENGTH_LONG)
                            snack.show()
                            val editor =
                                requireContext().getSharedPreferences(
                                    "userAuth",
                                    Context.MODE_PRIVATE
                                )
                                    .edit()
                            editor.putString("email", response.body()!!.customer!!.email)
                            editor.putString("password", response.body()!!.customer!!.tags)
                            editor.putString("fname", response.body()!!.customer!!.first_name)
                            editor.putString("lname", response.body()!!.customer!!.last_name)
                            editor.putString("phone", response.body()!!.customer!!.phone)
                            editor.putString(
                                "cusomerID",
                                response.body()!!.customer!!.id.toString()
                            )
                            editor.putBoolean("isLogin", true)
                            editor.commit()
                            requireActivity().finish()
                            startActivity(Intent(requireContext(), HomeActivity::class.java))

                        } else {
                            registerProgressbar.visibility = View.INVISIBLE
                            val snack = Snackbar.make(
                                it,
                                "Email or Phone already Register!!",
                                Snackbar.LENGTH_LONG
                            )
                            snack.show()
                        }
                    }
                } else {
                    registrationValidation()
                }
            }else{
                val snake = Snackbar.make(view, "Ops! You Lost internet connection!!!", Snackbar.LENGTH_LONG)
                snake.show()
            }
        }

        return view
    }
    fun registrationValidation(){
        if (TextUtils.isEmpty(registerFName)) {
            edtFName.setError("First Name is requried")
            edtFName.requestFocus()
        }
        if (TextUtils.isEmpty(registerLName)) {
            edtLName.setError("Last Name is requried")
            edtLName.requestFocus()
        }
        if (TextUtils.isEmpty(registerEmail)) {
            edtEmail.setError("Email is requried")
            edtEmail.requestFocus()
        }
        if (TextUtils.isEmpty(registerPassword)) {
            edtPassword.setError("Password is requried")
            edtPassword.requestFocus()
        }
        if (TextUtils.isEmpty(registerConfirmPassword)) {
            edtConfirmPASS.setError("Confirm Password is requried")
            edtConfirmPASS.requestFocus()
        }
        if (TextUtils.isEmpty(registerPhone)) {
            myEdtPhone.setError("Phone is requried")
            myEdtPhone.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()) {
            edtEmail.setError("please enter email in correct format")
            edtEmail.requestFocus()
        }
        if (registerPassword.length < 6) {
            edtPassword.setError("required more than 6")
            edtPassword.requestFocus()
        }
        if (registerConfirmPassword != registerPassword) {
            edtConfirmPASS.setError("must be matched")
            edtConfirmPASS.requestFocus()
        }

    }

}