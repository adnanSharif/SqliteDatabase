package app.example.com.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true); //If back pressed, minimize the app
    }

    /**
     * This method will be called if add contact button is tapped
     * @param view
     */
    public void addContact(View view){
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);
    }

    /**
     * This method will be called be called if show contact button is tapped
     * @param view
     */
    public void showContact(View view){
        Intent intent = new Intent(MainActivity.this, ShowContact.class);
        startActivity(intent);

    }
}
