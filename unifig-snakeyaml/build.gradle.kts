dependencies {
	api(project(":unifig-core"))
	implementation("org.snakeyaml:snakeyaml-engine:2.1")
	testImplementation(testFixtures(project(":unifig-core")))
}
