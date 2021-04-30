package com.icthh.xm.tmf.ms.resourcepool.persistence.repository;

import java.io.Serializable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BatchRequest implements Pageable, Serializable {

    private final Integer limit;
    private final Integer offset;
    private final Sort sort;

    public BatchRequest(Integer limit, Integer offset, Sort sort) {
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public static BatchRequest of(Integer limit, Integer offset, Sort sort) {
        return new BatchRequest(limit, offset, sort);
    }

    public static BatchRequest of(Integer limit, Integer offset) {
        return new BatchRequest(limit, offset, null);
    }


    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }
}
