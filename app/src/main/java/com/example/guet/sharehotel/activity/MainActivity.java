package com.example.guet.sharehotel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.fragment.BaseFragment;
import com.example.guet.sharehotel.fragment.CommentFragment;
import com.example.guet.sharehotel.fragment.HistoryOrderFragment;
import com.example.guet.sharehotel.fragment.HomeFragment;
import com.example.guet.sharehotel.fragment.HousingFragment;
import com.example.guet.sharehotel.fragment.MessageFragment;
import com.example.guet.sharehotel.fragment.OrderFragment;
import com.example.guet.sharehotel.fragment.PersonalCenterFragment;
import com.example.guet.sharehotel.fragment.UncomfirmFragment;
import com.example.guet.sharehotel.model.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心， 抽屉 ， 百度定位 ， 初始化Bmob
 */
public class MainActivity extends FragmentActivity
        implements PersonalCenterFragment.OnFragmentInteractionListener, MessageFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener
        , OrderFragment.OnFragmentInteractionListener, View.OnClickListener,
        UncomfirmFragment.OnFragmentInteractionListener, HousingFragment.OnFragmentInteractionListener,
        CommentFragment.OnFragmentInteractionListener, HistoryOrderFragment.OnFragmentInteractionListener {

    private static final String TAG = "DrawLayoutActivity";
    /**
     * 登录/注册的LinearLayout布局的height
     */
    private static int height;

    private ViewPager mViewPager;

    /**
     * 用于ReserveFragment中的显示本地图片的参数
     */
    private static Context context;
    /**
     * 记录用户按下退出键的时间，双击退出MainActivity即主activity
     */
    private long exitTime;
    /**
     * 抽屉 ， 个人中心
     */
    private static DrawerLayout drawer;
    /**
     * 自动登录的账号
     */
    private static String account;
    /**
     * 自动登录的密码
     */
    private String password;
    /**
     * 登录或注册按钮
     */
    private static LinearLayout loginOrRegister_LinearLayout;
    /**
     * 登录/注册  字体显示  ， 登陆后显示用户账号
     */
    private static TextView loginOrRegister_tv;
    /**
     * 登录/注册  图片显示  ， 登陆后显示用户图片
     */
    private static ImageView loginOrRegister_iv;
    /**
     * 标记用户是否登录，来决定登录/注册按钮是否能点击
     */
    private static boolean isLogined = false;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"主页", "消息", "订单", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.main_tab_home, R.mipmap.main_tab_message,
            R.mipmap.main_tab_order, R.mipmap.main_tab_personal};
    private int[] mIconSelectIds = {
            R.mipmap.main_tab_home_pressed, R.mipmap.main_tab_message_pressed,
            R.mipmap.main_tab_order_pressed, R.mipmap.main_tab_personal_pressed};

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };
    private CommonTabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * xml的selector对应的java代码设置
     */
    private static StateListDrawable addStateDrawable(Context context, int idNormal, int idPressed, int idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focus = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        //sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);
        //sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        //sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按了后退键  且  是按下按键事件 。        双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {      //如果两次点击间隔大于2s
                exitTime = System.currentTimeMillis();
                Toast.makeText(this, "请再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                //System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initViews() {
        //  View view = LayoutInflater.from(DrawLayoutActivity.this ).inflate(R.layout.hotel_main , null);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        List<BaseFragment> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());  //滑动viewPager的时候，tabLayout跟着滑动
        tabLayout = (CommonTabLayout) findViewById(R.id.tab);        //把viewpage控件放在tab上
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    // tabLayout.showMsg(0, mRandom.nextInt(100) + 1);         //设置提示信息 ，像QQ那种红色提示
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
    }

    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        BaseFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);

        BaseFragment operateFragment = new MessageFragment();
        fragments.add(operateFragment);

        BaseFragment noteFragment = new OrderFragment();
        fragments.add(noteFragment);

        BaseFragment activityFragment = new PersonalCenterFragment();
        fragments.add(activityFragment);

        return fragments;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onClick(View v) {

    }


    /**
     * 主页，消息，订单，个人中心Fragment的ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tabLayout.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> mFragments;

        public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }

    public static Context getContext() {
        return context;
    }


}