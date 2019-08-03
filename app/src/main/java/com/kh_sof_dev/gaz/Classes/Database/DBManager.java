package com.kh_sof_dev.gaz.Classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kh_sof_dev.gaz.Classes.Products.Product;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private static OnAddOrder_listenner mlistenner = null;

    public DBManager(Context c) {
        this.context = c;
    }

    public DBManager(Context c, OnAddOrder_listenner listenner) {
        this.context = c;
        mlistenner = listenner;
    }

    public DBManager open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void Delet_Address(int id) {
        dbHelper.DeleteAddress(this.database, id);
    }

    public void update_Item(int id, int new_qty) {
        dbHelper.UpdatItem(this.database, id, new_qty);
    }

    public void update_Item(String prod_id, int new_qty) {
        dbHelper.UpdatItem_prodId(this.database, prod_id, new_qty);
    }

    public void Delet_Item(int id) {

        Long count = dbHelper.DeleteItem(this.database, id);
        if (count == 0 && mlistenner != null) {
            mlistenner.add_basket(false);

        }

    }

    public void insert_searchWord(String word) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.Insert_Search_wrod(database, word);
    }

    public void insert_subcategories(String categories_id, String subcategories_id, String subcategories_name) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.Insert_if_not_exist_subcategories(database, categories_id, subcategories_id, subcategories_name);
    }

    public void insert_basket(String id, String image, String name, Double price) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.Insert_Basket(database, id, image, name, price);
    }

    public void insert_deliver_way(String id, String name) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.Insert_Deliver_Way(database, id, name);
    }

    public void insert_deliver_opt(String id, String name) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.Insert_Deliver_Opt(database, id, name);
    }

    public void insert_order(Product product) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        ContentValues contentValue = new ContentValues();

        contentValue.put(SQLiteHelper.PRODUCT_ID, product.getId());
        contentValue.put(SQLiteHelper.PRODUCT_NAME, product.getName());
        contentValue.put(SQLiteHelper.QUANTITY, 1);
        contentValue.put(SQLiteHelper.Price, product.getPrice());
        contentValue.put(SQLiteHelper.IMAGE, product.getImage());

        this.database.insert(SQLiteHelper.TABLE_NAME_ORDER, null, contentValue);
        if (mlistenner != null) {
            mlistenner.add_basket(true);
        }
//        Log.e(MainActivity.TAG, "insert: " + this.database.getPath() );
    }

    public void insert_bestProd(Product product) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        ContentValues contentValue = new ContentValues();

        contentValue.put(SQLiteHelper.PRODUCT_ID, product.getId());
        contentValue.put(SQLiteHelper.PRODUCT_NAME, product.getName());
        contentValue.put(SQLiteHelper.QUANTITY, 1);
        contentValue.put(SQLiteHelper.Price, product.getPrice());
        contentValue.put(SQLiteHelper.IMAGE, product.getImage());

        this.database.insert(SQLiteHelper.TABLE_NAME_BEST, null, contentValue);
        if (mlistenner != null) {
            mlistenner.add_basket(true);
        }
//        Log.e(MainActivity.TAG, "insert: " + this.database.getPath() );
    }
//    public void insert_Address(location location_) {
//        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
//        sqLiteHelper.onCreate(database);
//        ContentValues contentValue = new ContentValues();
//
//        contentValue.put(SQLiteHelper.ADDRESS_NAME, location_.getAddress_name());
//        contentValue.put(SQLiteHelper.ADDRESS_LAT, location_.getAddress_latlng().latitude);
//        contentValue.put(SQLiteHelper.ADDRESS_LNG, location_.getAddress_latlng().longitude);
//        contentValue.put(SQLiteHelper.ADDRESS_MAP, location_.getAddress_map());
//        contentValue.put(SQLiteHelper.ADDRESS_NEARE, location_.getAddress_near());
//        contentValue.put(SQLiteHelper.ADDRESS_TYPE, location_.getAddress_typ());
//        contentValue.put(SQLiteHelper.ADDRESS_NOTE, location_.getAuth_note());
//        contentValue.put(SQLiteHelper.ADDRESS_WRITE, location_.getAddress_write());
//
//        this.database.insert(SQLiteHelper.TABLE_NAME_ADDRESS, null, contentValue);
//
//    }

    public long get_order_count() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        return sqLiteHelper.get_order_count(database);
    }

    public long exist(String pro_id) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        long qnty = sqLiteHelper.exist(database, pro_id);
