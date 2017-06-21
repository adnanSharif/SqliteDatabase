package app.example.com.sqlitedatabase;

import android.provider.BaseColumns;

/**
 * Created by Adnan Sharif on 6/22/17.
 */

class DBContractClass {
    public static final String DATABASE_NAME = "contact_database";
    public static final int DATABASE_VERSION = 1;
    public static class ContactList implements BaseColumns{
        public static final String TABLE_NAME = "contact_list";

        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_PHONE = "user_phone";
        public static final String COLUMN_USER_RELATION = "user_relation";

        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +"("
                + COLUMN_USER_NAME + " TEXT PRIMARY KEY , "
                + COLUMN_USER_PHONE+ " TEXT , "
                + COLUMN_USER_RELATION + " TEXT )";

        public static final String DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
