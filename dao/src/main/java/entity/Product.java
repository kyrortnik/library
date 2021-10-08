package entity;


public class Product {

    private Long id;
    private String title;
    private String author;
    private boolean isReserved;

    public Product() {
    }

    public Product(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Product(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Product(Long id, String title, String author, boolean isReserved) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isReserved = isReserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        Product that = (Product) o;

        if (!id.equals(that.id)) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return author != null ? author.equals(that.author) : that.author == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isReserved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
