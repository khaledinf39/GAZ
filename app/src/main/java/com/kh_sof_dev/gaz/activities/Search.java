package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.kh_sof_dev.gaz.Adapters.Search_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.R;

public class Search extends AppCompatActivity {

    private SearchView search_ed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_search);

        RecyclerView searchRV = findViewById(R.id.search_RV);
        search_ed = findViewById(R.id.search_et);
        search_ed.setClickable(true);
        ImageView back = findViewById(R.id.back_btn);
        searchRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final DBManager dbManager = new DBManager(this);
        dbManager.open();
        searchRV.setAdapter(new Search_adapter(this, dbManager.fetch_search_word()));
        dbManager.close();
        search_ed.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String wrod = search_ed.getQuery().toString();
                if (!wrod.isEmpty()) {

                    dbManager.open();
                    dbManager.insert_searchWord(wrod);
                    dbManager.close();
                    Intent intent = new Intent(Search.this, SearchResults.class);
                    intent.putExtra(SearchResults.productName, wrod);
                    startActivity(intent);
//                    MainNew.goto_(new SearchResults(wrod), );
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
                finish();
//                startActivity(new Intent(getContext(), MainNew.class));
//        MainNew._goto(Search.this,new Home(),View.VISIBLE);
            }
        });
    }
}
