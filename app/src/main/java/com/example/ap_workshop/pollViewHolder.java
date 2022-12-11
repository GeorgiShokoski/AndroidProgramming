package com.example.ap_workshop;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class pollViewHolder
        extends RecyclerView.ViewHolder {
    TextView authorName;
    TextView pollQuestion;
    TextView PollOption1;
    TextView PollOption2;
    TextView PollOption3;
    TextView PollOption4;

    View view;

    pollViewHolder(View itemView)
    {
        super(itemView);
        authorName
                = (TextView)itemView
                .findViewById(R.id.authorName);
        pollQuestion
                = (TextView)itemView
                .findViewById(R.id.questionText);
        PollOption1
                = (TextView)itemView
                .findViewById(R.id.optionOne);
        PollOption2
                = (TextView)itemView
                .findViewById(R.id.optionTwo);
        PollOption3
                = (TextView)itemView
                .findViewById(R.id.optionThree);
        PollOption4
                = (TextView)itemView
                .findViewById(R.id.optionFour);
        view  = itemView;
    }
}