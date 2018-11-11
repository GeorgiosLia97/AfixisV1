package com.example.liapi_000.afixisv1;
//package com.example.bright.trackmenew;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class Maps extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnPoiClickListener{


    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    Marker [] onMarkers = new Marker[3];
    LatLng [] onLangs = new LatLng[3];
    String [] onDescrpts = new String[3];

    int[] tables = new int[11] ;
    Random generator = new Random();
    Premise [] p = new Premise[3];
    double lat = 0;
    double lang = 0;

    LocationListener locationListener;
    private FusedLocationProviderClient mFusedLocationClient;
  //  protected GeoDataClient mGeoDataClient;

    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // Used for selecting the current place.



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        for(int i=0; i < 11; i++)
        {
            tables[i] = generator.nextInt(2);
        }

        lat = 40.59483;
        lang = 22.95277;
         onLangs[0] = new LatLng(lat, lang);
         p[0] = new Premise("1","onoma1", tables,  onLangs[0] );

        for(int i=0; i < 11; i++)
        {
            tables[i] = generator.nextInt(3);
        }
         lat = 40.59484;
         lang = 22.95002;
        onLangs[1] = new LatLng(lat, lang);
         p[1] = new Premise("2","onoma2", tables,  onLangs[1]);

        for(int i=0; i < 11; i++)
        {
            tables[i] = generator.nextInt(3);
        }
         lat = 40.59415;
         lang = 22.95106;
        onLangs[2] = new LatLng(lat, lang);
         p[2] = new Premise("3","onoma3", tables,onLangs[2]  );





        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfd);
/*
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        */

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

