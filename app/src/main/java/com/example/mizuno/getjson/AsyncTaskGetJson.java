package com.example.mizuno.getjson;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mizuno on 2016/01/14.
 */
public class AsyncTaskGetJson extends AsyncTask<Void, Void, String> {

    private final static String API_URL = "http://j13000.sangi01.net/cakephp/points/index2";
    private JsonActivity activity;

    public AsyncTaskGetJson(JsonActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String result = new String();
        ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String data = EntityUtils.toString(responseEntity);

                JSONObject rootObject = new JSONObject(data);

                JSONArray userArray = rootObject.getJSONArray("Point");
                Log.d("json1_data", userArray.toString());

                for (int n = 0; n < userArray.length(); n++) {
                    // User data
                    JSONObject userObject = userArray.getJSONObject(n);
                    String userId = userObject.getString("id");
                    String userName = userObject.getString("name");
                    double userLatitude = userObject.getDouble("latitude");
                    double userLongitude = userObject.getDouble("longitude");
                    result += "ID"+ userId + "\r\n" + "User: "+userName+" \r\n" +"緯度: " +userLatitude+"\r\n" +"経度: " +userLongitude +"\r\n";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.textView.setText(s);

    }
}
