all: parser.java Symbols.java Yylex.java

parser.java: appetizer3.cup
	java -jar ../../lib/java-cup-11a.jar -expect 30 -parser parser -symbols Symbols -interface < appetizer3.cup

Symbols.java: appetizer3.cup
	java -jar ../../lib/java-cup-11a.jar -expect 30 -parser parser -symbols Symbols -interface < appetizer3.cup

Yylex.java: appetizer2.flex
	java -cp ../../lib/JFlex.jar JFlex.Main appetizer2.flex

clean:
	rm -f parser.java Symbols.java Yylex.java
