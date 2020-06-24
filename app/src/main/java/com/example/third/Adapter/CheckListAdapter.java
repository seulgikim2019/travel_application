package com.example.third.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.third.Class.CheckContentsClass;
import com.example.third.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {



    public static ArrayList<CheckContentsClass> checkData = new ArrayList<CheckContentsClass>();
    Context context;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView textView1 ;
        CheckBox checkBox;
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.check_name) ;
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }



    // 생성자에서 데이터 리스트 객체를 전달받음.
    // CheckListAdapter를 생성하게 되면 checkData를 초기화시켜 줌.
    public CheckListAdapter(ArrayList<CheckContentsClass> checkData) {
        this.checkData = checkData ;
    }


    //viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성.
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //context
        context = viewGroup.getContext();

        //각 data마다 context를 가지고 오는 것으로 보임.
        //     Log.e("context학인해보자",context.toString());


        //inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;


        View view = inflater.inflate(R.layout.check_contents, viewGroup, false) ;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

   //     Log.e("here","here");
        viewHolder.textView1.setText(checkData.get(i).check_name);


        if (checkData.get(i).check_box){
            viewHolder.textView1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            viewHolder.textView1.setPaintFlags(0);
        }

        viewHolder.checkBox.setChecked(checkData.get(i).check_box);

        //삭제기능
        viewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem delete = menu.add(Menu.NONE,1,1,"삭제");
                delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.e("삭제 몇번째???", String.valueOf(i));
                        removeItem(i);
//
                checkData.remove(i);
                checkData=checkData;
               Log.e("삭제RESET???", String.valueOf(checkData.size()));
//
//                //데이터 전체가 바뀌었을 때 호출
                notifyDataSetChanged();
                        return false;
                    }
                });
            }
        });



        //if true, your checkbox will be selected, else unselected
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //원래 true로 선택되어 있는 것이 클릭되었을때
                if (checkData.get(i).getCheck_box()==true){
                    //false로 바꾸어줌
                    checkData.get(i).setCheck_box(false);
                }else{//반대
                    checkData.get(i).setCheck_box(true);
                }
                resetItem(i);
                notifyDataSetChanged();
            //    Log.e("몇번째???", String.valueOf(i));

            }
        });




    }

    //전체 아이템 갯수 리턴.
    @Override
    public int getItemCount() {

        return checkData.size();
    }

    private void removeItem(int su){
        SharedPreferences sharedPreferences=context.getSharedPreferences("checkContents",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson=new Gson();


        String some=sharedPreferences.getString("checkContents","");

        try {
            JSONArray array=new JSONArray(some);

//                String result=jsonrearray.get(sharePosition).toString();
//                Log.e("result",result);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                array.remove(su);

                if (array.length()==0){
                    Log.e("남아있는  값이 없습니다.","!");
                    editor.remove("checkContents");
                    editor.commit();
                }else{
                    editor.putString("checkContents", String.valueOf(array));
                    editor.apply();
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void resetItem(int su){
        SharedPreferences sharedPreferences=context.getSharedPreferences("checkContents",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson=new Gson();


        String some=sharedPreferences.getString("checkContents","");

        try {
            JSONArray array=new JSONArray(some);

           // String that=array.get(su).toString();
           // array.getJSONObject(su).get("check_name");
//
//            Log.e("that",that);
//
//            Log.e("that2", String.valueOf(array.getJSONObject(su).get("check_name")));


            //array에서 선택된 아이의 check_box 값을 바꾸어준다.


            boolean checkTF= (boolean) array.getJSONObject(su).get("check_box");


            //버튼이 클릭되어 있을 때 -> 클릭 : 취소 의미

            if (checkTF==true){
               // Log.e("true","true");
                array.getJSONObject(su).put("check_box",false);
            }else {//반대
               // Log.e("false","false");
                array.getJSONObject(su).put("check_box", true);
            }

            //반영한 jsonarray를 반영함.
            editor.putString("checkContents", String.valueOf(array));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }


}
