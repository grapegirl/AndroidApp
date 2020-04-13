/**
 *
 */
package com.kiki.android.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.io.File;

/**
 * @version 1.0
 * @Class Name : 사운드 및 진동 관련 유틸
 * @Description :
 * @since 2016. 1. 23.
 */
public class MediaUtils {

    /****
     * @param context 컨텍스트
     * @param time    초
     * @Description : 진동 울리기
     */
    public static void vibrate(Context context, long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    /**
     * PDF 파일 열기
     *
     * @param context    컨텍스트
     * @param strPdfFile pdf 파일 경로
     */
    public static void showPdfFile(Context context, String strPdfFile) {
        try {
            Uri apkUri = Uri.fromFile(new File(strPdfFile));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(apkUri, "application/pdf");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 오디오 자동 재생 메소드
     *
     * @param context 컨텍스트
     * @param nAudio  오티오
     */
    public static void onAudioPlay(Context context, int nAudio) {
        try {
            RingtoneManager rm = new RingtoneManager(context);
            rm.setType(RingtoneManager.TYPE_NOTIFICATION);
            rm.getCursor();

            Uri notification = rm.getRingtoneUri(nAudio - 1);

            MediaPlayer m_pMediaPlayer = MediaPlayer.create(context, notification);
            m_pMediaPlayer.setLooping(false);
            m_pMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    mp.stop();
                }
            });
            m_pMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
