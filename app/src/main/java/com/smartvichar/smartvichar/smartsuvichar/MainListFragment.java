package com.smartvichar.smartvichar.smartsuvichar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smartvichar.smartvichar.smartsuvichar.R;
import com.smartvichar.smartvichar.smartsuvichar.adaper.MainListAdapter;
import com.smartvichar.smartvichar.smartsuvichar.model.SplashData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainListFragment extends Fragment {
    private RecyclerView recyclerView;

    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        try {

            JSONArray jsonArray = new JSONArray(getActivity().getIntent().getExtras().getString("txt"));
            ArrayList<SplashData> splashData = new ArrayList<>();

            for(int i=0;i < jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                SplashData data = new SplashData();
                data.setTitle(jsonObj.getString("title"));
                data.setUrl(jsonObj.getString("url"));
                splashData.add(data);
            }
            MainListAdapter mainListAdapter = new MainListAdapter(view.getContext(),splashData,R.layout.custom_main_list);
            recyclerView.setAdapter(mainListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}