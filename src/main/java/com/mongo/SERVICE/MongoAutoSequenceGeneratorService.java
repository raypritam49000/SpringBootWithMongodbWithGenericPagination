package com.mongo.SERVICE;

import com.mongo.modal.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class MongoAutoSequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;


    public int getSequenceNumber(String sequenceNumber) {

        //get seq no.
        Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("id").is(sequenceNumber));

        //update seq no.
        Update update = new Update().inc("seq", 1);

        DbSequence sequence = mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true),
        DbSequence.class);

        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;


    }


}
