package hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Machine Learning Assignment 1
 * @author Dasha Pruss
 *
 */


public class KNearestNeighbor {
	
	private static int correct;

	
	/**
	 * Determines accuracy of K nearest neighbors algorithm
	 * @param test
	 * @param actualLabels
	 * @param table
	 * @param features
	 * @param K
	 * @return
	 */
	public static double validation(ArrayList<ArrayList<String>> test, ArrayList<String> actualLabels, ArrayList<ArrayList<String>> table, ArrayList<Integer> features, int K){
		
		ArrayList<String> testLabels = new ArrayList<String>();
		testLabels = KNearestNeighbor.KNearestNeighbor(table, test, K, features);
		
		correct = 0;
		
		for(int i = 0; i < testLabels.size(); i++){
			if(actualLabels.get(i).equals(testLabels.get(i)))
				correct++;
		}
		
		return (correct / (double)testLabels.size()*100);
	}
	
	/**
	 * Return number of correct labels
	 * @return
	 */
	public static int getCorrect(){
		return correct;
	}
	
	/**
	 * Returns arraylist with the most common label for the K closest neighbors
	 * @param table
	 * @param test
	 * @param K
	 * @param features
	 * @return
	 */
	public static ArrayList<String> KNearestNeighbor
	(ArrayList<ArrayList<String>> table, ArrayList<ArrayList<String>> test, int K, ArrayList<Integer> features){
		ArrayList<String> labels = new ArrayList<String>();
		
		for(int i = 0; i < test.size(); i++){
			ArrayList<String> currentTest = test.get(i);
			ArrayList<HammingDistance> hammingDistance = new ArrayList<HammingDistance>();
			String bestLabel = "";
			
			for(int j = 0; j < table.size(); j++){
				ArrayList<String> currentTable = table.get(j);
				int tempHam = 0;
				for(int k = 0; k < currentTest.size()-1; k++){
					if(!currentTest.get(k).equals(currentTable.get(k)))
						tempHam++;
				}
				hammingDistance.add(new HammingDistance(tempHam, currentTable.get(features.size())));
			}
			
			// Sort the hamming distance list
			Collections.sort(hammingDistance);
			
			// Find the most common label in the first K entries
			int positive = 0, negative = 0;
			for(int j = 0; j < K; j++){
				if(hammingDistance.get(j).getLabel().equals("positive"))
					positive++;
				else
					negative++;
			}
			
			if(positive > negative)
				bestLabel = "positive";
			else if(positive < negative)
				bestLabel = "negative";
			// If it's a tie, pick one at random
			else
				bestLabel = (Math.random() <= 0.5) ? "positive" : "negative";
			
			labels.add(bestLabel);
		}
		
		return labels;
	}

}
