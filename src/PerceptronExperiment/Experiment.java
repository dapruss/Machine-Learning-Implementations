package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 2
 * 
 */

public class Experiment {

	public static void main(String[] args) {

		ArrayList<Vector> trainingVectors = new ArrayList<Vector>();
		ArrayList<Vector> testingVectors = new ArrayList<Vector>();
		int epoch = 10, k = 1;
		double margin = 0;
		Scanner in;
		Double[] w = null;
		
		// QUESTION 1: Set up Perceptron for sanity check
		System.out.println("********Question 1********");
		System.out.println();
		
		try {
			in = new Scanner(new File(new File("").getAbsolutePath() + "/sanityCheck.txt"));
			
			k = 4;
			while (in.hasNextLine()){
				String[] temp = in.nextLine().split("\\s+");
				int label = Integer.parseInt(temp[0]);
				Double[] vector = new Double[k+1];
				for(int i = 1; i < temp.length; i++){
					VectorComponent vc = new VectorComponent(temp[i]);
					//System.out.println(vc.getIndex());
					vector[vc.getIndex()] = vc.getValue();
				}
				// Map label to vector
				trainingVectors.add(new Vector(label, vector));
			}

			// Updates w
			w = Perceptron.perceptronAlgorithm(epoch, w, trainingVectors, margin, k+1);
			
			System.out.println("Sanity check - weight vector is:");
			System.out.println(Arrays.toString(w));
			
			// Updates mistakes
			Perceptron.predict(w, trainingVectors, margin);
			
			System.out.println("Training Mistakes (updates): " + Perceptron.getTrainMistakes());
			System.out.println("Testing Mistakes: " + Perceptron.getTestMistakes());
			
			in.close(); 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		

		// QUESTION 2:
		System.out.println();
		System.out.println("********Question 2********");
		System.out.println();
		
		margin = 0;
		
		try {
			trainingVectors.clear();
			testingVectors.clear();
			
			in = new Scanner(new File(new File("").getAbsolutePath() + "/data0/" + "train0.10"));
			
			k = 10;
			while (in.hasNextLine()){
				String[] temp = in.nextLine().split("\\s+");
				int label = Integer.parseInt(temp[0]);
				Double[] vector = new Double[k+1];
				for(int i = 1; i < temp.length; i++){
					VectorComponent vc = new VectorComponent(temp[i]);
					vector[vc.getIndex()] = vc.getValue();
				}
				// Add to training vectors
				trainingVectors.add(new Vector(label, vector));
			}

			// Updates w
			w = Perceptron.perceptronAlgorithm(epoch, w, trainingVectors, margin, k+1);
			
			System.out.println("Sanity check - weight vector is:");
			System.out.println(Arrays.toString(w));
			
			in.close();
			
			in = new Scanner(new File(new File("").getAbsolutePath() + "/data0/" + "test0.10"));

			while (in.hasNextLine()){
				String[] temp = in.nextLine().split("\\s+");
				int label = Integer.parseInt(temp[0]);
				Double[] vector = new Double[k+1];
				for(int i = 1; i < temp.length; i++){
					VectorComponent vc = new VectorComponent(temp[i]);
					vector[vc.getIndex()] = vc.getValue();
				}
				// Add to testing vectors
				testingVectors.add(new Vector(label, vector));
			}
			
			// Updates mistakes
			Perceptron.predict(w, testingVectors, margin);
			
			System.out.println("Training Mistakes (updates): " + Perceptron.getTrainMistakes());
			System.out.println("Testing Mistakes: " + Perceptron.getTestMistakes());
			System.out.print("Accuracy: " + (testingVectors.size()-Perceptron.getTestMistakes()) + "/" + testingVectors.size());
			System.out.println(" = " + ((double)(testingVectors.size()-Perceptron.getTestMistakes())/testingVectors.size()));
			
			in.close(); 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		// QUESTION 3
		System.out.println();
		System.out.println("********Question 3********");
		System.out.println();
		
		try{
			ArrayList<File> trainFiles = new ArrayList<File>();
			ArrayList<File> testFiles = new ArrayList<File>();
			for(int i = 0; i <= 1; i++){
				for(int K = 10; K <= 100; K += 10){
					//System.out.println(new java.io.File("").getAbsolutePath() + "\\data" + i + "\\train" + i + "." + K);
					trainFiles.add(new File(new File("").getAbsolutePath() + "/data" + i + "/train" + i + "." + K));
					testFiles.add(new File(new File("").getAbsolutePath() + "/data" + i + "/test" + i + "." + K));
				}
			}

			int K = 10;
			int data = 0;
			epoch = 10;
			margin = 4;
			System.out.println("Data 0");
			System.out.println("K\tUpdates\tAccuracy");
			for(int f = 0; f < trainFiles.size(); f++){
				trainingVectors.clear();
				testingVectors.clear();
				if(K > 100){
					K = 10;
					data = 1;
					System.out.println("\nData 1");
					System.out.println("K\tUpdates\tAccuracy");
				}
				in = new Scanner(trainFiles.get(f));
				while (in.hasNextLine()){
					String[] temp = in.nextLine().split("\\s+");
					int label = Integer.parseInt(temp[0]);
					Double[] vector = new Double[K+1];
					for(int i = 1; i < temp.length; i++){
						VectorComponent vc = new VectorComponent(temp[i]);
						//System.out.println(vc.getIndex());
						vector[vc.getIndex()] = vc.getValue();
					}
					// Map label to vector
					trainingVectors.add(new Vector(label, vector));
				}
				
				w = null;
				
				// Updates w
				w = Perceptron.perceptronAlgorithm(epoch, w, trainingVectors, margin, K+1);
				
				in.close(); 
				
				in = new Scanner(testFiles.get(f));
				while (in.hasNextLine()){
					String[] temp = in.nextLine().split("\\s+");
					int label = Integer.parseInt(temp[0]);
					Double[] vector = new Double[K+1];
					for(int i = 1; i < temp.length; i++){
						VectorComponent vc = new VectorComponent(temp[i]);
						//System.out.println(vc.getIndex());
						vector[vc.getIndex()] = vc.getValue();
					}
					// Map label to vector
					testingVectors.add(new Vector(label, vector));
				}
				
				// Updates mistakes
				Perceptron.predict(w, testingVectors, margin);
				
				System.out.print(K);
				System.out.print("\t" + Perceptron.getTrainMistakes());
				//System.out.println("Testing Mistakes:\t" + Perceptron.getTestMistakes());
				System.out.println("\t" + ((double)(testingVectors.size()-Perceptron.getTestMistakes())/testingVectors.size()));
				K+=10;
				in.close();
			}
			
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Error! " + e.getMessage());
			return;
		}
		
	
		
		
	}


}
