package co.com.llanteria.www.llanteria;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.UUID;

/**
 * Created by soni on 14/10/2015.
 */
public class LocationsFragment extends SupportMapFragment {

    private static final String DIALOG_DIRECTIONS = "DialogDirections";
    private GoogleApiClient mClient;
    private android.location.Location mCurrentPosition;
    private Marker mMarker;

    public static LocationsFragment newInstace(){
        return new LocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        final List<Location> locations = LocationLab.get(getActivity()).getLocations();
                        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            mClient = new GoogleApiClient.Builder(getActivity())
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                        @Override
                                        public void onConnected(Bundle bundle) {
                                            getLocation(googleMap, locations);
                                        }

                                        @Override
                                        public void onConnectionSuspended(int i) {

                                        }
                                    })
                                    .build();
                            mClient.connect();
                        } else {
                            UpdateUI(locations, googleMap, null);
                        }
                    }
                });
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(mMarker)) {
                            return true;
                        } else {
                            FragmentManager manager = getFragmentManager();
                            DirectionsDialogFragment dialog = DirectionsDialogFragment
                                    .newInstance(UUID.fromString(marker.getTitle()), mCurrentPosition);
                            dialog.show(manager, DIALOG_DIRECTIONS);
                            return true;
                        }
                    }
                });
            }
        });
    }

    public void UpdateUI(List<Location> locations,GoogleMap mMap, android.location.Location currentLocation)
    {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        mMap.clear();
        for(Location loc :locations){
            LatLng point = new LatLng(loc.getLatitude(),loc.getLongitude());

            MarkerOptions locationMarker =  new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.llanteria_marker))
                    .position(point).title(loc.getId().toString());

            mMap.addMarker(locationMarker);
            boundsBuilder.include(point);
        }
        if(currentLocation != null) {
            LatLng point = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions locationMarker = new MarkerOptions()
                    .position(point);

            mMarker = mMap.addMarker(locationMarker);
            boundsBuilder.include(point);
        }

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        LatLngBounds bounds = boundsBuilder.build();

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);//, margin
        mMap.animateCamera(update);
    }

    public  void getLocation(final GoogleMap map,final List<Location> locations){

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(android.location.Location location) {
                        mCurrentPosition = location;
                        UpdateUI(locations, map, location);
                        mClient.disconnect();
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
   }

    @Override
    public void onStop() {
        super.onStop();
    }
}
