package com.example.guet.sharehotel.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.activity.CouponActivity;
import com.example.guet.sharehotel.activity.CreditScoreActivity;
import com.example.guet.sharehotel.activity.CustomerServiceActivity;
import com.example.guet.sharehotel.activity.LoginActivity;
import com.example.guet.sharehotel.activity.MyCollectionActivity1;
import com.example.guet.sharehotel.activity.MyHouseActivity;
import com.example.guet.sharehotel.activity.PostHouseActivity;
import com.example.guet.sharehotel.activity.SettingActivity;
import com.example.guet.sharehotel.application.MyApplication;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 我的
 */
public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "PersonalCenterFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    //private Boolean isLogin;
    //private static User user;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String jsonData = (String) msg.obj;

            }
        }
    };

    /**
     * 活动数据获取中的加载框
     */
    private ProgressDialog progressDialog;
    private TextView set_iv;
    private ImageView user_icon_iv;
    private TextView user_name_tv;
    //private TextView mobil_phone_tv;
    private LinearLayout one_key_to_check_out_linear_layout;
    private LinearLayout coupon_linear_layout;
    private LinearLayout credit_score_linear_layout;
    private LinearLayout my_collection_linear_layout;
    private LinearLayout customer_service_linear_layout;
    private LinearLayout my_wallet_linear_layout;
    private ImageView right_to_buy_iv;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    public static PersonalCenterFragment newInstance(String param1, String param2) {
        PersonalCenterFragment fragment = new PersonalCenterFragment();
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
        //本函数，在每次从其它fragment切换到此都会执行一次。
        view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        //isLogin = getArguments().getBoolean("isLogin");
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        resumeData();

    }

    private void resumeData() {
        MyApplication myApp = MyApplication.getInstance();
        if (myApp.isLogin()) {
            user_name_tv.setText(myApp.getAccount());
            user_name_tv.setClickable(false);
            user_icon_iv.setImageResource(R.mipmap.login_head);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initView(View view) {
        set_iv = view.findViewById(R.id.set_iv);
        set_iv.setOnClickListener(this);
        user_icon_iv = view.findViewById(R.id.user_icon_iv);
        user_name_tv = view.findViewById(R.id.user_name_tv);

        user_name_tv.setOnClickListener(this);
        //mobil_phone_tv = (TextView) view.findViewById(R.id.mobil_phone_tv);
        one_key_to_check_out_linear_layout = view.findViewById(R.id.one_key_to_check_out_linear_layout);
        one_key_to_check_out_linear_layout.setOnClickListener(this);
        coupon_linear_layout = view.findViewById(R.id.coupon_linear_layout);
        coupon_linear_layout.setOnClickListener(this);
        credit_score_linear_layout = view.findViewById(R.id.credit_score_linear_layout);
        credit_score_linear_layout.setOnClickListener(this);
        my_collection_linear_layout = view.findViewById(R.id.my_collection_linear_layout);
        my_collection_linear_layout.setOnClickListener(this);
        customer_service_linear_layout = view.findViewById(R.id.customer_service_linear_layout);
        customer_service_linear_layout.setOnClickListener(this);
        my_wallet_linear_layout = view.findViewById(R.id.my_wallet_linear_layout);
        my_wallet_linear_layout.setOnClickListener(this);
        right_to_buy_iv = view.findViewById(R.id.right_to_buy_iv);
        right_to_buy_iv.setOnClickListener(this);

        //发布房子
        LinearLayout postHouseLinearLayout = view.findViewById(R.id.post_house_ll);
        postHouseLinearLayout.setOnClickListener(this);
        //我的房子
        LinearLayout myHouseLinearLayout = view.findViewById(R.id.ll_center_myhouse);
        myHouseLinearLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.set_iv://设置
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.user_name_tv://登录
                Intent login_intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(login_intent, 1);
                break;
            case R.id.customer_service_linear_layout://客服
                Intent intent1 = new Intent(getActivity(), CustomerServiceActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_collection_linear_layout://收藏
                Intent intent2 = new Intent(getActivity(), MyCollectionActivity1.class);
                startActivity(intent2);
                break;
            case R.id.coupon_linear_layout://优惠券
                Intent intent3 = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent3);
                break;
            case R.id.credit_score_linear_layout://信用积分
                Intent intent4 = new Intent(getActivity(), CreditScoreActivity.class);
                startActivity(intent4);
                break;
            case R.id.post_house_ll://发布房子
                if (MyApplication.getInstance().getAccount() == null) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent5 = new Intent(getActivity(), PostHouseActivity.class);
                    startActivity(intent5);
                }
                break;
            case R.id.ll_center_myhouse://我的房子
                if (MyApplication.getInstance().getAccount() == null) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent myhouse = new Intent(getActivity(), MyHouseActivity.class);
                    startActivity(myhouse);
                }
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        MyApplication app = MyApplication.getInstance();
        switch (resultCode) {
            case 1://登录返回
                if (data.getExtras().getInt("Login", 0) == 1) {
                    String account = data.getExtras().getString("Account");
                    user_name_tv.setText(account);//设置显示用户账号
                    user_name_tv.setClickable(false);//设置账号不可点击
                    user_icon_iv.setImageResource(R.mipmap.login_head);
                    app.setLogin(true);
                    app.setAccount(account);
                }
                break;
            case 2://退出当前账号
                if (data.getExtras().getInt("Logout", 0) == 1) {
                    Log.i(TAG, "退出登录");
                    app.setLogin(false);
                    app.setAccount("");
                    user_name_tv.setText("登录");//恢复登录前设置
                    user_name_tv.setClickable(true);
                    user_icon_iv.setImageResource(R.mipmap.headpic);
                }
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
