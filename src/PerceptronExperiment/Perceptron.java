package PerceptronExperiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 2
 * 
 */

public class Perceptron {

	private static double r = 0.5;
	private static int mistakesTrain = 0;
	private static int mistakesTest = 0;
	private static Random rand = new Random();
	
	
	public static Double[] perceptronAlgorithm(int epoch, Double[] w, ArrayList<Vector> trainingVectors, double margin, int length){
		mistakesTrain = 0;
		w = new Double[length];
		
		// Initialize w to a random vector with values (-1, 1)
		for(int i = 0; i < w.length; i++){
			w[i] = rand.nextDouble();
			if(rand.nextDouble() >= .5)
				w[i] *= -1;
		}
		
		for(int i = 0; i < epoch; i++){
			if(epoch != 0)
				Collections.shuffle(trainingVectors);
			for(Vector v : trainingVectors){
				int y = v.getLabel();
				Double[] x = v.getVector();
				double dotProduct = dotProduct(w, x);
				
				// If the predictions are different, update w
				if(y*dotProduct <= margin){
					w = updateW(w, x, y); // w <- w + r*y*x
					mistakesTrain++;
				}
			}
		}
		
		return w;
	}
	
	public static void predict(Double[] w, ArrayList<Vector> testingVectors, double margin){
		mistakesTest = 0;
		for(Vector v : testingVectors){
			int y = v.getLabel();
			Double[] x = v.getVector();
			double dotProduct = dotProduct(w, x);
			
			// If the predictions are different, update w
			if(dotProduct*y <= margin){
				mistakesTest++;
			}
		}
	}
	
	public static int getTrainMistakes(){
		return mistakesTrain;
	}
	
	public static int getTestMistakes(){
		return mistakesTest;
	}
	
	/**
	 * Returns dot product of w and x
	 * @param w
	 * @param x
	 * @return
	 */
	private static double dotProduct(Double[] w, Double[] x){
		if(w.length != x.length){
			System.out.println("Error, w and x must be the same length!");
			return 0;
		}
		
		double sumOfProducts = 0;
		
		for(int i = 0; i < w.length; i++){
			sumOfProducts += (w[i] * x[i]);
		}
		
		return sumOfProducts;
	}
	
	/**
	 * Returns sum of w and x
	 * @param w
	 * @param x
	 */
	private static Double[] addVectors(Double[] w, Double[] x){
		if(w.length != x.length){
			System.out.println("Error, w and x must be the same length!");
			return null;
		}
		
		Double[] sumOfVectors = new Double[w.length];
		
		for(int i = 0; i < w.length; i++){
			sumOfVectors[i] = (w[i] + x[i]);
		}
		
		return sumOfVectors;
	}
	
	private static Double[] updateW(Double[] w, Double[] x, int y){
		Double[] newX = new Double[x.length];
		for(int i = 0; i < w.length; i++){
			newX[i] = r*y*x[i];
		}
		return addVectors(w, newX);
	}
	
}
