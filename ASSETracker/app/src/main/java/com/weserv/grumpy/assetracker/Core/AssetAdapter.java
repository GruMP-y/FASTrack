package com.weserv.grumpy.assetracker.Core;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;
import com.weserv.grumpy.assetracker.Utils.Helper;

import java.util.ArrayList;

/**
 * Created by Vans on 3/28/2016.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder>{

    private ArrayList<AssetItem> assetItems;
    OnItemClickListener itemClickListener;
    private int callingView;


    public AssetAdapter(ArrayList<AssetItem> assetItems) {
        this.assetItems = assetItems;


        //this.callingView = aCallingView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AssetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asset_item,parent,false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        String brandModel;

        viewHolder.textAssetType.setText(assetItems.get(position).getAssetType());
        viewHolder.textSerial.setText(assetItems.get(position).getSerialNumber());
        viewHolder.textAssetTag.setText(assetItems.get(position).getAssetTag());

        brandModel = assetItems.get(position).getAssetBrandModel();
        viewHolder.textModelBrand.setText(brandModel);
        viewHolder.imageStatus.setText(brandModel.substring(0,1));

        viewHolder.imageStatus.setBackgroundColor(Helper.GetRandomColor(brandModel.substring(0,1)));
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position );
    }

    public void SetOnItemClickListener(final OnItemClickListener anItemClickListener){
        this.itemClickListener = anItemClickListener;
    }



    // inner class to hold a reference to each item of RecyclerView
    public  class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView textAssetType;
        public TextView textSerial;
        public TextView textAssetTag;
        public TextView textModelBrand;
        public TextView imageStatus;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textAssetType = (TextView) itemLayoutView.findViewById(R.id.asset_item_type_desc);
            textSerial = (TextView) itemLayoutView.findViewById(R.id.asset_item_serial);
            textAssetTag = (TextView) itemLayoutView.findViewById(R.id.asset_item_asset_tag);
            textModelBrand = (TextView) itemLayoutView.findViewById(R.id.asset_item_model_brand);
            imageStatus = (TextView) itemLayoutView.findViewById(R.id.asset_list_accent);
            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            if( itemClickListener != null){
                itemClickListener.onItemClick(view,getPosition());
            }

        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return assetItems.size();
    }

}
