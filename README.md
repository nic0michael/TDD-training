## Some simple rules to follow When doing TDD with Micro-Services:
1. Keep your Unit Tests Simple KEEP UNIT TESTS SIMPLE SO THAT I CAN UNDERSTAND THEM
2. Always remember the "Single Responsibility Rule" of SOLID principles 
3. Let each Class do one thing only 
4. AND each method also do one thing only
5. In order for me to understand how to use the classes in a project look at the Unit Tests

## Working with Spring Boot Micro-services do these things:
1. Let the Controller Classes do not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET
2. Let the Service Classes do not have DATA CHANGING LOGIC that is RETURN WHAT YOU GET
3. DECOUPLE the Controller Classes from the Service Classes using BusinessLogic Helper Classes
4. IF YOU DO THIS THERE IS NO NEED TO TEST Controller Classes and Service Classes
5. Make the Service Classes implement Interfaces
6. We can now Mock the Service classes