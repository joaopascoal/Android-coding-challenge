# Build application
There are 3 build variants in the project.
- devDebug - run application in debug mode
- devRelease - run application in release mode
- junitDebug - contains all Espresso and jUnit tests to run, just select any espresso/junit test and run it

## Application main flow
- Just one screen which shows a basic greeting to user.
- Application will find out user's current location
- After this, 'voice' button will be enabled.
- Clicking on 'voice' button will trigger Android Speech Recognizer. Just speak a keyword and WeatherApp will return to user the information desired.
- There are, at the moment, three keywords implemented: "weather", "temperature" and "wind"
- Multiple languages supported: english, portuguese and french
