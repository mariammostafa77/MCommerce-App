package com.example.mcommerce.payment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerce.HomeActivity
import com.example.mcommerce.HomeActivity.Companion.addAddressFrom
import com.example.mcommerce.HomeActivity.Companion.myDetailsFlag
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.auth.model.Addresse
import com.example.mcommerce.draftModel.LineItem
import com.example.mcommerce.draftModel.OrderPrices
import com.example.mcommerce.me.view.setting.AddNewAddressFragment
import com.example.mcommerce.me.viewmodel.CustomerViewModel
import com.example.mcommerce.me.viewmodel.CustomerViewModelFactory
import com.example.mcommerce.model.Repository
import com.example.mcommerce.network.AppClient
import com.example.mcommerce.network.CheckInternetConnectionFirstTime
import com.example.mcommerce.network.InternetConnectionChecker
import com.google.android.material.snackbar.Snackbar

class PaymentAddressFragment : Fragment(), PaymentAddressClickListener {

    lateinit var txtNoAddressDataFound: TextView
    lateinit var imgNoAddress: ImageView
    lateinit var paymentAddressProgressBar: ProgressBar
    lateinit var address_back_icon : ImageView
    lateinit var address_add_new_address : ImageView
    lateinit var btnContinueToPayment : Button
    lateinit var paymentUserAddressesRecyclerView: RecyclerView
    lateinit var noInternetLayoutPaymentAddress: ConstraintLayout
    private lateinit var paymentAddressesAdapter: PaymentAddressesAdapter
    lateinit var customerAddressesLayoutManager: LinearLayoutManager
    lateinit var customerViewModel: CustomerViewModel
    lateinit var customerViewModelFactory: CustomerViewModelFactory
    lateinit var communicator: Communicator
    private lateinit var internetConnectionChecker: InternetConnectionChecker

    companion object {
        var lineItems: ArrayList<LineItem> = ArrayList()
        var orderPrices: ArrayList<OrderPrices> = ArrayList()
    }
    var selectedAddressFromAdapter: Addresse = Addresse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment_address, container, false)
        initComponent(view)

        communicator = activity as Communicator
        paymentAddressProgressBar.isVisible = true
        if(arguments != null){
            lineItems = arguments?.getSerializable("line_items") as ArrayList<LineItem>
            orderPrices = arguments?.getSerializable("order_price") as ArrayList<OrderPrices>
        }
        address_back_icon.setOnClickListener {
            val manager: FragmentManager = activity!!.supportFragmentManager
            manager.popBackStack()
        }
        paymentAddressesAdapter = PaymentAddressesAdapter(this,communicator,lineItems,orderPrices)
        customerAddressesLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        paymentUserAddressesRecyclerView.setLayoutManager(customerAddressesLayoutManager)
        paymentUserAddressesRecyclerView.setAdapter(paymentAddressesAdapter)
        customerViewModelFactory = CustomerViewModelFactory(Repository.getInstance(AppClient.getInstance(), requireContext()))
        customerViewModel = ViewModelProvider(this, customerViewModelFactory).get(CustomerViewModel::class.java)

        val sharedPreferences = requireContext().getSharedPreferences("userAuth", AppCompatActivity.MODE_PRIVATE)
        val customerId = sharedPreferences.getString("cusomerID",null).toString()

        if(CheckInternetConnectionFirstTime.checkForInternet(requireContext())){
            customerViewModel.getUserDetails(customerId)
            noInternetLayoutPaymentAddress.visibility=View.INVISIBLE
        }else{
            noInternetLayoutPaymentAddress.visibility=View.VISIBLE
        }
        internetConnectionChecker = InternetConnectionChecker(requireContext())
        internetConnectionChecker.observe(this,{ isConnected ->
            if (isConnected){
                customerViewModel.getUserDetails(customerId)
                noInternetLayoutPaymentAddress.visibility=View.INVISIBLE
            }else{
                noInternetLayoutPaymentAddress.visibility=View.VISIBLE
            }
        })

        customerViewModel.customerInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                paymentAddressesAdapter.setCustomerAddressesData(requireContext(), response.addresses!!)
                paymentAddressProgressBar.isVisible = false
                imgNoAddress.visibility=View.INVISIBLE
                txtNoAddressDataFound.visibility=View.INVISIBLE
            }
            else{
                paymentAddressProgressBar.isVisible = false
                imgNoAddress.visibility=View.VISIBLE
                txtNoAddressDataFound.visibility=View.VISIBLE
            }
        }

        address_add_new_address.setOnClickListener {
            addAddressFrom = 1
            replaceFragment(AddNewAddressFragment())
        }

        btnContinueToPayment.setOnClickListener {
            if(selectedAddressFromAdapter.city.isNullOrEmpty()){
                var snake = Snackbar.make(view, "Please select address", Snackbar.LENGTH_LONG)
                snake.show()
            }
            else{
                myDetailsFlag=0
                val bundle=Bundle()
                val paymentFragment = PaymentFragment()
                bundle.putSerializable("selectedAddress", selectedAddressFromAdapter)
                bundle.putSerializable("lineItems",lineItems)
                bundle.putSerializable("orderPrice",orderPrices)
                paymentFragment.arguments=bundle
                replaceFragment(paymentFragment)
            }

        }
        return view
    }

    private fun initComponent(view: View){
        address_back_icon = view.findViewById(R.id.address_back_icon)
        imgNoAddress = view.findViewById(R.id.imgNoAddress)
        address_add_new_address = view.findViewById(R.id.address_add_new_address)
        paymentAddressProgressBar = view.findViewById(R.id.paymentAddressProgressBar)
        txtNoAddressDataFound = view.findViewById(R.id.txtNoAddressDataFound)
        btnContinueToPayment = view.findViewById(R.id.btnContinueToPayment)
        paymentUserAddressesRecyclerView = view.findViewById(R.id.paymentUserAddressesRecyclerView)
        noInternetLayoutPaymentAddress = view.findViewById(R.id.noInternetLayoutPaymentAddress)
    }
    fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun goFromAddressToPayment(selectedAddress: Addresse) {
        selectedAddressFromAdapter = selectedAddress

    }


}