package DTAndKNNExperiment;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Machine Learning Assignment 1
 * @author Dasha Pruss
 *
 */

public class Classify {
	
	public static void main(String[] args) {
		
        ArrayList<String> rows = new ArrayList<String>();
        ArrayList<Integer> features = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> sixRows = new ArrayList<ArrayList<String>>();
        
		try{
			ArrayList<File> files = new ArrayList<File>();
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-1.txt"));
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-2.txt"));
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-3.txt"));
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-4.txt"));
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-5.txt"));
			files.add(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-train-6.txt"));
		
			for(File f : files){
				Scanner in = new Scanner(f);
				ArrayList<String>  tempRows = new ArrayList<String>();
	            while (in.hasNext()){
	            	String next = in.next();
	                rows.add(next);
	                tempRows.add(next);
	            }
	            sixRows.add(tempRows);
	            
	            in.close();
			}
		}
		catch(Exception e){
			System.out.println("Training file couldn't be read.");
			return;
		}
		
		// Construct table of data from the combined input files
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> sixTable = new ArrayList<ArrayList<ArrayList<String>>>();
		
		for(int i = 0; i < rows.size(); i++){
			ArrayList<String> temp = new ArrayList<String>();
			String[] arr = rows.get(i).split(",");
			for(int j = 0; j < arr.length; j++)
				temp.add(arr[j]);
			table.add(temp);
		}
		
		for(int k = 0; k < sixRows.size(); k++){
			ArrayList<ArrayList<String>> tempTable = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < sixRows.get(k).size(); i++){
				ArrayList<String> temp = new ArrayList<String>();
				String[] arr = sixRows.get(k).get(i).split(",");
				for(int j = 0; j < arr.length; j++)
					temp.add(arr[j]);
				tempTable.add(temp);
			}
			sixTable.add(tempTable);
		}
		
		for(int i = 0; i < table.get(0).size()-1; i++){
			features.add(i);
		}

		// Construct our tree using ID3
		DecisionTree tree = ID3.ID3(table, features, new DecisionTree(null, -1));
		
		// Read in test file
		rows.clear();
		try{
			Scanner in = new Scanner(new File(new File("").getAbsolutePath() + "/" + "tic-tac-toe-test.txt"));
			
            while (in.hasNext())
                rows.add(in.next());
            
            in.close();
		}
		catch(Exception e){
			System.out.println("Testing file couldn't be read.");
			return;
		}
		
		ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>();
		ArrayList<String> actualLabels = new ArrayList<String>();
		
		for(int i = 0; i < rows.size(); i++){
			ArrayList<String> temp = new ArrayList<String>();
			String[] arr = rows.get(i).split(",");
			for(int j = 0; j < arr.length-1; j++)
				temp.add(arr[j]);
			test.add(temp);
			actualLabels.add(arr[arr.length-1]);
		}
		
		String pattern = "##.###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		
		// Determine the accuracy of our decision tree
		ID3.validation(test, actualLabels, tree);
		int correct = ID3.getCorrect();
		
		System.out.println("Decision Tree:");
		System.out.println("Accuracy: " + correct + " out of " + actualLabels.size());
		System.out.println(decimalFormat.format(correct / (double)actualLabels.size()*100) + "% accurate");
		
		System.out.println();
		System.out.println("Cross Validation with k = 6:");
		int K = CrossValidation.CrossValidation(sixTable, test, features);
		System.out.println();
		System.out.println("Best value of K is " + K + "\nAverage cross validation accuracy: " + decimalFormat.format(CrossValidation.getAccuracy()) + "%");
		
		
		KNearestNeighbor.validation(test, actualLabels, table, features, K);
		
		correct = KNearestNeighbor.getCorrect();
		System.out.println();
		System.out.println("K Nearest Neighbors, where K = " + K + ":");
		System.out.println("Accuracy: " + correct + " out of " + actualLabels.size());
		System.out.println(decimalFormat.format(correct / (double)actualLabels.size()*100) + "% accurate");
	}
	
	
}
