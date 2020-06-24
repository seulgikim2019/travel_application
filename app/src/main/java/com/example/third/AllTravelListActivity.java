package com.example.third;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.third.Adapter.AllTravelListTestAdapter;
import com.example.third.Adapter.TravelContentsAdapter;
import com.example.third.Class.AllTravelListClass;
import com.example.third.Class.TravelContentsClass;
import com.example.third.Class.TravelListClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

public class AllTravelListActivity extends AppCompatActivity {

    //recyclerview
    RecyclerView recyclerView = null;
    //adapter
    AllTravelListTestAdapter allTravelListTestAdapter = null;
    //data를 담을 arraylist
    ArrayList<AllTravelListClass> allTravelData = new ArrayList<>();
    //날씨 정보 gson
    Gson gson;
    private Intent intent;
    //날씨 정보 변동을 위해 thread 사용
    Thread thread;
    Boolean flag=true;
    static int nextPage=-1;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alltravellist);

        setTitle("나의여행목록");


                     nextPage=-1;
                    getStringArrayPref();

                    //값을 다 담고 나면, 리스트 생성
                    recyclerView = findViewById(R.id.alltravel_recycler);
                    allTravelListTestAdapter = new AllTravelListTestAdapter(allTravelData);
                    recyclerView.setAdapter(allTravelListTestAdapter);

                    DividerItemDecoration dividerItemDecoration =
                            new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getApplicationContext()).getOrientation());
                    RecyclerDecoration spaceDecoration = new RecyclerDecoration(25);

                    recyclerView.addItemDecoration(dividerItemDecoration);
                    recyclerView.addItemDecoration(spaceDecoration);

                    // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

                    //클릭 이벤트
                    allTravelListTestAdapter.setOnItemClickListener(new AllTravelListTestAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(AllTravelListTestAdapter.ViewHolder holder, View view, int position) {
                            SharedPreferences sharedPreferences = getSharedPreferences("travelListShared", MODE_PRIVATE);
                            intent = new Intent(getApplicationContext(), TravelListActivity.class);
                            intent.putExtra("position", String.valueOf(position));
                            intent.putExtra("json", sharedPreferences.getString(String.valueOf(position), ""));
                            nextPage=position;
                            startActivity(intent);
                        }
                    });


                    allTravelListTestAdapter.setOnCreateContextMenuListener(new AllTravelListTestAdapter.OnCreateContextMenuListener() {
                        @Override
                        public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo, final int position) {
                            MenuItem delete = menu.add(Menu.NONE, 1, 1, "삭제");
                            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    //travellist 저장한 값 접군 -> 해당 position 삭제
                                    allTravelData.remove(position);
                                    removeStringArrayPref(position);
                                    allTravelListTestAdapter.notifyItemRemoved(position);
                                    allTravelListTestAdapter.notifyDataSetChanged();
                                    return true;
                                }
                            });
                        }
                    });


        Log.e("create", "allTRAVELList create");


    }

    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                for (int i = 0; i < allTravelListTestAdapter.items.size(); i++) {

                    String name = allTravelListTestAdapter.items.get(i).getAll_travel_img_name();

                    switch (name) {
                        case "체코":
                            timeZone = TimeZone.getTimeZone("Europe/Prague");
                            simpleDateFormat.setTimeZone(timeZone);
                            dateTime = new Date();

                            break;

                        case "러시아":
                            timeZone = TimeZone.getTimeZone("Europe/Moscow");
                            simpleDateFormat.setTimeZone(timeZone);
                            dateTime = new Date();

                            break;
                        case "벨라루스":
                            timeZone = TimeZone.getTimeZone("Europe/Minsk");
                            simpleDateFormat.setTimeZone(timeZone);
                            dateTime = new Date();
                            break;
                        case "독일":
                            timeZone = TimeZone.getTimeZone("Europe/Berlin");
                            simpleDateFormat.setTimeZone(timeZone);
                            dateTime = new Date();
                            break;
                        case "인도네시아":
                            timeZone = TimeZone.getTimeZone("Asia/Jakarta");
                            simpleDateFormat.setTimeZone(timeZone);
                            dateTime = new Date();
                            break;
                    }

                    allTravelListTestAdapter.items.get(i).setAlltravel_time(simpleDateFormat.format(dateTime));
                }
                allTravelListTestAdapter.notifyDataSetChanged();
            }

    };
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
        thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    while (flag){

                        handler.sendEmptyMessage(0);
                        Thread.sleep(5000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };



        thread.setDaemon(true);
        thread.start();
        Log.e("start", "alltravellist start");
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        flag=true;
        allTravelListTestAdapter.notifyDataSetChanged();

        Log.e("restart", "travel restart");
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "travel resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=false;
        Log.e("pause", "alltravel pause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        flag=false;
        allTravelListTestAdapter.notifyDataSetChanged();


        Log.e("stop", "ALLtravel stop");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        Log.e("destroy", "ALLtravel destroy");
    }



    TimeZone timeZone;
    Date dateTime=new Date();
    DateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
    //travellist 저장한 값 가지고 오기.
    private void getStringArrayPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("travelListShared", MODE_PRIVATE);


        gson = new Gson();
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < sharedPreferences.getAll().size(); i++) {
            list.add(sharedPreferences.getString(String.valueOf(i), ""));
        }


        ArrayList<AllTravelListClass> test1 = new ArrayList<>();


        TravelListClass test[];

        for (int j = 0; j < list.size(); j++) {

            test = gson.fromJson(list.get(j), TravelListClass[].class);

            if (test != null) {
                for (int k = 0; k < test.length; k++) {

                    if (k == test.length - 1) {
                        String date = test[0].getTravel_date1() + "~" + test[k].getTravel_date1();

                        String name = test[k].getTravel_img_name();
                        switch (name) {
                            case "체코":
                                timeZone = TimeZone.getTimeZone("Europe/Prague");
                                simpleDateFormat.setTimeZone(timeZone);
                                dateTime = new Date();
                                break;

                            case "러시아":
                                timeZone = TimeZone.getTimeZone("Europe/Moscow");
                                simpleDateFormat.setTimeZone(timeZone);
                                dateTime = new Date();
                                break;
                            case "벨라루스":
                                timeZone = TimeZone.getTimeZone("Europe/Minsk");
                                simpleDateFormat.setTimeZone(timeZone);
                                dateTime = new Date();
                                break;
                            case "독일":
                                timeZone = TimeZone.getTimeZone("Europe/Berlin");
                                simpleDateFormat.setTimeZone(timeZone);
                                dateTime = new Date();
                                break;
                            case "인도네시아":
                                timeZone = TimeZone.getTimeZone("Asia/Jakarta");
                                simpleDateFormat.setTimeZone(timeZone);
                                dateTime = new Date();
                                break;

                            default:

                                break;
                        }
                        test1.add(j, new AllTravelListClass(date, test[k].getTravel_img(), test[k].getTravel_img_name(), simpleDateFormat.format(dateTime)));


                    }

                }

            }


         allTravelData = test1;

        }

    }



    private void removeStringArrayPref(int position) {

        //값 가지고 오기.
        SharedPreferences sharedPreferences = getSharedPreferences("travelListShared", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("travelListContents", MODE_PRIVATE);

        //여기서 알 수 있는 것은 position 값 하나이나, sharedPreference를 통해 갯수를 알아온다.
        String numString = sharedPreferences.getString(String.valueOf(position), "");
        int num = 0;
        try {
            JSONArray jsonArray = new JSONArray(numString);
            num = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //리스트를 하나 만들어서, 삭제 되기 전에 모든 값.
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < sharedPreferences.getAll().size(); i++) {
            list.add(sharedPreferences.getString(String.valueOf(i), ""));
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        gson = new Gson();

        //position값을 받아 삭제하기.
        editor.remove(String.valueOf(position));
        //제거된 값 반영되도록
        editor.apply();

        //리스트 선언  -> travellist 에 해당.
        //삭제된 포지션 다음부터 값들을 넣어서 전달.
        ArrayList<String> listt = new ArrayList<>();
        ArrayList<TravelListClass> travelList;

        for (int j = 0; j < list.size(); j++) {

            //삭제된 포지션인지 확인.
            if (j == position) {


                for (int t = j + 1; t < list.size(); t++) {
                    listt.add(list.get(t));
                }

                //앞으로 당겨서 데이터에 집어넣기
                int su = position;
                TravelListClass[] test = null;
                for (int i = 0; i < listt.size(); i++) {
                    //새롭게 리스트가 갱신되지 않으면 이전의 값을 가지고 이어서 저장되므로 객체 생성.
                    travelList = new ArrayList<>();

                    //리스트를 class에 맞게 출력
                    test = gson.fromJson(listt.get(i), TravelListClass[].class);

                    //자신이 가지고 있는 날짜 수만큼 돌아감
                    for (int k = 0; k < test.length; k++) {
                        travelList.add(k, new TravelListClass(test[k].travel_date1, test[k].travel_list_check, test[k].travel_img, test[k].travel_img_name));

                        Type listType = new TypeToken<ArrayList<TravelListClass>>() {
                        }.getType();


                        String travelListString = gson.toJson(travelList, listType);



                        //마지막으로 들어가고 나면은 editor에 저장.
                        if (k == test.length - 1) {

                           //포지션 값부터 넣어줘야 함. 당기는 역할.
                            editor.putString(String.valueOf(su), travelListString);

                            //포지션 이후부터 또 들어가야 하므로 값을 증가 시켜 줌.
                            su++;

                            //적용
                            editor.apply();
                        }


                    }


                }


                //마지막에 있는 리스트는 삭제
                int remove = list.size() - 1;
                editor.remove(String.valueOf(remove));
                editor.apply();


                break;
            }

        }


        //key값이 index+index로 이루어 졌으므로, position뒤는 갯수를 알아내어 돌리면서 삭제.
        for (int i = 0; i < num; i++) {
            String that = position + String.valueOf(i);
            editor1.remove(that);
            editor1.apply();
        }



        //추후에 리스트를 삭제하고 여행 상세 계획 부분도 파일로 저장되어 있으므로 수정.
        ArrayList<String> list1 = new ArrayList<>();
        Set set = sharedPreferences1.getAll().keySet();


        String key = set.toString();
        key = key.replace("[", "");
        key = key.replace("]", "");
        key = key.replace(" ", "");
        String[] keyset = key.split(",");

        TreeSet<Integer> treeSet = new TreeSet<>();
        TreeSet<Integer> treeSet2 = new TreeSet<>();
        if (keyset.length > 0) {
            for (int i = 0; i < keyset.length; i++) {
                //숫자로 변환해서 넣게되면 앞에 0을 제외하고 넣게 된다! 이거 참고!!!

                if (keyset[i] != "" || !keyset[i].equals("")) {
                    treeSet.add(Integer.parseInt(keyset[i]));
                    treeSet2.add(Integer.parseInt(keyset[i]));

                }

            }

        }


        //콘텐츠 선언  -> travelContents 에 해당.
        //삭제된 포지션 다음부터 값들을 넣어서 전달.

        String key1 = set.toString();
        key1 = key1.replace("[", "");
        key1 = key1.replace("]", "");
        String[] keyset1 = key.split(",");

        TreeSet<Integer> treeSet1 = new TreeSet<>();

        if (keyset1.length > 0) {
            for (int i = 0; i < keyset1.length; i++) {
                //숫자로 변환해서 넣게되면 앞에 0을 제외하고 넣게 된다! 이거 참고!!!

                if (keyset1[i] != "" || !keyset1[i].equals("")) {
                    treeSet1.add(Integer.parseInt(keyset1[i]));
                }

            }

        }

        //treeset으로 key값을 정렬해서 넣어주기. 그래야지 이후에 수정이 편리함.
        //treeset은 기본 오름차순 정렬.
        Iterator iterator3 = treeSet.iterator();
        String that = null;
        while (iterator3.hasNext()) {

            // 1. 오류 발생:java.util.NoSuchElementException
            // 원인: 하단에 treeset의 마지막 번호를 다시 한 번 호출하여서( 같은 값 2번 호출)
            // 해결방법: 마지막 값만 담은 변수를 담을 수 있도록 local 변수로 만들어 줌.
            that = String.valueOf(iterator3.next());
            if (that.length() == 1) {
                that = "0" + that;
            }

            //삭제된 리스트 뒤에도 리스트가 있는 경우에만 앞의 값으로 이동해야 하므로
            if (position < Integer.parseInt(that.substring(0, 1))) {
                Log.e("뒤에값을하나씩앞으로", that.substring(0, 1));

                int su1 = Integer.parseInt(that.substring(0, 1)) - position;
                int su = Integer.parseInt(that.substring(0, 1)) - su1;
                String lastSu = su + that.substring(1);
                //앞의 값만 -position 만큼 빼주면 된다.
                //그리고 원래 값을 넣어준다.
                editor1.putString(lastSu, sharedPreferences1.getString(that, ""));
                editor1.apply();
            }


        }


        if (that != null) {
            String last = that.substring(0, 1);
            //이미 위의 리스트는 삭제가 되고 반영이 되었을 것이므로
            //만약 반영이 되기전이면 last그대로 진행.
            String lastString="";
            if (Integer.parseInt(last)==0){
                lastString = sharedPreferences.getString(String.valueOf(Integer.parseInt(last)), "");
            }else{
                lastString = sharedPreferences.getString(String.valueOf(Integer.parseInt(last) - 1), "");
            }

            int lastnum = 0;
            try {
                JSONArray jsonArray = new JSONArray(lastString);
                lastnum = jsonArray.length();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //key값이 index+index로 이루어 졌으므로, position뒤는 갯수를 알아내어 돌리면서 삭제.
            for (int i = 0; i < lastnum; i++) {
                String that1 = last + i;

                //상세계획의 경우 작성하지 않을 수도 있기 때문에, 그냥 마지막 값을 제거해 버리면 삭제한 포지션 이전의 값이 삭제될 가능성 있음.
                //따라서 마지막값이 삭제된 포지션의 key의 1번째 부분과 같다면 삭제를 진행하도록 하는 조건문.
                if(last.equals(that.substring(0,1))&&position<Integer.parseInt(last)){
                    editor1.remove(that1);
                    editor1.apply();
                }else{
                    Log.e("기존에저장된값",that+"이 존재하지 않습니다.");
                }


            }



        }


    }

}



