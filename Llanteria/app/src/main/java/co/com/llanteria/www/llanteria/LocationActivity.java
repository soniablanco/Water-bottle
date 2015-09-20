package co.com.llanteria.www.llanteria;

import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by soni on 20/09/2015.
 */
public class LocationActivity extends SingleFragmentActivity {
    public static final String EXTRA_LOCATION_ID = "co.com.llanteria.www.location_id";
    public static final String EXTRA_CURRENT_LOCATION ="co.com.llanteria.www.current_location";
    @Override
    protected Fragment createFragment() {
        return LocationFragment.newInstace();
    }

    public static Intent newIntent(Context packageContext, UUID locationId, android.location.Location currentPosition){
        Intent intent = new Intent(packageContext,LocationActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID,locationId);
        intent.putExtra(EXTRA_CURRENT_LOCATION,currentPosition);
        return intent;
    }
}
