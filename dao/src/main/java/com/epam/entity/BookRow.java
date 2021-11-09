package com.epam.entity;

public class BookRow extends ProductRow {

    private Integer publishingYear;
    private String publisher;
    private String genre;
    private Integer numberOfPages;
    private boolean isHardCover;

    public BookRow() {
    }

    public BookRow(Long id, String title, String author, Integer publishingYear) {
        super(id, title, author);
        this.publishingYear = publishingYear;
    }

    public BookRow(Long id, String title, String author, Integer publishingYear, String publisher) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
    }

    public BookRow(Long id, String title, String author, Integer publishingYear, String publisher, String genre) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
    }

    public BookRow(Long id, String title, String author, Integer publishingYear, String publisher, String genre, Integer numberOfPages) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
    }

    public BookRow(Long id, String title, String author, Integer publishingYear, String publisher, String genre, Integer numberOfPages, boolean isHardCover) {
        super(id, title, author);
        this.publishingYear = publishingYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.isHardCover = isHardCover;
    }

    public BookRow(Long id, String title, String author, Integer publishYear, String publisher, boolean isReserved, String genre, Integer numberOfPages, boolean isHardCover) {
        super(id,title,author,isReserved);
        this.publishingYear = publishYear;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.isHardCover = isHardCover;


    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
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

    public Integer getNumberOfPages() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BookRow bookRow = (BookRow) o;

        if (isHardCover != bookRow.isHardCover) return false;
        if (publishingYear != null ? !publishingYear.equals(bookRow.publishingYear) : bookRow.publishingYear != null)
            return false;
        if (publisher != null ? !publisher.equals(bookRow.publisher) : bookRow.publisher != null) return false;
        if (genre != null ? !genre.equals(bookRow.genre) : bookRow.genre != null) return false;
        return numberOfPages != null ? numberOfPages.equals(bookRow.numberOfPages) : bookRow.numberOfPages == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (publishingYear != null ? publishingYear.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (numberOfPages != null ? numberOfPages.hashCode() : 0);
        result = 31 * result + (isHardCover ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title=" + getTitle() + '\'' +
                ", author=" + getAuthor() + '\'' +
                ", publishingYear=" + publishingYear + '\'' +
                ", publisher='" + publisher + '\'' +
                ", genre='" + genre + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", isHardCover=" + isHardCover +
                '}';
    }
}
