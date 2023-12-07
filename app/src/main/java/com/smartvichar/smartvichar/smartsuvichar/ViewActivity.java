package com.smartvichar.smartvichar.smartsuvichar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.smartvichar.smartvichar.smartsuvichar.Utility.UtilityHelper;
import com.smartvichar.smartvichar.smartsuvichar.adaper.MainListAdapter;
import com.smartvichar.smartvichar.smartsuvichar.model.SplashData;
import com.smartvichar.smartvichar.smartsuvichar.network.HttpGet;
import com.smartvichar.smartvichar.smartsuvichar.network.NetCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;

public class ViewActivity extends AppCompatActivity implements NetCallback {
    private RecyclerView recyclerView;
    private ArrayList<SplashData> list = new ArrayList<>();
    private final HttpGet httpGet = new HttpGet();
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        recyclerView = findViewById(R.id.recycle);
        progress = findViewById(R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        sendRequest();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setRecyclerView(String response){

        try {
            JSONArray jsonArray = new JSONArray(response);
            ArrayList<SplashData> listdata = new ArrayList<>();
            for(int i=0;i < jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                SplashData data = new SplashData();
                data.setTitle(jsonObj.getString("title"));
                data.setUrl(null);
                listdata.add(data);
            }
            MainListAdapter mainListAdapter = new MainListAdapter(getApplicationContext(),listdata,R.layout.custom_txt_view);
            recyclerView.setAdapter(mainListAdapter);
            recyclerView.setHasFixedSize(true);

        } catch (JSONException e) {
            e.printStackTrace();
          runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UtilityHelper.showToast(getApplicationContext(),e.toString());
                    showBox("Json Error, try again !");
                }
            });
        }
    }

    public void sendRequest(){
        if(UtilityHelper.isConnected(getApplicationContext())){
            httpGet.setOnNetWorkListener(this);
            httpGet.request_get(getIntent().getExtras().getString("url"),"data");
            progress.setVisibility(View.VISIBLE);
        }else{
            showBox("Check Internet, Error!");
            progress.setVisibility(View.GONE);
        }

    }

    @Override
    public void onFailure(Request request, IOException e, String tag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                UtilityHelper.showToast(getApplicationContext(),e.toString());
                showBox("Network Failure Error, try again !");
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponse(Response response, String tag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
                try {
                    String res = response.body().string();
                    setRecyclerView(res);
                }catch (Exception e){
                    showBox("Response Error, try again !");
//                    UtilityHelper.showToast(getApplicationContext(),e.toString());
            }
            }
        });
    }

    @Override
    public void customError(String str, String tag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
                showBox("Custom Error, try again !");
//                UtilityHelper.showToast(getApplicationContext(),str);
            }
        });
    }

    private void showBox(String message){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sendRequest();
                    }
                });

        builder1.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                         dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}