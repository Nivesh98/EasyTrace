package com.nivacreation.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivacreation.login.R;
import com.nivacreation.login.model.TransactionDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionAdapterDriver extends RecyclerView.Adapter<TransactionAdapterDriver.ViewHolder>{


    Context context, contextDriver;
    ArrayList<TransactionDetails> userSupportArrayList, userSupportArrayDriver;

    public TransactionAdapterDriver(Context context, ArrayList<TransactionDetails> userSupportArrayList){

        this.context = context;
        this.userSupportArrayList = userSupportArrayList;

    }

    @NonNull
    @NotNull
    @Override
    public TransactionAdapterDriver.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(context).inflate(R.layout.list_transaction_driver,parent,false);
        return new TransactionAdapterDriver.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionAdapterDriver.ViewHolder holder, int position) {
        TransactionDetails user = userSupportArrayList.get(position);

        holder.tvDate.setText(user.getTime());
        holder.tvStartEnd.setText(user.getStLocation()+" - "+user.getEnLocation());
        holder.tvCost.setText(String.valueOf(user.getPaid()));
        holder.tvRemain.setText(String.valueOf(user.getRemain()));
        holder.tvTransaction.setText(user.getUserId());

    }

    @Override
    public int getItemCount() {

        return userSupportArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvStartEnd, tvCost, tvTransaction, tvRemain;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvStartEnd = itemView.findViewById(R.id.tvStartEnd);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvTransaction = itemView.findViewById(R.id.passengerTransactionDriver);
            tvRemain = itemView.findViewById(R.id.tvRemain);

        }
    }
}
