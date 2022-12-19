package com.example.ap_workshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.CollationElementIterator;
import java.util.LinkedList;
import java.util.List;

public class pollAdapter extends RecyclerView.Adapter<pollAdapter.PollViewHolder>{
    class PollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;

        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.question);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private List<String> data;

    public pollAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.poll_recycleview_layout, parent, false);
        return new PollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String item = data.get(position);
        holder.textView.setText(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = data.get(position);
                Intent intent = new Intent(view.getContext(), voteActivity.class);
                intent.putExtra("text", item);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
