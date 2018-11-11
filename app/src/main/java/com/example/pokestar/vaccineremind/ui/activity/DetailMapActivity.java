package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailMapActivity extends AppCompatActivity {

    private static final String TAG = "DetailMapActivity";
    private MapView mapView = null;
    private BaiduMap map;

    String company;
    double comp_lat;
    double comp_lot;
    double latude;
    double lotude;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setMapCustomFile(this, "mymap.json");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        company = getIntent().getStringExtra("company");

        if(company.substring(0,5).equals(("浙江卫信生物药业").substring(0,5))){
            comp_lat = 29.3556523462;
            comp_lot = 121.4700333274;
        }else if(company.substring(0,5).equals(("北京天坛生物制品股份有限公司").substring(0,5))){
            comp_lat = 39.9209597771;
            comp_lot = 116.5774888734;
        }else if(company.substring(0,5).equals(("山西康宝生物制品股份有限公司").substring(0,5))){
            comp_lat = 36.2201086529;
            comp_lot = 113.1053738173;
        }else if(company.substring(0,5).equals(("浙江天元生物药业有限公司").substring(0,5))){
            comp_lat = 30.4464459883;
            comp_lot = 120.3000850212;
        }else if(company.substring(0,5).equals(("上海科华生物工程股份有限公司").substring(0,5))){
            comp_lat = 31.1834490781;
            comp_lot = 121.4085711492;
        }else if(company.substring(0,5).equals(("上海生物制品研究所有限责任公司").substring(0,5))){
            comp_lat = 31.2163963911;
            comp_lot = 121.4335440991;
        }




        mapView = (MapView) findViewById(R.id.detail_map);
        map = mapView.getMap();

        map.setMapStatus(
                MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                        .target(new LatLng(36.0941023381,116.5261276258))
                        .zoom(7)
                        .build()));

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation location) {
                super.onReceiveLocation(location);

                //定义用户Maker坐标点
                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.vacpoint_greenthis);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                map.addOverlay(option);

                //定义公司Maker坐标点
                LatLng comppoint = new LatLng(comp_lat, comp_lot);
                //构建Marker图标
                BitmapDescriptor compbitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.comp);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions compoption = new MarkerOptions()
                        .position(comppoint)
                        .icon(compbitmap);
                //在地图上添加Marker，并显示
                map.addOverlay(compoption);
                OverlayOptions textOption = new TextOptions()
                        //.bgColor(0xAAFFFF00)
                        .fontSize(30)
                        //.fontColor(0xFFFF00FF)
                        .text("疫苗生产地")
                        //.rotate(-30)
                        .position(comppoint);

                //在地图上添加该文字对象并显示
                map.addOverlay(textOption);


                LatLng p1 = new LatLng((location.getLatitude() + comp_lat)/1.9, (location.getLongitude() + comp_lot)/2);
                LatLng p12 = new LatLng((location.getLatitude() + comp_lat)/1.9-1, (location.getLongitude() + comp_lot)/2);
                //构建Marker图标
                BitmapDescriptor pbitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.plane);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions poption = new MarkerOptions()
                        .position(p12)
                        .icon(pbitmap);
                //在地图上添加Marker，并显示
                map.addOverlay(poption);



                List<LatLng> points = new ArrayList<LatLng>();
                points.add(point);
                points.add(p1);
                points.add(comppoint);
//                OverlayOptions ooPolyline = new PolylineOptions().width(10)
//                        .color(0xAAFF0000).points(points);
//                Polyline mPolyline = (Polyline) map.addOverlay(ooPolyline);
//                mPolyline.setDottedLine(true);

                OverlayOptions ooArc = new ArcOptions().color(0xAA00FF00).width(4)
////设置颜色和透明度，均使用16进制显示，0xAARRGGBB，如 0xAA00FF00 其中AA是透明度，00FF00为颜色
                        .points(point, p1,comppoint);
                map.addOverlay(ooArc);

            }
        });
        //注册监听函数

        initMap();

        mLocationClient.start();
//mLocationClient为第二步初始化过的LocationClient对象
//调用LocationClient的start()方法，便可发起定位请求

//        map.setMapStatus(
//                MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
//                        .target(new LatLng(41.5794065934,120.4573500100))
//                        .zoom(7)
//                        .build()));
//
//        MapView.setMapCustomEnable(true);

    }

    private void initMap() {

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认GCJ02
//GCJ02：国测局坐标；
//BD09ll：百度经纬度坐标；
//BD09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(1000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明




    }


    public class MyLocationListener extends BDAbstractLocationListener {



        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            latude = latitude;
            lotude = longitude;
            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准


            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

    // 设置个性化地图config文件路径
    private void setMapCustomFile(Context context, String PATH) {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream = context.getAssets()
                    .open(PATH);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            moduleName = context.getFilesDir().getAbsolutePath();
            File f = new File(moduleName + "/" + PATH);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MapView.setCustomMapStylePath(moduleName + "/" + PATH);

    }






    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mapView.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(myListener);
        }
    }
}
