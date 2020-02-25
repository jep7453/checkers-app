MVN=mvn

run:
	$(MVN) compile exec:java

build:
	$(MVN) compile

build-test:
	$(MVN) test-compile

test: test-ui test-appl test-model

test-ui: build-test
	$(MVN) surefire:test@ui

test-appl: build-test
	$(MVN) surefire:test@appl

test-model: build-test
	$(MVN) surefire:test@model

