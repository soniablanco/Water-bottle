package co.com.llanteria.www.llanteria;

import java.util.UUID;

/**
 * Created by soni on 10/09/2015.
 */
public class Location {
    private UUID mId;
    private String mName;
    private String mAddress;
    private double mDistance;
    private double mLatitude;
    private double mLongitude;

    public Location(){
        mId = UUID.randomUUID();
        mDistance = 0.0;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public double getDistance() {
        return mDistance;
    }

    public String getDistanceString() {
        return String.valueOf(mDistance) + " Km";
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void CalculateDistance(double latitude, double longitude){
        double mDistanceTo = 0.0;
        float[] results = new float[1];
        android.location.Location.distanceBetween(latitude,longitude,mLatitude,mLongitude,results);
        if(results != null) {
            if(results.length >= 1){
                mDistanceTo = results[0]/1000;//To return km
            }
        }
        double roundOff = (double) Math.round(mDistanceTo * 100) / 100;
        mDistance = roundOff;
    }
}
