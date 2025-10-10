plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // ⚙️ Web MVC / REST API
    implementation("org.springframework.boot:spring-boot-starter-web")

    // 📦 Validation (Bean Validation API)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // 🌱 Thymeleaf (template engine)
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // 🔐 Security (login, JWT, form auth)
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // 📊 Actuator (theo dõi health, metrics)
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // 🔄 Hot reload
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // 🧪 Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
