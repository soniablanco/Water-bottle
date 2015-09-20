package co.com.llanteria.www.llanteria;

import android.location.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

/**
 * Created by soni on 20/09/2015.
 */
public class LocationFragment extends SupportMapFragment {
    private android.location.Location mCurrentPosition;
    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private static final String TAG = "LocationFragment";
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
                if(mCurrentPosition != null) {
                    UpdateUI();
                }
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
                .position(locationPoint);
        LatLng myPositionPoint = new LatLng(mCurrentPosition.getLatitude(), mCurrentPosition.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPositionPoint);
        mMap.clear();
        mMap.addMarker(myMarker);
        mMap.addMarker(locationMarker);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPositionPoint)
                .include(locationPoint)
                .build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mMap.animateCamera(update);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        return v;
    }*/
}
