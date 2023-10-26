package com.example.apopsharebook;

public class Books {
    private String title,author,genre,status,owner,year,isbn,publisher,id;
    private double timeSpent;

    public Books(String title, String author, String genre, String status, String owner, String year,
                String isbn,String publisher,String id) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.owner = owner;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
        this.id = id;
    }

    public Books(String title, String author,String isbn, double time){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        timeSpent = time;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public String getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getId() {
        return id;
    }

    public double getTime() {
        return timeSpent;
    }


}

