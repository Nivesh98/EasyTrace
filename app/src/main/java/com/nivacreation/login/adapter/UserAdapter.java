package com.nivacreation.login.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nivacreation.login.R;
import com.nivacreation.login.model.User;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    FirebaseFirestore fStore;

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
        holder.contact.setText(user.getContact());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+user.getContact()));
                context.startActivity(intent);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.email.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1050)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText firstName =view.findViewById(R.id.txtFirstName);
                EditText lastName =view.findViewById(R.id.txtLastName);
                EditText contact =view.findViewById(R.id.txtContact);
                EditText city =view.findViewById(R.id.txtCity);
                EditText street =view.findViewById(R.id.txtStreet);

                Button btnUpdate =view.findViewById(R.id.btnUpdate);

                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                contact.setText(user.getContact());
                city.setText(user.getCity());
                street.setText(user.getStreet());

                contact.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (validateMobile(contact.getText().toString())){

                        }else{
                            contact.setError("Invalid Mobile No! First Digit 0, Include 10 Digits!");

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map = new HashMap<>();

                        map.put("firstName",firstName.getText().toString());
                        map.put("lastName",lastName.getText().toString());
                        map.put("contact",contact.getText().toString());
                        map.put("city",city.getText().toString());
                        map.put("street",street.getText().toString());

                        FirebaseFirestore.getInstance().collection("Users").document(user.getUserID()).update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.firstname.getContext(),"Data Updated Successfully!",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(holder.firstname.getContext(),"Error While Updating!",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });
            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.firstname.getContext());
                builder.setTitle("Deleting User");
                builder.setMessage("Are you Sure Want to Delete the user?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseFirestore.getInstance().collection("Users").document(user.getUserID()).delete();
                        Toast.makeText(holder.firstname.getContext(),"User successfully deleted.!",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {

        return userArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView firstname, lastName, userType, email,contact;

        Button btnEdit, btnDelete;

        public UserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            firstname = itemView.findViewById(R.id.userFirstName);
            lastName = itemView.findViewById(R.id.userLastName);
            userType = itemView.findViewById(R.id.userType);
            email = itemView.findViewById(R.id.userEmail);
            contact =itemView.findViewById(R.id.contact);

            btnEdit = itemView.findViewById(R.id.updateBtn);
            btnDelete = itemView.findViewById(R.id.deleteBtn);
        }
    }
    boolean validateMobile(String input){

        Pattern p = Pattern.compile("[0][0-9]{9}");
        Matcher m = p.matcher(input);

        return m.matches();
    }
}
