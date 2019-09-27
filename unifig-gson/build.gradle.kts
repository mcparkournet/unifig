dependencies {
	api(project(":unifig-basic"))
	implementation("com.google.code.gson:gson:2.8.5")
	testImplementation(testFixtures(project(":unifig-basic")))
}
