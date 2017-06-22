package app.example.com.sqlitedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyContact extends AppCompatActivity {

    //UI Items
    private EditText mName, mPhone, mRelation;

    private DatabaseHelper db;
    private SingleContact mContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contact);

        //Instantiate UI components
        mName = (EditText) findViewById(R.id.modify_text_name);
        mPhone = (EditText) findViewById(R.id.modify_text_phone);
        mRelation = (EditText) findViewById(R.id.modify_text_relation);

        //get the object of database
        db = new DatabaseHelper(this);

        //get the object of SingleContact class
        mContact = new SingleContact();

        //Start fetching data from previous activity
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mContact.name = extras.getString("name");
            mContact.phone = extras.getString("phone");
            mContact.relation = extras.getString("relation");
        }
        //End fetching data from previous activity

        //set hint to the EditTexts
        mName.setHint(mContact.name);
        mPhone.setHint(mContact.phone);
        mRelation.setHint(mContact.relation);
    }

    /**
     * This method will be called if the modify contact button is tapped
     * @param view
     */
    public void modifyContact(View view){
        //declare variables
        String name, phone, relation, original;

        original= mContact.name; //store the previous contact name (before edit)

        //If content is changed replace that, otherwise keep old content
        name = (mName.getText().toString().isEmpty()? mContact.name : mName.getText().toString());
        phone = (mPhone.getText().toString().isEmpty()? mContact.phone : mPhone.getText().toString());
        relation = (mRelation.getText().toString().isEmpty()? mContact.relation : mRelation.getText().toString());

        //Try modify the contact and store the response from database
        String response = db.modifyContact(original,name,phone, relation);
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show(); //show the response
        finish(); //after modifying back to the previous activity

    }

    /**
     * This method will be called if the delete contact button is tapped
     * @param view
     */
    public void deleteContact(View view){
        AlertDialog.Builder builder;

        //instantiate the object according to the android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        //Show an Alert message before deleting
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Yes button is tapped, so continue with delete

                        //Get the response from delete method
                        String response = db.deleteContact(mContact.name);
                        //show the response using toast
                        Toast.makeText(ModifyContact.this, response, Toast.LENGTH_SHORT).show();

                        //After deletion, go back to the main activity
                        Intent intent = new Intent(ModifyContact.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
