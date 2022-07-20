# --== CS400 File Header Information ==--
# Name: Allistair Mascarenhas   
# Email: anmascarenha@wisc.edu
# Team: DC
# TA: Yelun Bao
# Lecturer: Gary Dahl
# Notes to Grader: <optional extra notes>

run: FrontEnd BackEnd DataWranglers
	java CommandLineDriver

compile: FrontEnd BackEnd DataWranglers TestSuites

test: TestSuites
	java -jar junit5.jar -cp . --scan-classpath -n DataWranglerTestSuite
	java -jar junit5.jar -cp . --scan-classpath -n BackEndFrontEndTestSuite

clean:
	rm *.class

junit5.jar:
	wget http://pages.cs.wisc.edu/~cs400/junit5.jar

# Front End
FrontEnd: CommandLineDriver.class

CommandLineDriver.class:
	javac CommandLineDriver.java

# Back End
BackEnd: GraphADT.class CS400Graph.class PathFinder.class

GraphADT.class:
	javac GraphADT.java

CS400Graph.class:
	javac CS400Graph.java

PathFinder.class:
	javac PathFinder.java

# DataWranglers
DataWranglers: DataLoader.class Airport.class

DataLoader.class:
	javac DataLoader.java

Airport.class:
	javac Airport.java

# TestEngineers
TestSuites: junit5.jar DataWranglerTestSuite.class BackEndFrontEndTestSuite.class

DataWranglerTestSuite.class: junit5.jar DataWranglerTestSuite.java
	javac -cp .:junit5.jar DataWranglerTestSuite.java

BackEndFrontEndTestSuite.class: junit5.jar BackEndFrontEndTestSuite.java
	javac -cp .:junit5.jar BackEndFrontEndTestSuite.java

