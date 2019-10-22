package dev.edmt.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import dev.edmt.weatherapp.Common.Common;
import dev.edmt.weatherapp.Helper.Helper;
import dev.edmt.weatherapp.Model.OpenWeatherMap;
import pl.droidsonroids.gif.GifImageView;

import static java.lang.Boolean.valueOf;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Activity activity;

    public SharedPreferences prefs;

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius, textview;
    TextView txtDescription2; // fake
    ImageView imageView,
            top1, top2, bot1, bot2,dogTalk;

    GifImageView gif;

    int gender = 0;

    String city = "";

    boolean cookie;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 제거
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        Button cloth = (Button) findViewById(R.id.cloth);

        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cloth.class);
                intent.putExtra("celsius", txtCelsius.getText().toString());
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
        Button windowbt = (Button) findViewById(R.id.windowbt);
        windowbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Window.class);
                intent2.putExtra("lati", lat);
                intent2.putExtra("long", lng);
                intent2.putExtra("city", city);
                Log.e("lati, long", lat + ", " + lng);
                startActivity(intent2);
            }
        });
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //툴바 설정
        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만들어 준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 제거

        // 최초 1회 실행 기능
        checkFirstRun();

        //Control
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription2 = (TextView) findViewById(R.id.txtDescription2);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.imageView);
        textview = (TextView) findViewById(R.id.textView);
        top1 = (ImageView) findViewById(R.id.top1);
        top2 = (ImageView) findViewById(R.id.top2);
        bot1 = (ImageView) findViewById(R.id.bottom1);
        bot2 = (ImageView) findViewById(R.id.bottom2);
        gif= (GifImageView) findViewById(R.id.gif);
        dogTalk=(ImageView)findViewById(R.id.dogtalk);

        //Get Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "No Location");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }


    // 툴바에 메뉴 넣기
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //툴바 버튼 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        //각각의 버튼을 클릭할때의 수행할것을 정의해 준다.
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "홈버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                return true;
            //break;
            case R.id.action_bt2:
                Toast.makeText(this, "성별 바꾸기를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Gender.class);
                intent.putExtra("celsius", txtCelsius.getText().toString());
                startActivity(intent);
                return true;

            case R.id.action_bt3:
                Toast.makeText(this, "위치 변경하기를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, Search.class);
                startActivity(intent2);
                return true;
            //break;

            default:
                return super.onOptionsItemSelected(item);
        }

        //   return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = getIntent().getDoubleExtra("lati", location.getLatitude());
        lng = getIntent().getDoubleExtra("long", location.getLongitude());
        Log.e("lati, long", lat + " , " + lng);

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));

        Geocoder geocoder = new Geocoder(this);

        List<Address> list = null;
        try {


            double d1 = Double.parseDouble(String.valueOf(lat));
            double d2 = Double.parseDouble(String.valueOf(lng));


            list = geocoder.getFromLocation(
                    d1, // 위도
                    d2, // 경도
                    10); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size() == 0) {
                txtCity.setText("해당되는 주소 정보는 없습니다");
            } else {
                txtCity.setText(//list.get(0).getCountryName() + " " +   //  국가명
                        list.get(0).getAdminArea() + " " +   // 도시명
                        list.get(0).getLocality() + " " + // 구
                        list.get(0).getThoroughfare()); // 동

                city = list.get(0).getLocality();
                if (list.get(0).getAdminArea() == null) {
                    txtCity.setText("해당되는 주소 정보는 없습니다");
                } else if (list.get(0).getLocality() == null) {

                    txtCity.setText(list.get(0).getAddressLine(0).substring(5));

                }

            }

        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetWeather extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();

        }


        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")) {
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>() {
            }.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pd.dismiss();

            // txtCity.setText(String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));  // 지역
            txtLastUpdate.setText(String.format("마지막 날씨정보 업데이트 : %s", Common.getDateNow()));  // 마지막 업데이트 정보
            //txtDescription.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription())); // 기상상태 real
            txtDescription2.setText(String.format("%s", openWeatherMap.getWeather().get(0).getDescription())); // 기상상태 fake

            String fakeDes = txtDescription2.getText().toString().trim(); // 날씨정보 string으로 받아오기}

            //  fakeDes에 넣어진 날씨 정보를 replace를 통해 한국어로 변경하는 과정
            switch (fakeDes){
                case "clear sky" :
                    String replaceDes = fakeDes.replace("clear sky", "맑은 하늘");
                    txtDescription.setText(replaceDes);
                    gif.setImageResource(R.drawable.clearsky);
                    //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv);
                    //Glide.with(this).load(R.raw.qweasd).into(imageViewTarget);
                    break;
                case "few clouds" :
                    String replaceDes2 = fakeDes.replace("few clouds", "구름 조금");
                    txtDescription.setText(replaceDes2);
                    gif.setImageResource(R.drawable.fewclouds);
                    break;
                case "overcast clouds" :
                    String replaceDes3 = fakeDes.replace("overcast clouds", "흐림");
                    txtDescription.setText(replaceDes3);
                    gif.setImageResource(R.drawable.overcastclouds);
                    break;
                case "haze" :
                    String replaceDes4 = fakeDes.replace("haze", "실안개");
                    txtDescription.setText(replaceDes4);
                    gif.setImageResource(R.drawable.haze);
                    break;
                case "mist" :
                    String replaceDes5 = fakeDes.replace("mist", "안개");
                    txtDescription.setText(replaceDes5);
                    gif.setImageResource(R.drawable.haze);
                    break;
                case "moderate rain" :
                    String replaceDes6 = fakeDes.replace("moderate rain", "비");
                    txtDescription.setText(replaceDes6);
                    gif.setImageResource(R.drawable.rain);
                    break;
                case "scattered clouds" :
                    String replaceDes7 = fakeDes.replace("scattered clouds", "구름 조금");
                    gif.setImageResource(R.drawable.fewclouds);
                    break;
                case "broken clouds" :
                    String replaceDes8 = fakeDes.replace("broken clouds", "드문 구름");
                    txtDescription.setText(replaceDes8);
                    gif.setImageResource(R.drawable.fewclouds);
                    break;
                case "light rain" :
                    String replaceDes9 = fakeDes.replace("light rain", "약한 비");
                    txtDescription.setText(replaceDes9);
                    gif.setImageResource(R.drawable.rain);
                    break;
                default:
                    txtDescription.setText("Error");
                    break;
            }


            //txtHumidity.setText(String.format("습도 : %d%%",openWeatherMap.getMain().getHumidity())); // 습도
            //txtTime.setText(String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset()))); // 시간
            txtCelsius.setText(String.format("%.1f °C", openWeatherMap.getMain().getTemp())); // 기온

            //일교차 구하는곳
            double tem = openWeatherMap.getMain().getTemp_max() - openWeatherMap.getMain().getTemp_min();
            if(tem<5){
                dogTalk.setImageResource(R.drawable.talkgood);
            }
            else if (tem > 5){
                dogTalk.setImageResource(R.drawable.talkcool);
            }
             else if (tem > 5){
                dogTalk.setImageResource(R.drawable.talksick);
                }


            Picasso.get()
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);

        }

    }

    // 앱 최초 실행 여부 판단하기 (true = 최초 1회 실행 기능)
    public void checkFirstRun(){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun)
        {
            Intent intent = new Intent(activity , TransprentActivity.class);
            startActivity(intent); // 가이드 화면 출력하기
            prefs.edit().putBoolean("isFirstRun",false).apply();
        }
    }
}
