SET /P _filepath= Absolute path to project:
cd %_filepath%
SET /P _inputname= Test board file: 
start chrome --incognito "http://127.0.0.20:4567"
start chrome --incognito "http://127.0.0.21:4567"
mvn -q clean compile exec:java -Dexec.mainClass="src.main.java.com.webcheckers.Application.java" -Dexec.args="%_inputname%"
