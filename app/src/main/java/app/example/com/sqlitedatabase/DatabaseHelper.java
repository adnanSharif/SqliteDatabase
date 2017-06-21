package app.example.com.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adnan Sharif on 6/22/17.
 */

class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context){
        super(context,DBContractClass.DATABASE_NAME,null, DBContractClass.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table ContactList
        sqLiteDatabase.execSQL(DBContractClass.ContactList.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DBContractClass.ContactList.DELETE_TABLE_QUERY);

        //After Deletion, create tables
        onCreate(sqLiteDatabase);
    }


}
