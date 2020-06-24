package com.example.third;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


//노트 내용 작성

public class NoteTextActivity extends AppCompatActivity {


    Intent intent3;
    private TextView title;
    private TextView content;
    private ImageView img;
    int position=-1;
    JSONObject jsonObject;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_text);


        setTitle("상세 내용");


        jsonObject = new JSONObject();
        Button button1 = findViewById(R.id.note_before);
        Button button2 =findViewById(R.id.note_rewrite);
        Button button3 =findViewById(R.id.note_delete);
        title = findViewById(R.id.note_text_title);
        content = findViewById(R.id.note_text_content);
        img = findViewById(R.id.note_text_img);

        //노트 작성 부분에서 가지고 오는 부분
        intent3 = getIntent();

        if (intent3.getIntExtra("position", -1) >= 0) {
            position = intent3.getIntExtra("position", -1);
            reSharedNote(position);

            //클릭 이벤트
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.note_before:
                            finish();
                            break;
                        case R.id.note_rewrite:
                            Intent intent = new Intent(getApplicationContext(), NoteContentsActivity.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
                            finish();
                            break;
                        case R.id.note_delete:
                            deleteSharedNote(position);
                            Intent intent1 = new Intent(getApplicationContext(),NoteListActivity.class);
                            //하나의 activty만 존재하게
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            finish();
                            break;
                    }
                }
            };

            button1.setOnClickListener(listener);
            button2.setOnClickListener(listener);
            button3.setOnClickListener(listener);

        }


    }
        JSONArray jsonArrayText = new JSONArray();

        //노트 수정을 위해 들어온 경우, 값을 입력해 주는 메소드
        private void reSharedNote ( int position){
            SharedPreferences sharedPreferences = getSharedPreferences("noteContents", MODE_PRIVATE);
             String some = sharedPreferences.getString("noteContents", "");


            try {
                jsonArrayText = new JSONArray(some);
           //jsonarray로 전환
                JSONArray jsonrearray = jsonArrayText;
                int sharePosition = (jsonrearray.length() - 1) - position;
                JSONObject jsonObject = jsonrearray.getJSONObject(sharePosition);


                title.setText(jsonObject.getString("note_title"));
                content.setText(jsonObject.getString("note_content"));
                img.setImageURI(Uri.parse(jsonObject.getString("note_img")));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    //노트 삭제을 위해 들어온 경우, 처리.
    private void deleteSharedNote(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("noteContents", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        String some=sharedPreferences.getString("noteContents","");


        try {
            JSONArray array=new JSONArray(some);
            //jsonarray로 전환
            JSONArray jsonrearray1 = array;
            int sharePosition = (jsonrearray1.length() - 1) - position;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonrearray1.remove(sharePosition);

                if (jsonrearray1.length()==0){
                    editor.remove("noteContents");
                    editor.commit();
                }else{
                    editor.putString("noteContents", String.valueOf(jsonrearray1));
                    editor.apply();
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




}
