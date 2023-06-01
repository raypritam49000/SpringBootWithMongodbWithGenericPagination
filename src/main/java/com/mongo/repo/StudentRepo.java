package com.mongo.repo;

import com.mongo.modal.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface StudentRepo extends MongoRepository<Student, String> {



//    @Query("{name :?0 ,city:?1 }")
//    @Query("{$or :[{name: ?0},{city: ?1}]}")

    @Query("{ name : { $regex : ?0 } }")
    List<Student> findByQuery(String name, String city);


}
