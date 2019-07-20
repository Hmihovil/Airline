package com.kogicodes.airline.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.kogicodes.airline.R
import com.kogicodes.airline.models.airports.Airport
import com.kogicodes.airline.models.schedules.Flight
import com.kogicodes.airline.models.schedules.Schedule
import com.kogicodes.airline.utils.Const
import com.kogicodes.airline.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.toolback_back.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
        gMap = p0
        gMap?.isTrafficEnabled = true


    }

    private fun setUpPolygon() {


        val departureLatLng = LatLng(
            airportModelOrigin?.position?.coordinate?.latitude!!,
            airportModelOrigin?.position?.coordinate?.longitude!!
        )
        gMap?.addMarker(
            MarkerOptions().position(departureLatLng).title(airportModelOrigin?.names?.name?.`$`).icon(
                ViewUtils.bitmapDescriptorFromVector(this, R.drawable.ic_fly_l)
            )
        )

        val arrivalLatLng = LatLng(
            airportModelDestination?.position?.coordinate?.latitude!!,
            airportModelDestination?.position?.coordinate?.longitude!!
        )
        gMap?.addMarker(
            MarkerOptions().position(arrivalLatLng).title(airportModelDestination?.names?.name?.`$`).icon(
                ViewUtils.bitmapDescriptorFromVector(this, R.drawable.ic_land_l)
            )
        )



        if (airportModels == null) {
            airportModels = ArrayList()

        }


        if (airportModels != null) {

            val decodedPath: MutableList<LatLng> = ArrayList()
            for (airport in airportModels as List<Airport>) {
                val position =
                    LatLng(airport.position?.coordinate?.latitude!!, airport.position?.coordinate?.longitude!!)

                if (airport.airportCode != airportModelDestination!!.airportCode && airport.airportCode != airportModelOrigin!!.airportCode) {
                    gMap?.addMarker(MarkerOptions().position(position).title(airport.names?.name?.`$`))

                }

                decodedPath.add(position)
            }
            gMap?.addPolyline(
                PolylineOptions().width(15f).clickable(true).geodesic(true).color(Color.BLUE).addAll(
                    decodedPath
                )
            )

        }

        val latLngBounds = LatLngBounds.Builder()
            .include(departureLatLng)
            .include(arrivalLatLng)
            .build()

        val zoomPadding = 200
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        gMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, zoomPadding))


    }

    private var schedule: Schedule? = null
    private var airportModelOrigin: Airport? = null
    private var airportModelDestination: Airport? = null


    var originMarker: Marker? = null
    var destinationMarker: Marker? = null
    var gMap: GoogleMap? = null
    internal var mapFragment: SupportMapFragment? = null

    private var viewModel: MainViewModel? = null

    private var airportModels: MutableList<Airport>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        linear_back.setOnClickListener { finish() }
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)


        val intent = intent
        if (intent != null) {
            schedule = intent.getSerializableExtra(Const.SCHEDULE) as Schedule
            airportModelOrigin = intent.getSerializableExtra(Const.ORIGIN) as Airport
            airportModelDestination = intent.getSerializableExtra(Const.DESTINANTION) as Airport


            val flights: List<Flight>? = schedule?.flight
            if (flights != null) {

                val airports: MutableList<String?>? = ArrayList()
                for (flight in flights) {

                    if (flight != null) {
                        airports?.add(flight.departure?.airportCode)
                        airports?.add(flight.arrival?.airportCode)
                    }

                }

                if (airports != null) {
                    fetchAirtports(airports)
                }
            }

        }

        viewModel?.observeAirport()?.observe(this, Observer {

            ViewUtils.setStatus(this, view, it.status, it.message, false, ViewUtils.ErrorViewTypes.TOAST, it.exception)

            if (it.status == Status.SUCCESS) {


                if (it.data != null) {
                    for (aportmodel in it.data)

                        airportModels?.add(aportmodel.airportResource!!.airports!!.airport!!)

                }





                setUpPolygon()

            }


        })


    }

    private fun fetchAirtports(airports: MutableList<String?>) {

        viewModel?.airport(airports)


    }
}
