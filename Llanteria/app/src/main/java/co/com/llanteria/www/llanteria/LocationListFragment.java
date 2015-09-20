package co.com.llanteria.www.llanteria;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by soni on 10/09/2015.
 */
public class LocationListFragment extends Fragment {

    private RecyclerView mLocationRecyclerView;
    private static final int VERTICAL_SPACE_DECORATION = 48;
    private static final int[] LINE_DIVIDER = new int[]{android.R.attr.listDivider};

    private LocationAdapter mAdapter;

    private GoogleApiClient mClient;
    private static final String TAG = "LocationListFragment";

    public static LocationListFragment newInstace(){
        return new LocationListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location_list, container, false);
        mLocationRecyclerView = (RecyclerView)v.findViewById(R.id.payment_recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE_DECORATION));
        mLocationRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LINE_DIVIDER));
        UpdateUI(0, 0);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    private class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Location mLocation;

        public TextView mLocationNameTextView;
        public TextView mLocationDistanceTextView;
        public TextView mLocationAddressTextView;

        public LocationHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mLocationNameTextView = (TextView)itemView.findViewById(R.id.list_item_location_name_text_view);
            mLocationDistanceTextView = (TextView)itemView.findViewById(R.id.list_item_location_distance_text_view);
            mLocationAddressTextView = (TextView)itemView.findViewById(R.id.list_item_location_address_text_view);
        }

        public void bindLocation(Location location)
        {
            mLocation = location;
            mLocationNameTextView.setText(mLocation.getName());
            mLocationDistanceTextView.setText(String.valueOf(mLocation.getDistanceString()));
            mLocationAddressTextView.setText(mLocation.getAddress());
        }

        @Override
        public void onClick(View v) {
            /*Toast.makeText(getActivity(),mLocation.getName() + " clicked", Toast.LENGTH_SHORT)
            .show();*/
            Intent intent = new Intent(getActivity(),LocationActivity.class);
            startActivity(intent);
        }
    }

    public void UpdateUI(double latitude, double longitude){
        LocationLab locationLab = LocationLab.get(getActivity());
        if(latitude == 0 && longitude == 0) {
            mAdapter = new LocationAdapter(locationLab.getLocations());
        }
        else{
            mAdapter = new LocationAdapter(locationLab.getLocations(latitude, longitude));
        }
        mLocationRecyclerView.setAdapter(mAdapter);
    }

    public void getLocation(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(android.location.Location location) {
                        Log.i(TAG, "My location: " + location);
                        UpdateUI(location.getLatitude(), location.getLongitude());
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_location_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_locate:
                getLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LocationAdapter extends RecyclerView.Adapter<LocationHolder>{
        private List<Location> mLocations;
        public LocationAdapter (List<Location> locations){
            mLocations = locations;
        }

        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater =LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_location,parent,false);
            return new LocationHolder(view);
        }

        @Override
        public void onBindViewHolder(LocationHolder locationHolder, int position) {
            Location location = mLocations.get(position);
            //locationHolder.mLocationNameTextView.setText(location.getName());
            locationHolder.bindLocation(location);
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context,int[] divider) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(divider);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}


