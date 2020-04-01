plugins {
	kotlin("jvm") version "1.3.71"
}

dependencies {
	api(project(":unifig-core"))
	implementation(kotlin("stdlib-jdk8", "1.3.71"))
}

tasks {
	compileKotlin {
		kotlinOptions {
			apiVersion = "1.3"
			languageVersion = "1.3"
			jvmTarget = "11"
		}
	}
}