/*
        protected void onActivityResult(int requestCode,int resultCode, Intent data) {
            if (requestCode == PLACE_PICKER_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    String toastMsg = String.format("Place: %s", place.getName() + "type: " , place.getAddress() + " address: ", place.getAddress() + " type2 " , place.getPlaceTypes());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
            }
        }

*/


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                Log.e("drinks", "onMapReady: run ok");
                //double latitude = 40.59392;
              double  latitude = location.getLatitude();
                //double longitude = 22.95184;
                double longitude = location.getLongitude();
                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses =
                            geocoder.getFromLocation(latitude, longitude, 1);
                    String result = addresses.get(0).getLocality() + ":";
                    result += addresses.get(0).getCountryName();
                    LatLng latLng = new LatLng(latitude, longitude);
                    if (marker != null) {
                        marker.remove();
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.setMaxZoomPreference(50);
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    } else {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.setMaxZoomPreference(50);
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }




              if( p[0].getStatus() == 1) {
                  onMarkers[0] = mMap.addMarker(new MarkerOptions().position(onLangs[0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(p[0].getName() + " Stat " +  p[0].getStatus() + " Tot " + p[0].getTotalTables() +  " Av " + p[0].getAvailable() + " Un " + p[0].getUnavailable()));
              } else    if( p[0].getStatus() == 0) {
                  onMarkers[0] = mMap.addMarker(new MarkerOptions().position(onLangs[0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(p[0].getName()+ " Stat " +  p[0].getStatus() + " Tot " + p[0].getTotalTables() +  " Av " + p[0].getAvailable() + " Un " + p[0].getUnavailable()));
                         }else if( p[0].getStatus() == 2) {
                    onMarkers[0] = mMap.addMarker(new MarkerOptions().position(onLangs[0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(p[0].getName()+ " Stat " +  p[0].getStatus() + " Tot " + p[0].getTotalTables() +  " Av " + p[0].getAvailable() + " Un " + p[0].getUnavailable()));
                }


                if( p[1].getStatus() == 1) {
                    onMarkers[1] = mMap.addMarker(new MarkerOptions().position(onLangs[1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(p[1].getName()+ " Stat "+ p[1].getStatus() + " Tot "+ p[1].getTotalTables()  +  " Av "+ p[1].getAvailable() + " Un "+ p[1].getUnavailable()));
                } else    if( p[1].getStatus() == 0) {
                    onMarkers[1] = mMap.addMarker(new MarkerOptions().position(onLangs[1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(p[1].getName()+ " Stat "+ p[1].getStatus()+ " Tot " + p[1].getTotalTables()  +  " Av "+ p[1].getAvailable() + " Un "+ p[1].getUnavailable()));
                }else if( p[1].getStatus() == 2) {
                    onMarkers[1] = mMap.addMarker(new MarkerOptions().position(onLangs[1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(p[1].getName()+ " Stat "+ p[1].getStatus()+ " Tot " + p[1].getTotalTables()  +  " Av "+ p[1].getAvailable() + " Un "+ p[1].getUnavailable()));
                }

                if( p[2].getStatus() == 1) {
                    onMarkers[2] = mMap.addMarker(new MarkerOptions().position(onLangs[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(p[2].getName()+ " Stat "+ p[2].getStatus()+ " Tot " + p[2].getTotalTables() +  " Av " + p[2].getAvailable()+ " Un " + p[2].getUnavailable()));
                } else    if( p[2].getStatus() == 0) {
                    onMarkers[2] = mMap.addMarker(new MarkerOptions().position(onLangs[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(p[2].getName()+ " Stat "+ p[2].getStatus()+ " Tot " + p[2].getTotalTables()  +  " Av "+ p[2].getAvailable() + " Un "+ p[2].getUnavailable()));
                }else if( p[2].getStatus() == 2) {
                    onMarkers[2] = mMap.addMarker(new MarkerOptions().position(onLangs[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(p[2].getName()+ " Stat "+ p[2].getStatus() + " Tot "+ p[2].getTotalTables()  +  " Av "+ p[2].getAvailable()+ " Un " + p[2].getUnavailable()));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);




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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnPoiClickListener(this);

       // Service service = new google.Map.places.PlacesService(mMap);

      /*  mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.activity_cfd,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.Toast));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.Toast));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });*/

        String provider = LocationManager.NETWORK_PROVIDER;

        if( locationManager.isProviderEnabled(provider)){

            Log.e( "drinks", "onMapReady: passive ok");
        } else {

            provider = LocationManager.GPS_PROVIDER;

            if( locationManager.isProviderEnabled(provider)){

                Log.e("drinks2", "onMapReady: gps ok");
            } else {
                provider = LocationManager.PASSIVE_PROVIDER;

                if( locationManager.isProviderEnabled(provider)){
                    Log.e("drinks3", "onMapReady: network ok" );
                }
            }


        }

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            @Override
            public void onRequestPermissionsResult(int requestCode,
            String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                        // If request is cancelled, the result arrays are empty.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // permission was granted, yay! Do the
                            // contacts-related task you need to do.
                        } else {
                            // permission denied, boo! Disable the
                            // functionality that depends on this permission.
                        }
                        return;
                    }

                    // other 'case' lines to check for other
                    // permissions this app might request.
                }
            }
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

      //Location la = locationManager.getLastKnownLocation(provider);
       //locationManager.requestLocationUpdates(provider, 1000, 10f, locationListener);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location != null) {

                            locationListener.onLocationChanged(location);
                        } else {
                            Log.e("drinks5", "onMapReady: null loc" );
                        }

                    }
                });

    //Location la =

                // Location lastKnownLocation = locationManager.getLastKnownLocation(locationListener.toString());

        //locationListener.onLocationChanged(la);

        /*double a = 0;
        double b = 0;

        a = loc.getLatitude();
        b = loc.getLongitude();

        LatLng here = new LatLng(a , b); */

        //  Add a marker in Sydney and move the camera
       /* LatLng here = new LatLng(40.595392, 22.951841);
       mMap.addMarker(new MarkerOptions().position(here).title("Marker here"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(here, 15));*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);

    }



    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        pointOfInterest.name + "\n Place ID:" + pointOfInterest.placeId
                 ,
                Toast.LENGTH_SHORT).show();
        ((TextView)findViewById(R.id.Toast)).setText( pointOfInterest.name);



    }
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }*/


}