package com.epam.entity;

import java.util.ArrayList;
import java.util.List;

import com.epam.repository.utils.DBConstants;

public class Page<T> {


    private Long pageNumber;
    private long totalElements;
    private int limit;
    private int offset;
    private List<T> elements = new ArrayList<>();
//    private T filter;
    private String sortBy = "author";
    private String direction = "ASC";

    public Page() {
        this.limit = DBConstants.MAX_ROWS;
    }

    public Page(Long currentPage){
        this.pageNumber = currentPage;
        Long longOffset = calculateOffset(this.pageNumber,  DBConstants.MAX_ROWS);
        this.offset = longOffset.intValue();
        this.limit = DBConstants.MAX_ROWS;
    }

//    public Page(int pageNumber, long totalElements, int limit, List<T> elements,/* T filter,*/ String sortBy, String direction) {
//        this.pageNumber = pageNumber;
//        this.totalElements = totalElements;
//        this.limit = limit;
//        this.elements = elements;
//        this.filter = filter;
//        this.sortBy = sortBy;
//        this.direction = direction;
//    }

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

//    public T getFilter() {
//        return filter;
//    }
//
//    public void setFilter(T filter) {
//        this.filter = filter;
//    }

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

    private int calculateMaxPage(long countItems, int MAX_ROWS){
        return (int) Math.ceil(((double) countItems) / MAX_ROWS);
    }

    private Long calculateOffset(Long currentPage, int _MAX_ROWS_AT_PAGE) {
        return (currentPage - 1) * _MAX_ROWS_AT_PAGE;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Page<?> page = (Page<?>) o;
//
//        if (pageNumber != page.pageNumber) return false;
//        if (totalElements != page.totalElements) return false;
//        if (limit != page.limit) return false;
//        if (elements != null ? !elements.equals(page.elements) : page.elements != null) return false;
//        if (filter != null ? !filter.equals(page.filter) : page.filter != null) return false;
//        if (sortBy != null ? !sortBy.equals(page.sortBy) : page.sortBy != null) return false;
//        return direction != null ? direction.equals(page.direction) : page.direction == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = pageNumber;
//        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
//        result = 31 * result + limit;
//        result = 31 * result + (elements != null ? elements.hashCode() : 0);
//        result = 31 * result + (filter != null ? filter.hashCode() : 0);
//        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
//        result = 31 * result + (direction != null ? direction.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "Page{" +
//                "pageNumber=" + pageNumber +
//                ", totalElements=" + totalElements +
//                ", limit=" + limit +
//                ", elements=" + elements +
//                ", filter=" + filter +
//                ", sortBy='" + sortBy + '\'' +
//                ", direction='" + direction + '\'' +
//                '}';
//    }


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
