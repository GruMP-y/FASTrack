package com.weserv.grumpy.assetracker.UI;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.weserv.grumpy.assetracker.Core.Asset;
import com.weserv.grumpy.assetracker.Core.AssetAdapter;
import com.weserv.grumpy.assetracker.Core.AssetItem;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.Core.MISTransactions;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MISAcceptanceFragment extends Fragment
        implements Observer
{

    android.support.design.widget.FloatingActionButton acceptButton;
    Asset theAsset = new Asset();
    MISTransactions misTransactions=new MISTransactions();

    private ProgressDialog myProgress;

    public MISAcceptanceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        myProgress = new ProgressDialog(this.getContext());
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_misacceptance, container, false);


        acceptButton = ( android.support.design.widget.FloatingActionButton ) view.findViewById(R.id.button_mis_accept);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_mis_acceptance);

        MyApp app = (MyApp) getActivity().getApplication();
        JSONArray jsonMyAsset = app.getSession().getMisTransactions().getForAcceptance();

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

        acceptButton.setOnClickListener(new View.OnClickListener() {
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
                Log.d(Common.LOGNAME, "MISAcceptance Asset tag from Intent ->" + assetTag);

                acceptAssignment(assetTag);

            }
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,ex.toString());
        }
    }

    private void acceptAssignment(String assetTag){

        try {

            misTransactions = ((MyApp) this.getActivity().getApplication()).getSession().getMisTransactions();

            JSONObject theJSONAsset;
            theJSONAsset = misTransactions.getForAcceptanceByAssetTag(assetTag);

            if (theJSONAsset != null) {

                theAsset = new Asset(theJSONAsset);
                theAsset.addObserver(this);
                if (!myProgress.isShowing()) {
                    myProgress.show();
                }

                theAsset.acceptAsset(this.getActivity(), (MyApp) this.getActivity().getApplication());

            } else {
                Toast.makeText(this.getActivity().getApplicationContext(), "Asset Not Found", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,"MIS For Acceptance error ->" + ex.toString());
        }
    }

    @Override
    public void update(Observable observable, Object o) {

        try {
            if (observable instanceof Asset) {
                if (o == null) {
                    Log.d(Common.LOGNAME, "Asset Accepted");
                    //Toast.makeText(this.getContext(),"Accept Successful",Toast.LENGTH_LONG).show();
                } else {
                    Log.d(Common.LOGNAME, "Error Accepting Asset");
                    //Toast.makeText(this.getContext(),"Accept Unsuccessful. Please try again.",Toast.LENGTH_LONG).show();
                }
            }


            if (myProgress.isShowing()) {
                myProgress.hide();
            }

            ((MainScreenActivity) getActivity()).loadData();
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,ex.toString());
        }
        //onStart();
    }
}

