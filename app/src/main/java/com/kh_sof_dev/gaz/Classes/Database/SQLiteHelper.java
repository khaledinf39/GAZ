package com.kh_sof_dev.gaz.Classes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
//    public static final String AGE = "age";
    public static final String CREATE_TABLE_ORDER = " create table IF NOT EXISTS ORDER_DِETALIS " +
        "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "Product_Id TEXT , " +
        "Product_Name TEXT" +
        ",Quantity INTEGER," +
        "Price DOUBLE," +
        "image TEXT" +
        " );";
    public static final String CREATE_TABLE_BEST = " create table IF NOT EXISTS BEST " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Product_Id TEXT , " +
            "Product_Name TEXT" +
            ",Quantity INTEGER," +
            "Price DOUBLE," +
            "image TEXT," +
            " UNIQUE(Product_Id,Product_Name, image)"+
            " );";
    public static final String CREATE_TABLE_DELIVER_WAY= " create table IF NOT EXISTS DELIVER_WAY " +
            "(ID TEXT," +
            "Name TEXT," +
            " UNIQUE(ID,Name)"+
            " );";
    public static final String CREATE_TABLE_DELIVER_OPT = " create table IF NOT EXISTS DELIVER_OPT " +
            "(ID TEXT," +
            "Name TEXT," +
            " UNIQUE(ID,Name)"+
            " );";
    public static final String CREATE_TABLE_BASKET = " create table IF NOT EXISTS BASKET " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Price DOUBLE," +
            "image TEXT," +
            "Basket_Name TEXT," +
            "Basket_id INTEGER," +
            " UNIQUE(Basket_Name,Basket_id)"+
            " );";
public  static final String CREATE_TABLE_HAYBER= " create table IF NOT EXISTS HAYBERS " +
        "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "Supplier_Id TEXT," +
        "Supplier_Details TEXT," +
        "Supplier_Image TEXT," +
       " UNIQUE(Supplier_Id, Supplier_Details,Supplier_Image)"+
        " );";
    public  static final String CREATE_TABLE_SEARCH= " create table IF NOT EXISTS SEARCH " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT," +

            " UNIQUE(Name)"+
            " );";

    public  static final String CREATE_TABLE_CATEGORIES= " create table IF NOT EXISTS CATEGORIES " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Categories_Id TEXT," +
            "Categories_Name TEXT," +
            "Categories_Image TEXT," +
            " UNIQUE(Categories_Id)"+
            " );";
    public  static final String CREATE_TABLE_SUBCATEGORIES= " create table IF NOT EXISTS SUBCATEGORIES " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Categories_Id TEXT," +
            "SubCategories_Id TEXT," +
            "SubCategories_Name TEXT," +

            " UNIQUE(Categories_Id,SubCategories_Id, SubCategories_Name)"+
            " );";
    private static final String DB_NAME = "GAZ.db";
    private static final int DB_VERSION = 1;

    public static  final String TABLE_NAME_SEARCH="SEARCH";
public static  final String TABLE_NAME_ORDER="ORDER_DِETALIS";
    public static  final String TABLE_NAME_BEST="BEST";
    public static  final String TABLE_NAME_BASKET="BASKET";
    public static  final String TABLE_NAME_DELIVER_WAY="DELIVER_WAY";
    public static  final String TABLE_NAME_DELIVER_OPT="DELIVER_OPT";
    public static  final String TABLE_NAME_ADDRESS="ADDRESS";
    public static  final String TABLE_NAME_HAYBER="HAYBERS";
    public static  final String TABLE_NAME_CATEGORIES="CATEGORIES";
    public static  final String TABLE_NAME_SUBCATEGORIES="SUBCATEGORIES";

    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String PRODUCT_ID = "Product_Id";
    public static final String Price = "Price";
    public static final String Supplier_ID = "Supplier_Id";
    public static final String PRODUCT_NAME = "Product_Name";
    public static final String CATEGORIES_ID = "Categories_Id";
    public static final String CATEGORIES_Image = "Categories_Image";
    public static final String CATEGORIES_Name = "Categories_Name";
    public static final String SUBCATEGORIES_Name = "SubCategories_Name";
    public static final String SUBCATEGORIES_ID = "SubCategories_Id";
    public static final String Supplier_Image = "Supplier_Image";
    public static final String Supplier_Details = "Supplier_Details";
    public static final String QUANTITY = "Quantity";
    public static final String NEW_PRICE = "new_price";
    public static final String IMAGE = "image";
    public static final String OLD_PRICE = "old_price";
    public static final String ISOFFER = "ISOffer";
    public static final String Isbasket = "IsBasket";
    public static final String IsOfferQuota = "IsOfferQuota";
    public static final String PRICE_UNITE_NAME = "Price_Unite_Name";
    public static final String PRICE_UNITE_ID = "Price_Unite_ID";
