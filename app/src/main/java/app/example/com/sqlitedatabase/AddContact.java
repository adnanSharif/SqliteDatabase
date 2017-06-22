package app.example.com.sqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    //UI Components variables
    private EditText mName, mPhone, mRelation;

    //DatabaseHelper
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //Initializing
        mName = (EditText) findViewById(R.id.edit_text_name);
        mPhone = (EditText) findViewById(R.id.edit_text_phone);
        mRelation = (EditText) findViewById(R.id.edit_text_relation);

        mDatabaseHelper = new DatabaseHelper(this);
    }

    /** Will be executed if 'Add Button is pressed
     * @param view
     */
    public void addContact (View view){
        //Declare variables
        String name, phone, relation;

        //Collect input from UI
        name = mName.getText().toString();
        phone = mPhone.getText().toString();
        relation = mRelation.getText().toString();

        //Call database method to insert a new contact
        String response = mDatabaseHelper.addContact(name, phone, relation);

        //Toast the response from database
        Toast.makeText(AddContact.this, response, Toast.LENGTH_SHORT).show();
    }
}
