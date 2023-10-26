package com.example.apopsharebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Random;

public class ReadingHistoryListAdapter extends ArrayAdapter {
	Context context;
	int resource;
	List<Books> list;

	int[] images = {R.drawable.book_cover_1,R.drawable.book_cover_2,R.drawable.book_cover_3
			,R.drawable.cover02,R.drawable.cover01,R.drawable.cover03,
			R.drawable.cover04,R.drawable.cover05,R.drawable.cover06,
			R.drawable.cover07,R.drawable.cover08,R.drawable.cover09};




	public ReadingHistoryListAdapter(Context context, int resource, List<Books> list) {
		super(context, resource, list);
		this.context = context;
		this.resource = resource;
		this.list = list;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(resource,null);

		Random r = new Random();
		int low = 0;
		int high = 11;
		int img = r.nextInt(high-low) + low;

		ImageView cover = view.findViewById(R.id.imgBookCover);
		TextView title = view.findViewById(R.id.txtBookTitle);
		TextView author = view.findViewById(R.id.txtBookAuthor);
		TextView timeSpent = view.findViewById(R.id.txtTimeSpent);
		TextView isbn = view.findViewById(R.id.txtBookIsbn);

		Books bookList = list.get(position);

		cover.setImageResource(images[img]);
		title.setText(bookList.getTitle());
		author.setText(bookList.getAuthor());
		isbn.setText(bookList.getIsbn());
		timeSpent.setText(Double.toString(bookList.getTime())+" Hours");

		return view;
	}
}
