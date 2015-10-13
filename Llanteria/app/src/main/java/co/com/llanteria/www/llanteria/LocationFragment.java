package co.com.llanteria.www.llanteria;

import android.location.*;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.*;
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
 * Created by soni on 20/09/2015.
 */
public class LocationFragment extends SupportMapFragment {

    private static final String TAG = "LocationFragment";
    private static final String DIALOG_DIRECTIONS = "DialogDirections";

    private android.location.Location mCurrentPosition;
    private Marker mMarker;
    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private Location mLocation;

    public static LocationFragment newInstace(){
        return new LocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID locationId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LocationActivity.EXTRA_LOCATION_ID);
        mLocation = LocationLab.get(getActivity()).getLocation(locationId);
        mCurrentPosition = (android.location.Location)getActivity().getIntent()
                .getParcelableExtra(LocationActivity.EXTRA_CURRENT_LOCATION);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        //getLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        if (mCurrentPosition != null) {
                            UpdateUI();
                        }
                    }

                });

            }

        });
    }

    public void getLocation(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(android.location.Location location) {
                        Log.i(TAG, "My location: " + location);
                        mCurrentPosition = location;
                        UpdateUI();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    public void UpdateUI(){
        if(mMap == null){
            return;
        }
        LatLng locationPoint = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        MarkerOptions locationMarker =  new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.llanteria_marker))
                .position(locationPoint).title(mLocation.getId().toString());
        LatLng myPositionPoint = new LatLng(mCurrentPosition.getLatitude(), mCurrentPosition.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPositionPoint);
        mMap.clear();
        mMarker = mMap.addMarker(myMarker);
        mMap.addMarker(locationMarker);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPositionPoint)
                .include(locationPoint)
                .build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds,margin);//, margin
        mMap.animateCamera(update);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if (mCurrentPosition != null) {
            UpdateUI();
        }*/
    }

/*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        return v;
    }*/
}
