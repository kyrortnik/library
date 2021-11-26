package com.epam.entity;

public class BookRow extends ProductRow {

    private int publishingYear;
    private String publisher;
    private String genre;
    private int numberOfPages;
    private boolean isHardCover;
    private String description;

    public BookRow() {
    }


    public BookRow(Long id, String title, String author, int publishingYear) {
        super(id, title, author);
        this.publishingYear = publishingYear;
    }

    public BookRow(Long id, String title, String author, int publishingYear, String publisher,  String genre, int numberOfPages, boolean isHardCover, String description) {
        super(id, title, author);
        this.publishingYear = publishingYear;
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

        BookRow bookRow = (BookRow) o;

        if (publishingYear != bookRow.publishingYear) return false;
        if (numberOfPages != bookRow.numberOfPages) return false;
        if (isHardCover != bookRow.isHardCover) return false;
        if (publisher != null ? !publisher.equals(bookRow.publisher) : bookRow.publisher != null) return false;
        if (genre != null ? !genre.equals(bookRow.genre) : bookRow.genre != null) return false;
        return description != null ? description.equals(bookRow.description) : bookRow.description == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + publishingYear;
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + numberOfPages;
        result = 31 * result + (isHardCover ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookRow{" +
                "publishingYear=" + publishingYear +
                ", publisher='" + publisher + '\'' +
                ", genre='" + genre + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", isHardCover=" + isHardCover +
                ", description='" + description + '\'' +
                '}';
    }
}
