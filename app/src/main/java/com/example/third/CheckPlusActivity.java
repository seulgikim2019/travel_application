package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.third.Class.CheckContentsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class CheckPlusActivity extends AppCompatActivity {

    private Intent intent;

    private EditText newList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_plus);


        newList=findViewById(R.id.newList);
        Button button1=findViewById(R.id.plus_ok);

        Button button2=findViewById(R.id.plus_no);


        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.plus_ok:
                        setCheckList();
                        intent=new Intent(getApplication(),CheckListActivity1.class);
                       // Log.e("????",editText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                    case R.id.plus_no:
                        finish();
                        break;
                }
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);



    }

    //확인 버튼을 눌렀을 때 저장.
    private void setCheckList() {
        SharedPreferences sharedPreferences = getSharedPreferences("checkContents", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();

        //값을 작성하고 버튼을 눌렀을 때, 각 값들을 담을 수 있도록 리스트 선언
        ArrayList<CheckContentsClass> checkContentsClass = new ArrayList<CheckContentsClass>();

        //각 값을 가지고 와서 list에 담아둠.
        checkContentsClass.add(new CheckContentsClass(newList.getText().toString(),false));

        //타입을 선언해주고 json
        Type listType = new TypeToken<ArrayList<CheckContentsClass>>() {}.getType();
        String checkContents = gson.toJson(checkContentsClass, listType);


        //gson을 이용하여 jsonarray 생성 시,

            //JSON 배열로 바꾸어 줌.
        try {
            JSONArray jsonArray = new JSONArray(checkContents);

            //기존에 저장한 key가 있는지 여부 체크
            if (sharedPreferences.contains("checkContents")) {  //이미 저장된 값이 있다면
                String some = sharedPreferences.getString("checkContents", "");

                some = some.replace("[", "");
                some = some.replace("]", "");


                Log.e("some",some);

                checkContents = checkContents.replace("[", "");
                checkContents = checkContents.replace("]", "");


                String all = "[" + some + "," + checkContents + "]";

                JSONArray jsonArray1 = new JSONArray(all);
                editor.putString("checkContents", String.valueOf(jsonArray1));
                editor.apply();
            } else {

                editor.putString("checkContents", String.valueOf(jsonArray));
                editor.apply();
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
