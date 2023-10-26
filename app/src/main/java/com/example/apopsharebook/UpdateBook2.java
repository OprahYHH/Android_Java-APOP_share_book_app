package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UpdateBook2 extends AppCompatActivity  implements BooksAdapter.ItemClickListener{
    Database db;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BooksAdapter booksAdapter;
    List<Books> booksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");

        db = new Database(this);

        booksList = db.viewBooks(userId);
        recyclerView = findViewById(R.id.RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        booksAdapter = new BooksAdapter(this,booksList);
        booksAdapter.setClickListener(this);
        recyclerView.setAdapter(booksAdapter);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);

        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(UpdateBook2.this,MainMenu.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(UpdateBook2.this,Message.class)));

        //the bottom menu bar to link the pages
        bottom_menu.setSelectedItemId(R.id.menu_update_book);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(UpdateBook2.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(UpdateBook2.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(UpdateBook2.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(UpdateBook2.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(UpdateBook2.this,UserAccount.class));
            }
            return true;
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        String bookT,bookA,bookG,bookS,bookY,bookI,bookP,id;
        int Gpos=0,Spos=0;
             bookT = booksList.get(position).getTitle();
             bookA = booksList.get(position).getAuthor();
             bookG = booksList.get(position).getGenre();
            bookS = booksList.get(position).getStatus();
            bookY = booksList.get(position).getYear();
            bookI = booksList.get(position).getIsbn();
            bookP = booksList.get(position).getPublisher();
            id = booksList.get(position).getId();
            switch (bookG) {
                case "Fiction":
                    Gpos = 0;
                    break;
                case "NonFiction":
                    Gpos = 1;
                    break;
            }
            switch (bookS){
                case "Share":
                    Spos = 0;
                    break;
                case "Give-away":
                    Spos = 1;
                    break;
                case "Rent-out":
                    Spos = 2;
                    break;
            }

            Intent i = new Intent(UpdateBook2.this,UpdatePage.class);
            i.putExtra("title",bookT);
            i.putExtra("author",bookA);
            i.putExtra("year",bookY);
            i.putExtra("isbn",bookI);
            i.putExtra("pub",bookP);
            i.putExtra("genPos",Gpos);
            i.putExtra("statPos",Spos);
            i.putExtra("bookId",id);
            startActivity(i);
    }


}