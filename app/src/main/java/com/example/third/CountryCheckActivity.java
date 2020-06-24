package com.example.third;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class CountryCheckActivity extends AppCompatActivity{

    public static Activity countryActivity;
    private Intent intent;
    private ImageView imageView;
    private TextView textView;
    private int path;
    public static boolean doubleCheck=false;
    Button button4;
    TextView country_rink;
    boolean flag=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        countryActivity=CountryCheckActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country);
        setTitle("여행 국가 선택");

        intent=new Intent(getApplicationContext(),CalendarCheckActivity.class);



        if (savedInstanceState != null) {
            int id1=savedInstanceState.getInt("country");
            int id=savedInstanceState.getInt("countryName");
            String uriGet=savedInstanceState.getString("uri");
            //intent.putExtra("country",uri);
            if (id1!=0){
                imageView =findViewById(id1);
                imageView.setBackgroundColor(Color.YELLOW);

                doubleCheck=true;
            }


            if (id!=0){
                textView=findViewById(id);
                textView.setBackgroundColor(Color.YELLOW);

                doubleCheck=true;
            }


        }
        Button button1=findViewById(R.id.east_eu_btn);
        Button button2=findViewById(R.id.west_eu_btn);
        Button button3=findViewById(R.id.north_eu_btn);

        button4=findViewById(R.id.country_ok);

        ImageView img1=findViewById(R.id.russia);
        ImageView img2=findViewById(R.id.czech);
        ImageView belarus=findViewById(R.id.belarus);
        ImageView germany=findViewById(R.id.germany);
        ImageView indonesia=findViewById(R.id.indonesia);


        country_rink=findViewById(R.id.travel_country_rink);

        //버튼 이벤트 -> 감추기 보이기 기능 .
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tableRow = null;
                Button button;

                switch (v.getId()){
                    case R.id.east_eu_btn:
                        Log.e("east1","east1");
                        tableRow=findViewById(R.id.east_eu_2);

                        if (tableRow.getVisibility()==View.GONE) {
                            tableRow.setVisibility(View.VISIBLE);
                            tableRow = findViewById(R.id.east_eu_2_text);
                            tableRow.setVisibility(View.VISIBLE);
                            button=findViewById(R.id.east_eu_btn);
                            button.setText("▲(감추기)");
                        }else{
                            tableRow.setVisibility(View.GONE);
                            tableRow = findViewById(R.id.east_eu_2_text);
                            tableRow.setVisibility(View.GONE);
                            button=findViewById(R.id.east_eu_btn);
                            button.setText("▼(더보기)");
                        }
                        break;
                    case R.id.west_eu_btn:
                        Log.e("west2","west2");
                        tableRow=findViewById(R.id.west_eu_2);


                        if (tableRow.getVisibility()==View.GONE) {
                            tableRow.setVisibility(View.VISIBLE);
                            tableRow = findViewById(R.id.west_eu_2_text);
                            tableRow.setVisibility(View.VISIBLE);
                            button=findViewById(R.id.west_eu_btn);
                            button.setText("▲(감추기)");
                        }else{
                            tableRow.setVisibility(View.GONE);
                            tableRow = findViewById(R.id.west_eu_2_text);
                            tableRow.setVisibility(View.GONE);
                            button=findViewById(R.id.west_eu_btn);
                            button.setText("▼(더보기)");
                        }
                        break;
                    case R.id.north_eu_btn:
                        Log.e("north3","north3");
                        tableRow=findViewById(R.id.north_eu_2);


                        if (tableRow.getVisibility()==View.GONE) {
                            tableRow.setVisibility(View.VISIBLE);
                            tableRow = findViewById(R.id.north_eu_2_text);
                            tableRow.setVisibility(View.VISIBLE);
                            button=findViewById(R.id.north_eu_btn);
                            button.setText("▲(감추기)");
                        }else{
                            tableRow.setVisibility(View.GONE);
                            tableRow = findViewById(R.id.north_eu_2_text);
                            tableRow.setVisibility(View.GONE);
                            button=findViewById(R.id.north_eu_btn);
                            button.setText("▼(더보기)");
                        }
                        break;
                    case R.id.russia:

                        if (doubleCheck==true){

                            imageView.setBackgroundColor(Color.TRANSPARENT);
                            textView.setBackgroundColor(Color.TRANSPARENT);
                            Log.e("색아 변해라.","?");
                        }
                        thread=new Thread() {
                            public void run() {
                                Log.e("run", "run");
                                handler.sendEmptyMessage(1);
                        }};
                        thread.start();
                        imageView =findViewById(R.id.russia);
                        imageView.setBackgroundColor(Color.YELLOW);
                        textView=findViewById(R.id.russia_text);
                        textView.setBackgroundColor(Color.YELLOW);
                        intent.putExtra("countryName","러시아");
                        path=R.drawable.russia;
                        intent.putExtra("country",path);

                        doubleCheck=true;
                        break;

                    case R.id.czech:
                        if (doubleCheck==true){

                            imageView.setBackgroundColor(Color.TRANSPARENT);
                            textView.setBackgroundColor(Color.TRANSPARENT);

                        }
                        thread=new Thread() {
                            public void run() {

                                handler.sendEmptyMessage(1);
                                                }
                        };
                        thread.start();
                        imageView =findViewById(R.id.czech);
                        imageView.setBackgroundColor(Color.YELLOW);
                        textView=findViewById(R.id.czech_text);
                        textView.setBackgroundColor(Color.YELLOW);
                        intent.putExtra("countryName","체코");
                        path=R.drawable.czech;
                        intent.putExtra("country",path);
                        doubleCheck=true;
                        break;
                    case R.id.belarus:
                        if (doubleCheck==true){

                            imageView.setBackgroundColor(Color.TRANSPARENT);
                            textView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        thread=new Thread() {
                            public void run() {
                                handler.sendEmptyMessage(1);
                                 }
                        };
                        thread.start();
                        imageView =findViewById(R.id.belarus);
                        imageView.setBackgroundColor(Color.YELLOW);
                        textView=findViewById(R.id.belarus_text);
                        textView.setBackgroundColor(Color.YELLOW);
                        intent.putExtra("countryName","벨라루스");
                        path=R.drawable.belarus;

                        intent.putExtra("country",path);
                        doubleCheck=true;
                        break;

                    case R.id.germany:
                        if (doubleCheck==true){

                            imageView.setBackgroundColor(Color.TRANSPARENT);
                            textView.setBackgroundColor(Color.TRANSPARENT);
                         }
                        thread=new Thread() {
                            public void run() {
                                 handler.sendEmptyMessage(1);
                                 }
                        };
                        thread.start();
                        imageView =findViewById(R.id.germany);
                        imageView.setBackgroundColor(Color.YELLOW);
                        textView=findViewById(R.id.germany_text);
                        textView.setBackgroundColor(Color.YELLOW);
                        intent.putExtra("countryName","독일");
                        path=R.drawable.germany;

                        intent.putExtra("country",path);
                        doubleCheck=true;
                        break;

                    case R.id.indonesia:
                        if (doubleCheck==true){

                            imageView.setBackgroundColor(Color.TRANSPARENT);
                            textView.setBackgroundColor(Color.TRANSPARENT);

                        }
                        thread=new Thread() {
                            public void run() {
                                Log.e("run", "run");
                                handler.sendEmptyMessage(1);
                                   }
                        };
                        thread.start();
                        imageView =findViewById(R.id.indonesia);
                        imageView.setBackgroundColor(Color.YELLOW);
                        textView=findViewById(R.id.indonesia_text);
                        textView.setBackgroundColor(Color.YELLOW);
                        intent.putExtra("countryName","인도네시아");
                        path=R.drawable.indonesia;

                        intent.putExtra("country",path);
                        doubleCheck=true;
                        break;


                    case R.id.country_ok:
                        if (textView==null){
                            Toast.makeText(getApplicationContext(),"선택된 값이 없습니다",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(intent);
                        break;
                }
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        img1.setOnClickListener(listener);
        img2.setOnClickListener(listener);
        belarus.setOnClickListener(listener);
        germany.setOnClickListener(listener);
        indonesia.setOnClickListener(listener);

        thread1.start();
        Log.e("create","country create");

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.e("start", "country start");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        flag=true;
        thread1=new Thread() {
            public void run() {

                String[] messages={"1위:스리랑카","2위:독일","3위:짐바브웨","4위:파나마","5위:키르기스스탄","6위:요르단","7위:인도네시아",
                        "8위:벨라루스","9위:상투메 프린시페","10위:벨리즈"};


                try {
                    int count=0;
                    for (int i=0; i<10; i++){
                        if(!flag) {
                            break;
                        }

                        handler1.sendMessage(Message.obtain(handler1,1,messages[i]));
                        if (i==9){
                            i=-1;
                        }
                        Log.e("run", "list");
                        Thread.sleep(2000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

           }
        };
        thread1.start();
        Log.e("restart", "country restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "country resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=false;
        Log.e("pause", "country pause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (textView!=null&&imageView!=null) {
            if (textView.getId() != 0) {
                outState.putInt("countryName", textView.getId());
                intent.putExtra("countryName",textView.getText());

            }

            if (imageView.getId() != 0) {
                outState.putInt("country", imageView.getId());
                intent.putExtra("country",path);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        flag=false;

        Log.e("stop", "country stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doubleCheck=false;
        Log.e("destroy", "country destroy");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                button4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ani));
        }
    };

    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String str=String.valueOf(msg.obj);
            country_rink.setText(str);
        }
    };

    Thread thread=new Thread() {
        public void run() {
            Log.e("run", "run");
            handler.sendEmptyMessage(1);
        }
    };


    Thread thread1=new Thread() {
        public void run() {

            String[] messages={"1위:스리랑카","2위:독일","3위:짐바브웨","4위:파나마","5위:키르기스스탄","6위:요르단","7위:인도네시아",
                    "8위:벨라루스","9위:상투메 프린시페","10위:벨리즈"};


            try {
                int count=0;
                for (int i=0; i<10; i++){
                    if(!flag) {
                        break;
                    }

                    handler1.sendMessage(Message.obtain(handler1,1,messages[i]));
                    if (i==9){
                        i=-1;
                    }
                    Log.e("run", "list");
                    Thread.sleep(2000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
}
