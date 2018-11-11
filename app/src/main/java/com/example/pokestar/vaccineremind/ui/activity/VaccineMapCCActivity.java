package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VaccineMapCCActivity extends AppCompatActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;






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
    BitmapDescriptor bdGround = BitmapDescriptorFactory
            .fromResource(R.drawable.planetrack51);



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * MapView (TextureMapView)的
         * {@link MapView.setCustomMapStylePath(String customMapStylePath)}
         * 方法一定要在MapView(TextureMapView)创建之前调用。
         * 如果是setContentView方法通过布局加载MapView(TextureMapView), 那么一定要放置在
         * MapView.setCustomMapStylePath方法之后执行，否则个性化地图不会显示
         */
        setMapCustomFile(this, "mymap.json");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_map);


        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        initOverLay();
        initView();

        mBaiduMap.setMapStatus(
                MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                        .target(new LatLng(41.5794065934,120.4573500100))
                        .zoom(7)
                        .build()));

        MapView.setMapCustomEnable(true);

    }

    /**
    *疫苗运行轨迹
    * */
    private void initOverLay() {


        // add ground overlay
        LatLng northeast = new LatLng(36.64804,117.03988);//山东疾控中心
        LatLng southwest = new LatLng(43.7993,125.2542);//changchun
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
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


    }

    /**
     * 坐标点
     */
    private void initView() {
        //坐标点

        /** 长春长生制药*/
        //定义Maker坐标点
        LatLng cccsPoint = new LatLng(43.7993,125.2542);
        //构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.posion);


        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(cccsPoint)
                .icon(bitmap1);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option1);

        OverlayOptions textOption = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(40)
                //.fontColor(0xFFFF00FF)
                .text("长春长生制药/假疫苗源头")
                //.rotate(-30)
                .position(cccsPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);


        /** 山东疾控中心*/
        //定义Maker坐标点
        LatLng sdjkPoint = new LatLng(36.6539524483,117.0464421932);
        //构建Marker图标
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_blue);


        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option2 = new MarkerOptions()
                .position(sdjkPoint)
                .icon(bitmap2);

        OverlayOptions textOption1 = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("山东省(济南)疾控中心")
                //.rotate(-30)
                .position(sdjkPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption1);


        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option2);

        /** 山东各省市  济南、淄博、烟台、济宁、泰安、威海、日照、莱芜 **/
        /**
         * 济南疾控中心
         */
        //定义Maker坐标点
        LatLng jnjkPoint = new LatLng(36.7025734689,117.5204028515);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("济南市疾控中心")
                //.rotate(-30)
                .position(jnjkPoint));


        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(jnjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 淄博疾控中心
         */
        //定义Maker坐标点
        LatLng zbjkPoint = new LatLng(36.7950788323,118.0596837174);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("淄博市疾控中心")
                //.rotate(-30)
                .position(zbjkPoint));


        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(zbjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 烟台疾控中心
         */
        //定义Maker坐标点
        LatLng ytjkPoint = new LatLng(37.4719069326,121.4581601136);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("烟台市疾控中心")
                //.rotate(-30)
                .position(ytjkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(ytjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 济宁疾控中心
         */
        //定义Maker坐标点
        LatLng jnijkPoint = new LatLng(35.5826795913,116.9997452449);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("济宁市疾控中心")
                //.rotate(-30)
                .position(jnijkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(jnijkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 泰安疾控中心
         */
        //定义Maker坐标点
        LatLng tajkPoint = new LatLng(36.1869626953,116.8047691141);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("泰安市疾控中心")
                //.rotate(-30)
                .position(tajkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(tajkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 威海疾控中心
         */
        //定义Maker坐标点
        LatLng whjkPoint = new LatLng(37.5137206876,122.1291906890);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("威海市疾控中心")
                //.rotate(-30)
                .position(whjkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(whjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 日照疾控中心
         */
        //定义Maker坐标点
        LatLng rzjkPoint = new LatLng(35.4340743562,119.5303139449);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("日照市疾控中心")
                //.rotate(-30)
                .position(rzjkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(rzjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));

        /**
         * 莱芜疾控中心
         */
        //定义Maker坐标点
        LatLng lwjkPoint = new LatLng(36.2178311771,117.6934842771);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("莱芜市疾控中心")
                //.rotate(-30)
                .position(lwjkPoint));

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(lwjkPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vacpoint_green)));


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


    /**
     * Gets the view bitmap.
     *
     * @param addViewContent
     *            the add view content
     * @return the view bitmap
     */
    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }


    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        MapView.setMapCustomEnable(false);
        mMapView.onDestroy();
        super.onDestroy();

        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
        bd.recycle();
        bdGround.recycle();
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