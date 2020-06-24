package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.third.Adapter.CheckListAdapter;
import com.example.third.Class.CheckContentsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CheckListActivity1 extends AppCompatActivity {
    RecyclerView recyclerView=null;
    CheckListAdapter checkListAdapter=null;
    ArrayList<CheckContentsClass> checkData = new ArrayList<>();
    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.checklist);
        getCheckList();


        Button button1 = findViewById(R.id.plusButton);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.plusButton:
                        intent1=new Intent(getApplication(), CheckPlusActivity.class);
                        startActivityForResult(intent1,1);
                    break;


                }
            }
        };

        button1.setOnClickListener(listener);


        // 리사이클러뷰에 CheckListAdapter 객체 지정.
        recyclerView = findViewById(R.id.check_recycler) ;

        checkListAdapter=new CheckListAdapter(checkData);

        recyclerView.setAdapter(checkListAdapter);


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));


        Log.e("create","checkList create");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==1){ //추가
                getCheckList();
                checkListAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "checklist start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 리사이클러뷰에 CheckListAdapter 객체 지정.
        Log.e("restart", "checklist restart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkListAdapter.notifyDataSetChanged();
        Log.e("resume", "checklist resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "checklist pause");
    }





    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "checklist stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "checklist destroy");
    }



    private void getCheckList(){

        Log.e("다시 오는지 확인","그거먼저 확인");



        SharedPreferences sharedPreferences=getSharedPreferences("checkContents",MODE_PRIVATE);
        String some=sharedPreferences.getString("checkContents","");
        Gson gson=new Gson();

        Type listType = new TypeToken<ArrayList<CheckContentsClass>>(){}.getType();

        ArrayList<CheckContentsClass> list=gson.fromJson(some,listType);



        if (list!=null){
            checkData=list;

            if (checkListAdapter.checkData==null){
                checkListAdapter.checkData=new ArrayList<>();
            }

            checkListAdapter.checkData=checkData;



        }



    }
}
