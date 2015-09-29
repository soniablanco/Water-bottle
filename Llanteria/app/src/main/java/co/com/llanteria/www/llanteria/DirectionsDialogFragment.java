package co.com.llanteria.www.llanteria;

import android.app.Dialog;
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
    public static final String ARG_LOCATION = "directions";
    private Location mLocation;

    private TextView mLocationName;
    private TextView mLocationAddress;

    public static DirectionsDialogFragment newInstance(UUID uuid) {
        DirectionsDialogFragment f = new DirectionsDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        ParcelUuid parcelUuid = new ParcelUuid(uuid);
        args.putParcelable(ARG_LOCATION, parcelUuid);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        ParcelUuid locationId = getArguments().getParcelable(ARG_LOCATION);
        mLocation = LocationLab.get(getActivity()).getLocation(locationId.getUuid());
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_directions,null);

        mLocationName = (TextView)v.findViewById(R.id.dialog_directions_location_name_text_view);
        mLocationAddress = (TextView)v.findViewById(R.id.dialog_directions_location_address_text_view);

        mLocationName.setText(mLocation.getName());
        mLocationAddress.setText(mLocation.getAddress());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
