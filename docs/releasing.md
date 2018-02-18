# Releasing jDMN

Run the maven release plugin to prepare the build. This will invoke the tests.

```mvn release:clean release:prepare```

Once succeeded, perform the release to upload the artifacts to maven central. Watch out for the GPG keychain password prompt.

```mvn release:perform```

(We document this here only for knowledge purposes)

Follow the instructions on the [Sonatype OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html).

Include the drilldown page that [deploys via Apache Maven](http://central.sonatype.org/pages/apache-maven.html).
* We do not follow the steps on "Nexus Staging Maven Plugin for Deployment and Release"
* Instead, we use the maven-release-plugin: see the "Performing a Release Deployment with the Maven Release Plugin" section

Note the "maven-release-plugin" declaration in the parent pom here; see the references to the _release_ profile and the
release profile definition itself.
