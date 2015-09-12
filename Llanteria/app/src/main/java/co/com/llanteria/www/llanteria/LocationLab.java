package co.com.llanteria.www.llanteria;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by soni on 10/09/2015.
 */
public class LocationLab {
    private static LocationLab sLocationLab;
    private List<Location> mLocations;

    public static LocationLab get(Context context){
        if(sLocationLab == null){
            sLocationLab = new LocationLab(context);
        }
        return sLocationLab;
    }

    private LocationLab(Context context){
        mLocations = new ArrayList<>();
        Location loc = new Location();
        loc.setName("Citywest hotel");
        loc.setAddress("Citywest Hotel, Saggart, Co. Dublin, Ireland");
        loc.setLatitude(53.288103);
        loc.setLongitude(-6.449698);
        loc.setDistance(1.4);//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Aldi");
        loc.setAddress("Belgard Road, Tallagth, Dublin 24");
        loc.setLatitude(53.299989);
        loc.setLongitude(-6.373360);
        loc.setDistance(6.2);//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("The Square");
        loc.setAddress("3, The Square, Tallaght, Dublin 24");
        loc.setLatitude(53.286863);
        loc.setLongitude(-6.371434);
        loc.setDistance(6.9);//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Dp Systems");
        loc.setAddress("Unit A13, Calmount Park, Ballymount, Dublin 12");
        loc.setLatitude(53.312688);
        loc.setLongitude(-6.345136);
        loc.setDistance(10.7);//km
        mLocations.add(loc);

    }

    public List<Location> getLocations(){
        return mLocations;
    }

    public Location getLocation(UUID uuid){
        for(Location loc : mLocations){
            if(loc.getId().equals(uuid)){
                return loc;
            }
        }
        return null;
    }
}
