--------------------------------------------------------------------------------
# Final work for POO

# TODO
 --------------------------------------------------------------------------------
# FOLDERS in root directory
  - src
    * the code it self, divide by the packages folders

  - bin
    * only binaries, as .jar and .class files

  - doc
    * .html files generate only by the JavaDoc

  - TestesGerais
    * code from general tests

  - LICENSE
    * github file about the license of the project

  - README.md
    * this file

--------------------------------------------------------------------------------
# For compiling the java file having dependency on a jar
	$ javac -cp bin/lib/some_lib.jar:bin/lib/other_lib.jar src/*/*.java -d ./

# For executing the class file
	$ java -cp .:lib/some_lib.jar:.:lib/other_lib.jar tests.Main

# For generate the doc files
	$ javadoc src/*/.java -d doc
--------------------------------------------------------------------------------
