package app.example.com.sqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    //UI Components
    private EditText mName, mPhone, mRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //Initializing
        mName = (EditText) findViewById(R.id.edit_text_name);
        mPhone = (EditText) findViewById(R.id.edit_text_phone);
        mRelation = (EditText) findViewById(R.id.edit_text_relation);
    }

    public void addContact (View view){
        String name, phone, relation;

        name = mName.getText().toString();
        phone = mPhone.getText().toString();
        relation = mRelation.getText().toString();

    }
}