//long qnty=0;
//        for (order order_:Main.orderList
//             ) {
//            Log.d("pro_id",pro_id);
//            Log.d("order_pro_id",order_.getProduct_id());
//            Log.d("order_sup_id",order_.getSupplier_id());
//            if(order_.getProduct_id().equals(pro_id)){
//                qnty=order_.getQnty();
//            }
//        }
        return qnty;
    }

    public Boolean check_suppID(String supp_id) {

        String query = "Select * From ORDER_DِETALIS where Supplier_Id = '" + supp_id + "'";
        String query2 = "Select * From ORDER_DِETALIS";

        //Move to the first row in your results
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        if (sqLiteHelper.getData(database, query2).getCount() == 0) {
            return true;
        }

        if (sqLiteHelper.getData(database, query).getCount() > 0) {
            return true;
        }


        return false;
    }

    public List<Product> fetch_order() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        List<Product> products = new ArrayList<>();

        try {
            Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_ORDER, new String[]{
                    SQLiteHelper.ID,
                    SQLiteHelper.PRODUCT_ID   //1
                    , SQLiteHelper.PRODUCT_NAME     //3
                    , SQLiteHelper.QUANTITY,       //4
                    SQLiteHelper.Price        //5
                    , SQLiteHelper.IMAGE         //7


            }, null, null, null, null, null);

            if (cursor.getCount() != 0) {

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
//
                        do {
//
                            Product p = new Product();
                            p.setID_(cursor.getInt(0));
                            p.setId(cursor.getString(1));
                            p.setName(cursor.getString(2));
                            p.setQty(cursor.getInt(3));
                            p.setPrice(cursor.getDouble(4));
                            p.setImage(cursor.getString(5));
                            products.add(p);


                        } while (cursor.moveToNext());
//
                    }

                }


            }
        } catch (Exception ex) {
        }
        return products;
    }

    public List<Product> fetch_bestProd() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        List<Product> products = new ArrayList<>();

        try {
            Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_BEST, new String[]{
                    SQLiteHelper.ID,
                    SQLiteHelper.PRODUCT_ID   //1
                    , SQLiteHelper.PRODUCT_NAME     //3
                    , SQLiteHelper.QUANTITY,       //4
                    SQLiteHelper.Price        //5
                    , SQLiteHelper.IMAGE         //7


            }, null, null, null, null, null);

            if (cursor.getCount() != 0) {

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
//
                        do {
//
                            Product p = new Product();
                            p.setID_(cursor.getInt(0));
                            p.setId(cursor.getString(1));
                            p.setName(cursor.getString(2));
                            p.setQty(cursor.getInt(3));
                            p.setPrice(cursor.getDouble(4));
                            p.setImage(cursor.getString(5));
                            products.add(p);


                        } while (cursor.moveToNext());
//
                    }

                }


            }
        } catch (Exception ex) {
        }
        return products;
    }

    public Cursor fetch_Basket() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_BASKET, new String[]{

                SQLiteHelper.IMAGE   //1
                , SQLiteHelper.Basket_id    //2
                , SQLiteHelper.Basket_Name     //3
                , SQLiteHelper.Price,       //4

        }, null, null, null, null, null);
//
        if (cursor.getCount() != 0) {
//
            return cursor;
        }
        return null;
    }

    public List<Product> fetch_search_word() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);

        List<Product> products = new ArrayList<>();
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_SEARCH, new String[]{

                SQLiteHelper.NAME   //1


        }, null, null, null, null, null);
//
        if (cursor.getCount() != 0) {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
//
                    do {
//
                        Product p = new Product();
                        p.setName(cursor.getString(0));

                        products.add(p);


                    } while (cursor.moveToNext());
//
                }

            }
        }
        return products;
    }

    public Cursor fetch_Deliver_Way() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_DELIVER_WAY, new String[]{

                SQLiteHelper.ID   //1
                , SQLiteHelper.NAME    //2


        }, null, null, null, null, null);
