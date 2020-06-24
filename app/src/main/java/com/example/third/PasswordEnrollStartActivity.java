package com.example.third;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordEnrollStartActivity extends AppCompatActivity  {




    @Override
    protected void onStart() {
        super.onStart();

        Log.e("enroll_start","등록 스타트지롱");


    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordenroll);

        setTitle("비밀번호 설정하기");


        Button btn_enroll = findViewById(R.id.enroll_okay);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.enroll_okay:


                        EditText view2 = findViewById(R.id.enroll_password);
                      //  intent.putExtra("enroll_pass", view2.getText().toString())


                        EditText view3=findViewById(R.id.enroll_password2);



                        //비밀번호 두개가 일치하면 회원가입 등록이 가능하도록
                        if (!view2.getText().toString().equals("")&&!view3.getText().toString().equals("")&&view2.getText().toString().equals(view3.getText().toString())){
                            Intent intent=new Intent(getApplicationContext(), LoginSuccessActivity.class);
                            setEnrollShared(view2.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{

                            if (view2.getText().toString().equals("")&&view3.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(),"입력된 값이 없습니다.",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();

                            }


                            return;
                        }

                        break;

                }
            }
        });





    }
    private void setEnrollShared(String pass){
        SharedPreferences sharedPreferences=getSharedPreferences("enrollpass",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("a",pass);
        editor.apply();

    }





}
