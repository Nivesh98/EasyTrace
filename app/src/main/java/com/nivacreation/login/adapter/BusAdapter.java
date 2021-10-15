package com.nivacreation.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivacreation.login.AboutBusActivity;
import com.nivacreation.login.R;
import com.nivacreation.login.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;
    private List<Bus> busList ;
    private Context context;

    public BusAdapter(Context context, ItemClickListener itemClickListener, List<Bus> busList) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.layoutInflater = LayoutInflater.from(context);
        this.busList = busList;
    }

    public interface ItemClickListener {
        void onBusItem(String name);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_bus, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, itemClickListener, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bus_id.setText(busList.get(position).getBusId());

        holder.bus_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onBusItem(busList.get(position).getBusId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button bus_id;


        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, BusAdapter countryCodeAdapter) {
            super(itemView);

            bus_id = itemView.findViewById(R.id.bus_id);

        }

    }
}
