/* CPSC 50100 - Programming Fundamentals 
 * Bhavana Kota
 * Summer II 2020
 * Programming Assignment 3
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NearestNeighbor {

	public static void main(String[] args) {

		// Printing header
		System.out.print("Programming Fundamentals\n" + "Name: Bhavana Kota\n" + "PROGRAMMING ASSIGNMENT 3\n");

		System.out.println("\n");

		// Invoking scanner to start
		Scanner scan = new Scanner(System.in);

		// Invoking training file. iris-training-data.csv should be entered
		System.out.print("Enter the name of the training file: ");
		File trainingFile = new File(scan.next());

		// Invoking testing file. iris-testing-data.csv should be entered
		System.out.print("Enter the name of the testing file: ");
		File testingFile = new File(scan.next());

		// Creating arrays
		double[][] testingData = new double[75][4];
		double[][] trainingData = new double[75][4];
		String[] testingClass = new String[75];
		String[] trainingClass = new String[75];

		// Creating the prediction array to hold the predicted values
		String[] predictions = new String[75];

		// Creating new Scanners here to scan for each data
		Scanner scanTestingData = null;
		try {
			scanTestingData = new Scanner(testingFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanTrainingData = null;
		try {
			scanTrainingData = new Scanner(trainingFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanTestingClass = null;
		try {
			scanTestingClass = new Scanner(testingFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanTrainingClass = null;
		try {
			scanTrainingClass = new Scanner(trainingFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Created the findClass method to copy information into the arrays
		testingClass = findClass(scanTestingClass, testingClass);
		trainingClass = findClass(scanTrainingClass, trainingClass);

		// Created the findData method to the copy the doubles into the arrays
		testingData = findData(scanTestingData, testingData);
		trainingData = findData(scanTrainingData, trainingData);

		// This for loop creates a new method nearestNeighbor. It runs through all the
		// training values and copies into the predictions array
		int start = 0;
		for (int i = 0; i < 75; i++) {
			start = nearestNeighbor(i, testingData, trainingData);
			predictions[i] = trainingClass[start];
		}

		// This method is created to hold all the prediction accuracy and prints these
		// results
		predictAccuracy(predictions, testingClass);
		scan.close();
	}

	private static String[] findClass(Scanner classScan, String[] allClass) {

		int start = 0;
		String row = " ";

		// This while loop first splits each line and then splits by 4 and inputs into
		// method findData.
		while (classScan.hasNextLine()) {
			row = classScan.nextLine();
			String[] a = row.split(",");
			allClass[start] = a[4];
			start++;
		}
		return allClass;
	}

	private static double[][] findData(Scanner scanData, double[][] allData) {

		// Creates the 2D array by the data sizes
		String[][] data = new String[75][4];
		int start = 0;
		String row;

		// This while loop takes the values and splits again. It then parses into a
		// double string to create a double value for each input.
		while (scanData.hasNextLine()) {
			row = scanData.nextLine();
			String[] a = row.split(",");

			for (int i = 0; i < 4; i++) {
				data[start][i] = a[i];
				allData[start][i] = Double.parseDouble(data[start][i]);
			}
			start++;
		}
		return allData;
	}

	private static int nearestNeighbor(int testStart, double[][] training, double[][] testing) {
		int start = 0;

		// We set this variable equal to the first row from the trainingData
		double distanceTrain = findDistance(testStart, 0, training, testing);

		// This loop runs through all the rows in trainingData and finds the smallest
		// distance for each of the testingStart rows. This also continuously updates if
		// a new distance is smaller than the previous one.
		for (int i = 1; i < 75; i++) {
			if (findDistance(testStart, i, training, testing) < distanceTrain) {

				distanceTrain = findDistance(testStart, i, training, testing);
				start = i;
			}
		}
		return start;
	}

	private static double findDistance(int testStart, int trainStart, double[][] training, double[][] testing) {
		// In this method, the distance between the actual test values and the training
		// values is calculated by taking in the index of both rows from the start
		// variable. This then returns the calculated distance.
		double calcDistance = Math.sqrt(Math.pow((testing[testStart][0] - training[trainStart][0]), 2)
				+ Math.pow((testing[testStart][1] - training[trainStart][1]), 2)
				+ Math.pow((testing[testStart][2] - training[trainStart][2]), 2)
				+ Math.pow((testing[testStart][3] - training[trainStart][3]), 2));

		return calcDistance;
	}

	private static void predictAccuracy(String[] trueLabel, String[] predictedLabel) {

		double predictionCorrect = 0;
		int nEx = 1;

		// Printing the header for the first row
		System.out.println("\nEX#: TRUE LABEL, PREDICTED LABEL ");

		// This for loop princes the values from the trueLabel and the predictedLabels
		// for each row.
		for (int i = 0; i < 75; i++) {
			System.out.println(nEx + ": " + trueLabel[i] + " " + predictedLabel[i]);

			// If the predicted value is equal to the true value, then counts it as a
			// correct prediction
			if (predictedLabel[i].equals(trueLabel[i])) {
				predictionCorrect++;
			}
			nEx++;
		}

		// This calculates the accuracy between the true values and the predicted
		// values.
		double accurate = (predictionCorrect / trueLabel.length);

		// Prints the accurate rate
		System.out.println("ACCURACY: " + accurate);
	}
}
