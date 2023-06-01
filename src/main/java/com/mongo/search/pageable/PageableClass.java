package com.mongo.search.pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class PageableClass<T> {

    private static final Logger logger = LoggerFactory.getLogger(PageableClass.class);

    public boolean hasContent;
    public boolean hasNext;
    public boolean hasPrevious;
    public boolean isFirst;
    public boolean isLast;
    public long totalElements;
    public int totalPages;
    public List<T> data;
    public int perPage;
    public int pageNumber;
    public int size;
    public String pluralResourceName;

    public boolean isSorted;
    public String sortColumn;
    public String sortOrder;

    public boolean countEntities;

    public String getDisplayingText() {

        if (!hasContent) {
            logger.info("hasContent is false");
            return "There are no " + pluralResourceName + " to display";
        }

        String total = " of " + this.totalElements + " " + pluralResourceName;

        int startNumber = (size * pageNumber) + 1;
        int endNumber;

        if (isLast) {
            endNumber = startNumber + data.size() - 1;
        } else {
            endNumber = startNumber + size - 1;
        }

        return "Displaying " + startNumber + " through " + endNumber +  (countEntities ?  " of many " +pluralResourceName : total);
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPluralResourceName() {
        return pluralResourceName;
    }

    public void setPluralResourceName(String pluralResourceName) {
        this.pluralResourceName = pluralResourceName;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isCountEntities() {
        return countEntities;
    }

    public void setCountEntities(boolean countEntities) {
        this.countEntities = countEntities;
    }
}
