package com.example.mymvvmroomdemo.view.main.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.base.AppFragment
import com.example.mymvvmroomdemo.data.APIService
import com.example.mymvvmroomdemo.data.database.AppDatabase
import com.example.mymvvmroomdemo.databinding.FragmentHomeBinding
import com.example.mymvvmroomdemo.utils.util
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect



class HomeFragment : AppFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun initializeComponent(view: View?) {

    }

    override fun pageVisible() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(container!!.context),
            R.layout.fragment_home,
            container,
            false
        )
        var root = binding.root

        setupViewModel(container.context)
        setupList()
        setupView()

       /* val mLayoutManager: LinearLayoutManager
        mLayoutManager = LinearLayoutManager(this.context)
        binding.homeRecyclerview.setLayoutManager(mLayoutManager)

        binding.homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount()
                    totalItemCount = mLayoutManager.getItemCount()
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = true

                            getMoreItems()
                        }
                    }
                }
            }
        })
*/
        return root
    }

    private fun setupView() {
        lifecycleScope.launch {
            util.showProgressDialog(context, false)
            viewModel.listData.collect{
                util.dismissProgressDialog(context as Activity?)
                homeAdapter.submitData(it)
            }

            viewModel.getAllUsers()
        }
    }


    private fun setupList() {
        homeAdapter = HomeAdapter()
        binding.homeRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun setupViewModel(context: Context) {
        viewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(APIService.getApiService(), AppDatabase.getInstance(context).getData())
            )[HomeViewModel::class.java]
    }

    /*fun getMoreItems() {
        viewModel = ViewModelProviders.of(this,
            this.context?.let { HomeViewModelFactory(it, this) }).get(
            HomeViewModel::class.java
        )

    }

    override fun onSuccessWithData(jsonObject: JSONObject) {

        arrayList = ArrayList()

        val dataArray = jsonObject.getJSONArray("data")

        for (i in 0 until dataArray.length()) {
            var dataItem: DataItem =
                Gson().fromJson(dataArray.get(i).toString(), DataItem::class.java)
            arrayList.add(dataItem)
        }

        viewModel.pageCount.observe(viewLifecycleOwner, Observer {
            if (it == 0){
                setAdapter()
            }else{
                loading = false
                adapter = HomeAdapter(this.context, arrayList)
                adapter.addData(arrayList)
            }
        })
        if (adapter == null) {
            setAdapter()
        } else {
            loading = false
            adapter.addData(arrayList)
        }

    }

    private fun setAdapter() {
        binding.homeRecyclerview?.apply {
            adapter =
                HomeAdapter(
                    context,
                    arrayList
                )
        }
    }

    override fun onSuccess(message: String) {
    }

    override fun onFailure(message: String) {
        alertDialog(this.context, "Opppss...", message, getString(R.string.ok), false, "")
    }*/
}