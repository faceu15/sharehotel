package com.example.guet.sharehotel.listener;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import java.util.List;

/**
 *
 * BDLocationListener接口有1个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
 */
public class MyLocationListener implements BDLocationListener {


    private static final String TAG = "MyLocationListener";
    /**定位结果*/
    private StringBuffer sb;
    /**当前定位到的城市*/
    private static String localCity;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
        sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ncity : ");
            sb.append(location.getCity());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
            sb.append("\ncity : ");
            sb.append(location.getCity());

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        //LogUtil.e(TAG  , sb.toString());
        //LogUtil.e(TAG , "当前位置 ： " + location.getCity());
      /*  DrawLayoutActivity.stopLocation();           //在定位了一次后暂停定位。
        //定位完成后，显示当前定位到的城市
        ChildFragmentReserveHotelAllDay fragment1 = (ChildFragmentReserveHotelAllDay)ReserveFragment.getChildReverseFragment1();
        fragment1.setLocation(location.getCity());
        LogUtil.e(TAG , "城市 = " + location.getCity());
        ChildFragmentReserveHotelAllDay fragment2 = (ChildFragmentReserveHotelAllDay)ReserveFragment.getChildReverseFragment2();
        fragment2.setLocation(location.getCity());*/

        localCity = location.getCity();
    }

    /**获取当前定位的城市*/
    public static String getLocalCity() {
        return localCity;
    }


}
