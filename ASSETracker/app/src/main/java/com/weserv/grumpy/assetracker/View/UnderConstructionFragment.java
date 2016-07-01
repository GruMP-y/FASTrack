package com.weserv.grumpy.assetracker.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weserv.grumpy.assetracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnderConstructionFragment extends BaseFragment {


    public UnderConstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_under_construction, container, false);
    }


}
