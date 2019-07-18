plugins {
	`java-library`
}

repositories {
	jcenter()
}

dependencies {
	compileOnly("org.jetbrains:annotations:17.0.0")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}
