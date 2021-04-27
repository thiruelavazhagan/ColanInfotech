package com.example.colaninfotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Context context;
    private List<Data> contactList;
    private List<Data> contactListFiltered;
    private ContactsAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone,start;
        public ImageView thumbnail,comment;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);
            start =view.findViewById(R.id.textView4);
            comment =view.findViewById(R.id.thumbnail2);

            view.setOnClickListener(view1 -> {
                // send selected contact in callback
                listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
            });

            view.setOnLongClickListener(v -> {
                listener.onLongPress(getAdapterPosition(),contactListFiltered.get(getAdapterPosition()));
                return false;
            });

            comment.setOnClickListener(v ->
                    listener.onComment(contactListFiltered.get(getAdapterPosition()))
            );

        }
    }

    public ContactsAdapter(Context context, List<Data> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }
    public ContactsAdapter(Context context, List<Data> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Data contact = contactListFiltered.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getFull_name());
        holder.start.setText(contact.getLanguage());

        Picasso.with(context)
                .load(contact.getOwner().getAvatar_url())
                .into(holder.thumbnail);
/*
        Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }



    public interface ContactsAdapterListener {

        void onLongPress(int position,Data userFirebase);

        void onContactSelected(Data userFirebaseAdd);

        void onComment(Data userFirebaseAdd);
    }
}
