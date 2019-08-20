package cn.com.hk_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;

import java.util.ArrayList;
import java.util.List;

import cn.com.hkvideo.VideoRecycler;

public class Video_Look extends AppCompatActivity {

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private int m_iStartChan = 0; // start channel no
    private RecyclerView videoRecycler;
    private int mLoginId = -1;
    private int channel = 1;
    private String TAG = "HKNetDvrDeviceInfoV30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__look);
        videoRecycler = findViewById(R.id.video_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        videoRecycler.setLayoutManager(manager);
        VideoRecycler adapter = new VideoRecycler(this, getVideoList());
        videoRecycler.setAdapter(adapter);

        Config.register(this);

        //初始化SDK
        if (!initeSdk()) {
            this.finish();
            return;
        }

        //用户登陆信息
        String strIP = "192.168.1.65";
        int nPort = 8000;
        String strUser = "admin";
        String strPsd = "lain123456";
        //用户登陆
        mLoginId = loginNormalDevice(strIP,nPort,strUser,strPsd,channel+32);

    }

    /** 登录设备*/
    private int loginNormalDevice(String strIP, int nPort, String strUser, String strPsd, int channel)
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30 )
        {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd,
                m_oNetDvrDeviceInfoV30);

        Log.d(TAG, "loginNormalDevice: "+iLogID);

        if (iLogID < 0 )
        {
            Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.wDevType);
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byStartDChan);
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byDVRType);
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0 )
        {
            // m_oNetDvrDeviceInfoV30.byStartChan
            m_iStartChan = channel;
        }
        else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0 )
        {
            // m_oNetDvrDeviceInfoV30.byStartDChan
            m_iStartChan = channel + 32;
            Log.e("m_iStartChan",m_iStartChan+"");
        }

        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }

    private List<String> getVideoList() {

        ArrayList<String> al = new ArrayList<>();

        for (int i = 0; i < 40; i++)
            al.add("监控  "+i);

        return al;

    }

    //初始化SDK
    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e("lzc","HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }


}
