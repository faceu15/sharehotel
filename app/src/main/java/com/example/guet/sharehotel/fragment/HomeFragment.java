package com.example.guet.sharehotel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.activity.FindActivity;
import com.example.guet.sharehotel.dialog.MaterialCalendarDialog;
import com.example.guet.sharehotel.listener.MyBDLocationListener;
import com.example.guet.sharehotel.utility.DateTimeHelper;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * 主页。
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeFragment";

    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    /**ViewPager控件实例*/
    //  private ImageCircleViewPager1 image_circle_view_pager;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 定时器任务
     */
    private TimerTask timerTask;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private LinearLayout mMainLinearLayout;
    private LinearLayout mLocationLinearLayout;
    private TextView mCityTextView;
    private TextView mGuestNumber = null;
    private TextView mRoomNumber = null;

    private String guestNumber = "";
    private String roomNumber = "";
    private EditText mSearchTextView;//搜索栏

    private TextView main_room_number;
    private LinearLayout mDatePickerLL;//日期选择dialog
    private TextView mStartTimeTextView;
    private TextView mEndTimeTextView;
    private Date mCheckInDate;
    private Date mCheckOutDate;

    //定位
    private LocationClient mLocationClient = null;
    public MyBDLocationListener mBDLocationListener = new MyBDLocationListener();


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        hideKeyboard(view);

        initView(view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("CutPasteId")
    private void initView(View view) {
        mMainLinearLayout = view.findViewById(R.id.main_ll_id);
        mLocationLinearLayout = view.findViewById(R.id.locatioin_ll);
        mCityTextView = view.findViewById(R.id.tv_city);
        TextView guestSub = view.findViewById(R.id.main_guest_sub);
        TextView guestAdd = view.findViewById(R.id.main_guest_add);
        TextView roomSub = view.findViewById(R.id.main_room_sub);
        TextView roomAdd = view.findViewById(R.id.main_room_add);
        mGuestNumber = view.findViewById(R.id.main_guest_number);
        mRoomNumber = view.findViewById(R.id.main_room_number);

        //日期选择dialog
        mDatePickerLL = view.findViewById(R.id.choic_date_ll);
        mStartTimeTextView = view.findViewById(R.id.start_time_tv);
        mEndTimeTextView = view.findViewById(R.id.end_time_tv);
        Calendar c = Calendar.getInstance();
        mStartTimeTextView.setText(DateTimeHelper.formatToString(c.getTime(), "yyyy-MM-dd"));
        c.add(Calendar.DAY_OF_MONTH, 1);
        mEndTimeTextView.setText(DateTimeHelper.formatToString(c.getTime(), "yyyy-MM-dd"));

        guestNumber = mGuestNumber.getText().toString();
        roomNumber = mRoomNumber.getText().toString();
        mMainLinearLayout.setOnClickListener(this);
        mLocationLinearLayout.setOnClickListener(this);
        guestSub.setOnClickListener(this);
        guestAdd.setOnClickListener(this);
        roomSub.setOnClickListener(this);
        roomAdd.setOnClickListener(this);

        mSearchTextView = view.findViewById(R.id.main_search);
        TextView main_guest_sub = view.findViewById(R.id.main_guest_sub);
        TextView main_guest_number = view.findViewById(R.id.main_guest_number);
        TextView main_guest_add = view.findViewById(R.id.main_guest_add);
        TextView main_room_sub = view.findViewById(R.id.main_room_sub);
        main_room_number = view.findViewById(R.id.main_room_number);
        TextView main_room_add = view.findViewById(R.id.main_room_add);
        TextView find_tv = view.findViewById(R.id.find_tv);

        mStartTimeTextView.setOnClickListener(this);//选择入住时间
        mEndTimeTextView.setOnClickListener(this);

        //button.setOnClickListener(this);
        main_guest_sub.setOnClickListener(this);
        main_guest_add.setOnClickListener(this);
        main_room_sub.setOnClickListener(this);
        main_room_add.setOnClickListener(this);
        find_tv.setOnClickListener(this);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locatioin_ll://选择城市
                choiceCity();
                break;
            case R.id.main_ll_id://隐藏键盘
                hideKeyboard(view);
                break;
            case R.id.start_time_tv://入住时间选择dialog
                showDateChoiceDialog(true);
                break;
            case R.id.end_time_tv:
                showDateChoiceDialog(false);//退房时间选择dialog
                break;
            case R.id.main_guest_sub://减少房客
                int guestNumberTempSub = Integer.parseInt(guestNumber);
                if (guestNumberTempSub > 1) {
                    guestNumberTempSub--;
                }
                guestNumber = String.valueOf(guestNumberTempSub);
                mGuestNumber.setText(guestNumber);
                break;
            case R.id.main_guest_add://增加房客
                int guestNumberTempAdd = Integer.parseInt(guestNumber);
                guestNumberTempAdd++;
                guestNumber = String.valueOf(guestNumberTempAdd);
                mGuestNumber.setText(guestNumber);
                break;

            case R.id.main_room_sub://减少房子
                int roomNumberTempSub = Integer.parseInt(roomNumber);
                if (roomNumberTempSub > 1) {
                    roomNumberTempSub--;
                }
                roomNumber = String.valueOf(roomNumberTempSub);
                mRoomNumber.setText(roomNumber);
                break;
            case R.id.main_room_add://增加房子
                int roomNumberTempAdd = Integer.parseInt(roomNumber);
                roomNumberTempAdd++;
                roomNumber = String.valueOf(roomNumberTempAdd);
                mRoomNumber.setText(roomNumber);
                break;
            case R.id.find_tv://搜索
                Intent intent = new Intent(getActivity(), FindActivity.class);
                intent.putExtra("City", mCityTextView.getText().toString().trim());
                intent.putExtra("Search", mSearchTextView.getText().toString().trim());
                startActivity(intent);
                break;
        }
    }

    //城市定位和选择
    private void choiceCity() {
        CityPicker.getInstance()
                .setFragmentManager(getChildFragmentManager())
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(null)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City city) {
                        if (city != null) {
                            mCityTextView.setText(city.getName());
                        }
                    }

                    @Override
                    public void onLocate() {
                        //注册定位监听
                        mLocationClient = new LocationClient(getContext());
                        mLocationClient.registerLocationListener(mBDLocationListener);
                        LocationClientOption option = new LocationClientOption();
                        option.setIsNeedAddress(true);
                        mLocationClient.setLocOption(option);
                        mLocationClient.start();
                        Log.i(TAG, "开始定位");
                    }
                }).show();
    }


    /**
     * 选择日期dialog
     */
    private void showDateChoiceDialog(final boolean isCheckInDate) {
        MaterialCalendarDialog calendarDialog = MaterialCalendarDialog.getInstance(getContext(), null);
        calendarDialog.setOnOkClickLitener(new MaterialCalendarDialog.OnOkClickLitener() {
            @Override
            public void onOkClick(Date date) {
                if (isCheckInDate) {
                    mCheckInDate = date;
                    mStartTimeTextView.setText(DateTimeHelper.formatToString(date, "yyyy-MM-dd"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    mEndTimeTextView.setText(DateTimeHelper.formatToString(calendar.getTime(), "yyyy-MM-dd"));
                } else {
                    mCheckOutDate = date;
                    if (mCheckOutDate.getTime() > mCheckInDate.getTime()) {
                        mEndTimeTextView.setText(DateTimeHelper.formatToString(date, "yyyy-MM-dd"));
                    } else {
                        Toast.makeText(getContext(), "重新选择退房日期", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });
        calendarDialog.show(getChildFragmentManager(), TAG);

    }


    /**
     * 隐藏键盘
     */
    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


}
