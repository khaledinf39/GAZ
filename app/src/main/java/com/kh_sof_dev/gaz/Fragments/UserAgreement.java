package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kh_sof_dev.gaz.Adapters.Tirme_and_condiction_adapter;
import com.kh_sof_dev.gaz.Classes.constant.About_us.cons;
import com.kh_sof_dev.gaz.Classes.constant.About_us.show_s_cus;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAgreement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAgreement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public UserAgreement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAgreement.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAgreement newInstance(String param1, String param2) {
        UserAgreement fragment = new UserAgreement();
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
private RecyclerView argemmentRV;
    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_user_agreement, container, false);
final ProgressBar progressBar=view.findViewById(R.id.progress);
        argemmentRV=(RecyclerView)view.findViewById(R.id.RV);
        argemmentRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        Http_get_constant constant=new Http_get_constant();
        constant.getStaticPage(getContext(), new Http_get_constant.staticPageListener() {
            @Override
            public void onSuccess(show_s_cus stticpage) {
                progressBar.setVisibility(View.GONE);
                List<cons> consList=new ArrayList<>();
                List<cons> consList2=new ArrayList<>();
                consList=stticpage.getItems();
                for (int i=0;i<consList.size();i++
                     ) {
                    cons c =consList.get(i);
                    if (c.getId().equals("5ca8f84265100a0024f0bb35")){
                        consList2.add(c);
                    }
                }
                argemmentRV.setAdapter(new Tirme_and_condiction_adapter(getContext(),consList2));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        back=view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                MainNew._goto(UserAgreement.this,new Home(),View.VISIBLE);
            }
        });
        return view;
    }

}
