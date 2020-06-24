package com.example.third;


import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.third.Adapter.TravelContentsAdapter;
import com.example.third.Class.TravelContentsClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

//여행내용 작성 시 -> 여행 상세 내용 작성 부분

public class TravelContentsActivity extends AppCompatActivity implements Serializable{


    Gson gson;
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView=null;
    TravelContentsAdapter travelContentsAdapter=null;
    ArrayList<TravelContentsClass> travelContents=new ArrayList<TravelContentsClass>();

    static public Hashtable<Integer,ArrayList<TravelContentsClass>> travelContentsTest=new Hashtable<>();
    static int rewrite=-1;
    static int rewritePostion;

    public  Intent intent;

    Intent intent1;
    Intent intent55;

    private Button travel_route=null;

    private TextView view1=null;
    private TextView view2=null;
    private EditText view3=null;



    private TextView that_name=null;
    private TextView that_date=null;

    private ImageView travel_weather=null;
    private TextView travel_weather_text=null;
    private TextView travel_weather_text1=null;
    static int position=-1;
    int firstCreate=0;

    boolean flag=true;

    static int nextPage=-1;
    static String weather_api = null;


    public static MenuItem.OnMenuItemClickListener onCreateContextMenu;


        public interface ApiService{
            static final String units="metric";

            @GET("forecast?")
            Call<JsonObject> getHourly(@Query("lat")double lat,@Query("lon")double lon,@Query("units")String units,@Query("APPID")String weather_api);

        }


        ArrayList<String[]> getWeatherList=new ArrayList<>();
        String getNoWeather="";

