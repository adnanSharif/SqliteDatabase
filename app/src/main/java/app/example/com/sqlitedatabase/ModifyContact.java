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

        mName = (EditText) findViewById(R.id.modify_text_name);
        mPhone = (EditText) findViewById(R.id.modify_text_phone);
        mRelation = (EditText) findViewById(R.id.modify_text_relation);

        db = new DatabaseHelper(this);

        mContact = new SingleContact();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mContact.name = extras.getString("name");
            mContact.phone = extras.getString("phone");
            mContact.relation = extras.getString("relation");
        }
        mName.setHint(mContact.name);
        mPhone.setHint(mContact.phone);
        mRelation.setHint(mContact.relation);
    }

    public void modifyContact(View view){
        String name, phone, relation, original;
        original= mContact.name;

        name = (mName.getText().toString().isEmpty()? mContact.name : mName.getText().toString());
        phone = (mPhone.getText().toString().isEmpty()? mContact.phone : mPhone.getText().toString());
        relation = (mRelation.getText().toString().isEmpty()? mContact.relation : mRelation.getText().toString());

        String response = db.modifyContact(original,name,phone, relation);
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        finish();

    }

    public void deleteContact(View view){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String response = db.deleteHabit(mContact.name);
                        Toast.makeText(ModifyContact.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ModifyContact.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
