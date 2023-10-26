package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class EditAccount extends AppCompatActivity {
    Database database;
    String password,FName, LName, Address, fullName, stringAge, preferences, userId;
    int Age;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);

        EditText editPw=findViewById(R.id.editUserPassword);
        EditText editName=findViewById(R.id.editUserName);
        EditText editAddress=findViewById(R.id.editUserAddress);

        EditText editAge=findViewById(R.id.editUserAge);
       // Spinner spnPref=findViewById(R.id.spEditInterest);
        Button btnSubmitChange=findViewById(R.id.btnAdminUpdateUser);

        database=new Database(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getString("userId", "NA");

        c=database.getUserInfo(userId);
        if(c.getCount()>0) {
            while (c.moveToNext()) {
                password=c.getString(1);
                FName = c.getString(2);
                LName = c.getString(3);
                Address = c.getString(4);
                Age=c.getInt(5);
            }
        }
        //insert current information prior to udpate
       // editEmail.setText(userId);
        editPw.setText(password);
        fullName=FName+" "+LName;
        editName.setText(fullName);
        editAddress.setText(Address);
        editAge.setText(Age+"");

        btnSubmitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String newUserId=editEmail.getText().toString();
                password=editPw.getText().toString();
                fullName=editName.getText().toString();
                String nameSplit []=fullName.split(" ");
                FName=nameSplit[0];
                LName=nameSplit[1];
                Address=editAddress.getText().toString();
                Age=Integer.parseInt(editAge.getText().toString());

               boolean isInserted=database.updateUserAccount(userId,password,FName, LName, Address,Age);

                if(isInserted){
                    Toast.makeText(EditAccount.this,"User information had been updated.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(EditAccount.this,"There had been an error, please try again.", Toast.LENGTH_LONG).show();
                }

            }
        });



        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(EditAccount.this,UserAccount.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(EditAccount.this,Message.class)));

        //the bottom menu bar to link the pages
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(EditAccount.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(EditAccount.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(EditAccount.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(EditAccount.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(EditAccount.this,UserAccount.class));
            }
            return true;
        });
    }
}