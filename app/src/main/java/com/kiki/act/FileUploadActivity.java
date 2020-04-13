package com.kiki.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiki.android.mgr.net.INetReceive;
import com.kiki.android.utils.KLog;
import com.kiki.android.view.custom.KProgressBar;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : FileUploadActivity
 * @Description : 파일업로드 클래스
 * @since 2015-07-01.
 */
public class FileUploadActivity extends Activity implements View.OnClickListener, Handler.Callback, INetReceive {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SAVE_IMAGE = 2;
    private Button mSendBtn = null;
    private Button mDownLoadBtn = null;
    private Button mCametraBtn = null;
    private ImageView mImageView = null;
    private TextView mTextView = null;
    private Handler mHandler = null;
    private KProgressBar mProgressDialog = null;
    private String mFilePath = null;
    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(this);
        mProgressDialog = new KProgressBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 전송 버튼
            //    case R.id.fileupload_main_sendButton:// 웹 전송 업로드
            //mProgressDialog.setDataLoadingDialog(true, "파일을 전송하고 있습니다.");
            //ApacheFileUploadManager fileUploadTaskMangaer = new ApacheFileUploadManager(this);
            //fileUploadTaskMangaer.execute("url", mBitmap, "test.jpg");

            //FileUploadMgr manager = new FileUploadMgr(this);
            //manager.execute("url", mBitmap, "test.jpg");
            //   break;
            //case R.id.fileupload_main_cametraButton://사진찍기
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//                }
//                break;
            //  case R.id.fileupload_main_downloadButton: // 다운로드
            //mProgressDialog.setDataLoadingDialog(true, "파일을 다운로드 하고 있습니다.");
            //FileDownloadMgr fileDownloadManager = new FileDownloadMgr(this);
            //fileDownloadManager.execute("url", "filename.txt");

            //ApacheFileDownloadManager fileDownloadManager = new ApacheFileDownloadManager(this);
            //fileDownloadManager.execute("url","finame.txt");
            //break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            KLog.d(this.getClass().getSimpleName(), this.getClass() + "onActivityResult");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mHandler.sendMessage(mHandler.obtainMessage(0, imageBitmap));
        } else if (requestCode == REQUEST_SAVE_IMAGE && resultCode == RESULT_OK) {
            KLog.d(this.getClass().getSimpleName(), "@@ File Path" + mFilePath);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:// 이미지 표시
                mBitmap = (Bitmap) msg.obj;
                mImageView.setImageBitmap(mBitmap);
                break;
            case 1:// 텍스트 결과 표시
                mProgressDialog.setDataLoadingDialog(false, null);
                String result = (String) msg.obj;
                mTextView.setText(result);
                break;
        }
        return false;
    }

    @Override
    public void onNetReceive(int type, int actionId, Object obj) {
        switch (type) {
            case NET_OK:
                mHandler.sendMessage(mHandler.obtainMessage(1, obj));
                break;
        }
    }
}
