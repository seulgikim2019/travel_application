package com.example.third.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.third.Class.TravelContentsClass;
import com.example.third.Class.TravelListClass;
import com.example.third.R;
import com.example.third.TravelListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TravelListTestAdapter extends RecyclerView.Adapter<TravelListTestAdapter.ViewHolder> {




    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView travel_date;
        //String note_date;a


        TextView travel_list_check;

        ImageView travel_img;


        public TextView travel_img_name;

        // int note_img;
        OnItemClickListener listener;




        public ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            travel_date = itemView.findViewById(R.id.travel_date);
            travel_list_check=itemView.findViewById(R.id.travel_list_check);
            travel_img = itemView.findViewById(R.id.travel_img);
            travel_img_name=itemView.findViewById(R.id.travel_img_name);

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

    Context context;

    ArrayList<TravelListClass> items ;

    OnItemClickListener listener;

    SharedPreferences sharedPreferences;

    public int countSu=0;
    Gson gson;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public TravelListTestAdapter(ArrayList<TravelListClass> list) {
        this.items = list;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.travellist1, parent, false);

        return new ViewHolder(itemView);
    }

    int checksu=0;




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     //   Log.e("어디야?", String.valueOf(position));



        holder.travel_date.setText(items.get(position).travel_date1);


      String list_check=getStringArrayPref(position);

        holder.travel_list_check.setText(list_check);
        holder.travel_img.setImageResource(items.get(position).travel_img);
        holder.travel_img.setVisibility(View.VISIBLE);
        holder.travel_img_name.setText(items.get(position).travel_img_name);


      //  Log.e("checksu",String.valueOf(checksu));
        //생명주기 stop에 하면 좋은데 그렇게 되어 버리면 한번 들어가고 나올때마다 새롭게 생성이 됨.
        if (checksu==items.size()-1){
            setStringArrayPref();
        }
        checksu++;
        getItem(position);

        //interface 각 view마다 click event 설정.
        holder.setOnItemClickListener(listener);
    }

    public int getItem(int position) {
        return position;
    }





    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }




    private String getStringArrayPref(int position){

        Log.e("왜 늦는지 봐야겠어",String.valueOf(position));

        gson=new Gson();
        SharedPreferences sharedPreferences=context.getSharedPreferences("travelListContents",MODE_PRIVATE);
        String id=String.valueOf(TravelListActivity.getposition2)+String.valueOf(position);
        String some=sharedPreferences.getString(id,"");
        Type listType = new TypeToken<ArrayList<TravelContentsClass>>(){}.getType();
        ArrayList<TravelContentsClass> list=gson.fromJson(some,listType);

        String list_check="";
        if (list!=null){
            for (int i = 0; i <list.size() ; i++) {
                list_check+=list.get(i).getTravel_all_time()+" "+list.get(i).getTravel_all_todo()+" "+list.get(i).getTravel_all_detail()+"\n";
            }
        }

        return list_check;
    }







    JSONObject jsonObject=new JSONObject();
    JSONArray jsonArray1=new JSONArray();
    public void setStringArrayPref() {
        gson=new GsonBuilder().create();

      //  Log.e("travellist countsu",String.valueOf(countSu));

        Type listType = new TypeToken<ArrayList<TravelListClass>>(){}.getType();


        String  travelListString = gson.toJson(items,listType);

        sharedPreferences = context.getSharedPreferences("travelListSharedTestObject",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();



      //  Log.e("dmd?",String.valueOf(items.size()));






//        //JSONOBJECT 선언
//
//        try {
//        //items를 담기 위한 배열
//        JSONArray jsonArray=new JSONArray();
//            JSONObject jsonObject1=null;
//        for (int i=0; i<items.size(); i++){
//            //배열 내에 들어가야하는 json object
//            jsonObject1=new JSONObject();
//            jsonObject1.put("travel_date",items.get(i).travel_date1);
//            jsonObject1.put("travel_img_name",items.get(i).travel_img_name);
//            jsonObject1.put("travel_img",items.get(i).travel_img);
//            jsonArray.put(jsonObject1);
//        }
//
//            SharedPreferences sharedPreferences=context.getSharedPreferences("loginidpass",MODE_PRIVATE);
//            String id=sharedPreferences.getString("id","");
//
//
//        if (sharedPreferences.contains("travel")){
//            String test=sharedPreferences.getString("travel","");
//            jsonArray1.put(test);
//            jsonArray1.put(jsonObject1);
//            jsonObject.put("travelList",jsonArray1);
//            editor1.putString("travel", String.valueOf(jsonObject));
//        }else{
//            jsonObject.put("travelList",jsonArray);
//            editor1.putString("travel", String.valueOf(jsonObject));
//        }
//
//
//             editor1.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        sharedPreferences = context.getSharedPreferences("travelListShared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //자신의 여행목록 페이지를 통해서 여행을 수정하는 경우를 대비하여 postion값이 존재한다면
        //더 추가하여서 이용할 수 없도록 만드는 역할.
        if (TravelListActivity.getposition==-1){

            while (sharedPreferences.getAll().size()>=countSu&&sharedPreferences.contains(String.valueOf(countSu))){
                countSu++;
               // Log.e("countsu?",String.valueOf(countSu));
            }

            editor.putString(String.valueOf(countSu), travelListString);
            TravelListActivity.getposition=countSu;
            editor.apply();
        }

        //새로운 여행 계획 탭을 통해 작성하는 경우를 위해 다시 -1로 설정.
        TravelListActivity.getposition=-1;

    //    Log.e("travellist",editor.toString());
    //    Log.e("get all", String.valueOf(sharedPreferences.getAll().size()));
    }




}