package com.sean.newsoft.dialogs;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.sean.newsoft.R;
import com.sean.newsoft.databinding.EditListingBottomSheetBinding;
import com.sean.newsoft.enums.LocalData;
import com.sean.newsoft.interfaces.SimpleUIInterface;
import com.sean.newsoft.localdb.SharedPreferenceHelper;
import com.sean.newsoft.model.Listing;
import com.sean.newsoft.model.UserToken;
import com.sean.newsoft.model.response.GeneralResponse;
import com.sean.newsoft.services.MainService;

public class EditListingBottomSheetDialog extends BottomSheetDialog {
    public interface EditListingBottomSheetDialogCallback extends SimpleUIInterface {
        void onListingUpdated();
    }

    private EditListingBottomSheetBinding binding;
    private Listing listing;
    private EditListingBottomSheetDialogCallback callback;
    public EditListingBottomSheetDialog(@NonNull Context context, Listing listing,EditListingBottomSheetDialogCallback callback) {
        super(context);
        this.listing = listing;
        this.callback = callback;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.edit_listing_bottom_sheet, null, false);
        setContentView(binding.getRoot());

        bindUI(listing);
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateClicked();
            }
        });
    }

    private void bindUI(Listing listing) {
        binding.txtListName.setText(listing.getName());
        binding.txtDistance.setText(listing.getDistanceString());

    }

    private void onUpdateClicked() {
        String name = binding.txtListName.getText().toString();
        String distanceStr = binding.txtDistance.getText().toString();
        double distance = 0;
        try {
            distance = Double.parseDouble(distanceStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        apiUpdateListing(listing.getId(),name,distance);

    }

    private void apiUpdateListing(String id, final String name, final double distance){
        callback.toggleLoading(true);
        MainService service = new MainService();
        service.updateListing((UserToken) SharedPreferenceHelper.getInstance().getValue(LocalData.userToken), id, name, distance, new MainService.UpdateListingResponseCallback() {
            @Override
            public void onSuccessUpdateListing(String message) {
                callback.toggleLoading(false);
                callback.simpleToast(message);

                //now only update the listing object, references issue.
                listing.setName(name);
                listing.setDistance(distance);

                if(callback != null) {
                    callback.onListingUpdated();
                }
                dismiss();

            }

            @Override
            public void onError(GeneralResponse response) {
                callback.toggleLoading(false);
                callback.simpleToast(response.getMessage());
            }
        });
    }
}
