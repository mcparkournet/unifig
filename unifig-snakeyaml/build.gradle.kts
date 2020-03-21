dependencies {
	api(project(":unifig-basic"))
	implementation("org.yaml:snakeyaml:1.26")
	testImplementation(testFixtures(project(":unifig-basic")))
}
