package com.kogicodes.airline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kogicodes.airline.R
import com.kogicodes.airline.models.airports.Airport
import java.lang.ref.WeakReference
import java.util.*

class AirportListAdapter(
    private val context: Context,
    private var airportModels: List<Airport>?,
    private val recyclerListener: OnclickRecyclerListener
) : RecyclerView.Adapter<AirportListAdapter.ViewHolder>(), Filterable {
    private var orig: List<Airport>? = null
    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var oReturn: FilterResults = FilterResults()
                var results: MutableList<Airport>? = LinkedList()

                if (orig == null)
                    orig = airportModels
                if (constraint != null) {
                    if ((orig != null) and (orig!!.isNotEmpty())) {
                        for (g in orig!!) {
                            if (g.names?.name?.`$`?.toLowerCase()!!.contains(constraint.toString())) results?.add(g)
                        }
                    }
                    oReturn.values = results
                }
                return oReturn

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                airportModels = results?.values as List<Airport>
                notifyDataSetChanged()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.airport_item, parent, false)
        return ViewHolder(itemView, recyclerListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val airportModel = airportModels!![position]

        holder.txtAirportCode.text = airportModel.airportCode
        holder.txtAirportName.text = airportModel.names!!.name!!.`$`
        holder.txtCounrtyCode.text = airportModel.countryCode


    }

    override fun getItemCount(): Int {
        return if (airportModels != null) airportModels!!.size else 0
    }

    fun update(airports: List<Airport>?) {
        airportModels = airports


        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View, listener: OnclickRecyclerListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal var txtAirportCode: TextView
        internal var txtAirportName: TextView
        internal var txtCounrtyCode: TextView

        private val listenerWeakReference: WeakReference<OnclickRecyclerListener>


        init {
            listenerWeakReference = WeakReference(listener)


            txtAirportCode = itemView.findViewById(R.id.txt_airport_code)
            txtAirportName = itemView.findViewById(R.id.txt_airport_name)
            txtCounrtyCode = itemView.findViewById(R.id.txt_country_code)
            itemView.setOnClickListener(this)

        }


        override fun onClick(view: View) {
            listenerWeakReference.get()!!.onClickListener(adapterPosition)
        }
    }
}