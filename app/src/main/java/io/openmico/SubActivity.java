package io.openmico;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Apache HTTP
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class SubActivity extends AppCompatActivity {

    public static String ssecurity;
    public static String code;
    public static String passToken;
    public static String description;
    public static String nonce;
    public static String cUserId;
    public static String userId;
    public static String psecurity;
    public static String location;
    public static String desc;
    public static String clientSign;
    public static String serviceToken;
    public static String deviceList;
    public static String api_cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_layout);

        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView state = (TextView) findViewById(R.id.state_notice);
                EditText username = (EditText) findViewById(R.id.input_username);
                EditText password = (EditText) findViewById(R.id.input_password);
                String id = username.getText().toString();
                String passwd = password.getText().toString();

                if(id.length() <= 0) {
                    Log.e("MiLogin", "Username is empty");
                    Toast.makeText(SubActivity.this, "请先输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwd.length() <= 0) {
                    Log.e("MiLogin", "Password is empty");
                    Toast.makeText(SubActivity.this, "请先输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String passwdMD5 = getMD5(passwd).toUpperCase();

                Log.d("MiLogin", "Username: " + id + "; Password: " + passwdMD5);

                // Login
                new Thread() {
                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        String cbMsg;
                        try {
                            cbMsg = miLogin(id, passwdMD5);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("MiLogin", "Login failed");
                            state.setText("无法登录（网络错误）");
                            return;
                        }
                        Log.d("MiLogin", "The message has been delivered, waiting for a response");
                        state.setText("正在登录: " + id);
                        // 取出标记头
                        cbMsg = cbMsg.replace("&&&START&&&", "");
                        Log.d("MiLogin", "Message: " + cbMsg);
                        // 放入变量
                        ssecurity = getSubString(cbMsg, "\"ssecurity\":\"", "\",\"");
                        code = getSubString(cbMsg, "\"code\":\"", "\",\"");
                        passToken = getSubString(cbMsg, "\"passToken\":\"", "\",\"");
                        description = getSubString(cbMsg, "\"description\":\"", "\",\"");
                        nonce = getSubString(cbMsg, "\"nonce\":", ",\"");
                        cUserId = getSubString(cbMsg, "\"cUserId\":\"", "\",\"");
                        userId = getSubString(cbMsg, "\"userId\":", ",");
                        psecurity = getSubString(cbMsg, "\"psecurity\":\"", "\",\"");
                        location = getSubString(cbMsg, "\"location\":\"", "\",\"");
                        desc = getSubString(cbMsg, "\"desc\":\"", "\"}");
                        Log.d("MiLogin", "ssecurity: " + ssecurity);
                        Log.d("MiLogin", "passToken: " + passToken);
                        Log.d("MiLogin", "description: " + description);
                        Log.d("MiLogin", "nonce: " + nonce);
                        Log.d("MiLogin", "cUserId: " + cUserId);
                        Log.d("MiLogin", "userId: " + userId);
                        Log.d("MiLogin", "psecurity: " + psecurity);
                        Log.d("MiLogin", "location: " + location);

                        if(!desc.equals("成功")) {
                            state.setText("无法登录（账号或密码错误）");
                            return;
                        }
                        // 获取 clientSign
                        Log.d("MiLogin", "Login successful");
                        state.setText("正在获取 clientSign: " + id);
                        try {
                            clientSign = getClientSign(nonce, ssecurity);
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                            state.setText("获取 clientSign 失败");
                            return;
                        }
                        Log.d("MiLogin", "clientSign: " + clientSign);
                        String locationCallback;
                        // 获取 serviceToken
                        try {
                            locationCallback = getServiceToken(location, clientSign);
                            Log.d("MiLogin" , "location callback: " + locationCallback);
                        } catch (IOException e) {
                            e.printStackTrace();
                            state.setText("获取 serviceToken 失败");
                            return;
                        }
                        serviceToken = getSubString(locationCallback, "[name: serviceToken][value: ", "][");
                        if(serviceToken.equals("")) {
                            state.setText("获取 serviceToken 失败");
                            return;
                        }
                        Log.d("MiLogin" ,"serviceToken: " + serviceToken);
                        Log.d("MiLogin", "The login process is complete");
                        state.setText("登录成功: " + id);
                        Log.d("MiLogin", "Loading DeviceList");

                        api_cookie = "ssecurity=" + ssecurity + "; passToken=" + passToken + "; cUserId=" + cUserId + "; userId=" + userId + "; psecurity=" + psecurity + "; serviceToken=" + serviceToken + ";";
                        Log.d("MiLogin", "API Cookie: " + api_cookie);
                        deviceList = sendGetWithCookie("https://api2.mina.mi.com/admin/v2/device_list?requestId=MC41MTAzMDYyMTgwNDgwMTI=", api_cookie);
                        Log.d("MiLogin", "DeviceList: " + deviceList);

                        // deviceList = null;
                        if(deviceList == null) { // 如果为空（未绑定或其他错误）
                            Looper.prepare();
                            new AlertDialog.Builder(SubActivity.this).setTitle("出错")
                                    .setMessage("无法获取到设备列表，请检查账号是否已经在小爱音箱或米家客户端绑定")
                                    .setNegativeButton("好", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                            Looper.loop();
                        }

                        // 跳转到 DeviceList
                        Intent intent=new Intent();
                        intent.setClass(SubActivity.this, DeviceList.class);
                        startActivity(intent);
                    }
                }.start();
            }

            public String sendGetWithCookie(String path, String cookie) {
                try{
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Cookie", cookie);
                    con.connect();
                    if (con.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        return sb.toString();
                    }
                    con.disconnect();
                }catch (Exception e){
                    return e.toString();
                }
                return "";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public String getClientSign(String nonce, String ssecurity) throws NoSuchAlgorithmException, UnsupportedEncodingException {
                String necs = "nonce=" + nonce + "&" + ssecurity;
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                byte[] cipherBytes = messageDigest.digest(necs.getBytes());
                Base64.Encoder encoder = Base64.getEncoder();
                String encodedText = encoder.encodeToString(cipherBytes);
                return encodedText;
            }

            public String getServiceToken(String location, String clientSign) throws IOException {
                String url_get_token = location + "&clientSign=" + URLEncoder.encode(clientSign);
                Log.d("MiLogin", "location: " + url_get_token);
                return sendGetReq(url_get_token);
            }

            public String sendGetReq(String url) {
                HttpGet httpGet = new HttpGet(url);
                AbstractHttpClient httpClient = new DefaultHttpClient();
                try
                {
                    HttpResponse response = httpClient.execute(httpGet);
                    return httpClient.getCookieStore().getCookies().toString();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return "";
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

            public String miLogin(String username, String passwdHash) throws IOException {
                URL url = new URL(" https://account.xiaomi.com/pass/serviceLoginAuth2");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                String param = "_json=true&sid=micoapi&locale=zh_CN&user=" + username + "&hash=" + passwdHash;
                os.write(param.getBytes("utf-8"));
                os.flush();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String responseText = sb.toString();
                return responseText;
            } // 登录主函数，发送 POST

            public String getMD5(String input) {
                if (input == null || input.length() == 0) {
                    return null;
                }
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    md5.update(input.getBytes());
                    byte[] byteArray = md5.digest();
                    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                    char[] charArray = new char[byteArray.length * 2];
                    int index = 0;
                    for (byte b : byteArray) {
                        charArray[index++] = hexDigits[b >>> 4 & 0xf];
                        charArray[index++] = hexDigits[b & 0xf];
                    }
                    return new String(charArray);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return null;
            } // 计算文本MD5
        });
    }
}