package com.example.guet.sharehotel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guet.sharehotel.fragment.CommentFragment;
import com.example.guet.sharehotel.fragment.HistoryOrderFragment;
import com.example.guet.sharehotel.fragment.HousingFragment;
import com.example.guet.sharehotel.fragment.UncomfirmFragment;

public class OrderFragmentAdapter extends FragmentPagerAdapter {

    private final String[] titles = {"待确认", "入住中", "待评价", "历史订单"};
    //待确认
    private UncomfirmFragment mUncomfirmFragment;
    //入住中
    private HousingFragment mHousingFragment;
    //待评价
    private CommentFragment mCommentFragment;
    //历史订单
    private HistoryOrderFragment mHistoryOrderFragment;

    public OrderFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mUncomfirmFragment == null) {
                    mUncomfirmFragment = new UncomfirmFragment();
                }
                return mUncomfirmFragment;
            case 1:
                if (mHousingFragment == null) {
                    mHousingFragment = new HousingFragment();
                }
                return mHousingFragment;
            case 2:
                if (mCommentFragment == null) {
                    mCommentFragment = new CommentFragment();
                }
                return mCommentFragment;
            case 3:
                if (mHistoryOrderFragment == null) {
                    mHistoryOrderFragment = new HistoryOrderFragment();
                }
                return mHistoryOrderFragment;
            default:
                return null;
        }
    }

}
