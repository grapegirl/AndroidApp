package com.kiki.android.mgr.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : HTTPManager.java
 * @Description : HTTP 통신 매니저 클래스
 * @since 2017-02-11
 */
public class UrlManager extends AsyncTask<String, Void, Void> {

    private final String TAG = this.getClass().getSimpleName();
    private String mURl = null;
    private boolean isPost = false;
    private INetReceive mIHttpReceive = null;
    private int mId;

    public UrlManager(String url, boolean post, INetReceive receive, int id) {
        mURl = url;
        isPost = post;
        mIHttpReceive = receive;
        mId = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(String... params) {
        String data = "";
        try {
            URL url = new URL(mURl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (isPost) {
                try {
                    httpURLConnection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                httpURLConnection.setDoOutput(true);
            } else {
                httpURLConnection.setRequestMethod("GET");
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDefaultUseCaches(false);

            if (isPost) {//Post 방식으로 데이타 전달시
                OutputStream outputStream = httpURLConnection.getOutputStream();
                if (params != null) {
                    String sendData = (String) params[0];
                    outputStream.write(sendData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                }
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String buffer = null;
                BufferedReader bufferedReader;
                if (isPost) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                }
                while ((buffer = bufferedReader.readLine()) != null) {
                    data += buffer;
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                mIHttpReceive.onNetReceive(INetReceive.NET_OK, mId, data);
            } else {
                mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, httpURLConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(this.getClass().getSimpleName(), " @@ MalformedURLException");
            mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, null);

        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.d(this.getClass().getSimpleName(), " @@ ProtocolException");
            mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(this.getClass().getSimpleName(), " @@ UnsupportedEncodingException");
            mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, null);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(this.getClass().getSimpleName(), " @@ IOException");
            mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
