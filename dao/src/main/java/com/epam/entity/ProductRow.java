package com.epam.entity;


public class ProductRow extends Entity {


    private String title;
    private String author;
    private boolean isReserved;

    public ProductRow() {
    }

    public ProductRow(Long id, String title) {
        super(id);
        this.title = title;
    }

    public ProductRow(Long id, String title, String author) {
        super(id);
        this.title = title;
        this.author = author;
    }

    public ProductRow(Long id, String title, String author, boolean isReserved) {
        super(id);
        this.title = title;
        this.author = author;
        this.isReserved = isReserved;
    }

    public ProductRow(Long id) {
        super(id);
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProductRow productRow = (ProductRow) o;

        if (isReserved != productRow.isReserved) return false;
        if (title != null ? !title.equals(productRow.title) : productRow.title != null) return false;
        return author != null ? author.equals(productRow.author) : productRow.author == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isReserved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isReserved=" + isReserved +
                '}';
    }
}
