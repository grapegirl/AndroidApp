package com.kiki.android.mgr.file;

import android.os.AsyncTask;

import com.kiki.android.mgr.net.INetReceive;
import com.kiki.android.utils.KLog;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : FileUploadMgr
 * @Description : 파일 업로드
 * @since 2017-02-11
 */
public class FileUploadMgr extends AsyncTask<Object, Void, Void> {

    private final String TAG = this.getClass().getSimpleName();
    private String mURl = null;
    private INetReceive mIHttpReceive = null;
    private String BOUNDARY_STRING = "== DATA END ===";
    private int mId;
    private byte[] mByteArray;

    public FileUploadMgr(String url, INetReceive receive, int id, byte[] bytes) {
        mURl = url;
        mIHttpReceive = receive;
        mId = id;
        mByteArray = bytes;
    }

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    public static String setParams(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n" + value;
    }

    /**
     * 업로드할 파일에 대한 메타 데이터를 설정한다.
     *
     * @param key      : 서버에서 사용할 파일 변수명
     * @param fileName : 서버에서 저장될 파일명
     * @return
     */
    public static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object... params) {
        String filePath = (String) params[0];
        String setValue = (String) params[1];
        String reqValue = (String) params[2];
        String fileName = (String) params[3];

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mByteArray);
            URL connectUrl = new URL(mURl);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty(setValue, reqValue);
            conn.setRequestProperty("filename", fileName);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadFile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = byteArrayInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = byteArrayInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            byteArrayInputStream.close();
            dos.flush(); // finish upload...

            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            KLog.e("Test", "result = " + s);
            dos.close();
            mIHttpReceive.onNetReceive(INetReceive.NET_OK, mId, s);
        } catch (Exception e) {
            KLog.d("Test", "exception " + e.getMessage());
            mIHttpReceive.onNetReceive(INetReceive.NET_FAIL, mId, this.getClass() + " @@ Exception ");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

