package co.com.llanteria.www.llanteria;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by soni on 10/09/2015.
 */
public class LocationListFragment extends Fragment {

    private RecyclerView mLocationRecyclerView;

    private static final int VERTICAL_SPACE_DECORATION = 48;
    private static final int[] LINE_DIVIDER = new int[]{android.R.attr.listDivider};

    private LocationAdapter mAdapter;

    public static LocationListFragment newInstace(){
        return new LocationListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location_list, container, false);
        mLocationRecyclerView = (RecyclerView)v.findViewById(R.id.payment_recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE_DECORATION));
        mLocationRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LINE_DIVIDER));
        UpdateUI();
        return v;
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
            mLocationDistanceTextView.setText(String.valueOf(mLocation.getDistance()));
            mLocationAddressTextView.setText(mLocation.getAddress());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),mLocation.getName() + " clicked", Toast.LENGTH_SHORT)
            .show();
        }
    }

    public void UpdateUI(){
        LocationLab locationLab = LocationLab.get(getActivity());
        mAdapter = new LocationAdapter(locationLab.getLocations());
        mLocationRecyclerView.setAdapter(mAdapter);
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


