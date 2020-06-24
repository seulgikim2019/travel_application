package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.example.third.Adapter.TravelListTestAdapter;
import com.example.third.Class.TravelListClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TravelListActivity extends AppCompatActivity {


    RecyclerView recyclerView = null;
    TravelListTestAdapter travelListAdapter = null;
    ArrayList<TravelListClass> travelData;
    //날짜들을 저장할 빈 ArrayList 객체 생성.
    ArrayList<String> dates = new ArrayList<String>();

    private Intent intent;
    private Intent intent1;
    private int number;

    public static int getposition=-1;
    private int getposition1=-1;
    public static int getposition2=-1;


    private String countryName;
    private int country;



    Gson gson;
    static int firstCreate = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.travellist);


        setTitle("여행 리스트");

        if(travelData==null){
            travelData=new ArrayList<>();
        }


        //1calendar까지 넘어온 값을 가지고 온다.

        //2나의 여행 목록에서 넘겨주는 값 -> position 값과 json->즉, 저장소에 저장되어 있는 이미지,날짜 등을 넘겨준다.
        intent = getIntent();

        //달력에서 넘어온 값을 받는 경우
        if (intent.getStringExtra("countryName")!=""&&intent.getIntExtra("country", 0)!=0){
            countryName = intent.getStringExtra("countryName");
            country = intent.getIntExtra("country", 0);


            //사이에 있는 날짜 계산하는 식.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            //두 날짜 문자열.
            // 지정한 날짜 패턴의 형태로 두 날짜를 지정.
            // 시작 날짜보다 끝 날짜보다 이전일 수 없다고 가정.
            Date startDate = null;
            Date endDate = null;
            try {
                //SimpleDateFormat 객체의 parse 메소드.
                // 두 String 형태의 날짜를 Date 형태의 날짜로 변경.
                // parse 메소드는 ParseException
                startDate = sdf.parse(intent.getStringExtra("startDate"));
                endDate = sdf.parse(intent.getStringExtra("lastDate"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            Date currentDate = startDate;

            //Date 객체의 compareTo 메소드 -> 두 날짜의 크기 비교.
            // X.compareTo(Y) 의 형태로 사용 -> 결과는 1, 0, -1 3 가지 중 하나로 반환.
            // 1 은 X > Y, 0 은 X == Y, -1 은 X < Y 의 경우.
            // 두 Date 객체를 밀리세컨드 기준으로 크기를 비교.
            // currentDate.compareTo(endDate) <= 0 의 의미는 currentDate <= endDate 의 의미.
            // 즉, currentDate 가 끝 날짜를 넘지 않는 동안 반복.

            while (currentDate.compareTo(endDate) <= 0) {
                dates.add(sdf.format(currentDate));
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();
            }


            for (String date : dates) {
            //    Log.e("here", date);
             //   Log.e("country",String.valueOf(country));

                travelData.add(new TravelListClass(date, "",
                        intent.getIntExtra("country", 0),
                        intent.getStringExtra("countryName")));
            }

            SharedPreferences sharedPreferences2=getSharedPreferences("travelListShared",MODE_PRIVATE);
            //새롭게 생성된 리스트의 key값을 일치시키기 위해.
            //adapter에서 값의 변화를 인지해서 넣을 수 있도록
            getposition1=sharedPreferences2.getAll().size();
            getposition2=sharedPreferences2.getAll().size();

        //    Log.e("getPosition",String.valueOf(getposition2));

            //값을 다 담고 나면, 리스트 생성
            recyclerView = findViewById(R.id.travel_recycler);
            travelListAdapter = new TravelListTestAdapter(travelData);
            recyclerView.setAdapter(travelListAdapter);

            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getApplicationContext()).getOrientation());
            TravelListActivity.RecyclerDecoration spaceDecoration = new TravelListActivity.RecyclerDecoration(25);

            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.addItemDecoration(spaceDecoration);

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));



        }else{ //마이여행목록에서 넘겨주는 값을 받는 경우


            //마이여행목록에 담겨있는 것은 새롭게 생성된 것이 아니므로. -1 음수값을 주어
            //기존에 있는 여행 목록임을 알려줌.
            getposition=Integer.parseInt(intent.getStringExtra("position"));

            //나의 여행 목록에서의 index 번호.
            getposition1=Integer.parseInt(intent.getStringExtra("position"));
            getposition2=Integer.parseInt(intent.getStringExtra("position"));



            gson=new Gson();
            String result=intent.getStringExtra("json");
          //  ArrayList<TravelListClass> list = new ArrayList<>();
            Type listType = new TypeToken<ArrayList<TravelListClass>>(){}.getType();

            ArrayList<TravelListClass> list=gson.fromJson(result,listType);

            //string값의 배열로 가져온 것을 traveldata로 넣어줌.
            travelData=list;

            //날짜가 몇개가 있는지 받아옴.
           // Log.e("???", String.valueOf(check));
            //값을 다 담고 나면, 리스트 생성
            recyclerView = findViewById(R.id.travel_recycler);
            travelListAdapter = new TravelListTestAdapter(travelData);
            recyclerView.setAdapter(travelListAdapter);


            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getApplicationContext()).getOrientation());
            TravelListActivity.RecyclerDecoration spaceDecoration = new TravelListActivity.RecyclerDecoration(25);

            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.addItemDecoration(spaceDecoration);
            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));



        }




        //클릭 이벤트
        travelListAdapter.setOnItemClickListener(new TravelListTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TravelListTestAdapter.ViewHolder holder, View view, int position) {
                number = travelListAdapter.getItem(position);
          //     Log.e("travlelistadpater", holder.travel_img_name.getText().toString()); Log.e("??", holder.travel_date.getText().toString());
                intent = new Intent(getApplicationContext(), TravelContentsActivity.class);

                intent.putExtra("mylistposition", getposition1);

                intent.putExtra("position", number);

                intent.putExtra("name",holder.travel_img_name.getText().toString());

                intent.putExtra("date",holder.travel_date.getText().toString());

                startActivity(intent);

            }
        });


        Log.e("create", "TRAVELList create");


    }
    //recyclerview 경계선이랑 view 간격 조정
    public class RecyclerDecoration extends RecyclerView.ItemDecoration {

        private final int divHeight;


        public RecyclerDecoration(int divHeight) {
            this.divHeight = divHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)

                outRect.bottom = divHeight;

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "travel start");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        travelListAdapter.notifyDataSetChanged();
        Log.e("start", "travel restart");
    }

    @Override
    protected void onResume() {
        travelListAdapter.notifyDataSetChanged();
        super.onResume();
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
        firstCreate = 1;
        super.onDestroy();
        Log.e("destroy", "travel destroy");
    }




}