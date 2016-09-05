package com.example.yixu.fullcircle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class AttractionDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "attrationDatabase";
    private static final String TABLE_ATTRACTION = "attraction";

    private static final String KEY_ATTRECTIONID = "id";
    private static final String KEY_ATTRACTIONLONG = "attractionLong";
    private static final String KEY_ATTRACTIONLAT = "attractionaLat";
    private static final String KEY_ATTRACTIONNAME = "attractionName";
    private static final String KEY_DATEOFVISIT = "visitDate";

    public AttractionDatabaseHandler(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_ATTRACTION + "("
                + KEY_ATTRECTIONID + " INTEGER PRIMARY KEY," + KEY_ATTRACTIONNAME + " TEXT,"
                + KEY_ATTRACTIONLONG + " TEXT," + KEY_ATTRACTIONLAT + " TEXT," + KEY_DATEOFVISIT + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTRACTION);

        onCreate(db);

    }

    public void addAttraction(Attractions attraction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ATTRACTIONNAME, attraction.getAttractionName());
        values.put(KEY_ATTRACTIONLONG, attraction.getAttractionLong());
        values.put(KEY_ATTRACTIONLAT, attraction.getAttractionLat());
        values.put(KEY_DATEOFVISIT, attraction.getVisitDate());

        // Inserting Row
        db.insert(TABLE_ATTRACTION, null, values);
        db.close();
    }

    public Attractions getAttraction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ATTRACTION, new String[] { KEY_ATTRECTIONID,
                        KEY_ATTRACTIONNAME, KEY_ATTRACTIONLONG, KEY_ATTRACTIONLAT, KEY_DATEOFVISIT }, KEY_ATTRECTIONID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Attractions attraction = new Attractions(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                cursor.getString(4));

        return attraction;
    }

    public List<Attractions> getAllAttractions() {
        List<Attractions> attractionList = new ArrayList<Attractions>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ATTRACTION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Attractions attractions = new Attractions();
                attractions.setAttractionId(Integer.parseInt(cursor.getString(0)));
                attractions.setAttractionName(cursor.getString(1));
                attractions.setAttractionLong(Double.parseDouble(cursor.getString(2)));
                attractions.setAttractionLat(Double.parseDouble(cursor.getString(3)));
                attractions.setVisitDate(cursor.getString(4));
                attractionList.add(attractions);
            } while (cursor.moveToNext());
        }

        return attractionList;

    }

    public void deleteAttraction(Attractions attraction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTRACTION, KEY_ATTRECTIONID + " = ?",
                new String[] { String.valueOf(attraction.getAttractionId()) });
        db.close();
    }

}
