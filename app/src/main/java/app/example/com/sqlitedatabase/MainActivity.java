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
        moveTaskToBack(true);
    }
    public void addContact(View view){
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);
    }

    public void showContact(View view){
        Intent intent = new Intent(MainActivity.this, ShowContact.class);
        startActivity(intent);

    }
}
