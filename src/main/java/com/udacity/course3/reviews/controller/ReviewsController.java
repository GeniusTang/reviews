package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.document.ReviewMongo;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here

    @Autowired
    ProductRepository productRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ReviewMongoRepository reviewMongoRepo;
    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId,
                                                         @RequestBody Review review) {
        Optional<Product> optional = productRepo.findById(productId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        review.setProduct(optional.get());
        reviewRepo.save(review);

        ReviewMongo reviewMongo = new ReviewMongo();
        reviewMongo.setId(review.getId() + "");
        reviewMongo.setText(review.getText());
        reviewMongo.setProduct(optional.get());
        reviewMongoRepo.save(reviewMongo);
        return ResponseEntity.ok(review);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewMongo>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> optional = productRepo.findById(productId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Review> reviews = reviewRepo.findAllByProduct(new Product(productId));
        List<ReviewMongo> reviewMongos = new ArrayList<>();
        for (Review review : reviews) {
            reviewMongos.add(reviewMongoRepo.findById(review.getId() + "").get());
        }
        return ResponseEntity.ok(reviewMongos);
    }
}