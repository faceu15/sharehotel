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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.activity.FindActivity;
import com.example.guet.sharehotel.dialog.MaterialCalendarDialog;
import com.example.guet.sharehotel.utility.DateTimeHelper;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

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
    private CommonTabLayout tabLayout;
    private TextView mGuestSub = null;
    private TextView mGuestAdd = null;
    private TextView mRoomSub = null;
    private TextView mRoomAdd = null;
    private TextView mGuestNumber = null;
    private TextView mRoomNumber = null;

    private String guestNumber = "";
    private String roomNumber = "";
    private ImageView imageView;
    private ImageView main_menu;
    private EditText main_search;
    private ImageView main_share;
    private TextView textView2;
    private TextView textView;
    private Button button;
    private TextView main_guest_sub;
    private TextView main_guest_number;
    private TextView main_guest_add;
    private TextView main_room_sub;
    private TextView main_room_number;
    private TextView main_room_add;
    private TextView find_tv;

    private LinearLayout mDatePickerLL;//日期选择dialog
    private TextView mStartTimeTextView;
    private TextView mEndTimeTextView;
    private Date mCheckInDate;
    private Date mCheckOutDate;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    // TODO: Rename method, update argument and hook method into UI event
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
        mGuestSub = view.findViewById(R.id.main_guest_sub);
        mGuestAdd = view.findViewById(R.id.main_guest_add);
        mRoomSub = view.findViewById(R.id.main_room_sub);
        mRoomAdd = view.findViewById(R.id.main_room_add);
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
        mGuestSub.setOnClickListener(this);
        mGuestAdd.setOnClickListener(this);
        mRoomSub.setOnClickListener(this);
        mRoomAdd.setOnClickListener(this);

        imageView = view.findViewById(R.id.imageView);
        main_menu = view.findViewById(R.id.main_menu);
        main_search = view.findViewById(R.id.main_search);
        main_guest_sub = view.findViewById(R.id.main_guest_sub);
        main_guest_number = view.findViewById(R.id.main_guest_number);
        main_guest_add = view.findViewById(R.id.main_guest_add);
        main_room_sub = view.findViewById(R.id.main_room_sub);
        main_room_number = view.findViewById(R.id.main_room_number);
        main_room_add = view.findViewById(R.id.main_room_add);
        find_tv = view.findViewById(R.id.find_tv);

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_ll_id:
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.start_time_tv://入住时间选择dialog
                showDateChoiceDialog(true);
                break;
            case R.id.end_time_tv:
                showDateChoiceDialog(false);//退房时间选择dialog
                break;
            case R.id.main_guest_sub:
                int guestNumberTempSub = Integer.parseInt(guestNumber);
                if (guestNumberTempSub > 1) {
                    guestNumberTempSub--;
                }
                Log.d("StevenGo", "123");
                guestNumber = String.valueOf(guestNumberTempSub);
                mGuestNumber.setText(guestNumber);
                break;
            case R.id.main_guest_add:
                int guestNumberTempAdd = Integer.parseInt(guestNumber);
                guestNumberTempAdd++;
                guestNumber = String.valueOf(guestNumberTempAdd);
                Log.d("StevenGo", "123");
                mGuestNumber.setText(guestNumber);
                break;

            case R.id.main_room_sub:
                int roomNumberTempSub = Integer.parseInt(roomNumber);
                if (roomNumberTempSub > 1) {
                    roomNumberTempSub--;
                }
                roomNumber = String.valueOf(roomNumberTempSub);
                mRoomNumber.setText(roomNumber);
                break;
            case R.id.main_room_add:
                int roomNumberTempAdd = Integer.parseInt(roomNumber);
                roomNumberTempAdd++;
                roomNumber = String.valueOf(roomNumberTempAdd);
                mRoomNumber.setText(roomNumber);
                break;
            case R.id.find_tv:
                Intent intent = new Intent(getActivity(), FindActivity.class);
                startActivity(intent);
                break;
        }
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
