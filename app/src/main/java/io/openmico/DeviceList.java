package io.openmico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.openmico.adapter.DeviceAdapter;
import io.openmico.beans.BaseBean;
import io.openmico.beans.DeviceBean;

public class DeviceList extends AppCompatActivity {
    static String deviceList = SubActivity.deviceList;
    static int deviceNum = 0;
    static String api_cookie = SubActivity.api_cookie;

    List<DeviceBean> devices = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicelist_layout);
    }

    protected void onStart() {
        super.onStart();
        devices.clear();
        // 获取设备数量
        Log.d("MiLogin", "Number of device: " + deviceNum);

        BaseBean<List<DeviceBean>> src = new Gson().fromJson(deviceList, new TypeToken<BaseBean<List<DeviceBean>>>() {
        }.getType());
        if (src != null && src.getMessage().equals("Success") && src.getData() != null) {
            devices = src.getData();
            deviceNum=devices.size();
        }
        RecyclerView list=findViewById(R.id.deviceList);
        list.setLayoutManager(new LinearLayoutManager(this));
        DeviceAdapter adapter=new DeviceAdapter(devices);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                // TODO 这里写点击操作，设备信息用devices.get()取
                Log.d("Selected device: ", devices.get(i).getSerialNumber());
                // 传入参数到推送窗口
                PushOTA.deviceSN = devices.get(i).getSerialNumber();
                PushOTA.deviceID = devices.get(i).getDeviceID();
                PushOTA.deviceHARDWARE = devices.get(i).getHardware();
                PushOTA.api_cookie = api_cookie;
                // 跳转
                Intent intent=new Intent();
                intent.setClass(DeviceList.this, PushOTA.class);
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
