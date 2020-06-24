package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;



public class LoadingActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    static String allCheck="false";
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
            if (sharedPreferences!=null){

                if (sharedPreferences.getString("switch","")!=null||!sharedPreferences.getString("switch","").equals("")){
                    allCheck=sharedPreferences.getString("switch","");
                }
            }


            setContentView(R.layout.loading);

            linearLayout=findViewById(R.id.loading);

        }




    Thread thread=new Thread(){
        @Override
        public void run() {
           // Log.e("thread","thread");



            if (allCheck.equals("false")){
                handler2.sendEmptyMessageDelayed(0,1000);
            }else if(allCheck.equals("")){
                handler2.sendEmptyMessageDelayed(0,1000);
            }else{
                Log.e("allcheck",allCheck);
                handler.sendEmptyMessageDelayed(0,1000);
            }




        }
    };

        //핸들러로 넘겨준다.
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent=new Intent();
            intent.setClass(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent=new Intent();
            intent.setClass(getApplicationContext(), LoginSuccessActivity.class);
            startActivity(intent);
            finish();
        }
    };


        //로딩시 백버튼을 쓸 수 없게
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            return false;
        }

        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.e("start", "로딩 start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e("resume", "로딩 resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "로딩 pause");
    }




    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "로딩 stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "로딩 destroy");
    }
}