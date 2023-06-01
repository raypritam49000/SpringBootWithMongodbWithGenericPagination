package com.mongo.controller;



import com.mongo.modal.Student;
import com.mongo.modal.Subject;
import com.mongo.repo.StudentRepo;
import com.mongo.resultDTO.SubjectResult;
import com.mongo.search.StudentPagedClass;
import com.mongo.search.builder.SearchBuilder;
import com.mongo.search.builder.Specifications;
import com.mongo.search.pageable.PageableClass;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;


    @Autowired
    public MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(this.studentRepo.save(student));
    }


    @GetMapping("/get")
    public ResponseEntity<?>  getStudents(){

        return ResponseEntity.ok(this.studentRepo.findAll());
    }


    @PostMapping("/search")
    public  PageableClass<Student> searchStudent(@RequestParam(value="page",required = false) Integer page,
                                       @RequestParam(value="size",required = false) Integer size,
                                       @RequestParam(value="sort",required = false) String sort,
                                       @RequestParam(value="sortOrder",required = false) String sortOrder,
                                       @RequestParam(value="name",required = false) String name, @RequestParam(value = "collage",required = false) String collage , @RequestParam(value = "house",required = false) String house, @RequestParam(value = "subName",required = false) String subName){

        if(page==null){
            page=0;
        }
        if(size==null){
            size=15;
        }

        if(sort==null){
            sort="name";
        }

        if(sortOrder==null){
            sortOrder="ASC";
        }else{
            sortOrder="DESC";
        }

//
//
        //builder way
        SearchBuilder searchBuilder=new SearchBuilder(page, size,Student.class,mongoTemplate);
        searchBuilder.setSortCriterion(sortOrder,sort);


        searchBuilder.addSearchFilter(Specifications.exactMatchText(name,"name"));
        searchBuilder.addSearchFilter(Specifications.exactMatchText(collage,"collage"));
        searchBuilder.addSearchFilter(Specifications.exactMatchText(house,"address.house"));
        searchBuilder.addSearchFilter(Specifications.exactMatchWithinDocuments(subName,"subjects.subName"));

        PageableClass<Student> P = new StudentPagedClass();


        return searchBuilder.getAsPageDTO(P, sort, sortOrder, "Students");


        //traditional way
//        Pageable pageable =  PageRequest.of(page, size, sortOrder.equals("ASC") ? Sort.by(Sort.Direction.ASC) : Sort.by(Sort.Direction.DESC,sort));
//
//        final Query query = new Query();
//     query.fields().include("id").include("name");
//        final List<Criteria> criteria = new ArrayList<>();
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
//
//
//        System.out.println("criteria----"+criteria.size());
//        if (!criteria.isEmpty())
//            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
//
//        List<Student>  students= mongoTemplate.find(query, Student.class);
//
//
//        return PageableExecutionUtils.getPage(
//                students,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Student.class));

    }



    //unwind with filter is done
    @GetMapping("/unwind/subjects")
    public ResponseEntity<?>  unwindSub(@RequestParam("subName") String subName){

        //filter using child filters only
        AggregationOperation match = Aggregation.match(Criteria.where("subjects").exists(true));
        MatchOperation matchStage1 = Aggregation.match(new Criteria("name").in(List.of("name1")));



        AggregationOperation unwind = Aggregation.unwind("subjects");

        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "subName");
        AggregationOperation replaceRoot = Aggregation.replaceRoot("subjects");

        MatchOperation matchStage = Aggregation.match(new Criteria("subName").in(List.of("subject1","subject3","subject2")));

        //groupby getting subName and count of it in list
        GroupOperation aggregationg=Aggregation.group("subName").count().as("total");

        //projecting fields
        ProjectionOperation projectionOperation= Aggregation.project("total").and("subName").previousOperation();


//        MatchOperation matchStage = Aggregation.match(Criteria.where("subName").in(List.of("subject1","subject2","subject3")).and("name") );
        Aggregation aggregation = Aggregation.newAggregation(match,matchStage1,unwind, replaceRoot,matchStage,aggregationg,projectionOperation,sort);
//

        AggregationResults<SubjectResult> groupResults
                = mongoTemplate.aggregate(aggregation, Student.class, SubjectResult.class);
        List<SubjectResult> result = groupResults.getMappedResults();





        List<Subject> subjects = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Student.class), Subject.class).getMappedResults();


        //filter using parent filter and child filter both
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("name").is("name1")),
//                Aggregation.unwind("subjects"),
//                Aggregation.match(Criteria.where("subjects.subName").in(List.of("subject1","subject2","subject3"))));
//
//        AggregationResults<Subject> results = mongoTemplate.aggregate(aggregation, Student.class, Subject.class);
//        List<Subject> albums = results.getMappedResults();

        return ResponseEntity.ok(result);

    }



    @GetMapping("/repo")
    public ResponseEntity<?>  getStudentsRepoMethod(){
        return ResponseEntity.ok(studentRepo.findByQuery("name","city1"));
    }



}
