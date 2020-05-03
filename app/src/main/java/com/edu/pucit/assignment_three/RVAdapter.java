package com.edu.pucit.assignment_three;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
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
        DataContainer dataContainer = (DataContainer)listRecyclerItem.get(position);
        holder.tv_title.setText(dataContainer.title);
        holder.tv_level.setText(dataContainer.level);
        holder.tv_info.setText(dataContainer.info);
        holder.btn_load.setText(dataContainer.buttonText);
        Picasso.get().load(baseUrl+dataContainer.imagePath).into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

}
