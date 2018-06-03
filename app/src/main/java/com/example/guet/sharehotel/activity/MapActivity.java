package com.example.guet.sharehotel.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
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
import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.model.bean.Hotel;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.ll_location_back)
    LinearLayout mBackLinearLayout;

    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;

    private Geocoder mGeocoder;

    private Hotel mHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());//初始化SDK
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mHotel = (Hotel) getIntent().getBundleExtra("HotelBundle").getSerializable("Hotel");
        initLocation();
    }

    private void initLocation() {
        Address address = null;
        mGeocoder = new Geocoder(this);
        try {
            List<Address> list = mGeocoder.getFromLocationName(mHotel.getAddress(), 1);
            address = list.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        final LatLng newlatLng = new LatLng(address.getLatitude(), address.getLongitude());
        mLocationClient = new LocationClient(getApplicationContext());

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.red_location);
        OverlayOptions overlayOptions = new MarkerOptions().position(newlatLng).icon(bitmapDescriptor);//创建一个图层选项
        mBaiduMap.addOverlay(overlayOptions);
        MapStatus mapStatus = new MapStatus.Builder().target(newlatLng).zoom(14).build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);

       /* mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
               if (bdLocation == null) {
                    Toast.makeText(MapActivity.this, "location is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.red_location);
                OverlayOptions overlayOptions = new MarkerOptions().position(newlatLng).icon(bitmapDescriptor);//创建一个图层选项
                mBaiduMap.addOverlay(overlayOptions);
                MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(14).build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
        mLocationClient.setLocOption(InitLocOption());
        mLocationClient.start();*/

    }

    private LocationClientOption InitLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//默认高精度定位模式
        option.setCoorType("bd0911");//默认gcj02，设置返回的定位结果坐标系
        //option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(true);
        option.SetIgnoreCacheException(true);
        option.setEnableSimulateGps(false);
        return option;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
        super.onDestroy();

        mMapView.onDestroy();
    }

    @OnClick(R.id.ll_location_back)
    public void onViewClicked() {
        finish();
    }
}
