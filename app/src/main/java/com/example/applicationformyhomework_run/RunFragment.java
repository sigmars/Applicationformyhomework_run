package com.example.applicationformyhomework_run;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import java.util.concurrent.TimeUnit;

@SuppressLint("ValidFragment")
public class RunFragment extends Fragment {
    private  int tick =0;
    private  boolean Isrunning=false;
    private String content;
    private LocationClient mLocationClient = null;
    private MapView mMapView ;
    private TextView time_count;
    private Button btn_start_end;
    BaiduMap mBaiduMap;


    public RunFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run,container,false);
        mMapView = (MapView) view.findViewById(R.id.bmapView_run);
        time_count = (TextView) view.findViewById(R.id.textView_time_count);
        btn_start_end=(Button) view.findViewById(R.id.button_start_end);
        btn_start_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_start_end.getText()=="开始"){
                    btn_start_end.setText("暂停");
                    Isrunning=true;
                }else{
                    btn_start_end.setText("开始");
                    Isrunning=false;
                }
            }
        });
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Isrunning=false;

        Thread tr=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        if(Isrunning) tick++;
                        if(tick==0) Isrunning = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TimerShower();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        tr.start();
    }

    private void TimerShower() {
        int hour=tick/3600;
        int min=tick/60 % 60;
        int second=tick%60;
        String a = String.format("%02d:%02d:%02d",hour,min,second);
        time_count.setText(""+a);
    }

}