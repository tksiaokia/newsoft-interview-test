package com.sean.newsoft.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.sean.newsoft.R;
import com.sean.newsoft.adapter.ListingAdapter;
import com.sean.newsoft.databinding.ActivityMainBinding;
import com.sean.newsoft.dialogs.EditListingBottomSheetDialog;
import com.sean.newsoft.enums.LocalData;
import com.sean.newsoft.local.SharedPreferenceHelper;
import com.sean.newsoft.model.Listing;
import com.sean.newsoft.model.UserToken;
import com.sean.newsoft.model.response.GeneralResponse;
import com.sean.newsoft.services.MainService;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ListingAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        SharedPreferenceHelper.init(this);
        setupAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            //clear the local storage
            SharedPreferenceHelper.getInstance().putValue(LocalData.userToken,null);

            //clear adapter data
            adapter.setListings(new ArrayList<Listing>());

            //go to login screen
            goToLoginActivity();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAdapter() {
        adapter = new ListingAdapter(this, new ListingAdapter.ListingAdapterCallback() {
            @Override
            public void onItemClicked(Listing listing) {
                String displayStr = String.format("%s : %.2f", listing.getName(), listing.getDistance());
                simpleToast(displayStr);
            }

            @Override
            public void onItemLongClicked(Listing listing) {
                showEditListingDialog(listing);
            }
        });
        binding.content.recycleView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.content.recycleView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        UserToken userToken = SharedPreferenceHelper.getInstance().getValue(LocalData.userToken);
        //if user not logged, launch login screen
        if (userToken == null) {
            goToLoginActivity();
        } else {
            apiGetListing(userToken);
        }
    }

    private void goToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //use might backpress and close login activity
        //if result code != login success, finish main activity too
        if(requestCode == 1 && resultCode != LoginActivity.LOGIN_SUCCESS){
            finish();
        }
    }

    private void apiGetListing(UserToken userToken) {
        MainService service = new MainService();
        service.getListing(userToken, new MainService.ListingResponseCallback() {
            @Override
            public void onGetListings(ArrayList<Listing> listings) {
                //simpleToast(new Gson().toJson(listings));
                if (adapter != null)
                    adapter.setListings(listings);
            }

            @Override
            public void onError(GeneralResponse response) {
                simpleToast(response.getMessage());
            }
        });
    }



    private void showEditListingDialog(Listing listing) {
        EditListingBottomSheetDialog mBottomSheetDialog = new EditListingBottomSheetDialog(this, listing, new EditListingBottomSheetDialog.EditListingBottomSheetDialogCallback() {
            @Override
            public void simpleToast(String msg) {
                MainActivity.this.simpleToast(msg);
            }

            @Override
            public void toggleLoading(boolean isShow) {
                MainActivity.this.toggleLoading(isShow);
            }

            @Override
            public void onListingUpdated() {
                //refresh recyclerview
                adapter.notifyDataSetChanged();
            }
        });

        mBottomSheetDialog.show();
    }

}
