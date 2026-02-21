package com.example.firebase;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MsgViewHolder extends RecyclerView.ViewHolder {
    public TextView msgTv, authorTv;

    public MsgViewHolder(@NonNull View itemView) {
        super(itemView);
        msgTv = itemView.findViewById(R.id.textView);
        authorTv = itemView.findViewById(R.id.textView2);
    }
}