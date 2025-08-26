# Test Driven Development
## Some simple rules to follow When doing TDD:
1. Keep your Unit Tests Simple **KEEP UNIT TESTS SIMPLE SO THAT ANYONE CAN UNDERSTAND THEM**
2. Always remember the "**Single Responsibility Rule" of SOLID principles** 
3. Let each Class do one thing only and do it well
4. Let each method do one thing only and do it well
5. For one to understand how to use the classes in a project just look at the Unit Tests

## Working with Spring Boot Micro-services do these things:
1. Let the Controller Classes **not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET**
2. Let the Service Classes **not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET**
3. DECOUPLE the Controller Classes from the Service Classes **using BusinessLogicService (Helper) Classes**
4. **IF YOU DO THIS THERE IS NO NEED TO TEST** Controller Classes and Service Classes
5. Make the Service Classes implement Interfaces
6. We can now Mock the Service classes

## Project Business Rules
if income < 2000)  calculate tax as 10% of income \
if income < 10000)  calculate tax as 75% of income \
if income < 50000)  calculate tax as 80% of income \
if income > 50000)  calculate tax as 90% of income 
CustomerAdapter
if age > 70 calculate tax as 75% of income tax \
if age > 65 calculate tax as 80% of income tax 

If the Gender is Female calculate tax as 80% of income tax

We have provided you with a class: **CustomerAdapter** that converts **CustomerDto** to **CustomerRequest** to use in your solution \

We have decided that the Class **CustomerBusinessProcessor** should be renamed to ***CustomerBusinessService*** 

Please build the TaxCalculator class using TDD

## The TDD Steps
1. For the first Business Rule
2. Write a failing unit test
3. Write enough code to pass the test
4. Refactor the code making sure it passes the test
5. repeat this process till all Business Rules are working in code 

## Get the empty Project you need here:
You need checkout the development branch to do the exercise: \
by using this command: \
**git clone https://github.com/nic0michael/TDD-training.git -b development**

## Unit Tests using schizophrenic(orBehavioral) mock classes
Here we demonstrate how we do testing with MJock Classes:
**[https://www.youtube.com/watch?v=zubPvawwXyU](https://www.youtube.com/watch?v=zubPvawwXyU)** \
**Prefer to use Mock Classes, PLEASE DON'T USE MOCKITO** \
In this Project, we coined the phrase: schizophrenic mock classes

## Video Published
**[https://www.youtube.com/watch?v=zubPvawwXyU](https://www.youtube.com/watch?v=zubPvawwXyU)**




