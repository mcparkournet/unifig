dependencies {
	api(project(":unifig-basic"))
	implementation("org.yaml:snakeyaml:1.25")
	testImplementation(testFixtures(project(":unifig-basic")))
}
