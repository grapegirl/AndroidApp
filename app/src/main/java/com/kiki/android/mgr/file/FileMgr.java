package com.kiki.android.mgr.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : FileMgr
 * @Description : 파일 관련 정보 클래스
 * @since 2017-02-11.
 */
public class FileMgr {
    private final String TAG = this.getClass().getSimpleName();

    public ArrayList<String> readFile(String filePath) throws IOException {
        FileInputStream fis = null;
        ArrayList<String> list = new ArrayList<String>();
        try {

            fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    list.add(line);
                    System.out.println("## line Data : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                br.close();
                System.out.println("## file read end");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return list;
    }

    public void writeFile(String fileName, ArrayList<Objects> list) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (int j = 0, m = list.size(); j < m; j++) {
            fw.write(list.get(j).toString() + "\n");
        }
        fw.close();
    }

}
