package com.example.apopsharebook;

public class CurrentLoanList {
	int cover,loanId;
	String title, author, publisher, year, isbn, owner, sDate, rDate,price;


	public CurrentLoanList(int co, String t, String a, String p, String y, String isbn, String o, String sd,String rd,String pr, int loanId) {
		this.cover = co;
		this.title = t;
		this.author = a;
		this.publisher = p;
		this.year = y;
		this.isbn = isbn;
		this.owner = o;
		this.sDate = sd;
		rDate = rd;
		price = pr;
		this.loanId = loanId;
	}

	public int getCover() {
		return cover;
	}

	public int getLoanId() {
		return loanId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getYear() {
		return year;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getOwner() {
		return owner;
	}

	public String getsDate() {
		return sDate;
	}

	public String getrDate() {
		return rDate;
	}


	public String getPrice() {
		return price;
	}



}
