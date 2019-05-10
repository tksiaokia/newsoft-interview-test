package com.sean.newsoft.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sean.newsoft.R;
import com.sean.newsoft.databinding.ListingCellBinding;
import com.sean.newsoft.model.Listing;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder>  {
    public interface ListingAdapterCallback{
        void onItemClicked(Listing listing);
        void onItemLongClicked(Listing listing);
    }
    private Context mContext;
    private ArrayList<Listing> listings;
    private ListingAdapterCallback callback;


    public ListingAdapter(Context mContext,ListingAdapterCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    public void setListings(ArrayList<Listing> listings) {
        this.listings = listings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListingCellBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.listing_cell,viewGroup,false);
        ListingAdapter.ViewHolder vh = new ListingAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.clearData();//clear existing binded data
        if(listings != null && listings.size() > i){
            viewHolder.bindListing(listings.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return listings != null ? listings.size() : 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ListingCellBinding binding;

        public ViewHolder(ListingCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindListing(final Listing listing){
            binding.lblName.setText(listing.getName());
            binding.lblDistance.setText(listing.getDistanceString());

            binding.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callback != null)
                        callback.onItemClicked(listing);
                }
            });

            binding.cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(callback != null)
                        callback.onItemLongClicked(listing);
                    return true;
                }
            });
        }

        public void clearData(){
            binding.lblName.setText("");
            binding.lblDistance.setText("");
        }
    }
}
