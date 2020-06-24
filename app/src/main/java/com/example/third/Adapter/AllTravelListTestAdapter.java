package com.example.third.Adapter;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.third.Class.AllTravelListClass;
import com.example.third.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AllTravelListTestAdapter extends RecyclerView.Adapter<AllTravelListTestAdapter.ViewHolder> {



    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder{



        TextView alltravel_date;


        ImageView alltravel_img;


        TextView alltravel_img_name;

        TextView alltravel_time;

        OnItemClickListener listener;

        OnCreateContextMenuListener listener1;


        public ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            alltravel_date = itemView.findViewById(R.id.alltravel_date);
            alltravel_img = itemView.findViewById(R.id.all_travel_img);
            alltravel_img_name = itemView.findViewById(R.id.all_travel_img_name);
            alltravel_time=itemView.findViewById(R.id.all_travel_time);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    int position = getAdapterPosition();
                    listener1.onCreateContextMenu(menu,v,menuInfo,position);
                }
            });

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


        public void setOnCreateContextMenuListener(OnCreateContextMenuListener listener1) {
            this.listener1 = listener1;
        }

    }

    Context context;

    public ArrayList<AllTravelListClass> items = new ArrayList<>();

    OnItemClickListener listener;
    OnCreateContextMenuListener listener1;

    SharedPreferences sharedPreferences;
    private Gson gson;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public static interface OnCreateContextMenuListener {
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo,int position);
    }



    public AllTravelListTestAdapter(ArrayList<AllTravelListClass> list) {
        this.items = list;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.alltravellist_detail, parent, false);

        return new ViewHolder(itemView);
    }


    Thread thread;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.e("어디야?", String.valueOf(position));

                    holder.alltravel_date.setText(items.get(holder.getAdapterPosition()).alltravel_date);
                    holder.alltravel_img.setImageResource(items.get(holder.getAdapterPosition()).alltravel_img);
                    holder.alltravel_img_name.setText(items.get(holder.getAdapterPosition()).all_travel_img_name);
                     holder.alltravel_time.setText(items.get(holder.getAdapterPosition()).alltravel_time);

                    getItem(position);
                    holder.setOnItemClickListener(listener);
                    holder.setOnCreateContextMenuListener(listener1);



    }



    public int getItem(int position) {
        return position;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener listener1) {
        this.listener1 = listener1;
    }

}