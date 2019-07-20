package com.kogicodes.airline.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.models.custom.Status
import com.kogicodes.airline.R
import com.kogicodes.airline.adapters.AirportListAdapter
import com.kogicodes.airline.adapters.OnclickRecyclerListener
import com.kogicodes.airline.models.airports.Airport
import com.kogicodes.airline.utils.ViewUtils
import kotlinx.android.synthetic.main.airport_fragment.*

class AirportFragment : Fragment() {

    private var viewModel: MainViewModel? = null
    private var airportListAdapter: AirportListAdapter? = null
    private var airportModels: MutableList<Airport>? = ArrayList()

    companion object {
        fun newInstance() = AirportFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.airport_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        }
        initAirports()
        viewModel?.airports(false)
        viewModel?.observeAirports()?.observe(this, Observer {
            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                if (it != null && it.data?.airportResource != null && it.data.airportResource!!.airports != null && it.data.airportResource!!.airports!!.airport != null) {
                    refreshAirports(it.data.airportResource!!.airports!!.airport!!)
                } else {
                    Toast.makeText(context!!, "Error Loading Airports", Toast.LENGTH_LONG).show()
                }

            }

        })
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (airportListAdapter != null) {
                    if (TextUtils.isEmpty(newText)) {
                        airportListAdapter?.filter?.filter("")
                    } else {
                        airportListAdapter?.filter?.filter(newText)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })


    }

    private fun refreshAirports(airportlist: List<Airport>) {

        if (airportModels == null) {
            airportModels = ArrayList()
        }

        airportModels?.addAll(airportlist)
        airportListAdapter?.update(airportModels)

    }


    private fun initAirports() {
        val type = arguments?.getSerializable("TYPE") as Int

        airportListAdapter = AirportListAdapter(context!!, airportModels, object : OnclickRecyclerListener {
            override fun onClickListener(position: Int) {

                if (type == 1) {
                    viewModel?.originAirportData?.value = airportModels!![position]
                } else {
                    viewModel?.destinationAirportData?.value = airportModels!![position]

                }
                activity?.supportFragmentManager?.popBackStack()
            }

            override fun onLongClickListener(position: Int) {

            }
        })

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = airportListAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {

                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                    if (true) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {


                            viewModel?.airports(true)

                        } else {

                        }
                    }
                }

            }
        })

    }
}
