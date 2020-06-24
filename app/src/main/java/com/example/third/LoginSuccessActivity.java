package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;

public class LoginSuccessActivity extends AppCompatActivity{

    Intent intent;
    Intent intent1;
    Button changePassBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_success);



            Switch sw=findViewById(R.id.switch1);
            changePassBtn=findViewById(R.id.changePass);
            ImageView imageView1 = findViewById(R.id.card1);
            ImageView imageView2 = findViewById(R.id.card2);
            ImageView imageView3 = findViewById(R.id.card3);
            ImageView imageView4 = findViewById(R.id.card4);


        String a=getEnrollShared();
        String swcheck=getEnrollSharedSwitch();
        if (swcheck.equals("true")&&!a.equals("")){
            changePassBtn.setVisibility(View.VISIBLE);
        }else{
            changePassBtn.setVisibility(View.GONE);
        }


        sw.setChecked(Boolean.parseBoolean(swcheck));



            //비밀번호 설정 switch 선택 여부
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Toast.makeText(getApplicationContext(),"비밀번호를 잊어버린 경우 앱을 삭제하고 재설치 해야하며, 재설치 시 기존 데이터는 삭제됩니다.",Toast.LENGTH_LONG).show();
                        String pass=getEnrollShared();
                        if (!pass.equals("")){ //기존에 설정한 비밀번호가 있기 때문에 그걸로 들어가게 설정.
                            SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            changePassBtn.setVisibility(View.VISIBLE);
                            editor.putString("switch","true");
                            editor.apply();
                        }else{ //없으므로 비밀번호 설정창으로 이동
                            Intent intent=new Intent(getApplicationContext(),PasswordEnrollStartActivity.class);
                            startActivityForResult(intent,33);
                        }
                    }else{
                        SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        changePassBtn.setVisibility(View.GONE);
                        editor.putString("switch","false");
                        editor.apply();
                    }
                }
            });

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.changePass:

                            Log.e("비밀번호변경", "비밀번호변경");
                            intent=new Intent(getApplicationContext(),PasswordEnrollStartActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.card1:
                            Log.e("card1", "card1");
                            intent = new Intent(getApplication(), CountryCheckActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.card2:
                            Log.e("card2", "card2");
                            intent = new Intent(getApplication(), CheckListActivity1.class);
                            startActivity(intent);
                            break;
                        case R.id.card3:
                            Log.e("card3", "card3");
                            intent = new Intent(getApplication(), NoteListActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.card4:
                            Log.e("card4", "card4");
                            intent = new Intent(getApplication(), AllTravelListActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            };

            changePassBtn.setOnClickListener(listener);
            imageView1.setOnClickListener(listener);
            imageView2.setOnClickListener(listener);
            imageView3.setOnClickListener(listener);
            imageView4.setOnClickListener(listener);

            Log.e("create", "로그인성공 create");

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==33){
                //비밀번호 설정이 원활하게 이루어짐. //switch상태랑 비밀번호를 함께 저장하기.
                SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("switch","true");
                editor.apply();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "로그인성공 start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("restart", "로그인성공 restart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String a=getEnrollShared();
        String swcheck=getEnrollSharedSwitch();
        if (swcheck.equals("true")&&!a.equals("")){
            changePassBtn.setVisibility(View.VISIBLE);
        }else{
            changePassBtn.setVisibility(View.GONE);
        }

        Log.e("resume", "로그인성공 resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause", "로그인성공 pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "로그인성공 stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "로그인성공 destroy");
    }

    private String getEnrollShared(){
        SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
        String pass="";

        if (sharedPreferences!=null){
            if (sharedPreferences.getString("a","")!=null||!sharedPreferences.getString("a","").equals("")){
                pass=sharedPreferences.getString("a","");
            }
        }



        return pass;
    }

    private String getEnrollSharedSwitch(){
        SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
        String aswitch="false";

        if (sharedPreferences!=null){

            if (sharedPreferences.getString("switch","")!=null||!sharedPreferences.getString("switch","").equals("")){
                aswitch=sharedPreferences.getString("switch","");
            }
        }

        return aswitch;
    }





}
