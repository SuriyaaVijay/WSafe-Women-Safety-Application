package com.darkness.WSafety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


   String[] laws;
   Context context;
   MyOnClickListener myOnClickListener;

    public MyAdapter(Context context, String[] laws,MyOnClickListener onClickListener){
        this.laws = laws;
        this.context = context;
        this.myOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.law_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int newPosition = position + 1;
        String displayString = newPosition + " " + laws[position];
        holder.law.setText(displayString);
        holder.law.setOnClickListener(view -> myOnClickListener.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return laws.length;
    }

   static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView law;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            law = itemView.findViewById(R.id.lawItem);
           
        }
    }

}
