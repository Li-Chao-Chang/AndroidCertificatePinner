package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import okhttp3.OkHttpClient;
import okhttp3.CertificatePinner;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable(){
            @Override
            public void run() {
                String hostname = "com.xxxxx.xxx/";
                CertificatePinner certificatePinner = new CertificatePinner.Builder()
                        //第一次填入sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=執行後出現錯誤
                        //從Exception取得正確的SHA256值
                        //.add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                        //第二次第二次填入正確得SHA256,填入後執行成功完成驗證
                        .add(hostname, "sha256/eefgfwerqerqJ46gC3uBMBqBed5wM/qzdbLjO+1XE4to=")
                        .add(hostname, "sha256/YLh1dURfewfswefweAn7JKnbQG/uEtLMkBgFF2Fuihg=")
                        .add(hostname, "sha256/Vjsewewrtswew1YKepWQboSIRi63WsWXhIMN+eWys=")
                        //.....
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .certificatePinner(certificatePinner)
                        .build();

                Request request = new Request.Builder()
                        .url("https://" + hostname)
                        .build();
                try {
                    client.newCall(request).execute();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

}
