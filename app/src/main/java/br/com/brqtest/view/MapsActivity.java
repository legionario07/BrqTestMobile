package br.com.brqtest.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import br.com.brqtest.R;
import br.com.brqtest.model.Cliente;
import br.com.brqtest.model.Endereco;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Endereco endereco;
    private LatLng localizacao;

    private static final int REQUEST_FINE_LOCATION = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("ENDERECO")) {
            endereco = (Endereco) getIntent().getSerializableExtra("ENDERECO");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private String getEnderecoString() {

        StringBuilder enderecoFull = new StringBuilder();
        enderecoFull.append(endereco.getLogradouro());
        enderecoFull.append(", ");
        if (endereco.getNumero()!=null && endereco.getNumero().length()>0) {
            enderecoFull.append(endereco.getNumero());
            enderecoFull.append(", ");
        }
        enderecoFull.append(endereco.getBairro());
        enderecoFull.append(", ");
        enderecoFull.append(endereco.getCidade());
        enderecoFull.append(", ");
        enderecoFull.append(endereco.getUf());

        return enderecoFull.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            requestPermissions();
        }

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> enderecos = geocoder.getFromLocationName(getEnderecoString(),1);
            if(!enderecos.isEmpty()){
                Address address = enderecos.get(0);
                localizacao = new LatLng(address.getLatitude(), address.getLongitude());
            }else{

                Toast.makeText(this, "Não foi possivel encontrar o endereço no Mapa", Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(localizacao).title("Localização"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
        mMap.setMaxZoomPreference(17.8f);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localizacao,17.8f));
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        obterLocalizacao();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    private void obterLocalizacao() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location != null) {
                atualizarMapa(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }
    }

    private void atualizarMapa(LatLng local) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 17.0f));
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(local).title("Local Atual!"));
    }

    @Override
    public void finish() {
        super.finish();
    }
}
