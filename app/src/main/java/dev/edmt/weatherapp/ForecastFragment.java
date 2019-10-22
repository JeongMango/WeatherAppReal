package dev.edmt.weatherapp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.edmt.weatherapp.Adapter.WeatherForecastAdapter;
import dev.edmt.weatherapp.Common.Common;
import dev.edmt.weatherapp.Model.WeatherForecastResult;
import dev.edmt.weatherapp.Retrofit.IOpenWeatherMap;
import dev.edmt.weatherapp.Retrofit.RetrofitClient;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    static String city;
    TextView txt_city_name, txt_geo_coord, txt_city_name2;
    RecyclerView recycler_forecast;


    static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if(instance == null)
            instance = new ForecastFragment();

        return instance;
    }

    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_city_name2 = (TextView)itemView.findViewById(R.id.txt_city_name2);
        txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coord);

        recycler_forecast = (RecyclerView)itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getForecastWeatherInformation();

        if(getArguments() != null) {
            city = getArguments().getString("city");
        }else{
            Log.e("getArgument", "getArgument() is null") ;
        }

        return itemView;
    }

    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getForecastWeatherByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayForecastWeather(weatherForecastResult);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",""+throwable.getMessage());
                    }
                })
        );
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        /*txt_city_name2.setText(new StringBuilder(weatherForecastResult.city.name));
        String fakeDes = txt_city_name2.getText().toString().trim(); // 날씨정보 string으로 공백없이 받아오기}

        switch (fakeDes) {
            case "Incheon":
                String replaceDes = fakeDes.replace("Incheon", "인천");
                txt_city_name.setText(replaceDes);
                break;
            case "Seoul":
                String replaceDes2 = fakeDes.replace("Seoul", "대전");
                txt_city_name.setText(replaceDes2);
                break;
            case "Busan":
                String replaceDes3 = fakeDes.replace("Busan", "부산");
                txt_city_name.setText(replaceDes3);
                break;
            case "Jeju":
                String replaceDes4 = fakeDes.replace("Jeju", "제주도");
                txt_city_name.setText(replaceDes4);
                break;
            case "Daegu":
                String replaceDes5 = fakeDes.replace("Daegu", "대구");
                txt_city_name.setText(replaceDes5);
                break;
            default:
                txt_city_name.setText("" +
                        "위치를 받아 올 수 없습니다.");
                break;
        }*/
        txt_city_name.setText(city);
        String fakeDes = txt_city_name.getText().toString().trim();

        txt_geo_coord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }

}