    private void getWeather(double latitude, double longtitude){


        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();


        ApiService apiService=retrofit.create(ApiService.class);

        Call<JsonObject> call=apiService.getHourly(latitude,longtitude,ApiService.units,weather_api);



        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject object=response.body();

                    if (object!=null){

                        JsonArray jsonArray=object.getAsJsonArray("list");
                    //일기예보 가지고 오는 거
                        for (int i = 0; i <jsonArray.size() ; i++) {

                        //이때 날짜를 알 수 있는 부분이 dt_txt부분이며 앞서 list 안의 멤버변수로 존재.
                            String forecast=jsonArray.get(i).getAsJsonObject().get("dt_txt").getAsString();

                            //"를 담고 있기 때문에, substring으로 필요한 값만 뽑아내기
                            String[] forecastCheck=forecast.split(" ");

                            //클릭한 상세계획 일정 날짜와 맞는 일기 기상예보가 있는지 여부를 체크.
                            String[] date=that_date.getText().toString().split("/");

                            //값을 비교하기 위한 작업.
                            String checkDate=date[0]+"-"+date[1]+"-"+date[2];


                            //값을 비교하여 맞다면 -> 즉, 일기예측이 존재함을 의미함.
                            if (forecastCheck[0].equals(checkDate)){

                                JsonElement jsonElement=jsonArray.get(i).getAsJsonObject().get("weather");
                                JsonElement jsonElement1=jsonArray.get(i).getAsJsonObject().get("main");

                                String main=jsonElement.getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                                String icon=jsonElement.getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                                String dt_txt=jsonArray.get(i).getAsJsonObject().get("dt_txt").getAsString();


                                String[] dt_txt1=dt_txt.split(" ");


                                String[] dt_txt2=dt_txt1[1].split(":");



                                String temp=jsonElement1.getAsJsonObject().get("temp").getAsString();


                                String[] result=new String[4];
                                result[0]=main;
                                result[1]=icon;
                                result[2]=dt_txt2[0]+"시"+dt_txt2[1]+"분";
                                result[3]=temp;
                                getWeatherList.add(result);

                            }


                        }

                        if (getWeatherList.size()==0) {
                            getNoWeather = "일기예보가 존재하지 않습니다.";
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("실패","날씨 정보를 가지고 오는 것에 있어 실패");
            }
        });

    }


    //날씨와 관련된 class
    class WeatherCheck extends AsyncTask<Integer,String,Void>{

        @Override
        protected void onPreExecute() {
            travel_weather=findViewById(R.id.travel_weather);
            travel_weather_text=findViewById(R.id.travel_weather_text);
            travel_weather_text1=findViewById(R.id.travel_weather_text1);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            for (int i = 0; i < 5; i++) {

                //종료시키기
                if(isCancelled() == true){
                    break;
                }


                try {

                    //가지고온 날씨 데이터가 존재한다면
                    if (getWeatherList.size() != 0) {

                        //FOR문을 통해서 하나씩 값 뽑아서 체크 -> 일기 예보이므로 당일 포함 6일까지의 일기예보가 전달됨.
                        for (int j = 0; j < getWeatherList.size(); j++) {


                            String url="";
                            String main="";
                            switch (getWeatherList.get(j)[0]){
                                case "Thunderstorm":
                                    main="천둥";
                                    url="http://openweathermap.org/img/wn/11d@2x.png";
                                break;
                                case "Drizzle":
                                    main="보슬비";
                                    url="http://openweathermap.org/img/wn/10d@2x.png";
                                    break;
                                case "Rain":
                                    main="비";
                                    url="http://openweathermap.org/img/wn/09d@2x.png";
                                    break;
                                case "Snow":
                                    main="눈";
                                    url="http://openweathermap.org/img/wn/13d@2x.png";
                                    break;
                                case "Mist":
                                    main="옅은 안개";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Haze":
                                case "Fog":
                                    main="안개";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Dust":
                                    main="먼지";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Sand":
                                    main="모래";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Ash":
                                    main="화산재";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Squall":
                                case "Tornado":
                                    main="돌풍";
                                    url="http://openweathermap.org/img/wn/50d@2x.png";
                                    break;
                                case "Clear":
                                    main="쾌청한 날씨";
                                    url="http://openweathermap.org/img/wn/01d@2x.png";
                                    break;
                                case "Clouds":
                                    main="구름";
                                    url="http://openweathermap.org/img/wn/03d@2x.png";
                                    break;
                                default:
                                    main=getWeatherList.get(j)[0];
                                    url="";
                                    break;
                            }


                            //시,분  //메인  //기온 //아이콘 url
                            publishProgress(getWeatherList.get(j)[2],main,getWeatherList.get(j)[3],url);
                            Thread.sleep(1000);
                        }
                    }

                    //FOR문을 다 돌고나서도 없으면 일기예보가 존재하지 않는 것을 의미함.
                    if(!getNoWeather.equals("")&&getWeatherList.size() == 0){
                        publishProgress(getNoWeather);
                        Thread.sleep(2000);
                    }



                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();

                }

                if (i == 4) {
                    i = 0;
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


            //길이로 체크. 일기예보가 존재한다면 사이즈는 1보다 큼.
            if (values.length>1){

                //시,분  //메인  //기온  //아이콘
                travel_weather_text.setText(values[0]+" "+values[2]+"°C");
                travel_weather_text1.setText(values[1]);

                travel_weather.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(values[3]).into(travel_weather);

            }else{ //일기예보가 존재하지 않으면 전달되는 값은 1개밖에 없음.
                travel_weather_text1.setText(values[0]);
            }
        }
    }




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_write);

        weather_api=getString(R.string.weather_api_key);
        setTitle("여행 상세 계획 작성");
        nextPage=-1;

        //새로운 intent 생성
        intent = new Intent();

        //travellist에서 값을 가지고 넘어옴 -> 넘어오는 값: 앞서 여행 목록에서 선택된 position값과 여행 리스트 중 선택된 position값을 넘겨줌.
        intent1 = getIntent();


        that_name=findViewById(R.id.travel_that_name);
        that_date=findViewById(R.id.travel_that_day);

        //여행국가와 날짜를 보여주기 위해

        String name=intent1.getStringExtra("name");
        String date=intent1.getStringExtra("date");

        that_name.setText(name);
        that_date.setText(date);
        travel_weather_text=findViewById(R.id.travel_weather_text);

        //위도와 경도를 통해서 위치의 주소를 뽑아냄.
        Geocoder geocoder=new Geocoder(this);
        ArrayList<Address> list=null;
        double lat=0;
        double longt=0;
        try {
            list=(ArrayList<Address>)geocoder.getFromLocationName(name,1);
            lat=list.get(0).getLatitude();
            longt=list.get(0).getLongitude();
            getWeather(lat,longt);
        } catch (IOException e) {
            e.printStackTrace();
        }



        getStringArrayPref(intent1.getIntExtra("position",-1),intent1.getIntExtra("mylistposition",-1));
        if (view1 == null) {
            //view1에 클릭리스너... 지도와 연결
            view1 = findViewById(R.id.travel_todo);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1;
                    switch (v.getId()) {
                        case R.id.travel_todo:
                            intent1 = new Intent(getApplication(), TravelMapActivity.class);
                            intent1.putExtra("todo",view1.getText().toString());
                            intent1.putExtra("that_name",that_name.getText().toString());
                            startActivityForResult(intent1, 5);
                            nextPage=2;
                            break;

                    }

                }
            });

        }




        if (view2 == null) {
            //view2에 클릭리스너..시간 다이얼로그가 떠서 선택할 수 있음. .
            view2 = findViewById(R.id.travel_time);
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1;
                    switch (v.getId()) {
                        case R.id.travel_time:
                            intent1 = new Intent(getApplication(), TimePickActivity.class);
                            startActivityForResult(intent1, 1);
                            nextPage=2;
                            break;

                    }
                }
            });

        }


       travel_route=findViewById(R.id.travel_route);

        travel_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent55 = new Intent(getApplicationContext(), TravelMapRouteActivity.class);

                intent55.putExtra("that_name",that_name.getText().toString());

                intent55.putExtra("travel_route",(Serializable)travelContents);

                startActivity(intent55);



            }
        });


        //수정, 삭제의 기능 보유
        onCreateContextMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                rewritePostion = travelContentsAdapter.getItem(-1);
                if (item.getItemId() == 1) {

                    String time = travelContents.get(rewritePostion).travel_all_time;
                    String todo = travelContents.get(rewritePostion).travel_all_todo;
                    String detail = travelContents.get(rewritePostion).travel_all_detail;

                    view1 = findViewById(R.id.travel_todo);
                    view1.setText(todo);

                    view2 = findViewById(R.id.travel_time);
                    view2.setText(time);

                    view3 = findViewById(R.id.travel_detail);
                    view3.setText(detail);

                    rewrite=0;


                } else if (item.getItemId() == 2) {
                    travelContents.remove(rewritePostion);
                    travelContentsAdapter.notifyDataSetChanged();
                    removeStringArrayPref(intent1.getIntExtra("position",-1),intent1.getIntExtra("mylistposition",-1),rewritePostion);

                }
                return false;
            }
        };



            //확인을 눌렀을 때의 상황.
            Button travel_ok = findViewById(R.id.travel_ok);
            //메인으로 돌아가는 상황
            Button travel_ok_fianl = findViewById(R.id.travel_ok_final);

            Button.OnClickListener onClickListener=new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.travel_ok:
                            //확인을 누르면은 여행 계획 작성 중이던 것이 아래의 리스트에 저장된다.
                            view1 = findViewById(R.id.travel_todo);
                            view2 = findViewById(R.id.travel_time);
                            view3 = findViewById(R.id.travel_detail);


                            if (view1.getText().toString().isEmpty() && view2.getText().toString().isEmpty() && view3.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "아무값도 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                //travel 일정 작성 완료 후 -> 아래 하단에서 리스트를 볼 수 있게 하는 부분.

                                position=intent1.getIntExtra("position",-1);


                                if (rewrite==0){
                                    travelContents.get(rewritePostion).setTravel_all_time(view2.getText().toString());
                                    travelContents.get(rewritePostion).setTravel_all_detail(view3.getText().toString());
                                    travelContents.get(rewritePostion).setTravel_all_todo(view1.getText().toString());

                                    rewrite=-1;
                                }else if(rewrite==-1){
                                    travelContents.add(new TravelContentsClass(view2.getText().toString(),view1.getText().toString(),view3.getText().toString()));
                                    TravelListActivity.firstCreate=1;
                                }

                                travelContentsAdapter.notifyDataSetChanged();

                                //입력받은 값 저장시키기.
                                setStringArrayPref();

                                //저장을 하고 나면 새롭게 작성이 들어가야 하므로, 기존에 남아있던 값들을 ""로 바꿔준다.
                                view1.setHint("장소");
                                view1.setText("");
                                view2.setHint("시간");
                                view2.setText("");
                                view3.setText("");


                            }


                            break;
                        case R.id.travel_ok_final:
                            finish();
                            break;
                    }
                }
            };

            travel_ok.setOnClickListener(onClickListener);
            travel_ok_fianl.setOnClickListener(onClickListener);


            // 리사이클러뷰에 CheckListAdapter 객체 지정.
            recyclerView = findViewById(R.id.travel_write_recycler);
            travelContentsAdapter = new TravelContentsAdapter(travelContents);
            recyclerView.setAdapter(travelContentsAdapter);

            //이미지 뷰 넣는 객체 생성
            travel_weather=findViewById(R.id.travel_weather);

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            Log.e("create", "travelcontents create");
        }




    //시간을 받을 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==1){ //시간 다이얼로그에서 값을 받아올 때
                view2 = findViewById(R.id.travel_time);

                String hour=data.getExtras().get("hour").toString();

                //시간의 길이가 1일 때, 앞의 0을 붙여서 보여주기
                if (hour.length()==1){
                    hour="0"+hour;
                }
                String min=data.getExtras().get("min").toString();

                if (data.getExtras().get("min").toString().length()==1){
                    min="0"+data.getExtras().get("min").toString();
                }
                view2.setText(hour+" : "+min);
            }else if(requestCode==5){ //지도 다이얼로그에서 값을 받아올 때
                view1=findViewById(R.id.travel_todo);

                ArrayList<Address> list= (ArrayList<Address>) data.getExtras().get("location");

                String location_name=data.getStringExtra("location_name");


                view1.setText(location_name);
            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        flag=true;



        Log.e("restart", "travelcontents restart");
    }

    WeatherCheck weatherCheck=new WeatherCheck();
    @Override
    protected void onStart() {
        super.onStart();
        weatherCheck=new WeatherCheck();
        weatherCheck.execute(0);
        Log.e("start", "travelcontents start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "travelcontents resume");
    }

    @Override
    protected void onPause() {

        super.onPause();
       // weatherCheck.cancel(true);
        travelContentsAdapter.notifyDataSetChanged();
        Log.e("pause", "travelcontents pause");
    }


    @Override
    protected void onStop() {

        super.onStop();
        weatherCheck.cancel(true);
        firstCreate=0;


        Log.e("stop", "travelcontents stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstCreate=0;
        flag=false;
        Log.e("destroy", "travelcontents destroy");
    }


    //상세내용을 저장하는 부분

    JSONArray  jsonArray;

    private void setStringArrayPref() {
        gson=new Gson();


        Type listType = new TypeToken<ArrayList<TravelContentsClass>>(){}.getType();


        String  travelListString = gson.toJson(travelContents,listType);


        sharedPreferences = getSharedPreferences("travelListContents",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //id는 alltravellist index와 travellist의 index로 이루어짐
        String id=String.valueOf(intent1.getIntExtra("mylistposition", -1))+String.valueOf(intent1.getIntExtra("position",-1));


          //같은 key값이 존재하면 그 뒤에 이어서 값을 저장함. 단, jsonarray [{}] 유지하면서 추가
        if (sharedPreferences.contains(id)){

            travelListString = travelListString.replace("[", "");
            travelListString = travelListString.replace("]", "");


            String all = "[" +travelListString + "]";
           JSONArray jsonArray1 = null;
            try {
                jsonArray1 = new JSONArray(all);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            editor.putString(id, String.valueOf(jsonArray1));

        }else {
            firstCreate=1;
             editor.putString(id, String.valueOf(jsonArray));
        }
        editor.apply();

    }


    //저장한 값을 가지고 오는 메소드 (여행일자의 position, 나의 여행목록에 저장된 position)
    private void getStringArrayPref(int position, int mylistposition){
        gson=new Gson();

        sharedPreferences=getSharedPreferences("travelListContents",MODE_PRIVATE);


        //travelcontents의 key값
        String id=String.valueOf(mylistposition)+String.valueOf(position);
        String some=sharedPreferences.getString(id,"");


        Type listType = new TypeToken<ArrayList<TravelContentsClass>>(){}.getType();

        ArrayList<TravelContentsClass> list=gson.fromJson(some,listType);

        if (list!=null) {

            for (int i=0; i<list.size(); i++){
                travelContents.add(new TravelContentsClass(list.get(i).travel_all_time,list.get(i).travel_all_todo,
                        list.get(i).travel_all_detail));

            }
        }
    }

    //travelcontents 제거 메소드.(날짜별 position, 나의 여행목록 position, contents position)
    private void removeStringArrayPref(int position, int mylistposition, int rewritePostion) {

        //값 가지고 오기.
        sharedPreferences=getSharedPreferences("travelListContents",MODE_PRIVATE);
        SharedPreferences sharedPreferences1=getSharedPreferences("travelListShared",MODE_PRIVATE);
        String count=sharedPreferences1.getString(String.valueOf(mylistposition),"");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        //position값을 받아 삭제하기.
        String id=String.valueOf(mylistposition)+String.valueOf(position);

        String some=sharedPreferences.getString(id,"");
        try {
            JSONArray array=new JSONArray(some);
            //jsonarray로 전환
            JSONArray jsonrearray1 = array;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonrearray1.remove(rewritePostion);

                //내용자체가 없을 때
                if (jsonrearray1.length()==0){
                    //id값 자체를 삭제함.
                    editor.remove(id);
                    editor.commit();
                }else{
                    editor.putString(id, String.valueOf(jsonrearray1));
                    editor.apply();
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
