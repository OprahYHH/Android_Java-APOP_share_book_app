package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class popup extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        db = new Database(this);

        Intent i = getIntent();
        String rd = i.getStringExtra("returnDate");
        String[] date2 = rd.split(" ");
        String returnDate = date2[0]+", "+date2[1]+" "+date2[2];

        int loanId = i.getIntExtra("loanId",0);
        TextView out = findViewById(R.id.txtout);
        out.setText("The return date for the book is "+returnDate);
        Button extend = findViewById(R.id.btnExtend);

        extend.setOnClickListener(new View.OnClickListener() {
            boolean isUpdated;
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.CANADA);
                try {
                    cal.setTime(sdf.parse(rd));
                    cal.add(Calendar.DATE,30);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                isUpdated = db.extendDate(Integer.toString(loanId),cal.getTime().toString());
                if(isUpdated)
                    Toast.makeText(popup.this,
                            Html.fromHtml("<big>Return date extended by 30 days!</big>"),
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(popup.this, "Couldn't extend the return date.",
                            Toast.LENGTH_SHORT).show();

            }
        });
    }
}