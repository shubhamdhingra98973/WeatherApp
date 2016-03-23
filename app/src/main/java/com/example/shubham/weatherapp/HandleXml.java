package com.example.shubham.weatherapp;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Shubham on 24-12-2015.
 */
public class HandleXml
{
    private String country="country";
    private String temperature="temperature";
    private String humidity="humidity";
    private String pressure="pressure";
    private String urlString=null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile  boolean parsingcomplete=true;
    public HandleXml(String url)
    {
        this.urlString=url;
    }

    public String getCountry()
    {
        return country;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }
    public void parseXmlAndStore(XmlPullParser myParser )
    {
        int event;
        String text=null;
        try {
             event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        if(name.equals("country"))
                        {}
                        else if (name.equals("humidity")) {
                            humidity = myParser.getAttributeValue(null,"value");
                        }else if (name.equals("temperature")) {
                            temperature = myParser.getAttributeValue(null,"value");
                        } else if (name.equals("pressure")) {
                            pressure = myParser.getAttributeValue(null,"value");
                        } else {
                        }
                        break;
                    case XmlPullParser.TEXT:
                        country=myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                }
                event=myParser.next();

            }
        parsingcomplete=false;
        }
        catch(Exception e)
        {
e.printStackTrace();
        }
    }
    public void fetchXml()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();
                    InputStream stream=connect.getInputStream();
                    xmlFactoryObject=XmlPullParserFactory.newInstance();
                    XmlPullParser myParser=xmlFactoryObject.newPullParser();
                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(stream, null);
                    parseXmlAndStore(myParser);
                stream.close();
                }
                catch(Exception e)
                {

                }

            }
        });
        thread.start();
    }
}
