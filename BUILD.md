# Building jDMN

### Build Process

* Run ```mvn clean install``` from command line
* or build inside your IDE using the maven plugin.

### Dealing with branches in Github

#### When committing

Add the main branch as the upstream to your fork

```git remote add upstream https://github.com/goldmansachs/jdmn.git```

We request that pull requests are squashed into one commit before the pull request. Use these commands to do that
```
git rebase -i HEAD~3
git push -f
```

Finally, do a pull request in Github.

#### When pulling changes from upstream

```
git pull --rebase upstream master
git push -f
```
