AGENTS for jdmn
=================

Purpose
-------
This document lists the automated subagents and guidance for using them when working with this repository. The project is a Maven-based Java project targeting Java 17. The subagents described here are intended to help with planning multi-step tasks and remediating CVEs in project dependencies.

Available subagents
-------------------
1. Plan
   - Purpose: Researches and outlines multi-step plans for non-trivial tasks (design, refactors, test plans, release plans, migration strategies, etc.).
   - When to use: Use the Plan agent whenever you need a clear step-by-step plan, risk assessment, or breakdown of a complex change before making code edits.
   - Example prompt: "Plan: create a migration plan to move modules to Java 17 and update Maven plugins, include required build/test steps and potential breaking changes."

2. CVE Remediator
   - Purpose: Detects and fixes security vulnerabilities (CVEs) in project dependencies across supported ecosystems while preserving a working build.
   - When to use: Use for dependency security audits and automated fixes. It will attempt to update vulnerable dependencies, run the build and tests, and produce a patch or PR that fixes the issues.
   - Example prompt: "CVE Remediator: scan the repository for CVEs and propose fixes that keep the project buildable on Java 17 and Maven."

Guidance and best practices
---------------------------
- Environment expectations
  - JDK: Java 17 (builds and tests in CI expect Java 17)
  - Build tool: Apache Maven (root pom.xml is present)
  - Typical commands:

    mvn -v
    mvn -DskipTests package
    mvn test

- Choosing an agent
  - Use "Plan" first for large or risky changes. The Plan agent will provide a checklist and suggested sequence of edits and tests.
  - Use "CVE Remediator" when you need an automated, conservative approach to dependency upgrades and CVE fixes. Run it on a branch and review its proposed changes.

- How to interact (recommended prompts)
  - Ask for a plan: "Plan: propose a step-by-step plan to add JUnit 5 tests for module X and migrate existing tests from JUnit 4. Include validation steps."
  - Ask for CVE remediation: "CVE Remediator: scan and fix vulnerable Maven dependencies, prefer non-breaking upgrades and ensure mvn -DskipTests package succeeds."

Examples and expected outputs
-----------------------------
- Plan
  - Output: a numbered plan with concrete steps (e.g., update pom.xml plugin versions, run tests, refactor package names), estimated effort, and safety notes.

- CVE Remediator
  - Output: a list of discovered CVEs, proposed dependency upgrades, and a patch/PR that keeps the build working. The agent will run the build and report success/failure.

Safety and review
-----------------
- Always review changes produced by any automated agent before merging. Automated dependency upgrades can introduce behavioral changes.
- Run the full test suite locally and in CI (mvn test) before accepting dependency or code changes.

Extending and adding agents
---------------------------
This repository maintains the list of supported agents and guidelines here. To add a new agent, document its purpose, scope, and example prompts in this file and ensure it integrates with the workflow (e.g., accepts repository access and performs safe changes).

Contact / Notes
----------------
- The agents are tools to assist developers. They should be used as collaborators rather than authoritative sources.
- If an agent suggests a change that breaks the build or tests, revert and request a revised plan.

License and attribution
-----------------------
- This file is part of the jdmn repository. Follow the project license (see LICENSE.txt) when applying agent-produced changes.



