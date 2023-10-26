package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchOutput extends AppCompatActivity implements RecyclerAdapter.ItemClickListener{
    ImageButton btnBack, btnMsgIcon;
    BottomNavigationView bottom_menu;
    String keyword;

    int img=R.drawable.book_cover_1;
    RecyclerAdapter adapter;
    Database database;
    Cursor c;
    String inpLoc, title, author, genre, pub, pubYear, owner, status;
    int bookId, price;
    ArrayList<String> bTitles, bAuthors, bGenres, bPub, bPubYear, bOwner, bStatus;
    ArrayList<Integer> bIds, bPrices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_output);
        database=new Database(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");

        Intent i=getIntent();

           keyword=i.getStringExtra("searchWord");

        c=database.searchBooksByTitle(keyword,userId);

        bTitles=new ArrayList<String>();
        bAuthors=new ArrayList<String >();
        bGenres=new ArrayList<String>();
        bPub=new ArrayList<String>();
        bPubYear=new ArrayList<String>();
        bOwner=new ArrayList<String>();
        bStatus=new ArrayList<String>();
        bIds=new ArrayList<Integer>();
        bPrices=new ArrayList<Integer>();

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                bTitles.add(c.getString(0));
                bAuthors.add(c.getString(1));
                bGenres.add(c.getString(2));
                bPub.add(c.getString(3));
                bPubYear.add(c.getString(4));
                bOwner.add(c.getString(5));
                bStatus.add(c.getString(6));
                bIds.add(c.getInt(7));
                bPrices.add(c.getInt(8));
            }
        } else if (c.getCount() == 0) {
            Toast.makeText(SearchOutput.this, "No books matching search criteria", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView=findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapter(this, img,bTitles, bAuthors, bGenres);
        adapter.setClickListener(SearchOutput.this);
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onItemClick(View view, int position) {
        title=bTitles.get(position);
        author=bAuthors.get(position);
        genre=bGenres.get(position);
        pub=bPub.get(position);
        pubYear=bPubYear.get(position);
        owner=bOwner.get(position);
        status=bStatus.get(position);
        price=bPrices.get(position);
        bookId=bIds.get(position);


        Intent i = new Intent(SearchOutput.this, BorrowBookDetails2.class);
        i.putExtra("title",title);
        i.putExtra("author", author);
        i.putExtra("genre",genre);
        i.putExtra("pub",pub);
        i.putExtra("pubYear",pubYear);
        i.putExtra("owner",owner);
        i.putExtra("status",status);
        i.putExtra("price",price);
        i.putExtra("bookId",bookId);
        startActivity(i);
    }
}