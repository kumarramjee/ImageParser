package broadcastreceiver.mobimedia.com.imageprsingforweather;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ram on 3/7/15.
 */
public class FetchHourForcastJson {



    private static final String FORCAST_DAY_DETAIL_API =
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=16";

    public static JSONObject getJSON(Context context, String city) {
        try {
            URL url = new URL(String.format(FORCAST_DAY_DETAIL_API, city));
            Log.i("FetchForcastForDayJson", "Forcast Url is:==" + url);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200) {
                return null;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }
}
