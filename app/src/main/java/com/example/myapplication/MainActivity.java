package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OutputCerKey outputCerKey = new OutputCerKey();
        try {
            final String Key = outputCerKey.GetSHA256(getAssets().open("xxxx.crt"));

            //主要程式碼
            //請注意Android4.2以後如再MainThread執行httpRequest會引發android.os.NetworkOnMainThreadException
            new Thread(new Runnable(){
                @Override
                public void run() {
                    String hostname = "xxxx.ooo.com";
                    CertificatePinner certificatePinner = new CertificatePinner.Builder()
                            //第一次填入sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=執行後出現錯誤
                            //從Exception取得正確的SHA256值
                            //.add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                            //第二次填入正確得SHA256,填入後執行成功完成驗證
                            //.add(hostname, "sha256/tl87oc9yrrqJ46gC3uBMBqBed5wM/qzdbLjO+1XE4to=")
                            //.add(hostname, "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg=")
                            //.add(hostname, "sha256/Vjs8r4z+80wjNcr1YKepWQboSIRi63WsWXhIMN+eWys=")
                            //***********************************
                            //調用outputCerKey.GetSHA256方法取回Key
                            .add(hostname,Key)
                            //***********************************
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
