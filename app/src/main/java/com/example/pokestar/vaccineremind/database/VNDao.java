package com.example.pokestar.vaccineremind.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pokestar.vaccineremind.application.MyApplication;
import com.example.pokestar.vaccineremind.bean.VaccineNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PokeStar on 2018/10/5.
 */

public class VNDao {

    public static final String TABLE_NAME = "vaccinenews";//表名

    private static final String ID = "id";//id自增长
    private static final String TITLE = "title";//标题
    private static final String URL = "url";//链接
    private static final String IMAGEURL = "imageurl";//图片

    private static final String TAG = "VNDao";

    private VNhelper vnHelper;

    //创建表结构
    public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement," +
            TITLE + " text," +
            URL + " text," +
            IMAGEURL + " text" +
            ")";


    private VNDao() {
        vnHelper = new VNhelper(MyApplication.getAppContext());
    }

    public static VNDao getInstance() {
        return InnerDB.instance;
    }

    private static class InnerDB {
        private static VNDao instance = new VNDao();
    }

    /**
     * 数据库插入数据
     *
     * @param bean 实体类
     * @param <T>  T
     */
    public synchronized <T> void insert(T bean) {
        SQLiteDatabase db = vnHelper.getWritableDatabase();
        try {
            if (bean != null && bean instanceof VaccineNews) {
                VaccineNews vaccineNews = (VaccineNews) bean;
                ContentValues cv = new ContentValues();
                cv.put(TITLE, vaccineNews.getTitle());
                cv.put(URL, vaccineNews.getUrl());
                cv.put(IMAGEURL, vaccineNews.getImageUrl());
                db.insert(TABLE_NAME, null, cv);
                Log.d(TAG,"ok");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 删除表中所有的数据
     */
    public synchronized void clearAll() {
        SQLiteDatabase db = vnHelper.getWritableDatabase();
        String sql = "delete from " + TABLE_NAME;

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 查询数据
     *
     * @return List
     */
    public synchronized <T> List<T> query() {
        SQLiteDatabase db = vnHelper.getReadableDatabase();
        List<T> list = new ArrayList<>();
        String querySql = "select * from " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(querySql, null);
            while (cursor.moveToNext()) {
                VaccineNews vaccineNews = new VaccineNews();
                vaccineNews.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                vaccineNews.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
                vaccineNews.setImageUrl(cursor.getString(cursor.getColumnIndex(IMAGEURL)));
                list.add((T) vaccineNews);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

}
