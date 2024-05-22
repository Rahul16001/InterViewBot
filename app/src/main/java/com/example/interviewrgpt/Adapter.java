package com.example.interviewrgpt;

import static com.example.interviewrgpt.MainActivity.message;
import static com.example.interviewrgpt.MainActivity.userName;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        if(Objects.equals(message.get(position).getRole(), "user"))
        {
            viewHolder.who_replied.setText(userName);
            viewHolder.logo.setImageResource(R.drawable.suit_logo);
            viewHolder.reply_sum.setText(message.get(position).getContent());
        }
        else if(Objects.equals(message.get(position).getRole(), "assistant"))
        {
            viewHolder.who_replied.setText("PrepBot");
            viewHolder.logo.setImageResource(R.drawable.robo_smile);
            viewHolder.reply_sum.setText(message.get(position).getContent());
        }

        else if(Objects.equals(message.get(position).getRole(),"system"))
        {
            viewHolder.logo.setImageResource(R.drawable.gpt_logo);
            viewHolder.who_replied.setText("Management");
            viewHolder.reply_sum.setText("Your interview has started.........");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return message.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView who_replied;
        TextView reply_sum;
        ImageView logo;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            who_replied = view.findViewById(R.id.who_replied);
            reply_sum = view.findViewById(R.id.reply_sum);
            logo = view.findViewById(R.id.logo);
        }
    }
}

