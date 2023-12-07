package com.smartvichar.smartvichar.smartsuvichar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.smartvichar.smartvichar.smartsuvichar.Utility.UtilityHelper;
import com.smartvichar.smartvichar.smartsuvichar.network.HttpGet;
import com.smartvichar.smartvichar.smartsuvichar.network.NetCallback;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class SplashScreenActivity extends AppCompatActivity implements NetCallback {
    private final HttpGet httpGet = new HttpGet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }

        sendRequest();
    }

    public void sendRequest(){
        if(UtilityHelper.isConnected(getApplicationContext())){
            httpGet.setOnNetWorkListener(this);
            httpGet.request_get(getString(R.string.api),"splash");
        }else{
//            UtilityHelper.showToast(getApplicationContext(),"Check Network");
            showBox("Network Error");
        }
    }


    @Override
    public void onFailure(Request request, IOException e, String tag) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                UtilityHelper.showToast(getApplicationContext(),e.toString());
                showBox("Something Error, try again !");
            }
        });
    }

    @Override
    public void onResponse(Response response, String tag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = "null";
                    result = response.body().string();
                    Intent i = new Intent();
                    i.putExtra("txt",result);
                    i.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            UtilityHelper.showToast(getApplicationContext(),e.toString());
                            showBox("Server JSON Error, try again !");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void customError(String str, String tag) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                UtilityHelper.showToast(getApplicationContext(),str);
                showBox("Something Error, try again !");
            }
        });
    }
     private void showBox(String message){

         AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreenActivity.this);
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
                 "Exit",
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
//                         dialog.cancel();
                         finishAffinity();
                     }
                 });

         AlertDialog alert11 = builder1.create();
         alert11.show();
     }
}