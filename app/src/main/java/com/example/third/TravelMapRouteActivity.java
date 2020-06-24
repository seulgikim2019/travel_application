package com.example.third;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.third.Class.TravelContentsClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class TravelMapRouteActivity extends AppCompatActivity implements OnMapReadyCallback, Serializable {


    GoogleMap googleMap;
    Geocoder geocoder;
    String that_country="";

    ArrayList<Address> address=null;
    ArrayList<TravelContentsClass> travel_all=null;


    static class TravelMapFragment extends Fragment{
        public TravelMapFragment newInstance(){
            TravelMapFragment fragment=new TravelMapFragment();
            return fragment;
        }
    }



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_route_map);

        setTitle("여행 경로");


        that_country=getIntent().getStringExtra("that_name");
        travel_all= (ArrayList<TravelContentsClass>) getIntent().getSerializableExtra("travel_route");

        SupportMapFragment supportMapFragment= (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map_route);
        supportMapFragment.getMapAsync(this);




        Log.e("create", "rmap create");
    }



    LatLng latLng=null;
    MarkerOptions markerOptions=new MarkerOptions();
    PolylineOptions polylineOptions=new PolylineOptions();
    ArrayList<LatLng> latLngs=new ArrayList<>();
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        geocoder=new Geocoder(this);

        polylineOptions.color(Color.RED);
        polylineOptions.width(5);

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



        if (travel_all!=null) {


            //처음에 지도 위치가 있는 것을 기점으로 지도를 이동함.
            for (int i = 0; i < travel_all.size(); i++) {
                Log.e("travel_all_route", travel_all.get(i).getTravel_all_todo());

                String location = travel_all.get(i).getTravel_all_todo();

                try {

                    //검색한 값의 갯수를 설정할 수 있음. 우선은 한개의 주소만 받아오게 설정.

                    if (!location.equals("")) {
                        address = (ArrayList<Address>) geocoder.getFromLocationName(location, 1);
                        latLng=new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                        //위도 경도 지도에 넣기
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                        //줌 10으로 설정

                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                        //따라서 하나의 위치만 얻으면 해당 for문을 나가게 함.
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




        //마크를 표시해주는 for문
            for (int i = 0; i <travel_all.size() ; i++) {
                Log.e("travel_all_route",travel_all.get(i).getTravel_all_todo());

                String location=travel_all.get(i).getTravel_all_todo();

                try {

                    //검색한 값의 갯수를 설정할 수 있음. 우선은 한개의 주소만 받아오게 설정.

                    if (!location.equals("")){


                        address= (ArrayList<Address>) geocoder.getFromLocationName(location,1);

                        //mark를 넣어서 선택된 위치의 대략적인 위치를 확인할 수 있도록 설정.
                        //맵을 시작할 때 마다 값을 받아와서 그때그때 MARK 를 형성하도록 설정.

                            Log.e("LATLNGS",address.get(0).getLatitude()+":"+address.get(0).getLongitude());

                            LatLng latLng=new LatLng(address.get(0).getLatitude(),address.get(0).getLongitude());
                            latLngs.add(0,latLng);
                            markerOptions.position(latLng);
                            //우선은 주소 전체를 가지고 옴.
                            markerOptions.title((i+1)+"."+travel_all.get(i).getTravel_all_todo()).getInfoWindowAnchorU();



                            if (i==0){
                                googleMap.addMarker(markerOptions).showInfoWindow();
                            }else{
                                googleMap.addMarker(markerOptions);
                            }







                    }else{
                        Log.e("null","위도 경도를 찾을 수 없으니까...");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            polylineOptions.addAll(latLngs);
            googleMap.addPolyline(polylineOptions);


        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("restart", "rmap restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "rmap start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "rmap resume");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.e("pause", "rmap pause");
    }


    @Override
    protected void onStop() {

        super.onStop();


        Log.e("stop", "rmap stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "rmap destroy");
    }


}
