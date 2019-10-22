package dev.edmt.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class TransprentActivity extends AppCompatActivity {
    Button btClose;
    //boolean cookie; // 다시보지 않기 기능

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.trans_main);

        btClose = (Button) findViewById(R.id.trclose);
        //final CheckBox cookieBox = (CheckBox) findViewById(R.id.cookieBox);

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


        /*
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(TransprentActivity.this,(CharSequence) ("Option1 = " + cookieBox.isChecked()),Toast.LENGTH_LONG).show();
                if(cookieBox.isChecked()==true) {
                  //Toast.makeText(getApplicationContext(),"트루", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TransprentActivity.this, MainActivity.class);
                    cookie = true;
                    intent.putExtra("cookie", cookie);
                    startActivity(intent);
                    finish();
                }
                else {
                  //Toast.makeText(getApplicationContext(),"펄스", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TransprentActivity.this, MainActivity.class);
                    cookie = false;
                    intent.putExtra("cookie", cookie);
                    startActivity(intent);
                    finish();
                }

                //finish();
            }
        });
    }
}

//        if(cookieBox.isChecked()){
//            cookie = false;
//            Intent intent = new Intent(TransprentActivity.this, MainActivity.class);
//            intent.putExtra("cookie0",cookie);
//            startActivity(intent);
//        }
//        else {
//            cookie = true;
//            Intent intent = new Intent(TransprentActivity.this, MainActivity.class);
//            intent.putExtra("cookie0",cookie);
//            startActivity(intent);
//        }

        //findViewById(R.id.trclose).setOnClickListener(this);
    //}

//    public void onClick(View v){
//        switch(v.getId()){
//            case.R.id.trclose:
//                this.finish();
//                break;
//        }
//    }
//}
//
       */