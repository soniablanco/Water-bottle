package co.com.llanteria.www.llanteria;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by soni on 29/09/2015.
 */
public class DirectionsDialogFragment extends DialogFragment {
    public static final String ARG_LOCATION = "destination";
    public static final String ARG_POSITION = "source";
    private Location mLocation;
    private android.location.Location mCurrentPosition;

    private TextView mLocationName;
    private TextView mLocationAddress;

    public static DirectionsDialogFragment newInstance(UUID uuid, android.location.Location currentPosition) {
        DirectionsDialogFragment f = new DirectionsDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        ParcelUuid parcelUuid = new ParcelUuid(uuid);
        args.putParcelable(ARG_LOCATION, parcelUuid);
        args.putParcelable(ARG_POSITION,currentPosition);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        mCurrentPosition = getArguments().getParcelable(ARG_POSITION);
        ParcelUuid locationId = getArguments().getParcelable(ARG_LOCATION);
        mLocation = LocationLab.get(getActivity()).getLocation(locationId.getUuid());
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_directions,null);

        mLocationName = (TextView)v.findViewById(R.id.dialog_directions_location_name_text_view);
        mLocationAddress = (TextView)v.findViewById(R.id.dialog_directions_location_address_text_view);

        mLocationName.setText(mLocation.getName());
        mLocationAddress.setText(mLocation.getAddress());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v);
        if(mCurrentPosition != null){
            builder.setPositiveButton(R.string.directions_dialog_navigate_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String uri = "http://maps.google.com/maps?saddr=" + Double.toString(mCurrentPosition.getLatitude()) + "," + Double.toString(mCurrentPosition.getLongitude())
                            + "&daddr=" + Double.toString(mLocation.getLatitude()) + "," + Double.toString(mLocation.getLongitude())+"&dirflg=d";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });
        }
        return builder.create();
    }
}
