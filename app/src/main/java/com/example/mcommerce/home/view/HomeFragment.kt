package com.example.mcommerce.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

import com.example.mcommerce.HomeActivity.Companion.mySearchFlag
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.home.viewModel.HomeViewModel
import com.example.mcommerce.home.viewModel.HomeViewModelFactory
import com.example.mcommerce.model.Repository
import com.example.mcommerce.network.AppClient
import kotlinx.coroutines.*
import kotlin.math.abs

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mcommerce.search.view.MysearchFragment
import com.example.mcommerce.shopping_cart.view.ShoppingCartFragment


class HomeFragment : Fragment() {

    lateinit var bradsRecyclerView: RecyclerView
    lateinit var brandAdapter: BrandAdapter
    lateinit var discountCodeAdapter: DiscountCodeAdapter
    lateinit var couponsRecyclerView: RecyclerView
    lateinit var cardImg:ImageView

    lateinit var homeFactory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel
    lateinit var communicator: Communicator

    private lateinit var  adsViewPager: ViewPager2
    private lateinit var handler : Handler
    private lateinit var adsImageList:ArrayList<Int>
    private lateinit var adsAdapter: AdsAdapter
    lateinit var couponsLayoutManager: LinearLayoutManager

    private val runnable = Runnable {
        adsViewPager.currentItem = adsViewPager.currentItem + 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view:View = inflater.inflate(R.layout.fragment_home, container, false)
        communicator = activity as Communicator
        initAdsViewPager(view)
        setUpTransformer()
        adsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })

        bradsRecyclerView=view.findViewById(R.id.bradsRecyclerView)
        couponsRecyclerView = view.findViewById(R.id.couponsRecyclerView)
        cardImg=view.findViewById(R.id.cardImg)
        couponsLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        couponsRecyclerView.setLayoutManager(couponsLayoutManager)
        brandAdapter= BrandAdapter()
        discountCodeAdapter = DiscountCodeAdapter()
        bradsRecyclerView.setAdapter(brandAdapter)
        homeFactory = HomeViewModelFactory(
            Repository.getInstance(
                AppClient.getInstance(),
                requireContext()))
        couponsRecyclerView.setAdapter(discountCodeAdapter)
        homeFactory = HomeViewModelFactory( Repository.getInstance(AppClient.getInstance(), requireContext()))
        homeViewModel = ViewModelProvider(this, homeFactory).get(HomeViewModel::class.java)
        ////

        cardImg.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, ShoppingCartFragment())
            transaction.addToBackStack(null);
            transaction.commit()
          /*
            var navController: NavController = Navigation.findNavController(it)
            var navDir: NavDirections =HomeFragmentDirections.goToCart()
            navController.navigate(navDir)
           */
        }

        homeViewModel.onlineDiscountCodes.observe(viewLifecycleOwner) { coupons ->
            Log.i("getCodes","Get Discount Codes \n ${coupons.get(0)}")
            if (coupons != null){
                discountCodeAdapter.setCouponsData(requireContext(), coupons)
            }
        }
        homeViewModel.getAllProducts()
        homeViewModel.onlineBrands.observe(viewLifecycleOwner) { brands ->
            Log.i("TAG","hello from home fragment ${homeViewModel.onlineBrands.value?.get(1)?.id}")
            homeViewModel.onlineBrands.value?.let { brandAdapter.setUpdatedData(it,requireContext(),communicator) }
        }
        var img: ImageView =view.findViewById(R.id.searchImg);
        img.setOnClickListener {
            mySearchFlag=1
//            var navController: NavController = Navigation.findNavController(it)
//            var navDir: NavDirections =HomeFragmentDirections.actionHomeFragmentToSearchFragment()
//            navController.navigate(navDir)
            val fragment: Fragment = MysearchFragment()
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(com.example.mcommerce.R.id.frameLayout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable , 2000)
    }
    private fun initAdsViewPager(view : View){
        adsViewPager = view.findViewById(R.id.adsViewPager)
        handler = Handler(Looper.myLooper()!!)
        adsImageList = ArrayList()
        adsImageList.add(R.drawable.ads0)
        adsImageList.add(R.drawable.ads1)
        adsImageList.add(R.drawable.ads5)
        adsImageList.add(R.drawable.ads3)
        adsImageList.add(R.drawable.ads4)
        adsImageList.add(R.drawable.ads6)
        adsImageList.add(R.drawable.ads2)
        adsAdapter = AdsAdapter(adsImageList, adsViewPager)
        adsViewPager.adapter = adsAdapter
        adsViewPager.offscreenPageLimit = 3
        adsViewPager.clipToPadding = false
        adsViewPager.clipChildren = false
        adsViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        adsViewPager.setPageTransformer(transformer)
    }


}