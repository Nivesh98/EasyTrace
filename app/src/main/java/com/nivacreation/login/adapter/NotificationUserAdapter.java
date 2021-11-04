package com.nivacreation.login.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivacreation.login.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationUserAdapter extends RecyclerView.Adapter<NotificationUserAdapter.NotificationUserHolder> {

    List<String> notificationArray;

    public NotificationUserAdapter(List<String> notificationArray){
        this.notificationArray = notificationArray;
    }
    @NonNull
    @NotNull
    @Override
    public NotificationUserHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification_inbox,parent,false);
        NotificationUserHolder notificationUserHolder = new NotificationUserHolder(view);
        return notificationUserHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationUserAdapter.NotificationUserHolder holder, int position) {

        holder.textView.setText(notificationArray.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationArray.size();
    }

    public class NotificationUserHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public NotificationUserHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.busGampahaInbox);
        }
    }
}
