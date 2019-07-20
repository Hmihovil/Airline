package com.kogicodes.airline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kogicodes.airline.R
import com.kogicodes.airline.models.schedules.Schedule
import java.lang.ref.WeakReference

class ScheduleListAdapter(
    private val context: Context,
    internal var schedules: List<Schedule>?,
    private val recyclerListener: OnclickRecyclerListener
) : RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleListAdapter.ViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.shcedule_item, parent, false)
        return ViewHolder(itemView, recyclerListener)

    }

    override fun onBindViewHolder(holder: ScheduleListAdapter.ViewHolder, position: Int) {
        val schedule = schedules!![position]


        holder.txtDuration.text = "Duration : " + schedule.totalJourney!!.duration!!
        val flightListAdapter = FlightListAdapter(context, schedule.flight)

        val mStaggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        holder.recyclerView.layoutManager = mStaggeredLayoutManager
        holder.recyclerView.itemAnimator = DefaultItemAnimator()
        flightListAdapter.notifyDataSetChanged()
        holder.recyclerView.adapter = flightListAdapter


    }

    override fun getItemCount(): Int {
        return if (schedules != null) schedules!!.size else 0
    }

    fun update(fligts: List<Schedule>?) {
        schedules = fligts


        notifyDataSetChanged()
    }


    inner class ViewHolder internal constructor(itemView: View, listener: OnclickRecyclerListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var txtDuration: TextView
        internal var recyclerView: RecyclerView
        internal var cardView: CardView

        private val listenerWeakReference: WeakReference<OnclickRecyclerListener>


        init {
            listenerWeakReference = WeakReference(listener)


            cardView = itemView.findViewById(R.id.card)
            txtDuration = itemView.findViewById(R.id.txt_duration)
            recyclerView = itemView.findViewById(R.id.recyclerView_flights)
            itemView.setOnClickListener(this)
            recyclerView.setOnClickListener(View.OnClickListener { this.onClick(it) })
            cardView.setOnClickListener(this)
            recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    listenerWeakReference.get()?.onClickListener(adapterPosition)

                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                }
            })
        }


        override fun onClick(v: View) {

            listenerWeakReference.get()?.onClickListener(adapterPosition)
        }
    }
}