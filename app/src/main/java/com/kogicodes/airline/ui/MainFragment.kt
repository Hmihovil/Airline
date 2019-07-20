package com.kogicodes.airline.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.custom.Status
import com.kogicodes.airline.R
import com.kogicodes.airline.adapters.OnclickRecyclerListener
import com.kogicodes.airline.adapters.ScheduleListAdapter
import com.kogicodes.airline.models.airports.Airport
import com.kogicodes.airline.models.schedules.Schedule
import com.kogicodes.airline.models.schedules.ScheduleSearch
import com.kogicodes.airline.utils.Const
import com.kogicodes.airline.utils.DateTimeUtils
import com.kogicodes.airline.utils.ViewUtils
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {

    private var viewModel: MainViewModel? = null
    private var originAirport: Airport? = null
    private var destinationAirport: Airport? = null


    private var schedulesListAdapter: ScheduleListAdapter? = null
    private var schedules: List<Schedule>? = ArrayList()

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
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        }
        initSchedules()
        viewModel?.observeSchedules()?.observe(this, Observer {
            ViewUtils.setStatus(activity, view, it.status, it.message, false, ViewUtils.ErrorViewTypes.TOAST, it.exception)
            if (it.status == Status.SUCCESS) {


                if (it != null && it.data?.scheduleResource != null && it.data.scheduleResource!!.schedule != null) {
                    refreshSchedules(it.data.scheduleResource!!.schedule!!)
                } else {
                    refreshSchedules(ArrayList())

                    Toast.makeText(context!!, "No  Schedules found", Toast.LENGTH_LONG).show()
                }

            } else if (it.status == Status.ERROR) {
                Toast.makeText(context!!, "No  Schedules found", Toast.LENGTH_LONG).show()

                refreshSchedules(ArrayList())
            }

        })
        viewModel?.originAirportData?.observe(this, Observer {

            if (it != null) {

                edt_origin.setText(it.names?.name?.`$`)

                originAirport = it
                refreshSchedules()


            }
        })
        viewModel?.destinationAirportData?.observe(this, Observer {

            if (it != null) {

                edt_destination.setText(it.names?.name?.`$`)

                destinationAirport = it

                refreshSchedules()
            }
        })

        oringin_view.setOnClickListener { selectOrigin() }

        destination_view.setOnClickListener { selectDestination() }

        edt_origin.setOnClickListener { selectOrigin() }

        edt_destination.setOnClickListener { selectDestination() }


        edt_date_.setOnClickListener { selectDate() }


        edt_date_.setText(DateTimeUtils().today)


        showPlacePicker()
    }

    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val VERIFY_DATE_FORMAT = "yyyy-MM-dd"
        val cal = Calendar.getInstance()

        val dpd =
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(
                    VERIFY_DATE_FORMAT, Locale.US
                )
                edt_date_?.setText(sdf.format(cal.time))


            }, year, month, day)

        dpd.show()
    }

    private fun refreshSchedules() {


        if (originAirport == null) {
            Toast.makeText(context!!, "Select origin airport", Toast.LENGTH_LONG).show()
            return
        }
        if (destinationAirport == null) {
            Toast.makeText(context!!, "Select destination airport", Toast.LENGTH_LONG).show()
            return
        }
        schedules = ArrayList()
        viewModel?.schedules(
            ScheduleSearch(
                origin = originAirport!!.airportCode!!,
                destination = destinationAirport!!.airportCode!!,
                fromDateTime = edt_date_.text.toString()
            )
        )
    }

    private fun selectDestination() {

        var fragment = AirportFragment()
        fragment
            .arguments = Bundle().apply {
            putSerializable("TYPE", 2)
        }
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack("SUB")
            .commit()


    }

    private fun selectOrigin() {
        var fragment = AirportFragment()
        fragment
            .arguments = Bundle().apply {
            putSerializable("TYPE", 1)
        }
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack("sub")
            .commit()


    }

    private fun refreshSchedules(schedules: List<Schedule>) {


        this.schedules = schedules
        schedulesListAdapter?.update(schedules)

    }


    private fun initSchedules() {

        schedulesListAdapter = ScheduleListAdapter(context!!, schedules, object : OnclickRecyclerListener {
            override fun onClickListener(position: Int) {


                var schedule = schedules!!.get(position)
                val intent = Intent(activity, MapActivity::class.java)
                intent.putExtra(Const.SCHEDULE, schedule)
                intent.putExtra(Const.ORIGIN, originAirport)
                intent.putExtra(Const.DESTINANTION, destinationAirport)
                startActivity(intent)


            }

            override fun onLongClickListener(position: Int) {

            }
        })

        val linearLayoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = schedulesListAdapter


    }

    private fun showPlacePicker() {

        Toast.makeText(context!!, "Choose Origin and Destination Airports", Toast.LENGTH_LONG).show()

    }
}
