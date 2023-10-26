package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Timestamp;
import java.util.Date;

public class AdminMsgUser extends AppCompatActivity {
    String receiverId, msgTxt, userId, senderId, type;
    int bId;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_msg_user);
//        ImageButton go_back = findViewById(R.id.btnBack);
//        ImageButton btnMessageIcon = findViewById(R.id.btnMessageIcon);
        EditText userID = findViewById(R.id.editUserId);
        EditText messageText = findViewById(R.id.txtAdminMsg);
        EditText bookId=findViewById(R.id.editBookId);
        Button messageUser = findViewById(R.id.btnAdminUpdateUser);

        db=new Database(this);
        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiverId = userID.getText().toString();
                boolean userOk=db.checkUsername(receiverId);
                if(!userOk){
                    userID.setError("User does not exist or check ID");
                }
                msgTxt = messageText.getText().toString();
                bId=Integer.parseInt(bookId.getText().toString());
                senderId="Admin";
                type="Message from Admin-Do not reply";
                Timestamp t = new Timestamp(System.currentTimeMillis());
                Date time=new Date(t.getTime());
                String date=time.toString();
                boolean success=db.adminSendMessage(receiverId,senderId,date,bId,msgTxt,type);
                if(success){
                    Toast.makeText(AdminMsgUser.this,"Message sent.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AdminMsgUser.this,"Message not sent.Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}