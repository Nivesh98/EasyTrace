package com.nivacreation.login.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivacreation.login.R;
import com.nivacreation.login.model.TransactionDetails;
import com.nivacreation.login.model.UserSupport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
//
//    private LayoutInflater layoutInflater;
//    private ItemClickListener itemClickListener;
//    private List<TransactionDetails> transactionDetails ;
//    private Context context;
//
//    public TransactionAdapter(Context context, ItemClickListener itemClickListener, List<TransactionDetails> transactionDetails) {
//        this.context = context;
//        this.itemClickListener = (ItemClickListener) itemClickListener;
//        this.layoutInflater = LayoutInflater.from(context);
//        this.transactionDetails = transactionDetails;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = layoutInflater.inflate(R.layout.list_transaction, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view, itemClickListener, this);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        holder.tvCost.setText(transactionDetails.get(position).getCost());
//        holder.tvDate.setText(transactionDetails.get(position).getDate());
//        holder.tvStartEnd.setText(transactionDetails.get(position).getFromTo());
//
//        holder.transactionRecycler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemClickListener.onTransactionItemClicked(String.valueOf(position));
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return transactionDetails.size();
//    }
//
//
//    public interface ItemClickListener {
//        void onTransactionItemClicked(String name);
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        RelativeLayout transactionRecycler;
//        TextView tvDate, tvStartEnd, tvCost;
//
//        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, TransactionAdapter transactionAdapter) {
//            super(itemView);
//
//            transactionRecycler = itemView.findViewById(R.id.transactionRecycler);
//            tvDate = itemView.findViewById(R.id.tvDate);
//            tvStartEnd = itemView.findViewById(R.id.tvStartEnd);
//            tvCost = itemView.findViewById(R.id.tvCost);
//
//        }
//
//    }


    Context context;
    ArrayList<TransactionDetails> userSupportArrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionDetails> userSupportArrayList){
        this.context = context;
        this.userSupportArrayList = userSupportArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_transaction,parent,false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionAdapter.ViewHolder holder, int position) {
        TransactionDetails user = userSupportArrayList.get(position);

        holder.tvDate.setText(user.getTime());
        holder.tvStartEnd.setText(user.getStLocation()+" - "+user.getEnLocation());
        holder.tvCost.setText(String.valueOf(user.getFullPayment()));

    }

    @Override
    public int getItemCount() {

        return userSupportArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvStartEnd, tvCost;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvStartEnd = itemView.findViewById(R.id.tvStartEnd);
            tvCost = itemView.findViewById(R.id.tvCost);

        }
    }
}
