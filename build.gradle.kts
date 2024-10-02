plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.netflix.dgs.codegen") version "6.2.1"
}

group = "yummygyudon.java-example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["netflixDgsVersion"] = "9.1.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
	testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

tasks.generateJava {
	schemaPaths.add("${projectDir}/src/main/resources/graphql-client")
	packageName = "yummygyudon.java_example.graphql.codegen"
	generateClient = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}
