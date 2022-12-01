package es.unex.giiis.medicinex.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import es.unex.giiis.medicinex.databinding.FragmentNearbyPharmaciesMapsBinding

class NearbyPharmaciesMapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    companion object
    {
        private const val LOCATION_REQUEST_CODE = 1
    }

    private lateinit var binding: FragmentNearbyPharmaciesMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var mView: MapView
    private var located: Boolean = false
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentNearbyPharmaciesMapsBinding.inflate(inflater, container, false)

        mView = binding.mapaa
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        binding.takeMeHome.setOnClickListener {
            takeMeHome()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }


    private fun takeMeHome()
    {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLong = LatLng(location.latitude, location.longitude)
                    placeMarkerOnMap(currentLatLong)
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(currentLatLong, 14f),
                        1000,
                        null
                    )
                }
            }
        }
    }

    private fun setUpMap()
    {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLong, 14f),
                    1000,
                    null
                )
            }
        }
    }


    private fun placeMarkerOnMap(currentLatLong: LatLng)
    {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")

        if(!located)
        {
            map.addMarker(markerOptions)
            located = true
        }
    }


    override fun onResume() {
        mView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }

    override fun onMarkerClick(p0: Marker) = false
}