package com.epam.entity;


public class ProductRow extends Entity {


    private String title;
    private String author;

    public ProductRow() {
    }


    public ProductRow(Long id, String title, String author) {
        super(id);
        this.title = title;
        this.author = author;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProductRow that = (ProductRow) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return author != null ? author.equals(that.author) : that.author == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductRow{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
