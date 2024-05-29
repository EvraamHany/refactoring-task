# Refactor steps

  - update project structure so i produced model, service, utils and test packages to appling modularity over project structure
  - used enum class for movies type instead of string to make it more generalized over the project 
  - rename RentalInfo class to be a service one to be like a service layer as it contains a business logic
  - decoupling business logic inside rentalInfo "statement" by separating methods
  - change "statement" method's name to be a verb to follow the naming convention of method definition
  - create a stand-alone MovieService to be responsible for any movies change (single responsibility of each class)
  - make MovieService class a singleton class by applying synchronized code block to looks like a bean
  - extract test from main class to be a stand-alone class
  - make data preparation handled from static methods in utils class instead of main class 
  - add exception handling in movieRental service
  - add static fixed values in a separate constants interface to looks like a configuration file


# Refactoring Java

The code creates an information slip about movie rentals.
Rewrite and improve the code after your own liking.

Think: you are responsible for the solution, this is a solution you will have to put your name on.


## Handing in the assignment

Reason how you have been thinking and the decisions you took. 
You can hand in the result any way you feel (git patch, pull-request or ZIP-file).
Note: the Git history must be included.


## To run the test:

```
javac src/*.java src/model/*.java src/service/*.java src/utils/*.java src/test/*.java
java -cp src Main
