package com.example.apopsharebook;

public class RequestHistoryList {
	String title, status, user;
	int img;

	public RequestHistoryList(String t, String s, String u){
		this.title = t;
		this.status = s;
		this.user = u;
		setImg();

	}

	public String getTitle() {
		return title;
	}

	public String getStatus() {
		return status;
	}

	public String getUser() {
		return user;
	}
	public int getImg(){
		return img;
	}
	public void setImg( ){
		if(status.equals("Accepted")){
			this.img=R.drawable.icon_check;
		}
		else if(status.equals("Declined")){
			this.img=R.drawable.icon_cancel;
		}
	}

}
