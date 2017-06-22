package app.example.com.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Adnan Sharif on 6/22/17.
 *
 * Purpose: The purpose of this class is to handle the SQLite database used by android.
 *
 * @see android.database.sqlite.SQLiteOpenHelper
 * https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
 */

class DatabaseHelper extends SQLiteOpenHelper {


    /**
     * The constructor of the class
     * @param context The context of the calling activity which is required to initialize the database.
     */
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
        //Delete tables if it exits
        sqLiteDatabase.execSQL(DBContractClass.ContactList.DELETE_TABLE_QUERY);

        //After Deletion, create tables
        onCreate(sqLiteDatabase);
    }

    /**
     * The method is used to insert a row in table ContactList
     * @param name The name of the contact person
     * @param phone The phone number of the contact person
     * @param relation The relation between the contact person and the user
     * @return A response String containing the message about the data is inserted or not.
     */
    String addContact(String name, String phone, String relation){
        //Get instance of writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //Columns to select
        String[] tableColumns = new String[] {
                DBContractClass.ContactList.COLUMN_USER_NAME
        };
        //Selection Statements
        String whereClause = DBContractClass.ContactList.COLUMN_USER_NAME+" = ?";
        //Parameters for selection arguments
        String[] whereArgs = new String[] {
                name
        };

        Cursor cursor = db.query(
                DBContractClass.ContactList.TABLE_NAME, //Table Name
                tableColumns,   //Columns
                whereClause,    //Selection
                whereArgs,      //Selection Arguments
                null,           //Group by
                null,           //Having
                null            //Order by
        );

        //Check if the item is already in the database
        if(cursor!= null && cursor.moveToFirst()){
            //So an identical contact exist in the database
            cursor.close(); //close the cursor otherwise there will be memory leakage
            db.close(); //close the database

            //provide response that the data is identical
            return "Cannot add. Contact name is identical to an existing contact name";
        }
        //Otherwise no identical contact exist in the database

        /*
        Content value is a name-value pair
        store the value for the corresponding columns
         */
        ContentValues new_row = new ContentValues();
        new_row.put( DBContractClass.ContactList.COLUMN_USER_NAME, name );
        new_row.put( DBContractClass.ContactList.COLUMN_USER_PHONE, phone );
        new_row.put( DBContractClass.ContactList.COLUMN_USER_RELATION, relation );
        //Insert into database using those values
        db.insert( DBContractClass.ContactList.TABLE_NAME, null, new_row);
        db.close();//close the database instance

        //provide response that the data is inserted
        return name+" has been added to contact successfully ";
    }

    /**
     * This method will return the cursor for accessing all the existing contacts.
     * @param db the instance of SQLite database
     * @return the cursor for accessing all the existing contacts
     */
    private Cursor getContactsCursor(SQLiteDatabase db){
        //Chose the columns that are needed
        String[] tableColumns = new String[] {
                DBContractClass.ContactList.COLUMN_USER_NAME,
                DBContractClass.ContactList.COLUMN_USER_PHONE,
                DBContractClass.ContactList.COLUMN_USER_RELATION
        };

        //Return the cursor
        return db.query(
                DBContractClass.ContactList.TABLE_NAME, //Table Name
                tableColumns, //Projection
                null, //Selection
                null, //Selection Arguments
                null, //Group by
                null, //Having
                null //Order by
        );
    }

    /**
     * @return an ArrayList of SingleContact Class containing the data of all all contacts
     */
    ArrayList<SingleContact> getContacts() {
        //create an instance of readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //instantiate an ArrayList of SingleContact class
        ArrayList<SingleContact> contacts = new ArrayList<>();
        //get the cursor using getContactsCursor method
        Cursor cursor = getContactsCursor(db);

        //check if there is any row in database
        if (cursor != null) {
            //So, there are some rows
            if (cursor.moveToFirst()) { // Move cursor to the first
                do { //Iterate over each row
                    SingleContact contact = new SingleContact();  //create an instance of SingleContact class
                    contact.name = cursor.getString(0);     //First column is contact name
                    contact.phone = cursor.getString(1);    //Second column is contact phone
                    contact.relation = cursor.getString(2); //Third column is relation with user
                    contacts.add(contact); //put the contact into the array list
                } while (cursor.moveToNext()); //if any data row exists move the cursor to the next
            }
            cursor.close(); //close the cursor
            db.close(); //close the database instance
        }
        return contacts; //return the ArrayList of SingleContact class containing all the contact

    }

    /**
     * @param original String containing previous name of the contact
     * @param name String containing modified name of the contact
     * @param phone String containing modified phone number of the contact
     * @param relation String containing the relation of the contact with the user
     * @return String containing the response
     */
    String modifyContact(String original, String name, String phone, String relation){
        SQLiteDatabase db = this.getWritableDatabase(); //get the instance of writable database
        ContentValues values = new ContentValues(); //get an instance of contentValues to store data
        //store data along with the column name
        values.put(DBContractClass.ContactList.COLUMN_USER_NAME, name);
        values.put(DBContractClass.ContactList.COLUMN_USER_PHONE, phone);
        values.put(DBContractClass.ContactList.COLUMN_USER_RELATION, relation);

        //update database using the data
        db.update(
                DBContractClass.ContactList.TABLE_NAME, //Table name
                values, //contentValue (Name-value pair)
                DBContractClass.ContactList.COLUMN_USER_NAME +" = ? ", //selection
                new String[]{original} //selection arguments
        );
        db.close(); //close the instance of database
        return "Modification is successful"; // return the response
    }

    /**
     * @param name String containing name of the contact as contact name is the primary key
     * @return String containing response of the operation
     */
    public  String deleteContact(String name){
        String DELETE_QUERY = "DELETE FROM "+DBContractClass.ContactList.TABLE_NAME+" WHERE "
                +DBContractClass.ContactList.COLUMN_USER_NAME+" = '"+name+"'";

        SQLiteDatabase db = this.getWritableDatabase(); //get the instance of writable database
        db.execSQL(DELETE_QUERY); //execute sql

        db.close(); //close the database
        return "Contact deletion is successful!"; //return response string
    }

}
