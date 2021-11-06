package com.epam.entity;

import java.util.ArrayList;
import java.util.List;

public class Pageable<T> {

    private int pageNumber;
    private long totalElements;
    private int limit;
    private List<T> elements = new ArrayList<>();
    private T filter;
    private String sortBy = "author";
    private String direction = "ASC";

    public Pageable() {}

    public Pageable(int pageNumber, long totalElements, int limit, List<T> elements, T filter, String sortBy, String direction) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.elements = elements;
        this.filter = filter;
        this.sortBy = sortBy;
        this.direction = direction;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
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

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
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


    private int calculateMaxPage(long countItems, int _MAX_ROWS_AT_PAGE){
        return (int) Math.ceil(((double) countItems) / _MAX_ROWS_AT_PAGE);
    }

    private int calculateRow(int currentPage, int _MAX_ROWS_AT_PAGE) {
        return (currentPage - 1) * _MAX_ROWS_AT_PAGE;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pageable<?> pageable = (Pageable<?>) o;

        if (pageNumber != pageable.pageNumber) return false;
        if (totalElements != pageable.totalElements) return false;
        if (limit != pageable.limit) return false;
        if (elements != null ? !elements.equals(pageable.elements) : pageable.elements != null) return false;
        if (filter != null ? !filter.equals(pageable.filter) : pageable.filter != null) return false;
        if (sortBy != null ? !sortBy.equals(pageable.sortBy) : pageable.sortBy != null) return false;
        return direction != null ? direction.equals(pageable.direction) : pageable.direction == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        result = 31 * result + (filter != null ? filter.hashCode() : 0);
        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", totalElements=" + totalElements +
                ", limit=" + limit +
                ", elements=" + elements +
                ", filter=" + filter +
                ", sortBy='" + sortBy + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
