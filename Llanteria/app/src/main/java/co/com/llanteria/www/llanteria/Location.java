package co.com.llanteria.www.llanteria;

import java.util.UUID;

/**
 * Created by soni on 10/09/2015.
 */
public class Location {
    private UUID mId;
    private String mName;
    private String mAddress;
    private String mDistance;
    private double mLatitude;
    private double mLongitude;

    public Location(){
        mId = UUID.randomUUID();
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

    public String getDistance() {
        return mDistance + " Km";
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
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

    public double CalculateDistance(double latitude, double longitude){
        double mDistanceTo = 0.0;
        float[] results = new float[1];
        android.location.Location.distanceBetween(latitude,longitude,mLatitude,mLongitude,results);
        if(results != null) {
            if(results.length > 1){
                mDistanceTo = results[0]/1000;//To return km
            }
        }
        return mDistanceTo;
    }
}
