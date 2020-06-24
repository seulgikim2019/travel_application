package com.example.third.Adapter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.third.Class.TravelContentsClass;
import com.example.third.R;
import com.example.third.TravelContentsActivity;

import java.util.ArrayList;

public class TravelContentsAdapter extends RecyclerView.Adapter<TravelContentsAdapter.ViewHolder> {


    static int number;

    public TravelContentsAdapter() {

    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{



       // String travel_all_date;
            TextView travel_all_time;

       // String travel_all_todo;
            TextView travel_all_todo;

        // String travel_all_detail;
            TextView travel_all_detail;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            travel_all_time=itemView.findViewById(R.id.travel_all_time);
            travel_all_todo=itemView.findViewById(R.id.travel_all_todo);
            travel_all_detail=itemView.findViewById(R.id.travel_all_detail);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem rewrite = menu.add(Menu.NONE,1,1,"수정");
            MenuItem delete = menu.add(Menu.NONE,2,2,"삭제");
            number=getAdapterPosition();
            Log.e("content",String.valueOf(number));
            rewrite.setOnMenuItemClickListener(TravelContentsActivity.onCreateContextMenu);
            delete.setOnMenuItemClickListener(TravelContentsActivity.onCreateContextMenu);
        }


    }




    public int getItem(int position) {
        return number;
    }


    ArrayList<TravelContentsClass> travelContentsData ;
    ViewHolder viewHolder1;
    // 생성자에서 데이터 리스트 객체를 전달받음.
    // CheckListAdapter를 생성하게 되면 checkData를 초기화시켜 줌.
    public TravelContentsAdapter(ArrayList<TravelContentsClass> list) {
        travelContentsData = list ;
    }



    //viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성.
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //context
        Context context = viewGroup.getContext() ;

        //각 data마다 context를 가지고 오는 것으로 보임.
        Log.e("travel context학인해보자",context.toString());


        //inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;


        View view = inflater.inflate(R.layout.travel_write_all, viewGroup, false) ;


        return new ViewHolder(view);
    }


    static TravelContentsActivity travelContentsActivity=null;

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i) {
        viewHolder1 = viewHolder;

            viewHolder1.travel_all_time.setText(travelContentsData.get(i).travel_all_time);
            viewHolder1.travel_all_todo.setText(travelContentsData.get(i).travel_all_todo);
            viewHolder1.travel_all_detail.setText(travelContentsData.get(i).travel_all_detail);

       // getItem(i);
        Log.e("travel 몇번째??? iii", String.valueOf(i));

    }


    @Override
    public int getItemCount() {
        return travelContentsData.size();
    }

}
