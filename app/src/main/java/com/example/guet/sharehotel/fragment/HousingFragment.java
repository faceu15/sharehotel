package com.example.guet.sharehotel.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.bean.Order;
import com.example.guet.sharehotel.presenter.OrderPresenter;
import com.example.guet.sharehotel.utils.OrderState;
import com.example.guet.sharehotel.view.IOrderView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HousingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HousingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HousingFragment extends Fragment implements IOrderView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final Integer STATE_HOUSING = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mListView;
    private OrderPresenter mOrderPresenter;

    private OnFragmentInteractionListener mListener;
    private AlertDialog mAlertDialog;
    private Order mOrder;

    public HousingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HousingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HousingFragment newInstance(String param1, String param2) {
        HousingFragment fragment = new HousingFragment();
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
        View view = inflater.inflate(R.layout.fragment_housing, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.housing_listview);
        mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (MyApplication.getInstance().isLogin()) {
            mOrderPresenter.load(STATE_HOUSING, MyApplication.getInstance().getAccount());
        }
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

    @Override
    public void showResult(List<Order> list) {
        mListView.setAdapter(new CommonAdapter<Order>(getContext(), list, R.layout.order_housing_item) {
            @Override
            public void convert(ViewHolder viewHolder, Order order) {
                mOrder = order;
                viewHolder.setText(R.id.tv_history_order_num, order.getObjectId());
                viewHolder.setText(R.id.tv_housing_order_state, OrderState.getState(order.getState()));
                viewHolder.setText(R.id.tv_housing_order_name, order.getHotel().getName());
                viewHolder.setText(R.id.tv_housing_order_checkin, order.getCheckInTime().getDate());
                viewHolder.setText(R.id.tv_housing_order_checkout, order.getCheckOutTime().getDate());
                viewHolder.setText(R.id.tv_housing_order_price, order.getPrice().toString());
                viewHolder.setText(R.id.tv_housing_order_amount, order.getRooms().toString() + "套/共" + order.getDays() + "晚");
                viewHolder.setOnClikListener(R.id.bt_housing_sure, onClick);
            }
        });
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_housing_sure:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示");
                    builder.setMessage("确定入住完成吗？");
                    builder.setCancelable(true);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mOrder.setState(4);
                            mOrder.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("HousingFragment", "确认入住成功");
                                    } else {
                                        Log.i("HousingFragment", "确认入住失败" + e.toString());
                                    }
                                    mAlertDialog.dismiss();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mAlertDialog.dismiss();
                        }
                    });
                    mAlertDialog = builder.create();
                    mAlertDialog.show();
                    break;
            }
        }
    };

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
