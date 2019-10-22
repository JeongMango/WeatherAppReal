package dev.edmt.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Gender extends AppCompatActivity implements View.OnClickListener {
    int sex = 1;
    Button male_btn, female_btn;
    String celcius;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gender);

        Toolbar mtoolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 활성화(툴바)
        getSupportActionBar().setTitle("성별 선택하기");

        male_btn = findViewById(R.id.bt_male);
        female_btn = findViewById(R.id.bt_female);

        male_btn.setOnClickListener(this);
        female_btn.setOnClickListener(this);
        Intent intent = getIntent();
        celcius = intent.getStringExtra("celsius");
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

    public void onClick(View v)
    {


        if(v.getId() == R.id.bt_male) //남자 버튼
        {

            sex = 1;

            Toast.makeText(this, "남자", Toast.LENGTH_SHORT).show();

        }
        else if(v.getId() == R.id.bt_female) //여자버튼
        {
            sex = 0;
            Toast.makeText(this, "여자", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(Gender.this, MainActivity.class);
            // startActivity(intent);
        }

        Intent intent = new Intent(Gender.this, Cloth.class);
        intent.putExtra("gender", sex);
        intent.putExtra("celsius", celcius);
        startActivity(intent);
    }


}






/*        Button bt_close = (Button) findViewById(R.id.button_close);
        bt_close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();;
            }
      });
    }

}*/