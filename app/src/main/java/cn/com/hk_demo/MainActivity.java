package cn.com.hk_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private Button sendVideo;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendVideo = findViewById(R.id.send_video);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        sendVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getText().toString();
                String pas = password.getText().toString();

                OkHttpClient client = new OkHttpClient();

                FormBody formBody = new FormBody.Builder()
                        .add("loginName", name)
                        .add("password", pas)
                        .build();

                String url = "http://192.168.1.39:8080/ktr-mrms/loginJson.html";

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("faril", "onFailure: 请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String request = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, Video_Look.class);
                                startActivity(intent);
                            }
                        });

                    }
                });

            }
        });

    }
}
