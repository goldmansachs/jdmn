call mvn clean install -Pjmh
cd dmn-performance-standard
java -cp target/benchmarks.jar com.gs.dmn.jmh.compiler.FileTestExecutionBenchmarkTest
java -cp target/benchmarks.jar com.gs.dmn.jmh.compiler.StringTestExecutionBenchmarkTest
cd ..