public static final String Basket_id="Basket_id";
    public static final String Basket_Name="Basket_Name";
public static final String QuotId="QuotId";

    public static final String ADDRESS_MAP = "ADDRESS_MAP";
    public static final String ADDRESS_WRITE = "ADDRESS_WRITE";
    public static final String ADDRESS_LAT = "ADDRESS_LAT";
    public static final String ADDRESS_LNG = "ADDRESS_LNG";
    public static final String ADDRESS_TYPE = "ADDRESS_TYPE";
    public static final String ADDRESS_NAME = "ADDRESS_NAME";
    public static final String ADDRESS_NOTE = "ADDRESS_NOTE";
    public static final String ADDRESS_NEARE = "ADDRESS_NEARE";
    public static final String CREATE_TABLE_ADDRESS = " create table IF NOT EXISTS ADDRESS " +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ADDRESS_MAP TEXT , " +
            "ADDRESS_WRITE TEXT," +
            "ADDRESS_TYPE TEXT" +
            ",ADDRESS_NAME INTEGER," +
            "ADDRESS_LAT DOUBLE," +
            "ADDRESS_LNG DOUBLE," +
            "ADDRESS_NOTE TEXT," +
            "ADDRESS_NEARE TEXT" +
            " );";
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        Log.e("SQLITE", "table reated");

       db.execSQL(CREATE_TABLE_SEARCH);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_BEST);
