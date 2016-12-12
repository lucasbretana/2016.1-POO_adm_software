--------------------------------------------------------------------------------
# Final work for POO

# TODO
  - compare and sort the months with the incomings
 --------------------------------------------------------------------------------
# FOLDERS in root directory
  - BuildingManagement/
    - src/
      * the code it self, divide by the packages folders

    - bin/
      * only binaries, as .jar and .class files

    - doc/
      * .html files generate only by the JavaDoc

    - extra/
      * the files that are read and/or write by the application, divite by subfolders
      - Bills/
        * contains the Bills file, respecting the model

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

# For generate the jar file
  	$ jar cmvf META-INF/MANIFEST.MF app.jar  edu/poo/\*

--------------------------------------------------------------------------------
