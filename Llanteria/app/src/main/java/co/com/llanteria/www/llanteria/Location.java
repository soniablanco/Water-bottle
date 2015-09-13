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
}
