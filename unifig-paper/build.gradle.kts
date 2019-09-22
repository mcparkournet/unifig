repositories {
	maven("https://papermc.io/repo/repository/maven-public") {
		content {
			includeGroup("com.destroystokyo.paper")
			includeGroup("net.md-5")
		}
	}
}

dependencies {
	api(project(":unifig-basic"))
	compileOnly("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
	testImplementation(testFixtures(project(":unifig-basic")))
	testImplementation("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
}
