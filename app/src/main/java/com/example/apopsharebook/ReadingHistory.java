package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistory extends AppCompatActivity {

    List<Books> bList = new ArrayList<>();
    ListView listView;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);

        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(ReadingHistory.this,UserAccount.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(ReadingHistory.this,Message.class)));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");

        //the bottom menu bar to link the pages
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(ReadingHistory.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(ReadingHistory.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(ReadingHistory.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(ReadingHistory.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(ReadingHistory.this,UserAccount.class));
            }
            return true;
        });
        db = new Database(this);
        bList = db.viewRHBooks(userId);
        //ListView using ArrayList

        listView = findViewById(R.id.ReadingHistoryListView);
        ReadingHistoryListAdapter adapter = new ReadingHistoryListAdapter(this,R.layout.readinghistory_list_item,bList);
        listView.setAdapter(adapter);
        listView.setDivider(null);
    }
}