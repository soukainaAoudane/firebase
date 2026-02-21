package com.example.firebase;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgViewHolder> {
    private List<Message> messageList;

    public MsgAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msgitem, parent, false);
        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder holder, int position) {
        Message msg = messageList.get(position);
        holder.msgTv.setText(msg.getMsg());
        holder.authorTv.setText(" - " + msg.getAuthor());

        // Clic simple pour MODIFIER
        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            EditText input = new EditText(v.getContext());
            input.setText(msg.getMsg());
            builder.setView(input);

            builder.setPositiveButton("Modifier", (dialog, which) -> {
                msg.setMsg(input.getText().toString());
                FirebaseDatabase.getInstance()
                        .getReference("msg")
                        .child(msg.getKey())
                        .setValue(msg);
            });

            builder.setNegativeButton("Annuler", null);
            builder.show();
        });

        // Clic long pour SUPPRIMER
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Suppression")
                    .setMessage("Voulez-vous supprimer ce message ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        FirebaseDatabase.getInstance()
                                .getReference("msg")
                                .child(msg.getKey())
                                .removeValue();
                    })
                    .setNegativeButton("Non", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();

}}