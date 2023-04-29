package com.example.applicationformyhomework_run;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {
    private static final String URL_BASE="https://www.yiketianqi.com/free/day?unescape=1&appid=17243864&appsecret=4lMHiL8L&city=";
    private static final String TAG = MainActivity.class.getSimpleName() ;
    private MapView mMapView = null;
    private String city="台州";
    private LocationClient mLocationClient = null;
    private TextView mTextView_temp ;
    private TextView mTextView_city_1 ;
    private TextView mTextView_city_2 ;
    private TextView mTextView_weather;
    private TextView mTextView_tips ;
    private TextView mTextView;
    private String content;
    private BaiduMap mBaiduMap;
    private MyLocationListenner myListener=null;
    public MyFragment(String content) {
        this.content = content;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        Log.i(TAG, "onCreateView_1: view");
        mMapView = (MapView) view.findViewById(R.id.bmapView1);
        mTextView_temp = (TextView)view.findViewById(R.id.textView_temp);
        mTextView_city_1 = (TextView) view.findViewById(R.id.textView_city_1);
        mTextView_city_2 = (TextView) view.findViewById(R.id.textView_city_2);
        mTextView_weather = (TextView) view.findViewById(R.id.textView_weather);
        mTextView_tips =(TextView) view.findViewById(R.id.textView_tips);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate_3: ");
        MyLocationListenner myListener= new MyLocationListenner();
        Log.i(TAG, "onCreateView_2: view");
        try {
            mLocationClient = new LocationClient(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocationClientOption option = new LocationClientOption();

        option.setOpenGps(true);

        option.setIsNeedAddress(true);

        option.setAddrType("all");

        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(myListener);

        mLocationClient.start();
        Log.i(TAG, "onCreate: "+city);
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    private class MyLocationListenner implements BDLocationListener {
        @Override

        public void onReceiveLocation(BDLocation location) {
            try {
//当前设备位置所在的市
                String lcity = location.getCity();
                Log.i(TAG, "onReceiveLocation: "+location.getLatitude());
                Log.i(TAG, "on1101: "+lcity);
                if(lcity==null) lcity="北京市";
                //Log.i(TAG, "on11011: yes");
                Log.i(TAG, "on11: "+lcity);
                city=lcity.substring(0,lcity.length()-1);
                Log.i(TAG, "onReceiveLocation: "+city);
                mTextView_city_1.setText(lcity);
                getweather(URL_BASE+city);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }
    private void getweather(String typeuri) {
        Log.i(TAG, "testgetweather: yes");
        OkHttpClient client = new OkHttpClient();
        Log.i(TAG, "getweather: "+city);
        Request request= new Request.Builder().url(typeuri).build();
        //Log.i(TAG, "getweather: reset");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String content = response.body().string();
                Gson gson=new Gson();
                WeatherResult weatherResult=gson.fromJson(content,WeatherResult.class);
                getActivity().runOnUiThread(()-> {
                    //实例化
                    Log.i(TAG, "onResponse: shili");
                    String str_weather = weatherResult.getWea();
                    mTextView_city_2.setText(String.format(weatherResult.getCity()));
                    mTextView_weather.setText(String.format(str_weather));

                    if(str_weather.contains("雨")){
                        Log.i(TAG, "onResponse: 确实下雨了");
                        mTextView_tips.setText(String.format("当前不适宜跑步"));
                        //不能进行跑步操作

                    }
                    int tem = Integer.parseInt(weatherResult.getTem());
                    Log.i(TAG, "气温为: "+tem);
                    mTextView_temp.setText(String.format("%s",tem));
                });

            }
        });
    }

}