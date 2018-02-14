# Building jDMN

Requirements:

### Java 8 or higher

* [Oracle JDK Download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [OpenJDK Download](http://openjdk.java.net/install/)


### Maven 3.2.5 or higher

[Download](https://maven.apache.org/download.cgi)


### IDE

This is a Maven based project that can be imported both in Intellij and Eclipse, hence the IDE files are ignored from version control. All you have to is to import the project in your IDE.

### Build Process

* Run ```mvn clean install``` from command line
* or build inside your IDE using the maven plugin.

### Releasing jDMN

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

### Dealing with branches in Github

#### When committing

Add the main branch as the upstream to your fork
```git remote add upstream https://github.com/goldmansachs/jdmn.git```

We request that pull requests are squashed into one commit before the pull request. Use these commands to do that
```
git rebase -i HEAD~3
git push -f
```

Finally, do a pull reqeust in Github.


#### When pulling changes from upstream

```
git pull --rebase upstream master
git push -f
```

### Developer Guide

Please visit the [Developer Guide](https://goldmansachs.github.io/jDMN/README.md) for notes on
developing with jDMN.

The guide includes:
* Generating Java from DMN
* Running the generated code
* Existing dialects
* Configuration
