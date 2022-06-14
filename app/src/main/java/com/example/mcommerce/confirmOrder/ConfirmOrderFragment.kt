package com.example.mcommerce.confirmOrder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.model.Repository
import com.example.mcommerce.network.AppClient
import com.example.mcommerce.orderDetails.view.OrderItemsAdapter
import com.example.mcommerce.orderDetails.viewModel.OrderDetailsViewModel
import com.example.mcommerce.orderDetails.viewModel.OrderDetailsViewModelFactory
import com.example.mcommerce.orders.model.Order


class ConfirmOrderFragment : Fragment() {

    private lateinit var itemRecycleView:RecyclerView
    private lateinit var tvSubTotal:TextView
    private lateinit var tvFees:TextView
    private lateinit var tvTotal:TextView
    private lateinit var tvAddress:TextView
    private lateinit var tvPhoneNum:TextView
    private lateinit var tvPaymentMethod:TextView
    private lateinit var okBtn:Button

    private lateinit var orderItemsAdapter: OrderItemsAdapter
    private lateinit var orderDetailsViewModelFactory: OrderDetailsViewModelFactory
    private lateinit var orderDetailsViewModel: OrderDetailsViewModel
    private lateinit var communicator: Communicator

    lateinit var myOrder: Order
    var fees:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_confirm_order, container, false)
        initComponent(view)
        Log.i("TAG","args $arguments")
        orderItemsAdapter= OrderItemsAdapter()
        communicator = activity as Communicator
        itemRecycleView.adapter = orderItemsAdapter
        orderDetailsViewModelFactory = OrderDetailsViewModelFactory(
            Repository.getInstance(
                AppClient.getInstance(),
                requireContext()))
        orderDetailsViewModel = ViewModelProvider(this, orderDetailsViewModelFactory)[OrderDetailsViewModel::class.java]
        if(arguments != null){
            myOrder=arguments?.getSerializable("order") as Order
            fees= arguments?.getDouble("fees")!!
            tvAddress.text=
                "${myOrder.billing_address?.address1} ${myOrder.billing_address?.city}, ${myOrder.billing_address?.country}"
            tvTotal.text=
                "${myOrder.total_price} ${myOrder.currency}"


            tvSubTotal.text= myOrder.subtotal_price.toString()
            tvFees.text=fees.toString()
            tvPhoneNum.text=myOrder.phone.toString()
            //tvPaymentMethod.text=myOrder.processing_method
            Log.i("TAG","line ${myOrder.line_items}")
            orderItemsAdapter.setUpdatedData(myOrder.line_items!!,requireContext(),communicator)

        }

        return view
    }

  private fun initComponent(view:View){
      itemRecycleView=view.findViewById(R.id.itemRecycleView)
      tvSubTotal=view.findViewById(R.id.tvSubTotal)
      tvFees=view.findViewById(R.id.tvFees)
      tvTotal=view.findViewById(R.id.tvTotal)
      tvAddress=view.findViewById(R.id.tvAddress)
      tvPhoneNum=view.findViewById(R.id.tvPhoneNum)
      tvPaymentMethod=view.findViewById(R.id.tvPaymentMethod)
      okBtn=view.findViewById(R.id.okBtn)
  }
}