package com.example.mcommerce.me.view.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.auth.model.Addresse
import com.example.mcommerce.draftModel.LineItem
import com.example.mcommerce.draftModel.OrderPrices
import com.example.mcommerce.me.viewmodel.CustomerViewModel
import com.example.mcommerce.me.viewmodel.CustomerViewModelFactory
import com.example.mcommerce.model.Repository
import com.example.mcommerce.network.AppClient

class UserAddressesFragment : Fragment() {

    lateinit var address_back_icon : ImageView
    lateinit var btnAddNewAddress : Button
    lateinit var customerAddressesRecyclerView: RecyclerView
    private lateinit var customerAddressAdapter: CustomerAddressAdapter
    lateinit var customerAddressesLayoutManager: LinearLayoutManager
    lateinit var customerViewModel: CustomerViewModel
    lateinit var customerViewModelFactory: CustomerViewModelFactory

    lateinit var communicator: Communicator

    var lineItems : ArrayList<LineItem> = ArrayList()
    var orderPrices : ArrayList<OrderPrices> = ArrayList()

   // var amount = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_addresses, container, false)
        initComponent(view)

        communicator = activity as Communicator

        if(arguments != null){
           // amount = arguments?.getString("sub_total") as String
            lineItems = arguments?.getSerializable("line_items") as ArrayList<LineItem>
            orderPrices = arguments?.getSerializable("order_price") as ArrayList<OrderPrices>
           // Log.i("paymenttt","payment From Address Fragment ${lineItems.get(0).quantity},${lineItems.get(0).tax_lines?.get(0)?.price}," +
             //       ",${lineItems.get(0).variant_id}, ${orderPrices.get(0).subTotal}, ${orderPrices.get(0).total}")
           // Toast.makeText(requireContext(),"${orderPrices.get(0).subTotal}", Toast.LENGTH_SHORT).show()
        }
        customerAddressAdapter = CustomerAddressAdapter(communicator,lineItems,orderPrices)
        customerAddressesLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        customerAddressesRecyclerView.setLayoutManager(customerAddressesLayoutManager)
        customerAddressesRecyclerView.setAdapter(customerAddressAdapter)
        customerViewModelFactory = CustomerViewModelFactory(Repository.getInstance(AppClient.getInstance(), requireContext()))
        customerViewModel = ViewModelProvider(this, customerViewModelFactory).get(CustomerViewModel::class.java)

        val sharedPreferences = requireContext().getSharedPreferences("userAuth", AppCompatActivity.MODE_PRIVATE)
        val customerId = sharedPreferences.getString("cusomerID",null).toString()
        customerViewModel.getUserDetails(customerId)
        customerViewModel.customerInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                customerAddressAdapter.setCustomerAddressesData(requireContext(),
                    response.addresses!!)
            }
        }
        btnAddNewAddress.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, AddNewAddressFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }
    private fun initComponent(view: View){
        address_back_icon = view.findViewById(R.id.address_back_icon)
        btnAddNewAddress = view.findViewById(R.id.btnAddNewAddress)
        customerAddressesRecyclerView = view.findViewById(R.id.userAddressesRecyclerView)
    }


}