package co.com.llanteria.www.llanteria;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.UUID;

/**
 * Created by soni on 17/10/2015.
 */
public class MyLocationManager {
    private  GoogleApiClient mClient;
    private PositionReadyListener mPositionReadyListener;
    private Context mContext;

    private MyLocationManager(Context context, final PositionReadyListener positionReadyListener){
        mContext = context;
        mPositionReadyListener = positionReadyListener;
        Initialise();
    }

    private void Initialise() {
        mClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
        mClient.connect();
    }

    public static void calculateCurrentPosition(Context context, final PositionReadyListener positionReadyListener){
        MyLocationManager manager = new MyLocationManager(context,positionReadyListener);
    }

    private void getLocation(){

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(android.location.Location location) {
                        mPositionReadyListener.onPositionReady(location);
                        mClient.disconnect();
                    }
                });
    }
}
