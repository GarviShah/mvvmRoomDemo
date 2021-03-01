package com.example.mymvvmroomdemo.view.main.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymvvmroomdemo.base.AppFragment
import com.example.mymvvmroomdemo.R

class ProfileFragment : AppFragment() {

    private lateinit var viewModel: ProfileViewModel
    override fun initializeComponent(view: View?) {
    }

    override fun pageVisible() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}