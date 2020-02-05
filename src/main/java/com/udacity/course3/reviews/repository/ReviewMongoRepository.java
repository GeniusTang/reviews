package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.document.ReviewMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewMongo, String> {
}
