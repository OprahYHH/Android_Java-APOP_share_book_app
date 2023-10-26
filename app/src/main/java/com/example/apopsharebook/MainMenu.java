package com.example.apopsharebook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //define ActionBar object
        ActionBar actionbar;
        actionbar = getSupportActionBar();

        //set the layout of background color
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#f3f3e8"));

        ImageButton btnAddBook = findViewById(R.id.btnAddBook);
        ImageButton btnUpdateBook = findViewById(R.id.btnUpdateBook);
        ImageButton btnBorrowBook = findViewById(R.id.btnBorrowBook);
        ImageButton btnReadingTracker = findViewById(R.id.btnReadingTracker);
        ImageButton btnUserIcon = findViewById(R.id.btnUserIcon);
        ImageButton btnUserArrow = findViewById(R.id.btnUserArrow);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
        ImageButton btnHome = findViewById(R.id.btnHome);
        TextView userName=findViewById(R.id.txtUserName);
        EditText search=findViewById(R.id.edTxtSearch);
        Button btnSearch=findViewById(R.id.btnSearch1);

        //Free word search by title

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word=search.getText().toString();
                Intent i=new Intent(MainMenu.this,SearchOutput.class);
                i.putExtra("searchWord", word);
                startActivity(i);
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");
        userName.setText(userId);

        //the button event
        btnMessage.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,Message.class)));
        btnHome.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,Login.class)));
        btnUserIcon.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,UserAccount.class)));
        btnUserArrow.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,UserAccount.class)));
        btnAddBook.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,AddBook.class)));
        btnUpdateBook.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,UpdateBook2.class)));
        btnBorrowBook.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,BorrowBook2.class)));
        btnReadingTracker.setOnClickListener(v -> startActivity(new Intent(MainMenu.this,ReadingTracker.class)));
    }
}