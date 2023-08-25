call mvn clean install -Pjmh
cd dmn-performance-signavio
java -cp target/benchmarks.jar com.gs.dmn.jmh.example_credit_decision.CreditDecisionSignavioBenchmarkTest
java -cp target/benchmarks.jar com.gs.dmn.jmh.example_credit_decision_mixed.CreditDecisionMixedSignavioBenchmarkTest
cd ..
cd dmn-performance-standard
java -cp target/benchmarks.jar com.gs.dmn.jmh.cl3_0004_lending.LendingStandardBenchmarkTest
cd ..
