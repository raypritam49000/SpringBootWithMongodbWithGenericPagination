package com.mongo.search.builder;

import com.mongo.modal.Student;
import com.mongo.search.pageable.PageableClass;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchBuilder<T> {


    public MongoTemplate mongoTemplate;


    final List<Criteria> criteria = new ArrayList<>();


    private final int pageNumber;
    private final int pageSize;
    Class<T> classOfT;


    private Pageable pageable;

    private Logger logger;


    public SearchBuilder(int pageNumber, int pageSize, Class<T> classOfT,MongoTemplate mongoTemplate) {
        this.classOfT = classOfT;
        this.mongoTemplate = mongoTemplate;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

    }


    public void setSortCriterion(String sortOrder, String sort) {

        System.out.println("pageNumber" + pageNumber);
        System.out.println("pageSize" + pageSize);
        System.out.println("sort"+sort);
        System.out.println("sortOrder"+sortOrder);
        pageable = PageRequest.of(pageNumber, pageSize, sortOrder.equals("ASC") ? Sort.by(Sort.Direction.ASC) : Sort.by(Sort.Direction.DESC, sort));

    }


    public void addSearchFilter(Criteria criteria1) {
        criteria.add(criteria1);
    }


    public Page<T> get() {

        final Query query = new Query();
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        List<T> list = mongoTemplate.find(query, classOfT);

        System.out.println("list---" + list);

        System.out.println("list size---" + list.size());

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), classOfT));

    }


    public <PageDTO extends PageableClass<T>>
    PageDTO getAsPageDTO(PageDTO pageDTO,
                         String verifiedSortColumn,
                         String verifiedSortOrder,
                         String pluralResourceName) {

        Page<T> entityPage;

        entityPage = get();

        return PageableUtilities.transferToPageDTO(
                entityPage,
                pageDTO,
                verifiedSortColumn,
                verifiedSortOrder,
                pluralResourceName);
    }

}
