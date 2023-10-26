package com.example.apopsharebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class CurrentLoanListAdapter extends ArrayAdapter {
	Context context;
	int resource;
	List<CurrentLoanList> list;

	public CurrentLoanListAdapter(Context context, int resource, List<CurrentLoanList> list) {
		super(context, resource, list);
		this.context = context;
		this.resource = resource;
		this.list = list;
	}

	@SuppressLint("UseCompatLoadingForDrawables")
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(resource,null);

		ImageView cover = view.findViewById(R.id.imgBookCover);
		TextView title = view.findViewById(R.id.txtBookTitle);
		TextView author = view.findViewById(R.id.txtBookAuthor);
		TextView publisher = view.findViewById(R.id.txtBookPublisher);
		TextView year = view.findViewById(R.id.txtBookYear);
		TextView isbn = view.findViewById(R.id.txtBookIsbn);
		TextView owner = view.findViewById(R.id.txtBookOwner);
		TextView sdate = view.findViewById(R.id.txtTimeSpent);
		TextView rDate = view.findViewById(R.id.txtReturnDate);

		CurrentLoanList clList = list.get(position);

		cover.setImageDrawable(context.getResources().getDrawable(clList.getCover(),null));
		title.setText(clList.getTitle());
		author.setText(clList.getAuthor());
		publisher.setText(clList.getPublisher());
		year.setText(clList.getYear());
		isbn.setText(clList.getIsbn());
		owner.setText(clList.getOwner());

		String[] date1 = clList.getsDate().split(" ");
		String startDate = date1[0]+", "+date1[1]+" "+date1[2];
		sdate.setText(startDate);

		String[] date2 = clList.getrDate().split(" ");
		String endDate = date2[0]+", "+date2[1]+" "+date2[2];
		rDate.setText(endDate);


		return view;
	}
}