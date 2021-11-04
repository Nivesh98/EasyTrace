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
import com.nivacreation.login.model.User;
import com.nivacreation.login.model.UserSupport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserSupportAdapter extends RecyclerView.Adapter<UserSupportAdapter.UserSupportViewHolder>{


    Context context;
    ArrayList<UserSupport> userSupportArrayList;

    public UserSupportAdapter(Context context, ArrayList<UserSupport> userSupportArrayList){
        this.context = context;
        this.userSupportArrayList = userSupportArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public UserSupportViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user_support,parent,false);
        return new UserSupportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserSupportAdapter.UserSupportViewHolder holder, int position) {
        UserSupport user = userSupportArrayList.get(position);

        holder.firstname.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.contact.setText(user.getContact());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+user.getContact()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return userSupportArrayList.size();
    }

    public static class UserSupportViewHolder extends RecyclerView.ViewHolder {

        TextView firstname, lastName, contact;
        public UserSupportViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            firstname = itemView.findViewById(R.id.firstNameSupport);
            lastName = itemView.findViewById(R.id.lastNameSupport);
            contact = itemView.findViewById(R.id.contactSupport);
        }
    }
}