//        db.execSQL(CREATE_TABLE_ADDRESS);
//        db.execSQL(CREATE_TABLE_CATEGORIES);
//        db.execSQL(CREATE_TABLE_SUBCATEGORIES);
//        db.execSQL(CREATE_TABLE_HAYBER);
//        db.execSQL(CREATE_TABLE_BASKET);
//        db.execSQL(CREATE_TABLE_DELIVER_WAY);
//        db.execSQL(CREATE_TABLE_DELIVER_OPT);

    }
    public void Insert_Basket(SQLiteDatabase db, String basketid,String basket_imag,String basket_name,Double price ) {
        try {
            db.execSQL(
                    "INSERT INTO Basket(" +
                            "Price, image,Basket_Id,Basket_Name" +
                            ")VALUES('" + price + "'" + ",'" + basket_imag + "','" + basketid + "'" +
                            ",'" + basket_name + "')"
//
            );
        }catch (Exception ex){
            Log.d("insert basket",ex.getMessage());
        }
    }
    public void Insert_Search_wrod(SQLiteDatabase db, String name) {
        try {
            db.execSQL(
                    "INSERT INTO SEARCH(" +
                            "Name" +
                            ")VALUES('" + name +"')"
//
            );
        }catch (Exception ex){
            Log.d("insert search word",ex.getMessage());
        }
    }
    public void Insert_Deliver_Way(SQLiteDatabase db, String id,String name ) {
        try {
            db.execSQL(
                    "INSERT INTO DELIVER_WAY(" +
                            "ID, Name" +
                            ")VALUES('" + id + "'" + ",'" + name + "')"
//
            );
        }catch (Exception ex){
            Log.d("insert deliver way",ex.getMessage());
        }
    }
    public void Insert_Deliver_Opt(SQLiteDatabase db, String id,String name ) {
        try {
            db.execSQL(
                    "INSERT INTO DELIVER_OPT(" +
                            "ID, Name" +
                            ")VALUES('" + id + "'" + ",'" + name + "')"
//
            );
        }catch (Exception ex){
            Log.d("insert deliver opt",ex.getMessage());
        }
    }
    public void Insert_if_not_exist_hayber(SQLiteDatabase db, String hayber_id,String hayber_imag,String hayber_details ) {
try {
    db.execSQL(
            "INSERT INTO HAYBERS(" +
                    "Supplier_Id, Supplier_Image,Supplier_Details" +
                    ")VALUES('" + hayber_id + "'" + ",'" + hayber_imag + "','" + hayber_details + "')"
//
    );
}catch (Exception ex){
    Log.d("insert hayber",ex.getMessage());
}
}
    public void Insert_if_not_exist_categories(SQLiteDatabase db, String categorie_id,String categorie_imag,String categorie_name ) {
        try {
            db.execSQL(
                    "INSERT INTO CATEGORIES(" +
                            "Categories_Id, Categories_Image,Categories_Name" +
                            ")VALUES('" + categorie_id + "'" + ",'" + categorie_imag + "','" + categorie_name + "')"
//
            );
        }catch (Exception ex){
            Log.d("insert categories",ex.getMessage());
        }
    }
    public void Insert_if_not_exist_subcategories(SQLiteDatabase db, String categorie_id,String Subcategorie_id,String Subcategorie_name ) {
        try {
            db.execSQL(
                    "INSERT INTO SUBCATEGORIES(" +
                            "Categories_Id,SubCategories_Id,SubCategories_Name" +
                            ")VALUES('" + categorie_id + "'" + ",'" + Subcategorie_id + "','" + Subcategorie_name + "')"
//
            );
        }catch (Exception ex){
            Log.d("insert subcategories",ex.getMessage());
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
      // db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
        onCreate(db);
    }
    public void delete_basket(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
        db.execSQL("DROP TABLE IF EXISTS BASKET");

        onCreate(db);
    }
    public long exist(SQLiteDatabase db, String Product_Id) {

        int ty;
        try{
        Cursor c = db.rawQuery("SELECT * FROM ORDER_DِETALIS WHERE  ID="+0+"; ", null);

        if (c.moveToFirst()) {
            ty = c.getInt(c.getColumnIndex("Quantity"));
            Log.d("product_nb_sql",ty+"");
        } else {
            ty = 0;
        }

        c.close();
        SQLiteStatement s = db.compileStatement( "SELECT Quantity FROM ORDER_DِETALIS WHERE Product_Id = '"+Product_Id+"'; " );

        long qnty=ty;

            qnty = s.simpleQueryForLong();
            Log.d("product_nb_sqls",qnty+"");

            return qnty;
        }catch (Exception ex)
        {
//            Log.d("exist_nb",ex.getMessage());
        }

       return 0;
    }
    public long get_order_count(SQLiteDatabase db) {
        SQLiteStatement s = db.compileStatement( "select count(*) from ORDER_DِETALIS ; " );

        long count = s.simpleQueryForLong();

//

        return count;
    }

    public Cursor getData(SQLiteDatabase db,String sql){

        return db.rawQuery(sql, null);
    }
public Long DeleteItem(SQLiteDatabase db,int id){
        db.execSQL("DELETE FROM ORDER_DِETALIS WHERE ID="+id);

        /********return count *****/
    SQLiteStatement s = db.compileStatement( "select count(*) from ORDER_DِETALIS ; " );

    long count = s.simpleQueryForLong();
return count;
}
    public void DeleteAddress(SQLiteDatabase db,int id){
        db.execSQL("DELETE FROM ADDRESS WHERE ID="+id);
    }
    public void UpdatItem_prodId(SQLiteDatabase db,String Product_Id,int new_qty){
        db.execSQL("UPDATE ORDER_DِETALIS SET Quantity ="+new_qty+" WHERE Product_Id='"+Product_Id+"'");
    }
    public void UpdatItem(SQLiteDatabase db,int id,int new_qty){
        db.execSQL("UPDATE ORDER_DِETALIS SET Quantity ="+new_qty+" WHERE ID="+id);
    }

    public void clear_db(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
//         db.execSQL("DROP TABLE IF EXISTS BASKET");
//        db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
//        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
//        db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
//        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
//        db.execSQL("DROP TABLE IF EXISTS ORDER_DِETALIS");
//
        onCreate(db);
    }

    public void delet_invalid_item(SQLiteDatabase db, String id) {
        db.execSQL("DELETE FROM ORDER_DِETALIS WHERE Product_Id='"+id+"'");
    }
}
