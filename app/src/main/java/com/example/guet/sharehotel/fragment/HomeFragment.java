package com.example.guet.sharehotel.fragment;

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
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.activity.FindActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
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

    private void initView(View view) {

        mGuestSub = (TextView) view.findViewById(R.id.main_guest_sub);
        mGuestAdd = (TextView) view.findViewById(R.id.main_guest_add);
        mRoomSub = (TextView) view.findViewById(R.id.main_room_sub);
        mRoomAdd = (TextView) view.findViewById(R.id.main_room_add);

        mGuestNumber = (TextView) view.findViewById(R.id.main_guest_number);
        mRoomNumber = (TextView) view.findViewById(R.id.main_room_number);

        guestNumber = mGuestNumber.getText().toString();
        roomNumber = mRoomNumber.getText().toString();
        mGuestSub.setOnClickListener(this);
        mGuestAdd.setOnClickListener(this);
        mRoomSub.setOnClickListener(this);
        mRoomAdd.setOnClickListener(this);


        imageView = (ImageView) view.findViewById(R.id.imageView);
        main_menu = (ImageView) view.findViewById(R.id.main_menu);
        main_search = (EditText) view.findViewById(R.id.main_search);
        main_share = (ImageView) view.findViewById(R.id.main_share);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView = (TextView) view.findViewById(R.id.textView);
        button = (Button) view.findViewById(R.id.button);
        main_guest_sub = (TextView) view.findViewById(R.id.main_guest_sub);
        main_guest_number = (TextView) view.findViewById(R.id.main_guest_number);
        main_guest_add = (TextView) view.findViewById(R.id.main_guest_add);
        main_room_sub = (TextView) view.findViewById(R.id.main_room_sub);
        main_room_number = (TextView) view.findViewById(R.id.main_room_number);
        main_room_add = (TextView) view.findViewById(R.id.main_room_add);
        find_tv = (TextView) view.findViewById(R.id.find_tv);

        button.setOnClickListener(this);
        main_guest_sub.setOnClickListener(this);
        main_guest_add.setOnClickListener(this);
        main_room_sub.setOnClickListener(this);
        main_room_add.setOnClickListener(this);
        find_tv.setOnClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
     * 隐藏键盘
     */
    private void hideKeyboard(View v) {

        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


}
