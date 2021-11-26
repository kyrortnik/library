package com.epam.entity;

import static com.epam.repository.utils.DBConstants.*;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {


    private Long pageNumber;
    private long totalElements;
    private int limit = MAX_ROWS;
    private int offset;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "author";
    private String direction = "ASC";

    public Page() {
    }


    public Page(Long pageNumber, long totalElements, int limit, List<T> elements, String sortBy, String direction) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.elements = elements;
        this.sortBy = sortBy;
        this.direction = direction;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page<?> page = (Page<?>) o;

        if (totalElements != page.totalElements) return false;
        if (limit != page.limit) return false;
        if (offset != page.offset) return false;
        if (pageNumber != null ? !pageNumber.equals(page.pageNumber) : page.pageNumber != null) return false;
        if (elements != null ? !elements.equals(page.elements) : page.elements != null) return false;
        if (sortBy != null ? !sortBy.equals(page.sortBy) : page.sortBy != null) return false;
        return direction != null ? direction.equals(page.direction) : page.direction == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber != null ? pageNumber.hashCode() : 0;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + offset;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
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
                ", offset=" + offset +
                ", elements=" + elements +
                ", sortBy='" + sortBy + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
