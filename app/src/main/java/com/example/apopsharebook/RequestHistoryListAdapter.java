package com.example.apopsharebook;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RequestHistoryListAdapter extends ArrayAdapter {
	Context context;
	int resource;
	List<RequestHistoryList> list;

	public RequestHistoryListAdapter(Context context, int resource, List<RequestHistoryList> list) {
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

		TextView title = view.findViewById(R.id.txtRTitle);
		TextView status = view.findViewById(R.id.txtRStatus);
		TextView user = view.findViewById(R.id.txtRUser);
		ImageView img=view.findViewById(R.id.imgAorD);

		RequestHistoryList rhList = list.get(position);
		title.setText(rhList.getTitle());
		status.setText(rhList.getStatus());
		user.setText(rhList.getUser());
		img.setImageResource(rhList.getImg());


		return view;
	}
}
