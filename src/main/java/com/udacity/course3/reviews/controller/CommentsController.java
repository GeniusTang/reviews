package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.document.ReviewMongo;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    ReviewMongoRepository reviewMongoRepo;
    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId,
                                                    @RequestBody Comment comment) {
        Optional<Review> optional = reviewRepo.findById(reviewId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comment.setReview(optional.get());
        commentRepo.save(comment);

        ReviewMongo reviewMongo = reviewMongoRepo.findById(reviewId + "").get();
        reviewMongo.getComments().add(comment.getText());
        reviewMongoRepo.save(reviewMongo);
        return ResponseEntity.ok(comment);
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> optional = reviewRepo.findById(reviewId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(commentRepo.findAllByReview(new Review(reviewId)));
    }
}