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
import java.util.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MGRApprovalsFragment extends BaseFragment{

    public MGRApprovalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mgrapprovals, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_mgr_approvals);

        MyApp app = (MyApp) getActivity().getApplication();
        final JSONArray jsonMyAsset = app.getSession().getMgrTransactions().getForAcceptance();

        JSONObject jsonAsset;

        final ArrayList<AssetItem> myAssets = new ArrayList<AssetItem>();
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
                try {

                    Bundle params = new Bundle();

                    params.putInt(Common.PARAM_BNDL_SRC,Common.SRC_MGRAPPROVALS);
                    params.putString(Common.PARAM_BNDL_DATA_01, myAssets.get(position).getAssetTag());
                    params.putString(Common.PARAM_BNDL_DATA_02,myAssets.get(position).getAssetStatus());

                    Intent intent = new Intent(getActivity(),ApprovalsActivity.class);
                    intent.putExtra(MainScreenActivity.EXTRA_MESSAGE,params);
                    getActivity().startActivity(intent);

                } catch (Exception ex) {
                    Log.d(Common.LOGNAME, ex.toString());
                }
            }
        });


        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        super.update(observable,o);
    }

}
