package com.ai360.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ENTRY POINT — Spring Boot Application
 * =======================================
 * @SpringBootApplication = 3 annotation gop lai:
 *   @Configuration     : class nay la nguon cau hinh Bean
 *   @EnableAutoConfiguration : tu dong cau hinh Spring dua tren classpath
 *   @ComponentScan     : quet toan bo package tim @Component, @Service, @Repository...
 *
 * Chay bang: mvn spring-boot:run
 * Hoac build jar: mvn package -> java -jar target/ecommerce-api-1.0.0.jar
 */
@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
