package br.com.brqtest.viewkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.brqtest.R
import br.com.brqtest.modelkotlin.Endereco
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private var mMap: GoogleMap? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var endereco: Endereco? = null
    private var localizacao: LatLng? = null

    private val enderecoString: String
        get() {

            val enderecoFull = StringBuilder()
            enderecoFull.append(endereco!!.logradouro)
            enderecoFull.append(", ")
            if (endereco!!.numero != null && endereco!!.numero!!.length > 0) {
                enderecoFull.append(endereco!!.numero)
                enderecoFull.append(", ")
            }
            enderecoFull.append(endereco!!.bairro)
            enderecoFull.append(", ")
            enderecoFull.append(endereco!!.cidade)
            enderecoFull.append(", ")
            enderecoFull.append(endereco!!.uf)

            return enderecoFull.toString()
        }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (intent.extras != null && intent.extras!!.containsKey(ENDERECO)) {
            endereco = intent.getSerializableExtra(ENDERECO) as Endereco
        } else {
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .build()


    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            mGoogleApiClient!!.disconnect()
        }
        super.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap!!.isMyLocationEnabled = true
        } else {
            requestPermissions()
        }

        val geocoder = Geocoder(this)
        try {
            val enderecos = geocoder.getFromLocationName(enderecoString, 1)
            if (!enderecos.isEmpty()) {
                val address = enderecos[0]
                localizacao = LatLng(address.latitude, address.longitude)
            } else {

                Toast.makeText(this, "Não foi possivel encontrar o endereço no Mapa", Toast.LENGTH_LONG).show()
                finish()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mMap!!.addMarker(MarkerOptions().position(localizacao!!).title("Localização"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
        mMap!!.setMaxZoomPreference(17.8f)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 17.8f))
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_FINE_LOCATION
        )
    }

    override fun onConnected(bundle: Bundle?) {
        obterLocalizacao()
    }

    override fun onConnectionSuspended(i: Int) {
        mGoogleApiClient!!.connect()
    }

    private fun obterLocalizacao() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        } else {
            val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

            if (location != null) {
                atualizarMapa(LatLng(location.latitude, location.longitude))
            }
        }
    }

    private fun atualizarMapa(local: LatLng) {
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 17.0f))
        mMap!!.clear()
        mMap!!.addMarker(MarkerOptions().position(local).title("Local Atual!"))
    }

    override fun finish() {
        super.finish()
    }

    companion object {

        private val ENDERECO = "ENDERECO"

        private val REQUEST_FINE_LOCATION = 0
    }
}
