package com.mongo.controller;

import com.mongo.modal.Section;
import com.mongo.modal.Student;
import com.mongo.repo.SectionRepo;
import com.mongo.repo.StudentRepo;
import com.mongo.search.SectionPagedClass;
import com.mongo.search.StudentPagedClass;
import com.mongo.search.builder.SearchBuilder;
import com.mongo.search.builder.Specifications;
import com.mongo.search.pageable.PageableClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {


    @Autowired
    private SectionRepo sectionRepo;


    @Autowired
    public MongoTemplate mongoTemplate;

    private MongoOperations mongoOperations;

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostMapping("/add")
    public ResponseEntity<Section> addStudent(@RequestBody Section section) {
        return ResponseEntity.ok(this.sectionRepo.save(section));
    }


    @GetMapping("/get")
    public ResponseEntity<?> getSections() {

        return ResponseEntity.ok(this.sectionRepo.findAll());
    }


    @GetMapping("/filterSectionByName")
    public List<Section> filterSectionByName() {

        List<AggregationOperation> list = new ArrayList<AggregationOperation>();

        list.add(Aggregation.unwind("students"));
        list.add(Aggregation.match(Criteria.where("students.name").in(List.of("stuname5", "stuname4"))));
        list.add(Aggregation.group("name").push("students").as("students"));
        list.add(Aggregation.project("name", "students"));
        TypedAggregation<Section> agg = Aggregation.newAggregation(Section.class, list);

        System.out.println("agg--" + agg);
        System.out.println("mongoOperations--" + mongoOperations);
        return mongoOperations.aggregate(agg, Section.class, Section.class).getMappedResults();

    }


    @PostMapping("/search")
    public Page<Section> seacrhSection(@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "size", required = false) Integer size,
                                       @RequestParam(value = "sort", required = false) String sort,
                                       @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                       @RequestParam(value = "name", required = false) String name, @RequestParam(value = "collage", required = false) String collage, @RequestParam(value = "house", required = false) String house, @RequestParam(value = "subName", required = false) String subName) {

        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 15;
        }

        if (sort == null) {
            sort = "name";
        }

        if (sortOrder == null) {
            sortOrder = "ASC";
        } else {
            sortOrder = "DESC";
        }

//
//
        //builder way
//        SearchBuilder searchBuilder=new SearchBuilder(page, size,Section.class,mongoTemplate);
//        searchBuilder.setSortCriterion(sortOrder,sort);
//
//
//        searchBuilder.addSearchFilter(Specifications.exactMatchText(name,"name"));
//        searchBuilder.addSearchFilter(Specifications.exactMatchText(collage,"collage"));
//        searchBuilder.addSearchFilter(Specifications.exactMatchText(house,"address.house"));
//        searchBuilder.addSearchFilter(Specifications.exactMatchWithinDocuments(subName,"subjects.subName"));
//
//        PageableClass<Section> P = new SectionPagedClass();
//
//
//        return searchBuilder.getAsPageDTO(P, sort, sortOrder, "Students");
//

        //traditional way
        Pageable pageable = PageRequest.of(page, size, sortOrder.equals("ASC") ? Sort.by(Sort.Direction.ASC) : Sort.by(Sort.Direction.DESC, sort));

        final Query query = new Query();
        query.fields().include("id").include("name");
        final List<Criteria> criteria = new ArrayList<>();
//        if (name != null && !name.isEmpty())
//            criteria.add(Criteria.where("name").is(name));
//        if (collage != null && !collage.isEmpty())
//            criteria.add(Criteria.where("collage").is(collage));
//
//        if (house != null && !house.isEmpty())
//            criteria.add(Criteria.where("address.house").is(house));
//
//        if (subName != null && !subName.isEmpty())
//            criteria.add(Criteria.where("subjects.subName").in(subName));


        System.out.println("criteria----" + criteria.size());
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        List<Section> students = mongoTemplate.find(query, Section.class);


        return PageableExecutionUtils.getPage(
                students,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Section.class));

    }


    @GetMapping("/project")
    public List<Section> projectionOne() {


//        Query query = new Query().fields().include("id").include("field2");

        Query query = new Query();
        query.fields().include("name").include("id").exclude("students");

        List<Section> results = mongoTemplate.find(query, Section.class, "section");


        return results;


    }


}
