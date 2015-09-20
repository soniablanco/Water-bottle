package co.com.llanteria.www.llanteria;

import android.support.v4.app.Fragment;

/**
 * Created by soni on 20/09/2015.
 */
public class LocationActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return LocationFragment.newInstace();
    }
}
