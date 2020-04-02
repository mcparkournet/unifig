dependencies {
	api(project(":unifig-core"))
	implementation("org.yaml:snakeyaml:1.26")
	testImplementation(testFixtures(project(":unifig-core")))
}
