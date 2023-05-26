package com.darkness.WSafety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    HashMap<String,String> contacts;
    Context context;
    ArrayList<String> send;
    MyOnClickListener myOnClickListener;

    ContactsAdapter(Context context, ArrayList<String> send,MyOnClickListener myOnClickListener){
        this.send = send;
        this.context = context;
        this.myOnClickListener = myOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contact.setText(send.get(position));
        holder.delete.setOnClickListener(view -> myOnClickListener.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return send.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,contact;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contactItem);
            delete = itemView.findViewById(R.id.deleteIcon);
        }
    }

}
