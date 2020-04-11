package com.kh_sof_dev.gaz.Fragments.Refill_frg;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Adapters.GazType_adapter;
import com.kh_sof_dev.gaz.Adapters.Refill_adapter;
import com.kh_sof_dev.gaz.Adapters.Time_adapter;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.show_cites;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.ConfRefill;
import com.kh_sof_dev.gaz.activities.Refill;
import com.kh_sof_dev.gaz.activities.Shipping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class RefillHome extends Fragment {
    private int order_type;

    public RefillHome(int order_type) {
        this.order_type = order_type;
    }

    public RefillHome() {
        // Required empty public constructor
    }


    private ConstraintLayout layoutAddress, layoutTime, layoutDate, layoutCount;
    private RecyclerView gaztypRV, produRV;
    private LinearLayout l1, l2;
    private Button continue_btn;
    private ProgressBar progressBar;
    private TextView locationTV, date_tv, time_tv, nb_item, tx3;
    /************************order info*****************/
    public static String date = "", time = "";
    int qty = 1;
    List<Product> productList2, productList3, productList4;
    public static List<Product> productList = new ArrayList<>();
    /*********************************************/

    /***********************pagenation **************************/
    int limit = 20;
    /*****************************************************/
    private String cat_id;
    private ImageView add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_refill_home, container, false);

        productList2 = new ArrayList<>();
        productList3 = new ArrayList<>();
        productList4 = new ArrayList<>();
//        productList = new ArrayList<>();
        produRV = view.findViewById(R.id.Name_tv);
        produRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final Refill_adapter adapter = new Refill_adapter(getContext(), productList, new Refill_adapter.OnGaztype_selected_listenner() {
            @Override
            public void OnAdd(int postion) {
                if (productList != null && !productList.isEmpty()) {
                    int qty = productList.get(postion).getQty() + 1;
                    productList.get(postion).setQty(qty);
                }
            }

            @Override
            public void OnDelete(int postion) {
                if (productList != null && !productList.isEmpty()) {
                    int qty = productList.get(postion).getQty() - 1;
                    if (qty > 0) {
                        productList.get(postion).setQty(qty);
                    }
                }

            }
        });
        produRV.setAdapter(adapter);
        l1 = view.findViewById(R.id.linear1);
        l2 = view.findViewById(R.id.linear2);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        layoutAddress = (ConstraintLayout) view.findViewById(R.id.layAddress);
        layoutDate = (ConstraintLayout) view.findViewById(R.id.layoutDate);
        layoutTime = (ConstraintLayout) view.findViewById(R.id.layoutTime);
        layoutCount = (ConstraintLayout) view.findViewById(R.id.layoutCount);
        nb_item = (TextView) view.findViewById(R.id.nb_item);
        add = (ImageView) view.findViewById(R.id.add);
        locationTV = (TextView) view.findViewById(R.id.location_tv);
        tx3 = view.findViewById(R.id.tx3);
        date_tv = (TextView) view.findViewById(R.id.date_tv);
        time_tv = (TextView) view.findViewById(R.id.time_tv);
//        user_info user_info=new user_info(getContext());
        locationTV.setText(Shipping.locationAdd);
        date_tv.setText(date);
        time_tv.setText(time);
        if (date.isEmpty()) {
            date_tv.setText("إختار وقت التوصيل");
        }
        if (time.isEmpty()) {
            time_tv.setText("إختار تاريخ التوصيل");
        }
        if (Shipping.locationAdd.isEmpty() || Shipping.mLatLng == null) {
            locationTV.setText("إختار عنوان التوصيل");
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                nb_item.setText(qty + "");
            }
        });
        nb_item.setText(qty + "");

        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePopup();
            }
        });
        layoutCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountPopup();
            }
        });
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePopup();
            }
        });
        layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Shipping.class);
                intent.putExtra(Shipping.typ2_Order_1_test, 1);
                intent.putExtra(Shipping.order_type_s, 0);
                startActivity(intent);
//                MainNew.goto_(new Shipping(1, 0), getContext());
            }
        });

/*********************************************************************************************************/

        continue_btn = (Button) view.findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (order_type) {
                    case 2:

                        productList = productList2;
                        break;
                    case 3:
                        productList = productList3;

                        break;
                    case 4:
                        productList = productList4;
                        break;
                }
                if (productList.size() == 0) {
                    Toast.makeText(getContext(), "يرجي اختيار عنصر من القائمة", Toast.LENGTH_LONG).show();
                    return;
                }
                if (date.equals("")) {
                    Toast.makeText(getContext(), "يرجي اختيار تاريخ التوصيل", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Shipping.mLatLng == null) {
                    Toast.makeText(getContext(), "يرجي اختيار عنوان للتوصيل", Toast.LENGTH_LONG).show();
                    return;
                }
                if (time.equals("يرجي اختيار وقت التوصيل")) {
                    Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(getActivity(), ConfRefill.class);
                intent.putExtra(Refill.order_type_s, order_type);
                startActivity(intent);
//                MainNew.goto_(new ConfRefill(order_type), getContext());
//        OutherActivty._goto(RefillHome.this,new ConfRefill(rellifHome),View.GONE );
            }
        });


