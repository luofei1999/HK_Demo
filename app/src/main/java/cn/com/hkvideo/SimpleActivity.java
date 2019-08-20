package cn.com.hkvideo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hikvision.netsdk.HCNetSDK;

import cn.com.hk_demo.Config;
import cn.com.hk_demo.R;

/**
 * Created by Administrator on 2017/6/24.
 */

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener{

    public SurfaceView surfaceView;
    public PlayAssistant assistant;
    ImageView tv;
    ImageView tv2;
    ImageView tv3;
    ImageView tv4;
    private boolean flag;
    private boolean flag2;
    private boolean flag3;
    FrameLayout.LayoutParams lp;
    FrameLayout.LayoutParams lp2;
    FrameLayout.LayoutParams lp3;
    FrameLayout.LayoutParams lp4;

    public void play(){
        assistant.play("192.168.1.65",8000,"admin","lain123456",Config.getInt("channel"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surface);
        Config.register(this);

        HCNetSDK.getInstance().NET_DVR_Init();
        surfaceView = (SurfaceView)findViewById(R.id.surfaceviewId);
        surfaceView.setOnClickListener(this);
        assistant = new PlayAssistant(surfaceView);
        play();
//        assistant.play("192.168.1.65",8000,"admin","lain123456",Config.getInt("channel"));

        tv = new ImageView(this);
        tv.setMinimumHeight(100);
        tv.setMinimumWidth(100);
        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp.setMargins(0,0,0,370);
        addContentView(tv,lp);
        tv.setVisibility(View.GONE);

        tv2 = new ImageView(this);
        tv2.setMinimumHeight(100);
        tv2.setMinimumWidth(100);
        lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.BOTTOM | Gravity.CENTER;
        lp2.setMargins(0,0,0,370);
        addContentView(tv2,lp2);
        tv2.setVisibility(View.GONE);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag2){
                    if(flag){
//                        tv2.setText("停止");
                        flag2 = true;
                        assistant.startRecord();
                    }else{
                    }

                }else{
//                    tv2.setText("录像");
                    assistant.stopRecord();
                    flag2 = false;
                }

            }
        });


        tv3 = new ImageView(this);
        tv3.setMinimumHeight(100);
        tv3.setMinimumWidth(100);
//        tv3.setText("全屏");
//        tv3.setTextColor(getResources().getColor(R.color.colorAccent));
         lp3 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp3.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        lp3.setMargins(0,0,0,370);
        addContentView(tv3,lp3);
        tv3.setVisibility(View.GONE);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag3){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    tv3.setText("竖屏");
                    flag3 = true;
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    tv3.setText("横屏");
                    flag3 = false;
                }
            }
        });


        tv4 = new ImageView(this);
        tv4.setMinimumHeight(100);
        tv4.setMinimumWidth(100);
        lp4 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp4.gravity = Gravity.RIGHT | Gravity.CENTER;
        addContentView(tv4,lp4);
        tv4.setVisibility(View.GONE);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //replay


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HCNetSDK.getInstance().NET_DVR_Cleanup();
        assistant.Cleanup();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.surfaceviewId){
            if(!flag){
                tv.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                flag = true;
            }else{
                tv.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                flag = false;
            }

        }

    }
    //全屏横屏控制
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            lp.setMargins(0,0,0,0);
            lp2.setMargins(0,0,0,0);
            lp3.setMargins(0,0,0,0);
            tv.setLayoutParams(lp);
            tv2.setLayoutParams(lp2);
            tv3.setLayoutParams(lp3);

        }
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            lp.setMargins(0,0,0,310);
            lp2.setMargins(0,0,0,310);
            lp3.setMargins(0,0,0,310);
            tv.setLayoutParams(lp);
            tv2.setLayoutParams(lp2);
            tv3.setLayoutParams(lp3);
        }

    }


}
