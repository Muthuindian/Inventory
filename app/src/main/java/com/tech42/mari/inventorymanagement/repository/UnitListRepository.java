package com.tech42.mari.inventorymanagement.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mari on 2/1/17.
 */

public class UnitListRepository extends SQLiteOpenHelper {

    private static String dbName = "Inventory.sqlite";
    private static String dbPath = "";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;


    public UnitListRepository(Context context) {
        super(context, dbName , null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            dbPath = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            dbPath = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                //Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(dbPath + dbName);
        Log.v("dbFile", dbFile + "   " + dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = context.getAssets().open("Inventory.sqlite");
        String outFileName = dbPath + dbName;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = dbPath + dbName;
        Log.v("mPath", mPath);
        sqLiteDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return sqLiteDatabase != null;
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            super.close();
        }
    }

    public Cursor getAllData() {
        try {
            String sql = "SELECT * FROM Unit";

            Cursor mCur = sqLiteDatabase.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
                //Log.e(TAG, "getAllData >>"+mCur.getCount());
            }
            return mCur;
        } catch (SQLException mSQLException) {
            //Log.e(TAG, "getAllData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getRowData(String name) {
        try {
            String sql = "SELECT * FROM Unit WHERE unit='" + name + "';";

            Cursor mCur = sqLiteDatabase.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
                //Log.e(TAG, "getRowData >>"+mCur.getCount());
            }
            return mCur;
        } catch (SQLException mSQLException) {
            //Log.e(TAG, "getRowData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void insert(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("unit", name);
        // update Row
        db.insert("Unit", null , values);
        db.close(); // Closing database connection
        //Log.d(TAG, "New user update into sqlite: ");
    }

    public void update(String name, String newname) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("unit", newname);
        // update Row
        db.update("Unit", values, "unit = '" + name + "'", null);
        db.close(); // Closing database connection
        //Log.d(TAG, "New user update into sqlite: ");
    }
}
