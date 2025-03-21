CREATE TABLE `t_orders`
(
    `id` bigint NOT NULL AUTO_INCREMENT,
    `order_number` varchar(255) DEFAULT NULL,
    `sku_code` varchar(255) DEFAULT NULL,
    `quantity` int DEFAULT NULL,
    `price` decimal(19,2) DEFAULT NULL,
    PRIMARY KEY (`id`)
);