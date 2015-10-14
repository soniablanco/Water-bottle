package co.com.llanteria.www.llanteria;

import android.support.v4.app.Fragment;

/**
 * Created by soni on 14/10/2015.
 */
public class LocationsActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return LocationsFragment.newInstace();
    }
}
