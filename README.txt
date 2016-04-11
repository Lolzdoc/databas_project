How to run the program:
1. Unzip the provided .zip to a path of your choice.
2. cd into the "Krusty_Project" folder using the Terminal.
3. execute the following command: java -jar databas_project.jar.
	- if the system does not start, there is a problem with the Internet Connection.
4. Use the program with the help of the provided manual.

** If there is a lack of raw materials to create a pallet do the following:
1. cd into the "databas_project" folder using the Terminal.
2. Execute the following commands:
	- mysql -u db98 -h puccini.cs.lth.se -p db98
	  Enter Password: znw031qr
	- source initDB.sql
3. Try creating the pallet again.

** GUI PROBLEMS: 
If the list of recipes is empty, the tables required for the program have probably not been initialized. Do the following: 
1. cd into the "Krusty_Project" folder using the Terminal-
2. Run the following commands:
	- mysql -u db98 -h puccini.cs.lth.se -p db98
	  Enter Password: znw031qr
	- source initDB.sql

If the list of pallets is empty, no pallets have been created. 

