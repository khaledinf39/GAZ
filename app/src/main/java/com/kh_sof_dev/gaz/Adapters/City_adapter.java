package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.constant.City;
import com.kh_sof_dev.gaz.R;

import java.util.List;

public class City_adapter extends BaseAdapter implements SpinnerAdapter {

        public interface  citylissener{
                void  onsuccessfully(String city);
        }
        List<City> cityList;
        Context context;
        citylissener lissenr;
//        String[] colors = {"#13edea","#e20ecd","#15ea0d"};
//        String[] colorsback = {"#FF000000","#FFF5F1EC","#ea950d"};

        public City_adapter(Context context, List<City> cityList,citylissener lissenr) {
                this.cityList = cityList;
                this.context = context;
                this.lissenr=lissenr;
        }

        @Override
        public int getCount() {
                return cityList.size();
        }

        @Override
        public Object getItem(int position) {
                return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
                View view =  View.inflate(context, R.layout.row_signup_city, null);
                TextView textView = (TextView) view.findViewById(R.id.city_tv);
                textView.setText(cityList.get(position).getName());
                view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                lissenr.onsuccessfully(cityList.get(position).getName());
                        }
                });
                return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View view;
                view =  View.inflate(context, R.layout.row_signup_city, null);
                final TextView textView = (TextView) view.findViewById(R.id.city_tv);
                textView.setText(cityList.get(position).getName());




                return view;
        }
}