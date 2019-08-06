repositories {
	maven("https://papermc.io/repo/repository/maven-public")
}

dependencies {
	api(project(":unifig-core"))
	compileOnly("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
	testImplementation("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
}
