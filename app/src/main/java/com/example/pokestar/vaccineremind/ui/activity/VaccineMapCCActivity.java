package com.example.pokestar.vaccineremind.ui.activity;

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

public class VaccineMapCCActivity extends AppCompatActivity {

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
    BitmapDescriptor bdGround = BitmapDescriptorFactory
            .fromResource(R.drawable.planetrack51);



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_map);


        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(8).build()));

        initOverLay();
        initView();

    }

    private void initOverLay() {

//        // add marker overlay
//        LatLng llA = new LatLng(39.963175, 116.400244);
//        LatLng llB = new LatLng(39.942821, 116.369199);
//        LatLng llC = new LatLng(39.939723, 116.425541);
//        LatLng llD = new LatLng(39.906965, 116.401394);
//
//        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//                .zIndex(9).draggable(true);
//      /*  if (animationBox.isChecked()) {
//            // 掉下动画
//            ooA.animateType(MarkerAnimateType.drop);
//        }*/
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//        MarkerOptions ooB = new MarkerOptions().position(llB).icon(bdB)
//                .zIndex(5);
//      /*  if (animationBox.isChecked()) {
//            // 掉下动画
//            ooB.animateType(MarkerAnimateType.drop);
//        }*/
//        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
//        MarkerOptions ooC = new MarkerOptions().position(llC).icon(bdC)
//                .perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
//       /* if (animationBox.isChecked()) {
//            // 生长动画
//            ooC.animateType(MarkerAnimateType.grow);
//        }*/
//        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//        giflist.add(bdA);
//        giflist.add(bdB);
//        giflist.add(bdC);
//        MarkerOptions ooD = new MarkerOptions().position(llD).icons(giflist)
//                .zIndex(0).period(10);
//       /* if (animationBox.isChecked()) {
//            // 生长动画
//            ooD.animateType(MarkerAnimateType.grow);
//        }*/
//        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

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

    private void initView() {
//坐标点

        //长春长生制药
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
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("长春长生制药/假疫苗源头")
                //.rotate(-30)
                .position(cccsPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);





        //山东疾控中心
        //定义Maker坐标点
        LatLng sdjkPoint = new LatLng(36.6539524483,117.0464421932);
        //构建Marker图标
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.vacmappoint);


        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option2 = new MarkerOptions()
                .position(sdjkPoint)
                .icon(bitmap2);

        OverlayOptions textOption1 = new TextOptions()
                //.bgColor(0xAAFFFF00)
                .fontSize(24)
                //.fontColor(0xFFFF00FF)
                .text("山东省疾控中心")
                //.rotate(-30)
                .position(sdjkPoint);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption1);


        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option2);



//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_marker, null);
//
//        // ImageView img_hotel_image=
//        // (ImageView)view.findViewById(R.id.img_hotel_image);
//        // new
//        // DownloadImageTask(img_hotel_image).execute(hotel.getHotelImageUrl());
//
//        TextView tv_hotel_price = (TextView) view.findViewById(R.id.tv_hotel_price);
//        tv_hotel_price.setText(new StringBuilder().append(hotel.getHotelPrice()).append("￥起"));
//        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("HOTEL", hotel);
//
//        OverlayOptions oo = new MarkerOptions().position(ll).icon(markerIcon).zIndex(9).draggable(true).extraInfo(bundle);
//        mBaiduMap.addOverlay(oo);


        //        //构建文字Option对象，用于在地图上添加文字
//        OverlayOptions textOption = new TextOptions()
//                .bgColor(0xAAFFFF00)
//                .fontSize(24)
//                .fontColor(0xFFFF00FF)
//                .text("长春长生制药/假疫苗源头")
//                .rotate(-30)
//                .position(cccsPoint);
//
//        //在地图上添加该文字对象并显示
//        mBaiduMap.addOverlay(textOption);

//        //武汉生物制药
//        LatLng whswPoint = new LatLng(30.3707,114.2604);
//        //构建Marker图标
//        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.back);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option2 = new MarkerOptions()
//                .position(whswPoint)
//                .icon(bitmap2);
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option2);
//
//        //创建InfoWindow展示的view 信息窗
//        Button button = new Button(getApplicationContext());
//        button.setBackgroundResource(R.drawable.icon_marka);
//        TextView textView = new TextView(getApplicationContext());
//        textView.setText("武汉生物制药");
//
//
//         //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//        InfoWindow mInfoWindow = new InfoWindow(textView, whswPoint, -47);
//
//        //显示InfoWindow
//        mBaiduMap.showInfoWindow(mInfoWindow);







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