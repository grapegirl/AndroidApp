package com.kiki.android.mgr.file;

import android.os.AsyncTask;

import com.kiki.android.mgr.net.INetReceive;
import com.kiki.android.utils.KLog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : HttpFileDownloadManager
 * @Description : 파일 다운로드 매니저
 * @since 2017-02-11
 */
public class FileDownloadMgr extends AsyncTask<Object, Void, Void> {

    private final String TAG = this.getClass().getSimpleName();
    private INetReceive mHttpReceive = null;
    private int mId;
    private String mURl = null;

    /**
     * 생성자
     *
     * @param receive 응답 리시버 객체
     */
    public FileDownloadMgr(String url, INetReceive receive, int id) {
        mURl = url;
        mHttpReceive = receive;
        mId = id;
    }

    @Override
    protected Void doInBackground(Object... params) {
        int count = 0;
        try {
            URL url = new URL(mURl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream input = urlConnection.getInputStream();

            String saveFile = (String) params[0];
            KLog.d(this.getClass().getSimpleName(), "@@ saveFile : " + saveFile);
            OutputStream output = new FileOutputStream(saveFile);

            byte[] data = new byte[1024];
            long total = 0;
            if (input != null) {
                while ((count = input.read(data)) != -1) {
                    total += count;
                    KLog.d(this.getClass().getSimpleName(), "@@ data loading = " + (int) total);
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                mHttpReceive.onNetReceive(INetReceive.NET_OK, mId, "HttpResponse Download Ok");
            } else {
                mHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, "HttpResponse InputStream null");
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            KLog.d(this.getClass().getSimpleName(), this.getClass() + "IOException ");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
