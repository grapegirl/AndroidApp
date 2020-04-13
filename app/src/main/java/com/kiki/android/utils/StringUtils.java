package com.kiki.android.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @Class Name : 스트링 유틸
 * @Description :
 * @since 2015. 1. 8.
 */
public class StringUtils {

    /****
     * @Description : HTML 태그 변환된거 다시 변환하는 메소드
     * @Return 변환된 스트링 값
     */
    public static String convertString(String str) {
        if (str.contains("&amp;")) {
            str = str.replace("&amp;", "&");
        }
        if (str.contains("&apos;")) {
            str = str.replace("&apos;", "'");
        }
        if (str.contains("&quot;")) {
            str = str.replace("&quot;", "\"");
        }
        if (str.contains("\\")) {
            str = str.replace("\\", "\\");
        }
        if (str.contains("&lt;")) {
            str = str.replace("&lt;", "<");
        }
        if (str.contains("&gt;")) {
            str = str.replace("&gt;", ">");
        }
        return str;
    }


    /**
     * 포스트 방식으로 데이타 전송시 인자 설정 메소드
     *
     * @param sendData
     * @return 포스트 방식 전송 데이타
     */
    public static String getHTTPPostSendData(HashMap<String, Object> sendData) {
        StringBuilder sb = new StringBuilder();

        //키값과 값을 추가함.
        Set<String> key = sendData.keySet();
        for (Iterator iterator = key.iterator(); iterator.hasNext(); ) {
            String keyName = (String) iterator.next();
            Object value = (Object) sendData.get(keyName);

            if (iterator.hasNext())
                sb.append(keyName).append("=").append(value).append("&");
            else
                sb.append(keyName).append("=").append(value);
        }
        return sb.toString();
    }

    /***
     * 버전정보 비교 메소드
     *
     * @param srcVersion 버전
     * @param newVersion 서버에서 내려오는 버전
     * @return 버전 비교값(0이면 같음, 1이면 서버에서 내려오는 버전이 크다, -1이면 서버에서 이전버전이 내려옴)
     */
    public static final int compareVersion(String srcVersion, String newVersion) {
        Integer[] arrSrc = getIntegrArrayFromStringArray(srcVersion.split("\\."));
        Integer[] arrNew = getIntegrArrayFromStringArray(newVersion.split("\\."));

        if (arrSrc.length != arrNew.length) {
            return 1;
        }

        for (int i = 0, n = arrSrc.length; i < n; i++) {
            if (arrNew[i] > arrSrc[i]) {
                return 1;
            } else if (arrNew[i] < arrSrc[i]) {
                return -1;
            }
        }
        return 0;

    }

    /***
     * String 배열을 integer 배열로 변환 메소드
     *
     * @param arr 변환할 String 배열
     * @return 변환된 integer 배열
     * @throws NumberFormatException
     */
    public static final Integer[] getIntegrArrayFromStringArray(String[] arr) throws NumberFormatException {
        List<Integer> list = new ArrayList<Integer>();
        for (String str : arr) {
            list.add(Integer.parseInt(str));
        }
        return list.toArray(new Integer[list.size()]);
    }

    /**
     * 스트링 트림 처리.
     */
    public static String checkString(String str, String tmp) {
        if (!(str == null || str.trim().equals("") || str.trim().equals("null")))
            return (String) str.trim().toString();
        else return tmp;
    }
}
