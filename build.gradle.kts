import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.2.2.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("kapt") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

allprojects {
	repositories {
		jcenter()
	}
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	group = "io.spring.demo"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_1_8

	dependencies {

		implementation("org.springframework.boot:spring-boot-starter")
		//implementation("org.springframework.boot:spring-boot-starter-data-jpa")

		// Lombok
		annotationProcessor ("org.projectlombok:lombok:1.18.4")
		implementation      ("org.projectlombok:lombok:1.18.4")

		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		/*testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}*/

	}

	repositories {
		mavenCentral()
	}


	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}
}

project("module-domain") {
	dependencies {
		implementation("com.h2database:h2")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project("module-api") {

	dependencies {
		implementation(project(":module-domain"))
		implementation("org.springframework.boot:spring-boot-starter-web")

		// OAuth2 라이브러리 관련
		implementation ("org.springframework.boot:spring-boot-starter-security:2.1.1.RELEASE")
		implementation ("org.springframework.security.oauth:spring-security-oauth2:2.1.1.RELEASE")
		implementation ("org.springframework.security:spring-security-jwt:1.0.9.RELEASE")
	}
}


project("client-web") {

	dependencies {
		// implementation(project(":module-domain"))
		// implementation(project(":module-api"))
		implementation("pl.allegro.tech.boot:handlebars-spring-boot-starter:0.3.0")
	}
}

project("oauth") {

	dependencies {

		implementation(project(":module-domain"))
		implementation("org.springframework.boot:spring-boot-starter-web")

		// OAuth2 라이브러리 관련
		implementation ("org.springframework.security.oauth:spring-security-oauth2:2.3.3.RELEASE")

	}
}



