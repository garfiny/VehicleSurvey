#Vehicle Survey Coding Challenge
=============

## Why I chose this?

I like both challenges, they all are very interesting. 

* Firstly, for 1-800-CODING-CHALLENGE, I recently done a similar one so that I want to try a different one.

* And another reason is that I like data-analysis type of projects, and I've been learning big data and data analysis technologies recently. So, for this reason I picked up the Vehicle Survey challenge.

## Design

The Vehicle Survey Challenge contains 5 main functions.

* Data loading, loading data from the given file

* Data Manipulating
	after load data we need to modelize the data, make them manipulatable
	I create new list type with customized operation methods, like groupBy() methods

* Data analysis services
	for doing calculation and actual data computing, each service has its own implementation with same interface

* Data analysis report
	Those are analysis result holders, which store analysis result and report metadata

* Report layout and export
	I only implemented printing the report to the console. All the reports have a table layout to be printed on the screen.

## Implementation

According to the requirements. I only used standard JDK(1.7) libraries. For unit testing, I only used JUnit4.

The code implemented in TDD, and used STS as IDE. 

## Building and Running

The project contains basic maven configuration. 

For building the project, run: 
	mvn install

For running the app, run:
	mvn exec:java -Dexec.mainClass="vehiclesurvey.VehicleSurvey"

