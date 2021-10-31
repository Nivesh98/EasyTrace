package com.nivacreation.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.nivacreation.login.R;
import com.nivacreation.login.model.Bus;
import com.nivacreation.login.model.TransactionDetails;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;
    private List<TransactionDetails> transactionDetails ;
    private Context context;

    public TransactionAdapter(Context context, EventListener<DocumentSnapshot> itemClickListener, List<TransactionDetails> transactionDetails) {
        this.context = context;
        this.itemClickListener = (ItemClickListener) itemClickListener;
        this.layoutInflater = LayoutInflater.from(context);
        this.transactionDetails = transactionDetails;
    }

    public TransactionAdapter(Context context, FragmentActivity activity, List<TransactionDetails> transactionList) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_transaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, itemClickListener, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvCost.setText("Rs : "+(int) transactionDetails.get(position).getFullPayment());
        holder.tvDate.setText(transactionDetails.get(position).getTime());
        holder.tvStartEnd.setText(transactionDetails.get(position).getStLocation()+" - "+transactionDetails.get(position).getEnLocation());

        holder.transactionRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onTransactionItemClicked(String.valueOf(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionDetails.size();
    }


    public interface ItemClickListener {
        void onTransactionItemClicked(String name);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout transactionRecycler;
        TextView tvDate, tvStartEnd, tvCost;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, TransactionAdapter transactionAdapter) {
            super(itemView);

            transactionRecycler = itemView.findViewById(R.id.transactionRecycler);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStartEnd = itemView.findViewById(R.id.tvStartEnd);
            tvCost = itemView.findViewById(R.id.tvCost);

        }

    }
}
