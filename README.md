# Vowels Processing

Functional requirements
 ```bash
    Given the supplied text file (INPUT.TXT), write a Java program that loads all words and determines the average number of vowels per word grouped by: 
    set of vowels present in a word and length of the word. Result should be written to the output file (OUTPUT.TXT).
    
    Assumptions:
    1.      Capital and lower case letter should be treated as the same.
    2.      Input contains only English words and punctuation, words are separated by at least one whitespace character.
    3.      Input is small enough to fit in memory.
    4.      If any additional assumptions about input\output are made, they should be stated in comments/email.
      
    Example:
    
    INPUT
    
    Platon made bamboo boats.
    
    OUTPUT
    
    ({a, o}, 6) -> 2.5
    
    ({a, o}, 5) -> 2
    
    ({a, e}, 4) -> 2
    
    Rules:
    
    1.     This exercise should be treated as a regular work task (i.e. apply usual professional dev practices: OOP, SOLID, unit tests, project structure).
    2.     The candidate must work independently, no collaboration is permitted.
    3.     The candidate may conduct research either online or offline as they see fit, in order to complete the task. Please do not copy a solution from Internet.
    4.     Task intended to be done in a 4 hours. Please send us version which you count as done with
    5.     Please note, that structure of the code and unit tests are the most important in this coding task
    
    Good to have:
    
    1.     Please provide solution as maven project if it is possible.
 ```   
## Technologies(Frameworks and Plugins)
This project I have developed using Intellij IDE and these technologies and frameworks:

	-Java 8
    -Springboot,
    -Maven,
    -Lombok,
    -Actuator.
    
## About project	
	This project is formed per one SpringBoot Application.
        Notes about application:
           1.     This application is configured to process the file in this path: /opt/file/input and to write the file response in /opt/file/output, before run the application you need to create and give permissions in these paths or you can change the paths in this class:  FileService.java in these constants: INPUT_PATH and OUT_PATH;
           2.     You can check if the application is running with this url: localhost:8090/vowels-processing/health;
           3.     This application has unit tests in this path: vowels-processing/src/test/java/com/processfile/vowelsprocessing/service;
           4.     During the application build the unit test will be run;
           5.     This application has log file in this path /opt/files, you can change it in application.properties file in this property: logging.file;
           6.     The application just will read a file if the file was created or pasted in the path after the application was started;
           7.      After the file is processed, the source file will be deleted.

## Run 
 Inside Intellij IDE:
 ```bash
    -Import the project;
    -Check Enable annotation processing field in Intellij options
    -Start the application running this class VowelsProcessingApplication.
 ```

 Without IDE:
 ```bash
	-Build the application (mvn clean install), the file vowels-processing-1.0.0.jar ,
    - Run the file generated (vowels-processing-1.0.0.jar) after maven build with: java -jar vowels-processing-1.0.0.jar.   
```

If you have questions, please feel free to contact me.