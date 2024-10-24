## Some simple rules to follow When doing TDD with Micro-Services:
1. Keep your Unit Tests Simple **KEEP UNIT TESTS SIMPLE SO THAT ANYONE CAN UNDERSTAND THEM**
2. Always remember the "**Single Responsibility Rule" of SOLID principles** 
3. Let each Class do one thing only and deas it well
4. AND each method also does one thing only and does it well
5. For one to understand how to use the classes in a project then look at the Unit Tests

## Working with Spring Boot Micro-services do these things:
1. Let the Controller Classes **not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET**
2. Let the Service Classes **not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET**
3. DECOUPLE the Controller Classes from the Service Classes **using BusinessLogic Helper Classes**
4. **IF YOU DO THIS THERE IS NO NEED TO TEST** Controller Classes and Service Classes
5. Make the Service Classes implement Interfaces
6. We can now Mock the Service classes

## Project Specifications
if income < 2000)  calculate tax as 10% of income \
if income < 10000)  calculate tax as 75% of income \
if income < 50000)  calculate tax as 80% of income \
if income > 50000)  calculate tax as 90% of income 

if age > 70 calculate tax as 75% of income tax \
if age > 65 calculate tax as 80% of income tax 

If the Gender is Female calculate tax as 80% of income tax
