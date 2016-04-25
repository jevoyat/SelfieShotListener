package com.example.jpv.selfieshotlistener_06;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView stauts_tevi_01;        //JPV: status string at the top of the screen
    //Chronometer chrono_01;        //JPV: chronometer to measure pressure time (probably will be deleted)
    ImageView injwaiting_img_01;    //JPV: image for the autoinjector befor injection
    ImageView injworking_img_01;    //JPV: image for the autoinjector injecting
    ImageView injdone_img_01;       //JPV: image for the autoinjector after injection
    long starttimems;               //JPV: injection start time [ms]
    long stoptimems;                //JPV: injection stop time [ms]
    TextView pressuretime_txt_01;   //JPV: measure of the pressure time
    ArrayList<Float> timesarray;    //JPV: collection of times from users
    ListView times_livi_01;         //JPV: to display the times obtained by users
    ArrayAdapter<Float> timesadapter;//JPV: adapter for the ListView of the injection times
    DecimalFormat twoDForm = new DecimalFormat("#.##");//JPV: rounding scheme
    FileOutputStream outputStream;  //JPV: to save the list of times measured
    String filename = "MeausredTimes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //References to the view
        stauts_tevi_01 = (TextView) findViewById(R.id.stauts_tevi_01);
        //chrono_01 = (Chronometer) findViewById(R.id.chrono_01);
        injwaiting_img_01 = (ImageView) findViewById(R.id.injwaiting_img_01);
        injworking_img_01 = (ImageView) findViewById(R.id.injworking_img_01);
        injdone_img_01 = (ImageView) findViewById(R.id.injdone_img_01);
        pressuretime_txt_01 = (TextView) findViewById(R.id.pressuretime_txt_01);

        //Initializing local variables
        starttimems = 0;
        stoptimems = 0;
        timesarray = new ArrayList<Float>();
        timesarray.add((Float)(float) 0.0);

        //Initializing views
        injwaiting_img_01.setVisibility(ImageView.VISIBLE);
        injdone_img_01.setVisibility(ImageView.INVISIBLE);
        injworking_img_01.setVisibility(ImageView.INVISIBLE);
        pressuretime_txt_01.setText("Injection time: 0.00 s");
        times_livi_01 = (ListView) findViewById(R.id.times_livi_01);
        timesadapter = new ArrayAdapter<Float>(this,android.R.layout.simple_list_item_1,timesarray);
        times_livi_01.setAdapter(timesadapter);
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
                return true;*/
            case KeyEvent.KEYCODE_ENTER:
                //stauts_tevi_01.setText("Return pressed");
                //return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //Time measuring
                pressuretime_txt_01.setText("Injection time: 0.00 s");

                //Image update
                injwaiting_img_01.setVisibility(ImageView.VISIBLE);
                injdone_img_01.setVisibility(ImageView.INVISIBLE);
                injworking_img_01.setVisibility(ImageView.INVISIBLE);

                //Status string update
                stauts_tevi_01.setText("Volumen Up pressed");

                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                //Time measuring
                if(starttimems == 0){
                    starttimems = System.currentTimeMillis();
                }
                pressuretime_txt_01.setText("Injecting...");

                //Staus string update
                stauts_tevi_01.setText("Volumen Down pressed");

                //Image update
                injworking_img_01.setVisibility(ImageView.VISIBLE);
                injwaiting_img_01.setVisibility(ImageView.INVISIBLE);
                injdone_img_01.setVisibility(ImageView.INVISIBLE);

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
                stauts_tevi_01.setText("Volumen Down released");
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                //Time measuring (keep track of the start delay due to SelfieShot)
                long timehere = System.currentTimeMillis();
                long delayhere = 0;
                while(delayhere<420) {
                    delayhere = System.currentTimeMillis()-timehere;
                }
                stoptimems = System.currentTimeMillis();
                Float pretime = (float) (stoptimems-starttimems)/1000;
                pressuretime_txt_01.setText("Injection time: " + String.format("%.2f",pretime) + " s");

                starttimems = 0;            //So ti will be updated at the next passage in the "pressed" routine

                //Staus string update
                stauts_tevi_01.setText("Volumen Down released");

                //Image update
                injworking_img_01.setVisibility(ImageView.INVISIBLE);
                injdone_img_01.setVisibility(ImageView.VISIBLE);
                injwaiting_img_01.setVisibility(ImageView.VISIBLE);

                //ListView update
                pretime = Float.valueOf(twoDForm.format(pretime));
                timesarray.add(pretime);
                timesadapter.notifyDataSetChanged();

                //Output file
                byte[] timeinbytes = float2ByteArray(pretime);
                //Output file
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(timeinbytes);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    stauts_tevi_01.setText("Couldn't save data");
                }

                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static byte [] float2ByteArray (float value)
    {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
}
