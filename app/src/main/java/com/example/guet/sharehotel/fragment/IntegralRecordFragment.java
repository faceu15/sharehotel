package com.example.guet.sharehotel.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.IntegralRecordAdapter;
import com.example.guet.sharehotel.bean.IntegralRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntegralRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 积分记录
 */
public class IntegralRecordFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView list_view;

    public IntegralRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntegralRecordFragment newInstance(String param1, String param2) {
        IntegralRecordFragment fragment = new IntegralRecordFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integral_record, container, false);
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

        List<IntegralRecord> list = new ArrayList<IntegralRecord>();
        IntegralRecord integralRecord = new IntegralRecord("1", "", "香港浅水湾", "英式小别墅!", "2017-6-23", "300", "1");
        IntegralRecord integralRecord1 = new IntegralRecord("3", " ", "四川大凉山", "十六山度假别墅", "2017-4-25", "200", "1");
        IntegralRecord integralRecord2 = new IntegralRecord("2", " ", "青岛如歌岛向华区", "如歌岛度假村别墅", "2017-5-01", "300", "1");
        IntegralRecord integralRecord3 = new IntegralRecord("4", " ", "海南海滨区", "银天大酒店", "2017-4-15", "400", "0");

        list.add(integralRecord);
        list.add(integralRecord1);
        list.add(integralRecord2);
        list.add(integralRecord3);

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        IntegralRecordAdapter adapter = new IntegralRecordAdapter(getContext(), R.layout.fragment_interal_record_item, list);
        listView.setAdapter(adapter);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
