package hw1;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Machine Learning Assignment 1
 * @author Dasha Pruss
 *
 */

public class CrossValidation {

	private static double accuracyValue = 0;

	/**
	 * Cross validates with k = 6 across K = [1,2,3,4,5]
	 * @param tables
	 * @param test
	 * @param features
	 * @return
	 */
	public static int CrossValidation(ArrayList<ArrayList<ArrayList<String>>> tables, ArrayList<ArrayList<String>> test,  ArrayList<Integer> features){

		double[][] accuracy = new double[5][6];



		for(int validationIndex = 0; validationIndex < tables.size(); validationIndex++){
			ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
			ArrayList<String> actualLabels = new ArrayList<String>();

			// Construct arraylist of test labels
			for(int i = 0; i < tables.get(validationIndex).size(); i++){
				actualLabels.add(tables.get(validationIndex).get(i).get(features.size()));
			}

			// Construct table full of all the other datasets
			for(int i = 0; i < tables.size(); i++){
				// Don't add the validation set to our total set
				if(i == validationIndex)
					continue;
				for(ArrayList<String> list : tables.get(i)){
					table.add(list);
				}
			}

			for(int K = 1; K < 6; K++){
				// Validate our data
				accuracy[K-1][validationIndex] = KNearestNeighbor.validation(tables.get(validationIndex), actualLabels, table, features, K);
			}
		}

		double maxAccuracy = 0;
		int maxIndex = 0;
		String pattern = "##.#####";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);

		System.out.println("K\tAverage Accuracy");
		for(int i = 0; i < accuracy.length; i++){
			double sum = 0;
			System.out.print((i+1));
			for(int j = 0; j < accuracy[i].length; j++){
				sum += accuracy[i][j];
			}
			double average = sum / (double) accuracy[i].length;
			System.out.println("\t" + decimalFormat.format(average));
			if(average > maxAccuracy){
				maxIndex = i;
				maxAccuracy = average;
			}
		}
		maxIndex++;
		accuracyValue = maxAccuracy;
		return maxIndex;
	}

	/**
	 * Returns the accuracy of the best size K
	 * @return
	 */
	public static double getAccuracy(){
		return accuracyValue;
	}

}