/**************************************************************************************************************/

        gaztypRV = (RecyclerView) view.findViewById(R.id.GaztypeRV);

        gaztypRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        Http_products http_products = new Http_products();
        http_products.Post_products_category(getContext(), cat_id, order_type, 20, 0, new Http_products.productsListener() {
            @Override
            public void onSuccess(final show_products products) {
                progressBar.setVisibility(View.GONE);
                if (products.getProducts().size() == 0) {
                    tx3.setVisibility(View.VISIBLE);

                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    continue_btn.setVisibility(View.GONE);

                } else {
                    tx3.setVisibility(View.GONE);

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    continue_btn.setVisibility(View.VISIBLE);


                }
                gaztypRV.setAdapter(new GazType_adapter(getContext(), products.getProducts()
                        , new GazType_adapter.OnGaztype_selected_listenner() {
                    @Override
                    public void onAdd(int postion) {
                        Product product_ = products.getProducts().get(postion);
                        product_.setQty(1);
                        Log.d("possition_Add", postion + " ");
                        switch (order_type) {

                            case 2:

                                productList2.add(product_);
                                final Refill_adapter adapter = new Refill_adapter(getContext(), productList2, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList2.get(postion).getQty() + 1;
                                        productList2.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList2.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList2.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter);
                                break;
                            case 3:

                                productList3.add(product_);
                                final Refill_adapter adapter3 = new Refill_adapter(getContext(), productList3, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList3.get(postion).getQty() + 1;
                                        productList3.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList3.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList3.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter3);
                                break;
                            case 4:
                                product_.setPrice(product_.getPrice_buy_new());
                                productList4.add(product_);
                                final Refill_adapter adapter4 = new Refill_adapter(getContext(), productList4, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList4.get(postion).getQty() + 1;
                                        productList4.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList4.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList4.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter4);
                                break;
                        }
                    }

                    @Override
                    public void onDellet(int postion) {
                        Log.d("possition_delet", postion + " ");
                        switch (order_type) {

                            case 2:

                                productList2.remove(products.getProducts().get(postion));
                                final Refill_adapter adapter = new Refill_adapter(getContext(), productList2, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList2.get(postion).getQty() + 1;
                                        productList2.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList2.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList2.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter);
                                break;
                            case 3:

                                productList3.remove(products.getProducts().get(postion));
                                final Refill_adapter adapter3 = new Refill_adapter(getContext(), productList3, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList3.get(postion).getQty() + 1;
                                        productList3.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList3.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList3.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter3);
                                break;
                            case 4:
                                productList4.remove(products.getProducts().get(postion));
                                final Refill_adapter adapter4 = new Refill_adapter(getContext(), productList4, new Refill_adapter.OnGaztype_selected_listenner() {
                                    @Override
                                    public void OnAdd(int postion) {
                                        int qty = productList4.get(postion).getQty() + 1;
                                        productList4.get(postion).setQty(qty);

                                    }

                                    @Override
                                    public void OnDelete(int postion) {
                                        int qty = productList4.get(postion).getQty() - 1;
                                        if (qty > 0) {
                                            productList4.get(postion).setQty(qty);
                                        }
                                    }
                                });
                                produRV.setAdapter(adapter4);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        user_info user_info = new user_info(getContext());
        locationTV.setText(Shipping.locationAdd);
        if (Shipping.locationAdd.isEmpty() || Shipping.mLatLng == null) {
            locationTV.setText("إختار عنوان التوصيل");
        }
    }

    private void CountPopup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_count);
        TextView nb1 = (TextView) dialog.findViewById(R.id.nb1);
        TextView nb2 = (TextView) dialog.findViewById(R.id.nb2);
        TextView nb3 = (TextView) dialog.findViewById(R.id.nb3);
        TextView nb4 = (TextView) dialog.findViewById(R.id.nb4);
        TextView nb5 = (TextView) dialog.findViewById(R.id.nb5);
        dialog.show();
        nb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                qty = 1;
                nb_item.setText(qty + "");
            }
        });
        nb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                qty = 2;
                nb_item.setText(qty + "");
            }
        });
        nb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                qty = 3;
                nb_item.setText(qty + "");
            }
        });
        nb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                qty = 4;
                nb_item.setText(qty + "");
            }
        });
        nb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                qty = 5;
                nb_item.setText(qty + "");
            }
        });
    }

    private void TimePopup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_select_time);
        final RecyclerView RV = (RecyclerView) dialog.findViewById(R.id.RV);
        RV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dialog.show();
        Http_get_constant http_get_constant = new Http_get_constant();
        http_get_constant.Time(getContext(), new Http_get_constant.citesListener() {
            @Override
            public void onSuccess(final show_cites cites) {
                RV.setAdapter(new Time_adapter(getActivity(), cites.getItems(), new Time_adapter.OnGaztype_selected_listenner() {
                    @Override
                    public void onSuccess(int postion) {
                        dialog.dismiss();
                        time = cites.getItems().get(postion).getName();
                        time_tv.setText(time);

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private int compareTime() {


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(currentDateandTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, 0);
        String currentTime = dateFormat.format(cal.getTime());
        String settime = "22:00";


        if (currentTime.compareTo(settime) >= 0) {


            Log.d("compareTime", currentTime + " is after than " + settime);
            return 1;

        } else {
            Log.d("compareTime", currentTime + " is before than " + settime);
            return 0;
        }


    }

    private void DatePopup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_select_date);

        int dateNB = 0;
        dateNB = dateNB + compareTime();

        TextView date1 = (TextView) dialog.findViewById(R.id.date1);
        date1.setText(getdat(dateNB));

        TextView date2 = (TextView) dialog.findViewById(R.id.date2);
        date2.setText(getdat(dateNB + 1));

        TextView date3 = (TextView) dialog.findViewById(R.id.date3);
        date3.setText(getdat(dateNB + 2));

        dialog.show();
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getdat(0);
                date_tv.setText(date);
                dialog.dismiss();
            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getdat(1);
                date_tv.setText(date);
                dialog.dismiss();
            }
        });
        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getdat(2);
                date_tv.setText(date);
                dialog.dismiss();
            }
        });
    }

    private String getdat(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(currentDateandTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, i);
        String convertedDate = dateFormat.format(cal.getTime());
        return convertedDate;
    }
}
