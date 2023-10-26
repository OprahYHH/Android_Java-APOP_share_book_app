package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminUserList extends AppCompatActivity {
	//fake data(delete when you finished)
	String[] userId = {"mary@a.com","tom@a.com","allan@a.com"};
	String[] userFname = {"Mary","Tom","Allan"};
	String[] userLname = {"Chen","Gu","Lin"};
	String[] userAddr = {"8290 Cambie st","1205 West rd","3210 5st rd"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_user_list);

		ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
		ImageButton go_back = findViewById(R.id.btnBack);

		btnMessage.setOnClickListener(v -> startActivity(new Intent(AdminUserList.this,Message.class)));
		go_back.setOnClickListener(v -> startActivity(new Intent(AdminUserList.this,AdminMenu.class)));

		//ListView
		List<HashMap<String,String>> userList = new ArrayList<>();

		for(int i=0; i<userId.length; i++) {
			HashMap<String,String> hashmap = new HashMap<>();
			hashmap.put("id",userId[i]);
			hashmap.put("fname",userFname[i]);
			hashmap.put("lname",userLname[i]);
			hashmap.put("address",userAddr[i]);
			userList.add(hashmap);
		}

		String[] from = {"id","fname","lname","address"};
		int[] to = {R.id.txtUserListId,R.id.txtUserListFName,R.id.txtUserListLName,R.id.txtUserListAdr};

		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),userList,
				R.layout.user_list_item,from,to);

		ListView listView = findViewById(R.id.UserListView);
		listView.setAdapter(adapter);
	}
}