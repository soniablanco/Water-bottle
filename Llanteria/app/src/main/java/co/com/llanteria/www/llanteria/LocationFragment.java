package co.com.llanteria.www.llanteria;

import android.location.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by soni on 20/09/2015.
 */
public class LocationFragment extends SupportMapFragment {
    private android.location.Location mCurrentLocation;
    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private static final String TAG = "LocationFragment";

    public static LocationFragment newInstace(){
        return new LocationFragment();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        return v;
    }*/
}
