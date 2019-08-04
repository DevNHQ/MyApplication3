package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CateHolder extends RecyclerView.ViewHolder {
    public TextView tvMessageAmin;
    public TextView tvMessageAmi;
    public TextView tvTimeadmin;
    public TextView tvTimeOther;

    public CateHolder(@NonNull View itemView) {
        super(itemView);
        tvMessageAmin = itemView.findViewById(R.id.messageAdmin);
        tvMessageAmi = itemView.findViewById(R.id.messageAdmi);
        tvTimeadmin = itemView.findViewById(R.id.timeAdmin);
        tvTimeOther = itemView.findViewById(R.id.timeOther);

    }
}
