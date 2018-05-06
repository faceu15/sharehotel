package com.example.guet.sharehotel.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.fragment.AllCouponFragment;
import com.example.guet.sharehotel.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;

import java.util.ArrayList;

/**
 * 个人中心->优惠券
 */
public class CouponActivity extends AppCompatActivity implements View.OnClickListener, AllCouponFragment.OnFragmentInteractionListener {


    private View mDecorView;
    private String[] mTitles = {"全部", "可用", "已过期"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        //this.getSupportActionBar().hide();

        initView();
    }

    private void initView() {

        mFragments.add(new AllCouponFragment());
        mFragments.add(new AllCouponFragment());
        mFragments.add(new AllCouponFragment());

        mDecorView = getWindow().getDecorView();
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_4);
        tabLayout_4.setTabData(mTitles, this, R.id.fl_change, mFragments);


        ImageView close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }
}
