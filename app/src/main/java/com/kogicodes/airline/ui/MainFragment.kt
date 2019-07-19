package com.kogicodes.airline.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.kogicodes.airline.R
import com.kogicodes.airline.utils.ViewUtils

class MainFragment : Fragment() {

    private var viewModel: MainViewModel? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel?.token()
        viewModel?.observeToken()?.observe(this, Observer {


            ViewUtils.setStatus(activity, view, it.status, it.message, false, ViewUtils.ErrorViewTypes.TOAST, it.exception)
            if (it.status == Status.SUCCESS) { showPlacePicker() }

        })

    }

    private fun showPlacePicker() {



    }
}
