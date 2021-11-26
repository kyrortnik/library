package com.epam.entity;


import java.util.ArrayList;
import java.util.List;

public class Pageable<T> {

    private Long pageNumber;
    private long totalElements;
    private int limit;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "author";
    private String direction = "ASC";

    public Pageable() {
    }


    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pageable<?> pageable = (Pageable<?>) o;

        if (totalElements != pageable.totalElements) return false;
        if (limit != pageable.limit) return false;
        if (pageNumber != null ? !pageNumber.equals(pageable.pageNumber) : pageable.pageNumber != null) return false;
        if (elements != null ? !elements.equals(pageable.elements) : pageable.elements != null) return false;
        if (sortBy != null ? !sortBy.equals(pageable.sortBy) : pageable.sortBy != null) return false;
        return direction != null ? direction.equals(pageable.direction) : pageable.direction == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber != null ? pageNumber.hashCode() : 0;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNumber=" + pageNumber +
                ", totalElements=" + totalElements +
                ", limit=" + limit +
                ", elements=" + elements +
                ", sortBy='" + sortBy + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
