CREATE TABLE `product` (
	`product_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE `review` (
	`review_id` int NOT NULL AUTO_INCREMENT,
    `review_text` varchar(10000) NULL,
    `product_id` int NOT NULL,
    PRIMARY KEY (`review_id`),
	CONSTRAINT `fk_review_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `comment` (
	`comment_id` int NOT NULL AUTO_INCREMENT,
    `comment_text` varchar(10000) NULL,
    `review_id` int NOT NULL,
    PRIMARY KEY (`comment_id`),
	CONSTRAINT `fk_comment_review` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
);