package co.com.llanteria.www.llanteria;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        //loc.setDistance("1.4");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Aldi");
        loc.setAddress("Belgard Road, Tallagth, Dublin 24");
        loc.setLatitude(53.299989);
        loc.setLongitude(-6.373360);
        //loc.setDistance("6.2");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("The Square");
        loc.setAddress("3, The Square, Tallaght, Dublin 24");
        loc.setLatitude(53.286863);
        loc.setLongitude(-6.371434);
        //loc.setDistance("6.9");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Dp Systems");
        loc.setAddress("Unit A13, Calmount Park, Ballymount, Dublin 12");
        loc.setLatitude(53.312688);
        loc.setLongitude(-6.345136);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);

        //Bogota
        /*loc = new Location();
        loc.setName("Mc Donnalds");
        loc.setAddress("Cl. 125 #32-13 Bogotá,Colombia");
        loc.setLatitude(4.705730);
        loc.setLongitude(-74.053240);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Santafe");
        loc.setAddress("Calle 185 # 45-03, Bogotá,Colombia");
        loc.setLatitude(4.762977);
        loc.setLongitude(-74.045559);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("Unicentro");
        loc.setAddress("Carrera 15 con calle 127-23, Bogotá,Colombia");
        loc.setLatitude(4.701915);
        loc.setLongitude(-74.042317);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("El Campin");
        loc.setAddress("Carrera 30 y Calle 57, Bogotá,Colombia");
        loc.setLatitude(4.647245);
        loc.setLongitude(-74.077394);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);

        loc = new Location();
        loc.setName("La Candelaria");
        loc.setAddress("Calle 11 carrera 5, Bogotá,Colombia");
        loc.setLatitude(4.597937);
        loc.setLongitude(-74.074613);
        //loc.setDistance("10.7");//km
        mLocations.add(loc);*/
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

    public List<Location> getLocations(double latitude,double longitude){
        calculateDistanceToLocations(latitude,longitude);
        return mLocations;
    }

    private void sortByDistance(){
        Collections.sort(mLocations, new Comparator<Location>() {
            @Override
            public int compare(Location lhs, Location rhs) {
                int startComparison = compare(lhs.getDistance(), rhs.getDistance());
                return startComparison != 0 ? startComparison : compare(lhs.getDistance(), rhs.getDistance());
            }

            private int compare(double a, double b) {
                return a < b ? -1
                        : a > b ? 1
                        : 0;
            }
        });
    }

    private void calculateDistanceToLocations(double latitude,double longitude)
    {
        for (Location loc:mLocations) {
            loc.CalculateDistance(latitude, longitude);
        }
        sortByDistance();
    }
}
