package com.example.pokestar.vaccineremind.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.pokestar.vaccineremind.R;

public class VaccineMapWHActivity extends AppCompatActivity {

    private MapView mMapView = null;
    BaiduMap mBaiduMap;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markb);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markc);
    BitmapDescriptor bdD = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markd);
    BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_add);
    BitmapDescriptor bdGround14 = BitmapDescriptorFactory
            .fromResource(R.drawable.planetrack14);
    BitmapDescriptor bdGround21 = BitmapDescriptorFactory
            .fromResource(R.drawable.planetrack21);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_map_wh);

        mMapView = (MapView) findViewById(R.id.bmapView_wh);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(8).build()));

        initOverLay();
        initView();

    }

    private void initView() {

        //武汉生物制药
        //定义Maker坐标点
        LatLng cccsPoint = new LatLng(30.3707,114.2604);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.posion);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(cccsPoint)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        OverlayOptions textOption = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("武汉生物制药/假疫苗源头")
                //.rotate(-30)
                .position(cccsPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);


        //河北省疾控中心
        //定义Maker坐标点
        LatLng hbjkPoint = new LatLng(38.0284596488,114.5255852654);
        //构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.vacmappoint);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(hbjkPoint)
                .icon(bitmap1);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option1);

        OverlayOptions textOption1 = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("河北省疾控中心")
                //.rotate(-30)
                .position(hbjkPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption1);


        //重庆市疾控中心
        //定义Maker坐标点
        LatLng cqjkPoint = new LatLng(29.5486729840,106.5337964588);
        //构建Marker图标
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.vacmappoint);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option2 = new MarkerOptions()
                .position(cqjkPoint)
                .icon(bitmap2);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option2);

        OverlayOptions textOption2 = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("重庆省疾控中心")
                //.rotate(-30)
                .position(cqjkPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption2);





    }

    private void initOverLay() {

        LatLng northeast = new LatLng(38.0284596488,114.5255852654);//河北疾控
        LatLng southwest = new LatLng(30.3707,114.2604);//武汉生物
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround14).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(
                        getApplicationContext(),
                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
                                + marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });

        LatLng northeast1 = new LatLng(30.3707,114.2604);//武汉生物
        LatLng southwest1 = new LatLng(29.5486729840,106.5337964588);//重庆疾控
        LatLngBounds bounds1 = new LatLngBounds.Builder().include(northeast1)
                .include(southwest1).build();

        OverlayOptions ooGround1 = new GroundOverlayOptions()
                .positionFromBounds(bounds1).image(bdGround21).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround1);

        MapStatusUpdate u1 = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u1);

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(
                        getApplicationContext(),
                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
                                + marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });


    }


    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        super.onDestroy();

        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
        bd.recycle();
        bdGround14.recycle();
        bdGround21.recycle();
    }
    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();

    }
    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();


    }
}
