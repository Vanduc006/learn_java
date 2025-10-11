package com.example.projectY;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

// @Target(ElementType.TYPE)
// @Retention(RetentionPolicy.RUNTIME)
// @SpringBootTest

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test ")
@SpringBootTest
public @interface IntegrationTest {
    
}
