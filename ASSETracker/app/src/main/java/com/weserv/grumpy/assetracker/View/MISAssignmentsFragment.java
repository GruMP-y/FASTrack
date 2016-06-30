package com.weserv.grumpy.assetracker.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weserv.grumpy.assetracker.Core.AssetAdapter;
import com.weserv.grumpy.assetracker.Core.AssetItem;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MISAssignmentsFragment extends BaseFragment {

    android.support.design.widget.FloatingActionButton manageButton;

    public MISAssignmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_misassignments, container, false);

        manageButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.button_mis_manage);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_mis_assignments);

        MyApp app = (MyApp) getActivity().getApplication();
        JSONArray jsonMyAsset = app.getSession().getMisTransactions().getMyAssets();

        JSONObject jsonAsset;
        ArrayList<AssetItem> myAssets = new ArrayList<AssetItem>();
        try {
            for (int i = 0; i < jsonMyAsset.length(); i++) {
                jsonAsset = jsonMyAsset.getJSONObject(i);
                myAssets.add(new AssetItem(jsonAsset));
            }
        } catch (JSONException ex) {

        }

        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        AssetAdapter mAdapter = new AssetAdapter(myAssets);
        // 4. set adapter
        rv.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        rv.setItemAnimator(new DefaultItemAnimator());

        mAdapter.SetOnItemClickListener(new AssetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView t = (TextView) view.findViewById(R.id.asset_item_asset_tag);
                Log.i(Common.LOGNAME, " " + t.getText().toString());
            }
        });


        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquireAssetTag();
            }
        });

        return view;
    }

    private void acquireAssetTag(){
        Intent intent = new Intent(getActivity(),GetAssetTagActivity.class);
        startActivityForResult(intent, Common.RSPNS_CD_GET_ASSET_TAG);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Common.RSPNS_CD_GET_ASSET_TAG) {
                String assetTag = data.getStringExtra(Common.DATA);
                Log.d(Common.LOGNAME, "MIS Assignments Asset tag from Intent ->" + assetTag);

                //acceptAssignment(assetTag);
                manageAssignment(assetTag);
            }
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,ex.toString());
        }
    }

    public void manageAssignment(String anAssetTag){
        Log.d(Common.LOGNAME,"EXTRA MESSAGE -> "+ anAssetTag);

        Bundle params = new Bundle();
        params.putInt(Common.PARAM_BNDL_SRC,Common.SRC_MISASSETS);
        params.putString(Common.PARAM_BNDL_DATA_01, anAssetTag);

        Intent intent = new Intent(getActivity(),ManageAssetActivity.class);
        intent.putExtra(MainScreenActivity.EXTRA_MESSAGE,params);
        startActivity(intent);

    }


}
