package dev.edmt.weatherapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dev.edmt.weatherapp.Common.Common;
import dev.edmt.weatherapp.Model.WeatherForecastResult;
import dev.edmt.weatherapp.R;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Load icon
        //Load image
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.img_weather);
        holder.txt_date_time.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).dt)));


        // 시간별 온도차 확인중!
        //holder.txt_description2.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
      /*  holder.txt_description2.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        String fakeDes2 = holder.txt_date_time.getText().toString().trim();
        switch (fakeDes2){
            case "12:00":
                String replaceDes = fakeDes2.replace("12:00", "바뀜");
                holder.txt_date_time.setText(replaceDes);
                break;
            default:
                holder.txt_description.setText("Error");
                break;

        }*/

        holder.txt_description2.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
        String fakeDes = holder.txt_description2.getText().toString().trim(); // 날씨정보 string으로 받아오기}

        //  날씨 한글화 작업
        switch (fakeDes) {
            case "clear sky":
                String replaceDes = fakeDes.replace("clear sky", "맑은 하늘");
                holder.txt_description.setText(replaceDes);
                break;

            case "few clouds":
                String replaceDes2 = fakeDes.replace("few clouds", "구름 조금");
                holder.txt_description.setText(replaceDes2);
                break;
            case "overcast clouds":
                String replaceDes3 = fakeDes.replace("overcast clouds", "흐림");
                holder.txt_description.setText(replaceDes3);
                break;
            case "haze":
                String replaceDes4 = fakeDes.replace("haze", "실안개");
                holder.txt_description.setText(replaceDes4);
                break;
            case "mist":
                String replaceDes5 = fakeDes.replace("mist", "안개");
                holder.txt_description.setText(replaceDes5);
                break;
            case "moderate rain":
                String replaceDes6 = fakeDes.replace("moderate rain", "비");
                holder.txt_description.setText(replaceDes6);
                //   windowview.setImageResource(R.drawable.rain);
                break;
            case "scattered clouds":
                String replaceDes7 = fakeDes.replace("scattered clouds", "구름 조금");
                holder.txt_description.setText(replaceDes7);
                //  windowview.setImageResource(R.drawable.fewclouds);
                break;
            case "broken clouds":
                String replaceDes8 = fakeDes.replace("broken clouds", "드문 구름");
                holder.txt_description.setText(replaceDes8);
                //   windowview.setImageResource(R.drawable.fewclouds);
                break;
            case "light rain":
                String replaceDes9 = fakeDes.replace("light rain", "약한 비");
                holder.txt_description.setText(replaceDes9);
                // windowview.setImageResource(R.drawable.rain);
                break;
            default:
                holder.txt_description.setText("Error");
                break;
        }

        holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));

        holder.txt_temperature2.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())));

        String fakeTem = holder.txt_temperature2.getText().toString().trim(); // 날씨정보 string으로 받아오기}

        String on = fakeTem.substring(0,4);
        double num = Double.parseDouble(on);

        if (num >= 27) {
            holder.img_clothes.setImageResource(R.drawable.mtop3);
        } else if (num < 27 && num >= 23) {
            holder.img_clothes.setImageResource(R.drawable.unitop1);
        } else if (num < 23 && num >= 20) {
            holder.img_clothes.setImageResource(R.drawable.mtop2);
        } else if (num < 20 && num >= 17) {
            holder.img_clothes.setImageResource(R.drawable.mtop1);
        } else if (num < 17 && num >= 12) {
            holder.img_clothes.setImageResource(R.drawable.unijack1);
        }
        else {
            holder.img_clothes.setImageResource(R.drawable.unipad1);
        }
    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_date_time, txt_description, txt_temperature, txt_description2, txt_temperature2, txt_test;
        ImageView img_weather, img_clothes;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_clothes = (ImageView)itemView.findViewById(R.id.img_clothes);
            img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
            txt_date_time = (TextView)itemView.findViewById(R.id.txt_date);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);
            txt_description2 = (TextView)itemView.findViewById(R.id.txt_description2);
            txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
            txt_temperature2 = (TextView)itemView.findViewById(R.id.txt_temperature2);
            txt_test = (TextView)itemView.findViewById(R.id.txt_test);

        }
    }
}
