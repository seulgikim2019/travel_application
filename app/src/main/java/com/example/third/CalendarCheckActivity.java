package com.example.third;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalendarCheckActivity extends AppCompatActivity{


    CalendarView calendarView;
    CalendarView calendarView2;

    //국가명 보여주기 위해
    TextView calendar_countryName;

    private String countryName;
    private int country;
    private int countNum=0;

    //시작일과 종료일 날짜 담기
    private String[] dateCheck=new String[2];

    Calendar calendar1=Calendar.getInstance();
    Calendar calendar2=Calendar.getInstance();

    Intent intent1;
    Intent intent;


    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendar);


        setTitle("여행 일정 선택");
        intent1 = getIntent();

        //선택한 국가명 보여주기 위한 코드
        calendar_countryName = findViewById(R.id.calendar_countryName);
        calendar_countryName.setText(intent1.getStringExtra("countryName"));


        //저장한 값이 있는 경우
        if (savedInstanceState != null) {

            //시작일
            dateCheck[0] = savedInstanceState.getString("startDate");
            //종료일
            dateCheck[1] = savedInstanceState.getString("lastDate");


            //시작일이 존재하는 경우
            if (dateCheck[0] != null) {

                //종료일 선택할 수 있도록 이동
                setContentView(R.layout.calendar2);
                calendarView2=findViewById(R.id.calendarView2);

                //선택한 국가명 보여주기 위한 코드
                calendar_countryName = findViewById(R.id.calendar_countryName);
                calendar_countryName.setText(intent1.getStringExtra("countryName"));
                //시작일을 보여주기 위한 코드
                TextView startDate=findViewById(R.id.calendar_startdate);
                startDate.setText(dateCheck[0]);

                //저장.
                button=findViewById(R.id.btn_dateSave);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (dateCheck[0]!=null&&dateCheck[1]!=null) {

                            intent = new Intent(getApplication(), TravelListActivity.class);

                            intent.putExtra("countryName",countryName);
                            intent.putExtra("country",country);
                            Log.e("countryName",countryName);
                            Log.e("country", String.valueOf(country));
                            intent.putExtra("startDate", dateCheck[0]);
                            intent.putExtra("lastDate", dateCheck[1]);

                            long firstDay = calendar1.getTimeInMillis()/86400000;
                            long lastDay = calendar2.getTimeInMillis()/86400000;
                            long countDay = lastDay-firstDay;

                            intent.putExtra("daySu",String.valueOf(countDay));

                            //   Log.e("check", String.valueOf(countDay+1));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            CountryCheckActivity.countryActivity.finish();
                            startActivity(intent);

                            finish();
                        }else{
                            Toast.makeText(getApplication(),"여행날짜를 선택해주셔야 합니다.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });



                Button button1=findViewById(R.id.btn_main);
                button1.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getApplication(), LoginSuccessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });



                Button button2=findViewById(R.id. btn_dateBefore2);
                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getApplication(), CalendarCheckActivity.class);
                        intent.putExtra("countryName",countryName);
                        intent.putExtra("country",country);
                        startActivity(intent);
                        finish();
                    }
                });



                calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                        countNum+=2;

                        if (countNum==2) {
                            calendar2.set(year, month, dayOfMonth);

                            dateCheck[countNum - 1] = year + "/" + (month + 1) + "/" + dayOfMonth;

                        }

                    }
                });


                Toast.makeText(this, "시작일이 존재합니다.", Toast.LENGTH_LONG).show();
            }

            if (dateCheck[1] != null) {
                Toast.makeText(this, "종료일이 존재합니다.", Toast.LENGTH_LONG).show();
            }
        }else{


            calendarView=findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                    countNum++;

                    if (countNum==1) {
                        calendar1.set(year, month, dayOfMonth);
                        dateCheck[countNum - 1] = year + "/" + (month + 1) + "/" + dayOfMonth;


                        setContentView(R.layout.calendar2);
                        calendarView2=findViewById(R.id.calendarView2);

                        SimpleDateFormat simpleDateFormat3=new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date date=simpleDateFormat3.parse(year + "/" + (month + 1) + "/" + dayOfMonth);
                            long date1=date.getTime();
                            calendarView2.setMinDate(date1);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        //선택한 국가명 보여주기 위한 코드
                        calendar_countryName = findViewById(R.id.calendar_countryName);
                        calendar_countryName.setText(intent1.getStringExtra("countryName"));
                        //시작일을 보여주기 위한 코드
                        TextView startDate=findViewById(R.id.calendar_startdate);
                        startDate.setText(dateCheck[0]);


                        //저장
                        button=findViewById(R.id.btn_dateSave);

                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                if (dateCheck[0]!=null&&dateCheck[1]!=null) {

                                    intent = new Intent(getApplication(), TravelListActivity.class);

                                    intent.putExtra("countryName",intent1.getStringExtra("countryName"));
                                    intent.putExtra("country",intent1.getIntExtra("country",0));

                                    intent.putExtra("startDate", dateCheck[0]);
                                    intent.putExtra("lastDate", dateCheck[1]);

                                    long firstDay = calendar1.getTimeInMillis()/86400000;
                                    long lastDay = calendar2.getTimeInMillis()/86400000;
                                    long countDay = lastDay-firstDay;

                                    intent.putExtra("daySu",String.valueOf(countDay+1));

                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    CountryCheckActivity.countryActivity.finish();
                                    startActivity(intent);

                                    finish();
                                }else{
                                    Toast.makeText(getApplication(),"여행날짜를 선택해주셔야 합니다.",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });


                        //종료일 결정 -> 메인돌아가는버튼
                        Button button1=findViewById(R.id.btn_main);
                        button1.setOnClickListener(new View.OnClickListener() {

                            Intent intent;
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(getApplication(), LoginSuccessActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });


                        //시작일을 재설정하기 위해 돌아가는 버튼
                        Button button2=findViewById(R.id. btn_dateBefore2);
                        button2.setOnClickListener(new View.OnClickListener() {
                            Intent intent;
                            @Override
                            public void onClick(View v) {
                                Log.e("여기로와?","응");
                                finish();
                                intent = new Intent(getApplication(), CalendarCheckActivity.class);
                                intent.putExtra("countryName",intent1.getStringExtra("countryName"));
                                intent.putExtra("country",intent1.getIntExtra("country",0));
                                startActivity(intent);

                            }
                        });


                        //종료일 선택시 -> 값을 저장하기
                        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                                countNum++;

                                if (countNum>=3){
                                    countNum=2;
                                }


                                if (countNum==2) {
                                    calendar2.set(year, month, dayOfMonth);
                                    dateCheck[countNum - 1] = year + "/" + (month + 1) + "/" + dayOfMonth;

                                }

                                thread=new Thread(){
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(1);
                                    }
                                };
                                thread.start();
                            }
                        });


                    }


                }
            });



        }


        //시작일 -> 메인이동 버튼


        Button button1=findViewById(R.id.btn_main);
        button1.setOnClickListener(new View.OnClickListener() {

            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplication(), LoginSuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });



        //시작일 -> 이전 이동 버튼(국가선택으로 가기)
        Button button2=findViewById(R.id. btn_dateBefore2);
        button2.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplication(),CountryCheckActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });





        Log.e("create","cal create");



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "cal start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart", "cal restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "cal resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "cal pause");
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        //startDate 날짜가 있는 경우
        if (dateCheck[0] != null) {
            outState.putString("startDate",dateCheck[0]);
        }


        //startDate 날짜가 없는 경우
        if (dateCheck[1] != null) {
            outState.putString("lastDate",dateCheck[1]);
        }


        countryName=intent1.getStringExtra("countryName");
        country=intent1.getIntExtra("country",0);




    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            button.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ani));

        }
    };

    Thread thread=new Thread(){
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };



    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "cal stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "cal destroy");
    }


}
