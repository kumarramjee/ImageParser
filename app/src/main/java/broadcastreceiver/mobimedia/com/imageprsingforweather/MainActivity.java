package broadcastreceiver.mobimedia.com.imageprsingforweather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    HourForecastAdapter houradapter;
    ListView imgaeparser;
    private Context mContext;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       String city = "sasaram";

       imgaeparser = (ListView) findViewById(R.id.imgaeparser);
        new AsyncTaskForDaywiseDetail().execute(city);




    }

    public class AsyncTaskForDaywiseDetail extends AsyncTask<String, Void, List<Hour>> {

        @Override
        protected List<Hour> doInBackground(String ... params) {
            List<Hour> hourlistAsync = new ArrayList<Hour>();
            String s = params[0];
            hourlistAsync = GetHourJson(s);
            Log.i("", "" + s);
            return hourlistAsync;
        }


        @Override
        protected void onPostExecute(List<Hour> mHourList) {
            super.onPostExecute(mHourList);
            houradapter = new HourForecastAdapter(mContext, mHourList);
            imgaeparser.setAdapter(houradapter);
            houradapter.notifyDataSetChanged();
        }

        private List<Hour> GetHourJson(String city) {
            List<Hour> getlistfromAsync = new ArrayList<Hour>();
            try {
                JSONObject json = FetchHourForcastJson.getJSON(MainActivity.this, city);
                Log.i("CITY ","IS=="+city);
                Log.i("",""+json);
                getlistfromAsync = RenderWeatherHour(json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return getlistfromAsync;
        }

        private List<Hour> RenderWeatherHour(JSONObject json) {

            {
                List<Hour> mHourList = new ArrayList<Hour>();
                try {
                    int length = json.getInt("cnt");
                    JSONArray ListArray = json.getJSONArray("list");
                    for (int i = 0; i < length; i++) {
                        Hour hourobject = new Hour();
                        JSONObject jobject = ListArray.getJSONObject(i);
                        JSONObject temp = jobject.getJSONObject("main");
                        JSONArray weather = jobject.getJSONArray("weather");
                        for (int j = 0; j < weather.length(); j++) {
                            JSONObject jweather = weather.getJSONObject(j);
                            hourobject.icon = jweather.getString("icon");
                            Log.i("", "" + hourobject.icon);

                            mHourList.add(hourobject);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mHourList;
            }
        }
    }
}
