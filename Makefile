MVN=mvn
PANDOC=pandoc

# Specify a board to use. Assumes BOARD is at
# src/main/resources/testboards/BOARD.txt
ifdef BOARD
EXEC_ARGS+= -Dexec.mainClass="src.main.java.com.webcheckers.Application.java" -Dexec.args="$(BOARD).txt"
endif

.PHONY: run build build-test clean test test-ui test-appl test-model

run:
	$(MVN) compile exec:java $(EXEC_ARGS)

build:
	$(MVN) compile

build-test:
	$(MVN) test-compile

clean:
	$(MVN) -q clean

test: test-ui test-appl test-model

test-ui: build-test
	$(MVN) surefire:test@ui

test-appl: build-test
	$(MVN) surefire:test@appl

test-model: build-test
	$(MVN) surefire:test@model

docs/DesignDoc.pdf: docs/DesignDoc.md
	cd docs; $(PANDOC) DesignDoc.md -o DesignDoc.pdf

