package com.nivacreation.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivacreation.login.R;
import com.nivacreation.login.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public UserAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_user_details,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.UserViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.firstname.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.userType.setText(user.getUserType());
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {

        return userArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView firstname, lastName, userType, email;

        public UserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            firstname = itemView.findViewById(R.id.userFirstName);
            lastName = itemView.findViewById(R.id.userLastName);
            userType = itemView.findViewById(R.id.userType);
            email = itemView.findViewById(R.id.userEmail);
        }
    }
}
