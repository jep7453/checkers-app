# WebCheckers

An online Checkers game system built in Java 8 and Spark,
a web micro-framework.


## Team

- Chris Tremblay
- Scott Court
- Jonathan Pofcher
- Sree Jupudy
- Kesa Abbas Lnu


## Prerequisites

- Java 8
- Maven


## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:4567/`
4. Start a game and begin playing.

## How to load a custom board
**You must have google chrome installed and be running windows**
1. In `/etc` there is a file `load_custom_board.bat`
2. All test board files **MUST** be present in `src/main/resources/testboards`, 
there is a readme in how to structure a test board
3. Click the batch file and enter the name of the test board 
4. The batch file will then open two tabs in Chrome and start the webserver
5. Be sure to close the webserver before you try rerunning it

## Known bugs and disclaimers

### Resignation error
> Sometimes when you start a game and immediately resign it can throw
> a 500 server error because not all players have been properly refreshed and 
> updated 

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `ui`, `appl`, `model`
2. Open in your browser the file at `PROJECT_HOME/target/site/jacoco/{ui, appl, model}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Model tier tests open in your browser the file at `PROJECT_HOME/target/site/jacoco/model/index.html`
3. To view the Application tier tests open in your browser the file at `PROJECT_HOME/target/site/jacoco/appl/index.html`
4. To view the UI tier tests open in your browser the file at `PROJECT_HOME/target/site/jacoco/ui/index.html`


## How to generate the Design documentation PDF

1. Execute `mvn exec:exec@docs`
2. Note: this command will fail on a clean project without a `/target`
directory. Create the directory first if running after a `clean` operation
without any intervening commands that create the directory, such as compile.
3. The generated PDF will be in `PROJECT_HOME/target/` directory


## How to create a zipfile distribution of the source for the project

1. Execute `mvn exec:exec@zip`
2. The distribution zipfile will be in `PROJECT_HOME/target/WebCheckers.zip`

## License

MIT License

See LICENSE for details.
