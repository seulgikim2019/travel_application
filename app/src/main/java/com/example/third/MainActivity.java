package com.example.third;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static ArrayList<Activity> list=new ArrayList<Activity>();

    String passCheck="";

    EditText passedit=null;

    Intent intentActivity=null;
    String activity="";
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            setContentView(R.layout.login);
        //    Log.e("create", "로그인 create");


            intentActivity=getIntent();
            if (intentActivity!=null){
                activity=intentActivity.getStringExtra("activity");

              //  Log.e("null이아니야",activity);
            }

            passCheck=getEnrollShared();

            Log.e("passCheck",passCheck);
            passedit = findViewById(R.id.login_password);

        }


    private String getEnrollShared(){
        SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
        String pass="";

       // Log.e("들어온건가?0","이링로?");
        if (sharedPreferences!=null){
            if (sharedPreferences.getString("a","")!=null||!sharedPreferences.getString("a","").equals("")){
                pass=sharedPreferences.getString("a","");
            }
        }

        return pass;
    }







    public void onClick(View view) {
        String pass;
            switch (view.getId()){

                case  R.id.login_click:



                    if (passedit.getText().toString().equals(passCheck)){

                        if (activity!=null&&!activity.equals("")){
                            Intent intent = null;

                            switch (activity){
                                case "AllTravelListActivity":
                                    intent = new Intent(getApplicationContext(), AllTravelListActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case "TravelContents":
                                    finish();
                                    break;
                            }



                        }else {
                            Intent intent = new Intent(getApplicationContext(), LoginSuccessActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                        Log.e("passedit",passedit.getText().toString());
                        passedit.setText("");
                        return;
                    }

                 break;


            }


    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "로그인 start");





    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart", "로그인 restart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "로그인 resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "로그인 pause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "로그인 stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "로그인 destroy");
    }
}