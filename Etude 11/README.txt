Etude 11

Ben Stacey - 2157359
Liam Flynn - 4239344

Resources:
- https://refactoring.guru/design-patterns/singleton/java/example#lang-features 

The design pattern we decide to use was singleton. Singleton is a creational design patter, which ensures 
that ensures that one object of its kind exists and provides a single point of access for any other code.
Singleton has almost the same pros and cons as global variables.

Pros of singleton pattern:
- Lazy Instantiation
    If you opt for the lazy instantiation paradigm, then the singleton variable will
    not get memory until the property or function designated to return the reference is first called. 
    This type of instantiation is very helpful if your singleton class is resource intense.

- Static Initialization
    In static initialization, memory is allocated to the variable at the time it is declared. 
    The instance creation takes place behind the scenes when any of the member singleton classes 
    is accessed for the first time. The main advantage of this type of implementation is that the 
    CLR automatically takes care of race conditions I explained in lazy instantiation. 
    We don't have to use any special synchronization constructs here. There are no significant 
    code changes in the singleton implementation when you switch from lazy instantiation to 
    static initialization. The only change is that the object creation part is moved to the 
    place where we are declaring the variable.

Cons of singleton pattern:
- They deviate from the Single Responsibility Principle. A singleton class has the responsibility to 
  create an instance of itself along with other business responsibilities. However, this issue can be 
  solved by delegating the creation part to a factory object.
- Singleton classes cannot be sub classed.
- Singletons can hide dependencies. One of the features of an efficient system architecture is minimizing 
  dependencies between classes. This will in turn help you while conducting unit tests and while isolating any 
  part of the program to a separate assembly. A singleton will make you sacrifice this feature in your application. 
  Since the object creation part is invisible to us, we cannot expect the singleton constructor to accept any 
  parameters. This setback may look unimportant on the first glance but as the software complexity increases, 
  it will limit the flexibility of the program.


How the program works: 
The user uses the terminal to run the VideoApp class which makes a single instance of the VideoManager class which 
you can then use the methods to add/delete video clips from the array list which stores them. The videoManager instance
is static to make sure only one instance can exist. The VideoManager class is also final. 