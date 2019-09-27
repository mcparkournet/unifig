dependencies {
	api(project(":unifig-basic"))
	implementation("com.moandjiezana.toml:toml4j:0.7.2")
	testImplementation(testFixtures(project(":unifig-basic")))
}
