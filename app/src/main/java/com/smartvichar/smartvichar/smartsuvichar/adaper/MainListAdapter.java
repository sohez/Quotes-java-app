package com.smartvichar.smartvichar.smartsuvichar.adaper;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartvichar.smartvichar.smartsuvichar.R;
import com.smartvichar.smartvichar.smartsuvichar.Utility.UtilityHelper;
import com.smartvichar.smartvichar.smartsuvichar.ViewActivity;
import com.smartvichar.smartvichar.smartsuvichar.model.SplashData;


import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private ArrayList<SplashData> arrayList ;
    private Context context;
    private int layout;
    private int img[] = {R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,R.drawable.bg4,R.drawable.bg5,R.drawable.bg6};

    public MainListAdapter(Context context, ArrayList<SplashData> arrayList, int layout){
        this.arrayList = arrayList;
        this.context  = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public MainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new MainListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(arrayList.get(position).getTitle());

        if(layout != R.layout.custom_txt_view) {
            holder.ll_bg.setBackgroundResource(img[UtilityHelper.randomNum()]);
        }

        holder.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getUrl() != null){
                    Intent i = new Intent();
                    i.putExtra("url",arrayList.get(position).getUrl());
                    i.setClass(context, ViewActivity.class);
                    context.startActivity(i);
                }
            }
        });

        holder.ll_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", holder.textView.getText());

                if (clipboardManager != null){
                    clipboardManager.setPrimaryClip(clipData);
                }
                UtilityHelper.showToast(context,"Copied !");
            }
        });

        holder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.putExtra(Intent.EXTRA_TEXT, holder.textView.getText());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private LinearLayout ll_bg, ll_share,ll_copy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            ll_bg = itemView.findViewById(R.id.ll_bg);
            ll_copy = itemView.findViewById(R.id.ll_copy);
            ll_share = itemView.findViewById(R.id.ll_share);
        }
    }
}
