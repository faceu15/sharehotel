package com.example.guet.sharehotel.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.guet.sharehotel.activity.OrderDetailActivity;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.Order;
import com.example.guet.sharehotel.presenter.OrderPresenter;
import com.example.guet.sharehotel.utils.OrderState;
import com.example.guet.sharehotel.view.IOrderView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class UncomfirmFragment extends Fragment implements IOrderView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final Integer STATE_UNCOMF = 1;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private ProgressDialog progressDialog;
    private OrderPresenter mOrderPresenter;
    private AlertDialog mAlertDialog;
    private List<Order> mOrderList;
    private Order mOrder;

    public UncomfirmFragment() {
    }


    public static UncomfirmFragment newInstance(String param1, String param2) {
        UncomfirmFragment fragment = new UncomfirmFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uncomfirm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.uncomfirm_list_view);
        mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (MyApplication.getInstance().isLogin()) {
            mOrderPresenter.load(STATE_UNCOMF, MyApplication.getInstance().getAccount());

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
    public void showResult(final List<Order> list) {
        mOrderList = list;
        mListView.setAdapter(new CommonAdapter<Order>(getContext(), mOrderList, R.layout.order_uncomfirm_item) {
            @Override
            public void convert(ViewHolder viewHolder, final Order order) {
                viewHolder.setText(R.id.tv_history_order_num, order.getObjectId());
                viewHolder.setText(R.id.tv_order_state, OrderState.getState(order.getState()));
                viewHolder.setText(R.id.tv_order_name, order.getHotel().getName());
                viewHolder.setText(R.id.tv_order_checkintime, order.getCheckInTime().getDate());
                viewHolder.setText(R.id.tv_order_checkouttime, order.getCheckOutTime().getDate());
                viewHolder.setText(R.id.tv_order_price, order.getPrice().toString());
                viewHolder.setText(R.id.tv_order_amount, order.getHotel().getMode() + "/"
                        + order.getHotel().getHouseType() + "/" + order.getHotel().getArea() + "m²");
                viewHolder.setOnClikListener(R.id.bt_uncomfirm_pay, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Order", order);
                        mOrder = order;
                        intent.putExtra("OrderBundle", bundle);
                        startActivity(intent);
                    }
                });
                viewHolder.setImageViewOnClikListener(R.id.iv_uncomfirm_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("取消订单");
                        builder.setMessage("确定取消订单？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                order.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            order.getHotel().setAvailable(1);
                                            order.getHotel().update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {
                                                        Log.i("UncomfirmFragment", "取消成功");
                                                        list.remove(order);
                                                        notifyDataSetChanged();
                                                        mAlertDialog.dismiss();
                                                        onStart();
                                                    } else {
                                                        Log.i("UncomfirmFragment", "取消失败");
                                                    }
                                                }
                                            });

                                        } else {
                                            Log.i("bmob", "失败：" + e.toString());
                                        }
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
                    }

                });
            }
        });
    }

    @Override
    public void showLoading(String msg) {
        progressDialog = new ProgressDialog(getContext(), 1);//后面的参数是风格，1比较好看
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showError(String msg) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
