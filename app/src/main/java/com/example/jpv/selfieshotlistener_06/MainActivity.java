package com.example.jpv.selfieshotlistener_06;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView stauts_tevi_01;
    Chronometer chrono_01;
    ImageView injwaiting_img_01;
    ImageView injworking_img_01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stauts_tevi_01 = (TextView) findViewById(R.id.stauts_tevi_01);
        chrono_01 = (Chronometer) findViewById(R.id.chrono_01);
        injwaiting_img_01 = (ImageView) findViewById(R.id.injwaiting_img_01);
        injworking_img_01 = (ImageView) findViewById(R.id.injworking_img_01);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
 /*           case KeyEvent.KEYCODE_MENU:
                Toast.makeText(this, "Menu key pressed", Toast.LENGTH_SHORT).show();
                return true;
            case KeyEvent.KEYCODE_SEARCH:
                Toast.makeText(this, "Search key pressed", Toast.LENGTH_SHORT).show();
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;*/
            case KeyEvent.KEYCODE_ENTER:
                stauts_tevi_01.setText("Return pressed");
                return true;            case KeyEvent.KEYCODE_VOLUME_UP:
                //Toast.makeText(this,"Volumen Up pressed", Toast.LENGTH_SHORT).show();
                stauts_tevi_01.setText("Volumen Up pressed");
                chrono_01.start();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //Toast.makeText(this,"Volumen Down pressed", Toast.LENGTH_SHORT).show();
                stauts_tevi_01.setText("Volumen Down pressed");
                injworking_img_01.setVisibility(ImageView.VISIBLE);
                injwaiting_img_01.setVisibility(ImageView.INVISIBLE);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode){
/*            case KeyEvent.KEYCODE_MENU:
                Toast.makeText(this, "Menu key released", Toast.LENGTH_SHORT).show();
                return true;
            case KeyEvent.KEYCODE_SEARCH:
                Toast.makeText(this, "Search key released", Toast.LENGTH_SHORT).show();
                return true;*/
            case KeyEvent.KEYCODE_ENTER:
                stauts_tevi_01.setText("Return released");
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                //if(event.isTracking() && !event.isCanceled())
                    //Toast.makeText(this, "Volumen Up released", Toast.LENGTH_SHORT).show();
                stauts_tevi_01.setText("Volumen Up released");
                chrono_01.stop();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //Toast.makeText(this, "Volumen Down released", Toast.LENGTH_SHORT).show();
                stauts_tevi_01.setText("Volumen Down released");
                injworking_img_01.setVisibility(ImageView.INVISIBLE);
                injwaiting_img_01.setVisibility(ImageView.VISIBLE);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
