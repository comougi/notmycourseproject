package com.og.MainClasses;

public class Book {
    private String title;
    private String author;
    private String genre;
    private int amount;
    private boolean inShop;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, String author, String genre, int amount, boolean inShop) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.amount = amount;
        this.inShop = inShop;
    }

    public Book() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isInShop() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
