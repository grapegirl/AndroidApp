package com.kiki.android.data;

/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : AppKey
 * @Description : 앱 관련 키 정보
 * @since 2016-02-11.
 */
public class AppKey {

    /**
     * 프리퍼런스 이름
     */
    public static final String KEY_PREFER_NAME = "APP_PRF_NAME";

    /**************************************************************************
     *
     * Shared Preference
     *
     * ***********************************************************************/
    /**
     * GCM 프로젝트 아이디
     */
    public static final String KEY_GCM_PROJECT_ID = "";
    /**
     * 운영 배포시 false
     * 개발 로그 true시 보임
     */
    public static boolean VIEW_LOG = false;

}