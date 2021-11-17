package com.epam.entity;

public class Book extends Product{

    private int publishingYear;
    private String publisher;
    private String genre;
    private int numberOfPages;
    private boolean isHardCover;
    private String description;

    public Book() {
    }
    public Book(Long id){
        super(id);
    }

    public Book(long id, String title, String author, int publishingYear) {
        super(id, title, author);
        this.publishingYear = publishingYear;
    }

    public Book(long id, String title, String author, int publishingYear, String publisher) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
    }

    public Book(long id, String title, String author, int publishingYear, String publisher, String genre) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
    }

    public Book(long id, String title, String author, int publishingYear, String publisher, String genre, int numberOfPages) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
    }

    public Book(long id, String title, String author, int publishingYear, String publisher, String genre, int numberOfPages, boolean isHardCover) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.isHardCover = isHardCover;
    }

    public Book(long id, String title, String author, int publishYear, String publisher, boolean isReserved, String genre, int numberOfPages, boolean isHardCover) {
        super(id,title,author,isReserved);
        this.publishingYear = publishYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.isHardCover = isHardCover;

    }

    public Book( String title, String author, int publishYear, String publisher, String genre, int numberOfPages, boolean isHardCover,String description) {
        super(title,author);
        this.publishingYear = publishYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.isHardCover = isHardCover;
        this.description = description;

    }
    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public boolean isHardCover() {
        return isHardCover;
    }

    public void setHardCover(boolean hardCover) {
        isHardCover = hardCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (publishingYear != book.publishingYear) return false;
        if (numberOfPages != book.numberOfPages) return false;
        if (isHardCover != book.isHardCover) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        return genre != null ? genre.equals(book.genre) : book.genre == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + publishingYear;
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + numberOfPages;
        result = 31 * result + (isHardCover ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "publishingYear=" + publishingYear +
                ", publisher='" + publisher + '\'' +
                ", genre='" + genre + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", isHardCover=" + isHardCover +
                '}';
    }
}
