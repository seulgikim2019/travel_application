package com.example.third;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickActivity extends AppCompatActivity {

    private Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

        setTitle("시간 선택");

        final TimePicker timePick=findViewById(R.id.time_picker);

        Button button1=findViewById(R.id.time_cancel);

        Button button2=findViewById(R.id.time_ok);


        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.time_cancel:
                          finish();
                        break;
                    case R.id.time_ok:
                          intent=new Intent(getApplication(),TravelContentsActivity.class);
                          intent.putExtra("hour",timePick.getHour());

                          Log.e("time", String.valueOf(timePick.getHour()));

                          intent.putExtra("min",timePick.getMinute());

                          intent.putExtra("what",timePick.getContext().toString());
                      //  Log.e("time", String.valueOf(timePick.));
                          setResult(RESULT_OK, intent);
                          finish();
                        break;
                }
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);



    }
}
