package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.third.Adapter.NoteListAdapter;
import com.example.third.Class.NoteListClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity {

    RecyclerView recyclerView=null;
    NoteListAdapter noteListAdapter=null;
    ArrayList<NoteListClass> noteData=new ArrayList<>();


    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notelist_recycler);

        setTitle("노트 목록");

        getNoteContents();

        //리싸이클러뷰 생성
        recyclerView = findViewById(R.id.notelist_recycler);

        //어댑터
        noteListAdapter = new NoteListAdapter(noteData);

        recyclerView.setAdapter(noteListAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

            //item view를 클릭했을 때
        noteListAdapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteListAdapter.ViewHolder holder, View view, int position) {
              //몇 번째인지 가지고 오기.

                //선택한 position을 반영함.
                int number = noteListAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),NoteTextActivity.class);
                intent.putExtra("position",number);

                startActivity(intent);
               }
        });

        Button note_plus = findViewById(R.id.note_plus);

        //노트 위 작성하기 버튼을 클릭했을 때
        note_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplication(), NoteContentsActivity.class);
                startActivityForResult(intent,1);
            }
        });


        Log.e("create","TRAVELList create");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==1){ //추가


                getNoteContents();

               Intent intent=new Intent(getApplicationContext(),NoteListActivity.class);
               startActivity(intent);
               finish();

            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart", "travel restart");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "travel start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 리사이클러뷰에 CheckListAdapter 객체 지정.

        Log.e("resume", "travel resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "travel pause");
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "travel stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "travel destroy");
    }
    private void getNoteContents(){

        SharedPreferences sharedPreferences=getSharedPreferences("noteContents",MODE_PRIVATE);
        String some=sharedPreferences.getString("noteContents","");
        Gson gson=new Gson();

        Type listType = new TypeToken<ArrayList<NoteListClass>>(){}.getType();

        ArrayList<NoteListClass> list=gson.fromJson(some,listType);

        if (list!=null) {

            for (int i=0; i<list.size(); i++){
                  noteData.add(0,new NoteListClass(list.get(i).note_date,list.get(i).note_title,list.get(i).note_content,list.get(i).note_img));

            }
        }


    }

}
