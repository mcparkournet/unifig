plugins {
	`java-library`
	`java-test-fixtures`
}

subprojects {
	apply {
		plugin("java-library")
		plugin("java-test-fixtures")
	}

	repositories {
		jcenter()
	}

	dependencies {
		compileOnly("org.jetbrains:annotations:17.0.0")
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
		testCompileOnly("org.jetbrains:annotations:17.0.0")
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_12
		targetCompatibility = JavaVersion.VERSION_12
	}

	tasks {
		test {
			useJUnitPlatform()
		}
	}
}
