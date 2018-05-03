package com.example.guet.sharehotel.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.fragment.IntegralRecordFragment;
import com.example.guet.sharehotel.utility.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;

import java.util.ArrayList;

/**
 * 个人中心->信用积分->记录
 */
public class IntegralRecordActivity extends AppCompatActivity implements IntegralRecordFragment.OnFragmentInteractionListener, View.OnClickListener {

    private View mDecorView;
    private String[] mTitles = {"全部", "收入", "支出"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_record);

        //this.getSupportActionBar().hide();
        iniView();
    }

    private void iniView() {


        mFragments.add(new IntegralRecordFragment());
        mFragments.add(new IntegralRecordFragment());
        mFragments.add(new IntegralRecordFragment());

        mDecorView = getWindow().getDecorView();
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_4);
        tabLayout_4.setTabData(mTitles, this, R.id.fl_change, mFragments);


        ImageView close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
