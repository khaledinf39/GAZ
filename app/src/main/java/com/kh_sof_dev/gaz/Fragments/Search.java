package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.kh_sof_dev.gaz.Adapters.Search_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.MainNew;

public class Search extends Fragment {

    public Search() {

    }

    private RecyclerView searchRV;
    private SearchView search_ed;
    private ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_search, container, false);
        searchRV = (RecyclerView) view.findViewById(R.id.search_RV);
        search_ed = (SearchView) view.findViewById(R.id.search_et);
        search_ed.setClickable(true);
        back = (ImageView) view.findViewById(R.id.back_btn);
        searchRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final DBManager dbManager = new DBManager(getContext());
        dbManager.open();
        searchRV.setAdapter(new Search_adapter(getContext(), dbManager.fetch_search_word(), Search.this));
        dbManager.close();
        search_ed.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String wrod = search_ed.getQuery().toString();
                if (!wrod.isEmpty()) {

                    dbManager.open();
                    dbManager.insert_searchWord(wrod);
                    dbManager.close();
                    MainNew.goto_(new SearchResults(wrod), getContext());
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainNew.class));
//        MainNew._goto(Search.this,new Home(),View.VISIBLE);
            }
        });
        return view;
    }

}
