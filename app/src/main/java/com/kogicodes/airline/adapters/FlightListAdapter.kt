package com.kogicodes.airline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kogicodes.airline.R
import com.kogicodes.airline.models.schedules.Flight
import com.kogicodes.airline.utils.DateTimeUtils

class FlightListAdapter(private val context: Context, internal var flights: List<Flight>?) :
    RecyclerView.Adapter<FlightListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightListAdapter.ViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FlightListAdapter.ViewHolder, position: Int) {
        val flight = flights!![position]

        if (flight.arrival != null && flight.arrival!!.scheduledTimeLocal != null) {

            //DateFormat.getInstance().format(flight.getDeparture().getScheduledTimeLocal().getDateTime());  //("dd/MM/yy")

            val date = DateTimeUtils().getDateAndTime(flight.arrival!!.scheduledTimeLocal!!.dateTime!!)
            holder.txtTime2.text = "Arrival : $date"
        } else {
            holder.txtTime2.text = ""
        }



        if (flight.departure != null && flight.departure!!.scheduledTimeLocal != null) {

            val date = DateTimeUtils().getDateAndTime(flight.departure!!.scheduledTimeLocal!!.dateTime!!)
            holder.txtTime1.text = "Departure : $date"

        } else {
            holder.txtTime1.text = ""
        }








        if (flight.departure != null && flight.departure!!.airportCode != null) {

            holder.txtCodeDepature.text = flight.departure!!.airportCode
        } else {
            holder.txtCodeDepature.text = ""
        }

        if (flight.arrival != null && flight.arrival!!.airportCode != null) {

            holder.txtCodeArrival.text = flight.arrival!!.airportCode
        } else {
            holder.txtCodeArrival.text = ""
        }

        if (flight.equipment != null && flight.equipment!!.aircraftCode != null) {

            holder.txtFlightDepature.text = "Craft " + flight.equipment!!.aircraftCode!!
        } else {
            holder.txtFlightDepature.text = ""
        }


        if (flight.arrival != null && flight.arrival!!.terminal!!.name != null) {

            holder.txtFlightArrival.text = "Terminal " + flight.arrival!!.terminal!!.name!!
        } else {
            holder.txtFlightArrival.text = ""
        }


    }

    override fun getItemCount(): Int {
        return if (flights != null) flights!!.size else 0
    }

    fun update(fligts: List<Flight>?) {
        this.flights == flights

        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txtTime1: TextView
        internal var txtTime2: TextView
        internal var txtCodeDepature: TextView
        internal var txtCodeArrival: TextView
        internal var txtFlightDepature: TextView
        internal var txtFlightArrival: TextView


        init {


            txtTime1 = itemView.findViewById(R.id.txt_time_1)
            txtTime2 = itemView.findViewById(R.id.txt_time_2)
            txtCodeArrival = itemView.findViewById(R.id.txt_code_arrival)
            txtCodeDepature = itemView.findViewById(R.id.txt_code_departure)
            txtFlightArrival = itemView.findViewById(R.id.txt_flight_arrival)
            txtFlightDepature = itemView.findViewById(R.id.txt_flight_depature)
        }


    }
}