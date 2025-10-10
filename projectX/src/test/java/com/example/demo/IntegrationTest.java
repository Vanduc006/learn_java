package com.example.demo;
import java.lang.annotation.ElementType;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@ActiveProfiles("test ")
public @interface IntegrationTest {
    // reuse able with custom annotation
}
