package dev.edmt.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import dev.edmt.weatherapp.Model.Main;
import dev.edmt.weatherapp.Common.Common;
import dev.edmt.weatherapp.MainActivity;
import dev.edmt.weatherapp.Model.OpenWeatherMap;
import dev.edmt.weatherapp.R;

public class Cloth extends AppCompatActivity {

    Activity activity;
    ImageView top1,top2,bot1,bot2;

    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    int gender;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cloth);

        Toolbar mtoolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 활성화(툴바)
        getSupportActionBar().setTitle("오늘 이 옷은 어떠세요?");


        Intent intent = getIntent();
        String celcius = intent.getStringExtra("celsius");
        gender = intent.getIntExtra("gender",0);
        Log.e("cel", celcius);



        String on = celcius.substring(0,4);
        Log.e("on", on);
        double num = Double.parseDouble(on);
        top1= (ImageView) findViewById(R.id.top1);
        top2= (ImageView) findViewById(R.id.top2);
        bot1= (ImageView) findViewById(R.id.bottom1);
        bot2= (ImageView) findViewById(R.id.bottom2);

        if(gender == 0 ){

        }

        if (num >= 27) {
            // textview.setText("더워");
            top1.setImageResource(R.drawable.mtop3);
            top2.setImageResource(R.drawable.unitop2);
            bot1.setImageResource(R.drawable.mpants4);
            bot2.setImageResource(R.drawable.mpants3);
        } else if (num < 27 && num >= 23) {
            //textview.setText("선선하네");
            top1.setImageResource(R.drawable.unitop1);
            top2.setImageResource(R.drawable.unitop2);
            bot1.setImageResource(R.drawable.mpants4);
            bot2.setImageResource(R.drawable.mpants3);
        }


        if (num < 23 && num >= 20) {

            top1.setImageResource(R.drawable.mtop2);
            top2.setImageResource(R.drawable.unitop10);
            bot1.setImageResource(R.drawable.mpants1);
            bot2.setImageResource(R.drawable.mpants3);

        } else if (num < 20 && num >= 17) {
            //textview.setText("선선하네");
            top1.setImageResource(R.drawable.mtop1);
            top2.setImageResource(R.drawable.unisweater2);
            bot1.setImageResource(R.drawable.mpants1);
            bot2.setImageResource(R.drawable.mpants3);
        } else if (num < 17 && num >= 12) {
            //textview.setText("선선하네");
            top1.setImageResource(R.drawable.unijack1);
            top2.setImageResource(R.drawable.unitop10);
            bot1.setImageResource(R.drawable.mpants1);
            bot2.setImageResource(R.drawable.mpants3);

        }
        // else if(numm==0){

        // }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

