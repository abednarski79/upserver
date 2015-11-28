# upserver


## Building the app:
> mvn package

## Setting the app:
Copy upserver.properties.tempalte to upserver.properties and update with your details
Copy log4j.properties.template to log4j.properties and update with your details 
(especially property log4j.appender.file.File)

## Running the app from command line:
> ./run.sh

## Running the app from crontab:
To run app from crontab edit run.sh and set all the path to full paths including the path to java binary