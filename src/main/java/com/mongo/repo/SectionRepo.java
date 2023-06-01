package com.mongo.repo;

import com.mongo.modal.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SectionRepo  extends MongoRepository<Section, String> {



}
