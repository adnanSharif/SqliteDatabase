package app.example.com.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    String addContact(String name, String phone, String relation){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] tableColumns = new String[] {
                DBContractClass.ContactList.COLUMN_USER_NAME
        };
        String whereClause = DBContractClass.ContactList.COLUMN_USER_NAME+" = ?";
        String[] whereArgs = new String[] {
                name
        };
        Cursor cursor = db.query(
                DBContractClass.ContactList.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        if(cursor!= null && cursor.moveToFirst()){
            cursor.close();
            db.close();
            return "Cannot add. Contact name is identical to an existing contact name";
        }

        ContentValues new_row = new ContentValues();
        new_row.put( DBContractClass.ContactList.COLUMN_USER_NAME, name );
        new_row.put( DBContractClass.ContactList.COLUMN_USER_PHONE, phone );
        new_row.put( DBContractClass.ContactList.COLUMN_USER_RELATION, relation );
        db.insert( DBContractClass.ContactList.TABLE_NAME, null, new_row);
        db.close();
        return name+" has been added to contact successfully ";
    }

    private Cursor getContactsCursor(SQLiteDatabase db){
        String[] tableColumns = new String[] {
                DBContractClass.ContactList.COLUMN_USER_NAME,
                DBContractClass.ContactList.COLUMN_USER_PHONE,
                DBContractClass.ContactList.COLUMN_USER_RELATION
        };

        return db.query(
                DBContractClass.ContactList.TABLE_NAME,
                tableColumns,
                null,
                null,
                null,
                null,
                null
        );
    }
    ArrayList<SingleContact> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SingleContact> contacts = new ArrayList<>();
        Cursor cursor = getContactsCursor(db);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SingleContact contact = new SingleContact();
                    contact.name = cursor.getString(0);
                    contact.phone = cursor.getString(1);
                    contact.relation = cursor.getString(2);
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return contacts;

    }

    String modifyContact(String original, String name, String phone, String relation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContractClass.ContactList.COLUMN_USER_NAME, name);
        values.put(DBContractClass.ContactList.COLUMN_USER_PHONE, phone);
        values.put(DBContractClass.ContactList.COLUMN_USER_RELATION, relation);

        db.update(DBContractClass.ContactList.TABLE_NAME,values, DBContractClass.ContactList.COLUMN_USER_NAME
                +" = ? ", new String[]{original});
        db.close();
        return "Modification is successful";
    }

    public  String deleteHabit(String name){
        String DELETE_QUERY = "DELETE FROM "+DBContractClass.ContactList.TABLE_NAME+" WHERE "
                +DBContractClass.ContactList.COLUMN_USER_NAME+" = '"+name+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_QUERY);

        db.close();
        return "Contact deletion is successful!";
    }

}
