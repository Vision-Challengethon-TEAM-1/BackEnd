plugins {
	id("java")
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.vision-hackathon"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
	maven(url = "https://repo.spring.io/milestone")
}

extra["springAiVersion"] = "1.0.0-M3"

dependencies {
	// Spring Boot Starters
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Spring AI
	implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")

	// Security
	implementation("org.springframework.security:spring-security-oauth2-client")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

	// Persistence
	implementation("com.mysql:mysql-connector-j")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Docker Compose
	//	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	//	developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// GCP Cloud
	implementation ("org.springframework.cloud:spring-cloud-gcp-starter:1.2.5.RELEASE")
	implementation ("org.springframework.cloud:spring-cloud-gcp-storage:1.2.5.RELEASE")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
		mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
