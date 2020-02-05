package com.udacity.course3.reviews;

import com.udacity.course3.reviews.document.ReviewMongo;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewsApplicationTests {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ReviewRepository reviewRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private ReviewMongoRepository reviewMongoRepo;

	@Test
	public void contextLoads() {
		assertNotNull(productRepo);
		assertNotNull(reviewRepo);
		assertNotNull(commentRepo);
	}

	@Test
	public void testProductRepo() {
		Product product1 = new Product("product");

		productRepo.save(product1);
		System.out.println(product1.getId());

		Optional<Product> optional = productRepo.findById(product1.getId());
		assertTrue(optional.isPresent());

		Product product2 = optional.get();

		assertEquals(product1.getName(), product2.getName());
	}

	@Test
	public void testReviewRepo() {
		Product product1 = new Product("product");
		productRepo.save(product1);

		Review review1 = new Review("review", product1);
		reviewRepo.save(review1);

		Optional<Review> optional = reviewRepo.findById(review1.getId());
		assertTrue(optional.isPresent());

		Review review2 = optional.get();

		assertEquals(review1.getText(), review2.getText());

		assertEquals(review1.getProduct().getId(), review2.getProduct().getId());
		assertEquals(review1.getProduct().getName(), review2.getProduct().getName());
	}

	@Test
	public void testCommentRepo() {
		Product product1 = new Product("product");
		productRepo.save(product1);

		Review review1 = new Review("review", product1);
		reviewRepo.save(review1);

		Comment comment1 = new Comment("comment", review1);
		commentRepo.save(comment1);

		Optional<Comment> optional = commentRepo.findById(comment1.getId());
		assertTrue(optional.isPresent());

		Comment comment2 = optional.get();

		assertEquals(comment1.getText(), comment2.getText());

		assertEquals(comment2.getReview().getId(), comment2.getReview().getId());
		assertEquals(comment1.getReview().getText(), comment2.getReview().getText());
	}

	@Test
	public void testReviewMongoRepo() {
		Product product1 = new Product("product");
		productRepo.save(product1);

		Review review1 = new Review("review", product1);
		reviewRepo.save(review1);

		ReviewMongo reviewMongo1 = new ReviewMongo();
		reviewMongo1.setProduct(product1);
		reviewMongo1.setText(review1.getText());
		reviewMongo1.setId(review1.getId() + "");
		reviewMongoRepo.save(reviewMongo1);

		Optional<ReviewMongo> optional = reviewMongoRepo.findById(review1.getId() + "");
		assertTrue(optional.isPresent());

		ReviewMongo reviewMongo2 = optional.get();

		assertEquals(review1.getText(), reviewMongo2.getText());

		assertEquals(review1.getProduct().getId(), reviewMongo2.getProduct().getId());
		assertEquals(review1.getProduct().getName(), reviewMongo2.getProduct().getName());

	}
}