//
        if (cursor.getCount() != 0) {
//
            return cursor;
        }
        return null;
    }

    public Cursor fetch_Deliver_Opt() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_DELIVER_OPT, new String[]{

                SQLiteHelper.ID   //1
                , SQLiteHelper.NAME    //2


        }, null, null, null, null, null);
//
        if (cursor.getCount() != 0) {
//
            return cursor;
        }
        return null;
    }

    public Cursor fetch_ADDress() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_ADDRESS, new String[]{

                SQLiteHelper.ADDRESS_NAME   //0
                , SQLiteHelper.ADDRESS_TYPE    //1
                , SQLiteHelper.ADDRESS_MAP     //2
                , SQLiteHelper.ADDRESS_WRITE,       //3
                SQLiteHelper.ADDRESS_NEARE        //4
                , SQLiteHelper.ADDRESS_NOTE       //5
                , SQLiteHelper.ADDRESS_LAT         //6
                , SQLiteHelper.ADDRESS_LNG                //7
                , SQLiteHelper.ID   //8
        }, null, null, null, null, null);

        if (cursor.getCount() != 0) {

            return cursor;
        }
        return null;
    }

    public Cursor fetch_Hayber() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_HAYBER, new String[]{

                SQLiteHelper.Supplier_ID   //0
                , SQLiteHelper.Supplier_Image    //1
                , SQLiteHelper.Supplier_Details   //2
                , SQLiteHelper.ID   //3
        }, null, null, null, null, null);

        if (cursor.getCount() != 0) {

            return cursor;
        }
        return null;
    }

    public Cursor fetch_Categories() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_CATEGORIES, new String[]{

                SQLiteHelper.CATEGORIES_ID   //0
                , SQLiteHelper.CATEGORIES_Image    //1
                , SQLiteHelper.CATEGORIES_Name   //2
                , SQLiteHelper.ID   //3
        }, null, null, null, null, null);

        if (cursor.getCount() != 0) {

            return cursor;
        }
        return null;
    }

    public Cursor fetch_SubCategories() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onCreate(database);
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_SUBCATEGORIES, new String[]{

                SQLiteHelper.CATEGORIES_ID   //0
                , SQLiteHelper.SUBCATEGORIES_ID    //1
                , SQLiteHelper.SUBCATEGORIES_Name   //2
                , SQLiteHelper.ID   //3
        }, null, null, null, null, null);

        if (cursor.getCount() != 0) {

            return cursor;
        }
        return null;
    }

    public void deleteFromCart() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.onUpgrade(database, 1, 2);
        String query = String.format("DROP TABLE IF EXISTS ORDER_DِETALIS");
        this.database.execSQL(query);
    }

    public void deletemybasket() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.delete_basket(database);

    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(SQLiteHelper.NAME, name);
//        contentValues.put(SQLiteHelper.AGE, desc);
        return this.database.update(SQLiteHelper.TABLE_NAME_ORDER, contentValues, "_id = " + _id, null);
    }

    public boolean deleteTitle(Long id) {
        return this.database.delete(SQLiteHelper.TABLE_NAME_ORDER, "ID" + "=" + id, null) > 0;
    }

    public void delete(long _id) {
        this.database.delete(SQLiteHelper.TABLE_NAME_ORDER, "ID = " + _id, null);
    }

    public void deleteBestPro(int _id) {
        this.database.delete(SQLiteHelper.TABLE_NAME_BEST, "ID = " + _id, null);
    }

    public interface OnAddOrder_listenner {

        void add_basket(Boolean state);

    }

    public void clear_db() {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.clear_db(database);
        if (mlistenner != null) {
            mlistenner.add_basket(false);
        }
    }

    public void delete_invalid_items(List<String> invalid_items) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        for (String pro : invalid_items
        ) {
            sqLiteHelper.delet_invalid_item(database, pro);
        }
    }
}