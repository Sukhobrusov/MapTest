package ru.alexander.maptest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var api: PositionApiService
    private var list = ArrayList<LatLng>()
    private var polygon: Polygon? = null
    private var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        api = PositionApiService.create()


        disposable = api.search()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    val list = response.map { LatLng(it.lat, it.lng) }
                    addPointsToMap(list)
                },
                { error -> error.printStackTrace() }
            )

        /** Set long click listener for map, if user adds a marker we add its location to [list]
         * We'll add polygon only if there are more then 3 locations in [list] */
        mMap.setOnMapLongClickListener {
            val options = MarkerOptions()
            options.position(it).title("T")
            mMap.addMarker(options)
            list.add(it)
            polygon?.remove()
            if (list.size >= 3) {
                val rect = PolygonOptions()
                    .addAll(list)
                    .fillColor(Color.BLUE)
                    .strokeColor(Color.RED)

                polygon = mMap.addPolygon(rect)
            }
        }

        // remove marker if it's user-added marker
        mMap.setOnMarkerClickListener {
            if (it.title == "T") {
                polygon?.remove()
                list.remove(it.position)
                if (list.size >= 3) {
                    val rect = PolygonOptions()
                        .addAll(list)
                        .fillColor(Color.BLUE)
                        .strokeColor(Color.RED)
                    polygon = mMap.addPolygon(rect)
                }
                it.remove()
            }
            false
        }
    }


    // Function that adds whole list of LatLng to the GoogleMap as Markers
    // Used when we download convert JSON from API
    private fun addPointsToMap(list: List<LatLng>) {
        if (list.isEmpty())
            return
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.first()))
        list.forEach {
            val options = MarkerOptions()
            options.position(it).title("Test")
            mMap.addMarker(options)
        }
    }

}
