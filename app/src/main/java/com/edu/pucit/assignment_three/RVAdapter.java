package com.edu.pucit.assignment_three;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
    private static final int PERM_CODE = 10;
    Context context;
    private List<Object> listRecyclerItem;
    String baseUrl="https://raw.githubusercontent.com/revolunet/PythonBooks/master/";

    public RVAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.my_layout, null);
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DataContainer dataContainer = (DataContainer) listRecyclerItem.get(position);
        holder.tv_title.setText(dataContainer.title);
        holder.tv_level.setText(dataContainer.level);
        holder.tv_info.setText(dataContainer.info);
        int length=dataContainer.url.length();
        final String extension = dataContainer.url.substring(length - 3);
        Picasso.get().load(baseUrl + dataContainer.imagePath).into(holder.iv_image);
        if(extension.equals("pdf") || extension.equals("zip")) {
            holder.btn_load.setText("DOWNLOAD");
            holder.btn_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ADDED EXTENSION PDF WITH TITLE TO OPEN IT IN PDF FORMAT FROM STORAGE
                    downloadFile(dataContainer.url, dataContainer.title+'.'+extension);
                }
            });
        }
        else
        {
            holder.btn_load.setText("READ ONLINE");
            holder.btn_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataContainer.url));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_image;
        public TextView tv_title;
        public TextView tv_level;
        public TextView tv_info;
        public Button btn_load;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image=itemView.findViewById(R.id.image);
            tv_title = itemView.findViewById(R.id.title);
            tv_level = itemView.findViewById(R.id.level);
            tv_info = itemView.findViewById(R.id.info);
            btn_load=itemView.findViewById(R.id.btnLoad);
        }
    }

    private void downloadFile(String url, String title)
    {
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("Time Remaining...");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
        manager.enqueue(request);
        Toast.makeText(context, "Downloading File..", Toast.LENGTH_SHORT).show();
    }

}
