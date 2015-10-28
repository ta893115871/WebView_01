package com.example.guxiuzhong.webview_01;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author 顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * @Title: HttpThread
 * @Package com.example.guxiuzhong.webview_01
 * @Description:
 * @date 15/10/25
 * @time 上午11:50
 */
public class HttpThread extends Thread {

    private String mUrl;


    public HttpThread(String url) {
        this.mUrl = url;
    }

    @Override
    public void run() {
        super.run();
        try {
            System.out.println("----------down start---->>>");

            URL url = new URL(this.mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            FileOutputStream fos = null;
            File dir;
            File file;

            if (connection.getResponseCode() == 200) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    dir = Environment.getExternalStorageDirectory();
                    file = new File(dir, "test.apk");
                    fos = new FileOutputStream(file);
                }
                byte buffer[] = new byte[1024*1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    if (fos != null){
                        fos.write(buffer, 0, len);
                    }
                }
                fos.flush();
                if (fos != null) {
                    fos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                System.out.println("----------down successs---->>>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
