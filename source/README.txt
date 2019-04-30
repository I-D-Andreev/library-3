How to rerun the project (Intellij).
That is what I have been following to get it to load from the ZIP file from scratch.

1. Open Intellij/ Open Project/ SEGroup3-A2
2. Right click the source folder -> mark directory as -> sources root
3. Right click SEGroup3-A2 in the Project View (left side) -> New -> Directory (might name it "out")
4. Open File (Top menu) / Project Structure/ Project  (ctrl+alt+shift+s)
	- select JDK - 1.8 (our is 1.8.0_45)
	- select Project language level - 8
	- set project compiler output to the "out" folder (or another if you have named it differently)
5. Add the libraries to the project. Extract the controlsfx-8.40.14.zip (in the source folder) if not already extracted.
	File -> Project Structure -> Libraries -> + -> controlsfx-8.40.14.jar
	File -> Project Structure -> Libraries -> + -> mail-1.4.jar
    File -> Project Structure -> Libraries -> + -> activation-1.1.1.jar


6. Click source/resources/ Main.java 
7. From the top menu, select Run -> Run... and choose Main


You can now enter in the library with librarian "librarian1" or user "sianspike".

* The program is given to you in the state that it was when the video started (not after the video ended). It only has the
most basic data inside.
** Obviously, the images will not work because of the absolute path they are stored with (also the dummy data has no images)
You can set them on your own and then they will work.



// POTENTIAL PROBLEM
It has not occurred on our side when saving/testing but there might be a chance that after being built and run,
the project will not open with File Reading exception. This would mean the 4 .ser (serialized) files 
have changed - any change whatsoever will break it. In order to fix this, you will need to rebuild them,
and there are some methods left just for this purpose.

1. Go to Main class, comment out the launch(args) line and uncomment the New Library and library.save lines.
2. Go to UserManager and in its constructor it is calling selfPopulate(). Change this to selfPopulate1().
2. Go to ResourceManager and in its constructor it is calling selfPopulate(). Change this to selfPopulate1().
4. Go to SaveStaticVariables and in its constructor it is calling selfPopulate(). Change this to selfPopulate1().
5. Go to EventManager and in its constructor it is calling selfPopulate(). Change this to selfPopulate1().
6. Run the main method. This will create a new Library which is loading from dummy data and the l.save() will overwrite the files.

7. Change back everything that you changed - namely get launch(args) back, comment out the library contructor in main and
the l.save, change UserManager, ResourceManager, SaveStaticVariables and EventManager back to selfPopulate(). 