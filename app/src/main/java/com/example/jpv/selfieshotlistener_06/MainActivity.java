package com.example.jpv.selfieshotlistener_06;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView stauts_tevi_01;        //JPV: status string at the top of the screen
    //Chronometer chrono_01;        //JPV: chronometer to measure pressure time (probably will be deleted)
    ImageView injwaiting_img_01;    //JPV: image of the autoinjector befor injection
    ImageView injworking_img_01;    //JPV: image of the autoinjector injecting
    long starttimems;               //JPV: injection start time [ms]
    long stoptimems;                //JPV: injection stop time [ms]
    TextView pressuretime_txt_01;   //JPV: measure of the pressure time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //References to the view
        stauts_tevi_01 = (TextView) findViewById(R.id.stauts_tevi_01);
        //chrono_01 = (Chronometer) findViewById(R.id.chrono_01);
        injwaiting_img_01 = (ImageView) findViewById(R.id.injwaiting_img_01);
        injworking_img_01 = (ImageView) findViewById(R.id.injworking_img_01);
        pressuretime_txt_01 = (TextView) findViewById(R.id.pressuretime_txt_01);

        //Initializing local variables
        starttimems = 0;
        stoptimems = 0;

        //Initializing views
        injwaiting_img_01.setVisibility(ImageView.VISIBLE);
        pressuretime_txt_01.setText("Pressure time: 0.0 s");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_MENU:
                //chrono_01.setBase(SystemClock.elapsedRealtime());
                return true;
 /*           case KeyEvent.KEYCODE_SEARCH:
                Toast.makeText(this, "Search key pressed", Toast.LENGTH_SHORT).show();
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_ENTER:
                stauts_tevi_01.setText("Return pressed");
                return true;*/

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //Time measuring
                pressuretime_txt_01.setText("Pressure time: 0.0 s");
                //chrono_01.start();

                //Status string update
                stauts_tevi_01.setText("Volumen Up pressed");

                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                //Staus string update
                stauts_tevi_01.setText("Volumen Down pressed");

                //Time measuring
                if(starttimems == 0){
                    starttimems = System.currentTimeMillis();
                }
                pressuretime_txt_01.setText("Measuring...");

                //Image update
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
                return true;
            case KeyEvent.KEYCODE_ENTER:
                stauts_tevi_01.setText("Return released");
                return true;*/

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //Staus string update
                stauts_tevi_01.setText("Volumen Up released");

                //Time measuring
                //chrono_01.stop();

                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                //Time measuring
                stoptimems = System.currentTimeMillis();
                pressuretime_txt_01.setText("Pressure time: " + (double) (stoptimems-starttimems)/1000 + " s");
                starttimems = 0;            //So ti will be updated at the next passage in the "pressed" routine

                //Staus string update
                stauts_tevi_01.setText("Volumen Down released");

                //Image update
                injworking_img_01.setVisibility(ImageView.INVISIBLE);
                injwaiting_img_01.setVisibility(ImageView.VISIBLE);

                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
