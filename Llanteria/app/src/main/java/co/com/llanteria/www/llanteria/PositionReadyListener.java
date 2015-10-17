package co.com.llanteria.www.llanteria;

import android.location.*;

/**
 * Created by soni on 17/10/2015.
 */
public abstract class PositionReadyListener {
    public abstract void onPositionReady(android.location.Location myLocation);
}
