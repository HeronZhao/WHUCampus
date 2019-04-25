package com.example.hp.mycampus.model;

public class Book {
    private String author;// 著者
    private String title;// 题名
    private String year;// 年
    private String shouldReturnDate;// 应还日期
    private String shouldReturnTime;// 应还时间
    private String returnDate;// 归还日期
    private String returnTime;// 归还时间
    private String fine;// 罚款
    private String branch;// 分馆
    private String loanDate;// 外借日期
    private String lastRenewalDate;// 最后续借日期
    private String number;// 续借次数

    public Book(String author, String title, String year, String shouldReturnDate, String shouldReturnTime,
                String returnDate, String returnTime, String fine, String branch, String loanDate, String lastRenewalDate,
                String number) {
        super();
        this.author = author;
        this.title = title;
        this.year = year;
        this.shouldReturnDate = shouldReturnDate;
        this.shouldReturnTime = shouldReturnTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.fine = fine;
        this.branch = branch;
        this.loanDate = loanDate;
        this.lastRenewalDate = lastRenewalDate;
        this.number = number;
    }
    public Book(String title){
        this.title=title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getShouldReturnDate() {
        return shouldReturnDate;
    }

    public void setShouldReturnDate(String shouldReturnDate) {
        this.shouldReturnDate = shouldReturnDate;
    }

    public String getShouldReturnTime() {
        return shouldReturnTime;
    }

    public void setShouldReturnTime(String shouldReturnTime) {
        this.shouldReturnTime = shouldReturnTime;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(String lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", title=" + title + ", year=" + year + ", shouldReturnDate="
                + shouldReturnDate + ", shouldReturnTime=" + shouldReturnTime + ", returnDate=" + returnDate
                + ", returnTime=" + returnTime + ", fine=" + fine + ", branch=" + branch + ", loanDate=" + loanDate
                + ", lastRenewalDate=" + lastRenewalDate + ", number=" + number + "]";
    }

    public Book(String author, String title, String year, String shouldReturnDate, String shouldReturnTime, String returnDate, String returnTime, String branch,String fine) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.shouldReturnDate = shouldReturnDate;
        this.shouldReturnTime = shouldReturnTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.branch = branch;
        this.fine=fine;
    }
}
