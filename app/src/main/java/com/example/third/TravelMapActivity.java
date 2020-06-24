package com.example.third;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.third.Class.TravelContentsClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class TravelMapActivity extends AppCompatActivity implements OnMapReadyCallback, Serializable {


    GoogleMap googleMap;
    Geocoder geocoder;
    Button map_find;
    Button map_ok;
    EditText map_location;
    String that_country="";


    static class TravelMapFragment extends Fragment{
        public TravelMapFragment newInstance(){
            TravelMapFragment fragment=new TravelMapFragment();
            return fragment;
        }
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_map);

        setTitle("여행 상세 계획_장소 선택");


        //todo라는 이름으로 가지고 오는 intent결과값이 있는 경우 -> 즉, 수정을 할 때를 의미함.
        Intent intent=getIntent();


        SupportMapFragment supportMapFragment= (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);




        map_location=findViewById(R.id.map_location);

        if (intent!=null){

            //수정하는 곳에서 값을 받아올 때

                map_location.setText(intent.getStringExtra("todo"));
                that_country=intent.getStringExtra("that_name");
        }

        map_find=findViewById(R.id.map_find);
        map_ok=findViewById(R.id.map_ok);



        Log.e("create", "map create");
    }

    //위치의 위도 경도 받는 변수
    LatLng latLng=null;
    ArrayList<Address> address=new ArrayList<>();

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        geocoder=new Geocoder(this);




        if (!map_location.getText().toString().equals("")){
            //처음에 맵을 켰을 때, 여행국가로 위치 설정

            String location=map_location.getText().toString();
            try {
                address= (ArrayList<Address>) geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }


            latLng = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());

            //새롭게 받아 온 위도 경도로 지도의 위치를 조정
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

            //상세 주소를 담을 확률이 크므로 줌의 정도는 앞서보다 높은 15로 설정.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            //mark를 넣어서 선택된 위치의 대략적인 위치를 확인할 수 있도록 설정.
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(latLng);
            //우선은 주소 전체를 가지고 옴.
            markerOptions.title(address.get(0).getAddressLine(0));
            googleMap.addMarker(markerOptions);


        }else{
            //처음에 맵을 켰을 때, 여행 국가로 위치 설정


            try {
                address= (ArrayList<Address>) geocoder.getFromLocationName(that_country,1);
                latLng = new LatLng(address.get(0).getLatitude(),address.get(0).getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   Log.e("latlng1",String.valueOf(latLng.latitude));

            //위도 경도 지도에 넣기
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));
            //줌
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(5));

        }



        //검색 버튼을 눌렀을 경우
        map_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (map_location.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"입력한 장소가 없습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                //검색창에서 text를 가지고 옴.
                String location=map_location.getText().toString();

                try {

                    //검색한 값의 갯수를 설정할 수 있음. 우선은 한개의 주소만 받아오게 설정.
                    address= (ArrayList<Address>) geocoder.getFromLocationName(location,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //받아오는 주소값이 있는 경우, 지도의 위치를 재설정.
                if (address.size()!=0) {

                    //앞서 주소를 받아서 geocoder로 돌린 부분을 위도 경도 변수에 저장.
                    latLng = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());


                    //새롭게 받아 온 위도 경도로 지도의 위치를 조정
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                    //상세 주소를 담을 확률이 크므로 줌의 정도는 앞서보다 높은 15로 설정.
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    //mark를 넣어서 선택된 위치의 대략적인 위치를 확인할 수 있도록 설정.
                    MarkerOptions markerOptions=new MarkerOptions();
                    markerOptions.position(latLng);
                    //우선은 주소 전체를 가지고 옴.
                    markerOptions.title(address.get(0).getAddressLine(0));
                    googleMap.addMarker(markerOptions);

                }else{
                    Toast.makeText(getApplicationContext(),"입력한 장소를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }



        });


        //검색 기능 완료 후, 클릭이 완성되면 넘겨주기.

        map_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if (!map_location.getText().toString().equals("")){

                if (address.size()==0) {
                    Toast.makeText(getApplicationContext(),"입력한 장소를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }


                    Intent intent=new Intent();
                    intent.putExtra("location",address);
                    intent.putExtra("location_name",map_location.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"입력하신 값이 없습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });




    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("restart", "map restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "map start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "map resume");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.e("pause", "map pause");
    }


    @Override
    protected void onStop() {

        super.onStop();


        Log.e("stop", "map stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "map destroy");
    }


}
