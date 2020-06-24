package com.example.third;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.third.Class.NoteListClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//노트 내용 작성

public class NoteContentsActivity extends AppCompatActivity {


    private Intent intent;
    private Intent intent1;
    Intent intent3;
    private EditText title;
    private EditText content;
    private ImageView img;
    private static final int PICK_FROM_ALBUM = 1;
    private Uri uri = null;


    JSONArray jsonArray;
    JSONObject jsonObject;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_write);



        setTitle("노트 작성");


        jsonObject = new JSONObject();
        Button button1 = findViewById(R.id.note_ok);
        ImageView imageView = findViewById(R.id.note_write_album);
        title = findViewById(R.id.note_write_title);
        content = findViewById(R.id.note_write_content);
        img = findViewById(R.id.note_write_img);

        //노트 작성 부분에서 가지고 오는 부분
        intent3 = getIntent();

        if (intent3.getIntExtra("position", -1) >= 0) {
            int position = intent3.getIntExtra("position", -1);
            reSharedNote(position);


        }

        //클릭 이벤트
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.note_ok:

                        //노트작성 확인을 눌렀을 경우
                        intent = new Intent(getApplicationContext(), NoteListActivity.class);
                        if (String.valueOf(title.getText()).equals("") && String.valueOf(content.getText()).equals("")
                                && String.valueOf(uri).equals("null")) {
                            Toast.makeText(getApplicationContext(), "입력된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        //수정을 하는 경우
                        if (intent3 != null) {
                            if (intent3.getIntExtra("position", -1) >= 0) {
                                Log.e("????", intent3.toString());
                                int position = intent3.getIntExtra("position", -1);

                                //현재 최신형을 먼저 보여주기 때문에 xml에 쌓여있는 리스트 목록과 반대로 적용
                                //따라서 xml의 전체 사이즈를 먼저 알 필요가 있고, 거기서 값을 빼줘서 적용해야 함.

                                resetSharedNote(position);
                                //하나의 activty만 존재하게
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else { //작성을 하는 경우

                                setSharedNote();
                                setResult(RESULT_OK, intent);
                            }
                        }


                        finish();
                        break;
                    case R.id.note_write_album:
                        //사진버튼을 누른 경우
                        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            return;
                        }

                        for (String permission : permission_list) {
                            //권한 허용 여부를 확인한다.
                            int chk = checkCallingOrSelfPermission(permission);

                            if (chk == PackageManager.PERMISSION_DENIED) {
                                //권한 허용을여부를 확인하는 창을 띄운다
                                requestPermissions(permission_list, 0);
                            } else {
                                getImgFromAlbum();
                            }

                        }
                        Log.e("clickImg", "click");
                        break;
                }
            }
        };

        button1.setOnClickListener(listener);
        imageView.setOnClickListener(listener);

    }

    //체크받을 permission
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {
                //허용됬다면
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getImgFromAlbum();
                } else {
                    Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    //앨범에서 이미지 가지고 오기
    private void getImgFromAlbum() {
        Log.e("clickImg1", "click1");


        //이미지 선택
        intent1 = new Intent(Intent.ACTION_PICK);
        //타입
        intent1.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent1.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent1, PICK_FROM_ALBUM);


    }


    //사진앨범을 선택하고 사진을 가지고 오는 것.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_FROM_ALBUM:

                try {

                    uri = data.getData();

                    img.setImageURI(uri);

                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
                break;
        }


    }


    //노트 작성 버튼을 눌렀을 때, 노트 제목, 내용, 사진의 값을 저장해 놓자!
    private void setSharedNote() {
        SharedPreferences sharedPreferences = getSharedPreferences("noteContents", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();


        //시스템 날짜를 입력해줌
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        //값을 작성하고 버튼을 눌렀을 때, 각 값들을 담을 수 있도록 리스트 선언
        ArrayList<NoteListClass> noteListClass = new ArrayList<>();

        //각 값을 가지고 와서 list에 담아둠.
        noteListClass.add(new NoteListClass(simpleDateFormat.format(date.getTime()), String.valueOf(title.getText()),
                String.valueOf(content.getText()), String.valueOf(uri)));

        //타입을 선언해주고 json
        Type listType = new TypeToken<ArrayList<NoteListClass>>() {}.getType();
        String noteContents = gson.toJson(noteListClass, listType);


        //gson을 이용하여 jsonarray 생성 시,
        try {

            //JSON 배열로 바꾸어 줌.
            jsonArray = new JSONArray(noteContents);


            //기존에 저장한 key가 있는지 여부 체크
            if (sharedPreferences.contains("noteContents")) {  //이미 저장된 값이 있다면
                String some = sharedPreferences.getString("noteContents", "");

                some = some.replace("[", "");
                some = some.replace("]", "");

                noteContents = noteContents.replace("[", "");
                noteContents = noteContents.replace("]", "");


                String all = "[" + some + "," + noteContents + "]";

                JSONArray jsonArray1 = new JSONArray(all);
                editor.putString("noteContents", String.valueOf(jsonArray1));
                editor.apply();

            } else {

                editor.putString("noteContents", String.valueOf(jsonArray));
                editor.apply();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    JSONArray jsonArrayText = new JSONArray();

    //노트 수정을 위해 들어온 경우, 값을 입력해 주는 메소드
    private void reSharedNote(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("noteContents", MODE_PRIVATE);

        String some = sharedPreferences.getString("noteContents", "");


        try {
            jsonArrayText = new JSONArray(some);


            //jsonarray로 전환
            JSONArray jsonrearray = jsonArrayText;
            int sharePosition = (jsonrearray.length() - 1) - position;


            //jsonarray를 jsonobject로 다시 전환 . key값을 뽑기위해서
            JSONObject jsonObject = jsonrearray.getJSONObject(sharePosition);


            title.setText(jsonObject.getString("note_title"));
            content.setText(jsonObject.getString("note_content"));
            img.setImageURI(Uri.parse(jsonObject.getString("note_img")));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    //노트 수정을 위해 들어온 경우, 처리.
    private void resetSharedNote(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("noteContents", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();


        String some=sharedPreferences.getString("noteContents","");


        try {
            JSONArray array=new JSONArray(some);
            //jsonarray로 전환
            JSONArray jsonrearray1 = array;
            int sharePosition = (jsonrearray1.length() - 1) - position;

            //jsonarray를 jsonobject로 다시 전환 . key값을 뽑기위해서
            JSONObject jsonObject =(JSONObject)jsonrearray1.get(sharePosition);



            //이전 타이틀 값으로
            String titlebefore=jsonObject.get("note_title").toString();

            if(jsonObject.get("note_title").equals(titlebefore)){
                jsonObject.put("note_title",String.valueOf(title.getText()));

                jsonrearray1.put(sharePosition,jsonObject);

            }

            //이전 내용 값으로
            String contentbefore=jsonObject.get("note_content").toString();

            if(jsonObject.get("note_content").equals(contentbefore)){
                jsonObject.put("note_content",String.valueOf(content.getText()));

                jsonrearray1.put(sharePosition,jsonObject);

            }

            //이전 이미지 값으로
            String imgbefore=jsonObject.get("note_img").toString();

            if(jsonObject.get("note_img").equals(imgbefore)){

                if (!String.valueOf(uri).equals("null")) {
                    jsonObject.put("note_img", String.valueOf(uri));
                }else if(String.valueOf(uri).equals("null")){
                    jsonObject.put("note_img", imgbefore);
                }
                jsonrearray1.put(sharePosition,jsonObject);

            }


            editor.putString("noteContents", String.valueOf(jsonrearray1));
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
