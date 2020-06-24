package com.example.third.Adapter;


import android.content.Context;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.third.Class.NoteListClass;
import com.example.third.R;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    Context context;

    ArrayList<NoteListClass> items;

    OnItemClickListener listener;


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView note_date;
        //String note_date;a

        TextView note_title;
        //String note_title;

        TextView note_content;
        //String note_content;

        ImageView note_img;
        // int note_img;
        OnItemClickListener listener;


        public ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            note_date = itemView.findViewById(R.id.note_date);
            note_title = itemView.findViewById(R.id.note_title);
            note_content = itemView.findViewById(R.id.note_content);
            note_img = itemView.findViewById(R.id.note_img);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }



        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }



    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public NoteListAdapter(ArrayList<NoteListClass> items){
        this.items=items;
    }

    public NoteListAdapter(Context context) {
        this.context=context;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context=parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.notelist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      //  Log.e("어디야?", String.valueOf(position));

     //   Log.e("???",String.valueOf(items.get(position).note_title));
        holder.note_date.setText(items.get(position).note_date);
        holder.note_title.setText(items.get(position).note_title);
        holder.note_content.setText(items.get(position).note_content);
        holder.note_img.setImageURI(Uri.parse(items.get(position).note_img));



        //     Bitmap photo = bundle.getParcelable("data");


        //        img_album.setImageBitmap(photo);


        //items.add(new NoteListClass(holder.note_date));
        getItem(position);
        holder.setOnItemClickListener(listener);
    }

    public int getItem(int position) {
        return position;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}