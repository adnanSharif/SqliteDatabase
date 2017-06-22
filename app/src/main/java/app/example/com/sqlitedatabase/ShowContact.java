package app.example.com.sqlitedatabase;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowContact extends AppCompatActivity {

    //UI Components
    private ListView mListView;

    private DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        mListView = (ListView) findViewById(R.id.list_view);
        mDatabaseHelper = new DatabaseHelper(this);

        init();
    }

    private void init(){
        ArrayList<SingleContact> contactList =mDatabaseHelper.getContacts();
        if (contactList.size()==0){
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.list_view), "There is no " +
                                    "contact to show. At first, add an item. Click 'OK' to get" +
                                    "redirected to Add contact Activity.",
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ShowContact.this,AddContact.class);
                            startActivity(intent);
                        }
                    });

            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(5);//this is your max line as your want
            snackbar.show();
        }
        ShowContactsAdapter adapter = new ShowContactsAdapter(this, contactList);

        mListView.setAdapter(adapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        init();
    }
}
