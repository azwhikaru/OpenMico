package io.openmico;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class PushOTA extends AppCompatActivity {

    static String API = "https://api.mina.mi.com/remote/ota/v2";
    static String deviceSN = "";
    static String deviceID = "";
    static String api_cookie = "";
    static String deviceHARDWARE = "";
    static String deviceEXTRA = "";
    static String deviceURL = "";
    static String deviceVERSION = "";
    static String deviceCHECKSUM = "";
    static String pushResult = "";
    static String callbackResult = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushota_layout);
    }

    protected void onStart() {
        super.onStart();

        // 判空
        if (deviceID == null || deviceSN == null || deviceHARDWARE == null || api_cookie == null) {
            new AlertDialog.Builder(PushOTA.this).setTitle("出错")
                    .setMessage("获取设备信息失败，请返回重新选择")
                    .setNegativeButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转
                            Intent intent = new Intent();
                            intent.setClass(PushOTA.this, DeviceList.class);
                            startActivity(intent);
                        }
                    }).show();
        }

        // 绑定
        Button start = (Button) findViewById(R.id.pushota_button_push);
        EditText inputLink = (EditText) findViewById(R.id.pushota_input_link);
        EditText inputVersion = (EditText) findViewById(R.id.pushota_input_version);
        EditText inputChecksum = (EditText) findViewById(R.id.pushota_input_hash);
        EditText inputExtra = (EditText) findViewById(R.id.pushota_input_extra);

        // 显示设备序列号
        TextView device = (TextView) findViewById(R.id.pushota_text_device);
        device.setText(device.getText().toString().replace("%s", deviceSN));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 推送点击事件
                // 置入变量
                deviceURL = inputLink.getText().toString();
                deviceVERSION = inputVersion.getText().toString();
                deviceCHECKSUM = inputChecksum.getText().toString();
                deviceEXTRA = inputExtra.getText().toString().replace("\\", "");
                try {
                    deviceEXTRA = URLEncoder.encode(deviceEXTRA, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("MiLogin", "Encoded extra: " + deviceEXTRA);
                // 判空
                if (deviceID.length() <= 0 || deviceURL.length() <= 0 || deviceHARDWARE.length() <= 0 || deviceVERSION.length() <= 0 || deviceCHECKSUM.length() <= 0 || deviceEXTRA.length() <= 0) {
                    Toast.makeText(PushOTA.this, "请检查参数是否填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                deviceId=<deviceId/>&hardware=<hardware/>
                &requestId=Gt6NrZ9VBlF5a1ceXeIR&extra=<extra/>
                &url=<url/>&version=<version/>&checksum=<checksum/>
                 */
                String pushParam = "deviceId=" + deviceID + "&hardware=" + deviceHARDWARE + "&requestId=Gt6NrZ9VBlF5a1ceXeIR&extra=" + deviceEXTRA +
                        "&url=" + deviceURL + "&version=" + deviceVERSION + "&checksum=" + deviceCHECKSUM;
                // 置入推送格式
                Log.d("MiLogin", "Push API: " + API);
                Log.d("MiLogin", "Push Cookie: " + api_cookie);
                // 开始推送
                try {
                    pushResult = pushOTA(API, pushParam, api_cookie);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MiLogin", "Result: " + pushResult);
                // 判空
                if(pushResult.length() <= 0) {
                    Toast.makeText(PushOTA.this, "推送失败，请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                0=OK
                2=Timeout
                601=Bad request
                401=Unauthorized
                 */
                else if(pushResult.equals("0")) {
                    Toast.makeText(PushOTA.this, "推送成功，请检查", Toast.LENGTH_SHORT).show();
                    return;
                }else if(pushResult.equals("2")) {
                    Toast.makeText(PushOTA.this, "推送失败，请检查设备网络是否正常", Toast.LENGTH_SHORT).show();
                    return;
                } else if(pushResult.equals("601")){
                    Toast.makeText(PushOTA.this, "推送失败，请检查请求访问是否正常", Toast.LENGTH_SHORT).show();
                    return;
                } else if(pushResult.equals("401")) {
                    Toast.makeText(PushOTA.this, "推送失败，请重新登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                return;
            }
        });
    }

    String pushOTA(String api, String param, String cookie) throws IOException, InterruptedException {
        new Thread() {
            public void run() {
                PrintWriter out = null;
                BufferedReader in = null;
                String result = "";
                try {
                    URL realUrl = new URL(api);
                    URLConnection conn = realUrl.openConnection();
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("Cookie",
                            cookie);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    out = new PrintWriter(conn.getOutputStream());
                    out.print(param);
                    out.flush();
                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result += line;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                callbackResult = result;
            }
        }.start();

        Thread.sleep(1000); // 等待线程返回
        pushResult = getSubString(callbackResult, "\"code\":", ",");
        return pushResult;
    }

    public String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    } // 文本取中间
}
