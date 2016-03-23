package com.example.shubham.weatherapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.xmlparserdemo.R;

public class MainActivity extends AppCompatActivity {

EditText txt1,txt2,txt3,txt4,txt5;
    TextView txt;
    private String url1="http://api.openweathermap.org/data/2.5/weather?q=";
    private String url2="&mode=xml&appid=425ea558f8bcd20ff9cd3105b44fe950";
    HandleXml obj;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.button);
        txt=(TextView)findViewById(R.id.textview1);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/harlowsolid.ttf");
        txt.setTypeface(face);
        txt1= (EditText) findViewById(R.id.txt_location);
        txt2= (EditText) findViewById(R.id.txt_currency);
        txt3= (EditText) findViewById(R.id.txt_pressure);
        txt4= (EditText) findViewById(R.id.txt_humidity);
        txt5= (EditText) findViewById(R.id.txt_temp);
        btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String url = txt1.getText().toString();
        if(url.equals(""))
        {
            Toast.makeText(getBaseContext(),"Enter Location",Toast.LENGTH_LONG).show();
            txt2.setText("");
            txt3.setText("");
            txt4.setText("");
            txt5.setText("");

        }
        else {
            String finalUrl = url1 + url + url2;
            txt2.setText(finalUrl);
            obj = new HandleXml(finalUrl);
            obj.fetchXml();
            while (obj.parsingcomplete) ;
            txt2.setText(obj.getCountry());
            txt3.setText(obj.getPressure()+" hPa");
            txt4.setText(obj.getHumidity()+" %");
            txt5.setText(obj.getTemperature()+" kelvin");
        }
    }
}) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
