package DTAndKNNExperiment;

import java.util.ArrayList;

/**
 * Machine Learning Assignment 1
 * @author Dasha Pruss
 *
 */


public class ID3 {
	
	private static int correct;
	
	/**
	 * Determines accuracy of DecisionTree
	 * @param test
	 * @param actualLabels
	 * @param tree
	 */
	public static void validation(ArrayList<ArrayList<String>> test, ArrayList<String> actualLabels, DecisionTree tree){
		ArrayList<String> testLabels = new ArrayList<String>();
		
		for(int i = 0; i < test.size(); i++){
			String label = ID3.classify(test.get(i), tree);
			testLabels.add(label);
		}
		
		// Accuracy
		correct = 0;
		
		for(int i = 0; i < testLabels.size(); i++){
			if(actualLabels.get(i).equals(testLabels.get(i)))
				correct++;
		}
	}
	
	/**
	 * Returns the number of correct labels from validation
	 * @return
	 */
	public static int getCorrect(){
		return correct;
	}
	
	/**
	 * Classifies given input of examples according to given DecisionTree
	 * @param examples
	 * @param node
	 * @return
	 */
	public static String classify(ArrayList<String> examples, DecisionTree node){
		
		while(!node.hasLabel()){
			int feature = node.getFeature();
			ArrayList<DecisionTree> children = node.getChildren();
			
			// Check what the input sample has as the attribute
			String attribute = examples.get(feature);
			
			// Loop through children to find the one with the matching attribute (x, o, b)
			for(DecisionTree child : children){
				if(child.getAttribute().equals(attribute)){
					// If it has a label, return it, otherwise keep going
					if(child.hasLabel())
						return child.getLabel();
					else{
						node = child;
						break;
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * ID3 algorithm
	 * @param examples
	 * @param features
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static DecisionTree ID3(ArrayList<ArrayList<String>> examples, ArrayList<Integer> features, DecisionTree root){
		
		boolean sameLabel = true;
		String label = examples.get(0).get(features.size());
		for(int i = 1; i < examples.size(); i++){
			if((examples.get(i).size()-1) != features.size()){
				System.out.println("Features size is " + features.size() + " but examples length is " + (examples.get(i).size()-1));
			}
			if(!examples.get(i).get(features.size()).equals(label)){
				sameLabel = false;
				break;
			}
		}
		
		if(sameLabel || features.size() == 0){
			root.addLabel(label);
			return root;
		}
		
		int attribute = findBestAttribute(examples, features);
		root.setFeature(attribute);
		
		ArrayList<Integer> featuresCopy = (ArrayList<Integer>) features.clone();
		
		featuresCopy.remove(attribute);
		
		// Create and add new children
		DecisionTree xChild = new DecisionTree("x", attribute);
		DecisionTree oChild = new DecisionTree("o", attribute);
		DecisionTree bChild = new DecisionTree("b", attribute);
		
		root.addChild(xChild);
		root.addChild(oChild);
		root.addChild(bChild);
		
		// Get each subset
		ArrayList<ArrayList<String>> Sx = getSubset(examples, attribute, "x");
		ArrayList<ArrayList<String>> So = getSubset(examples, attribute, "o");
		ArrayList<ArrayList<String>> Sb = getSubset(examples, attribute, "b");
		
		String commonLabel = mostCommonLabel(examples, features);
		
		// For each attribute, either add a label if the subset is empty, or add more branches below
		if(Sx.isEmpty())
			xChild.addLabel(commonLabel);
		else
			ID3(Sx, featuresCopy, xChild);
		
		if(So.isEmpty())
			oChild.addLabel(commonLabel);
		else
			ID3(So, featuresCopy, oChild);
		
		if(Sb.isEmpty())
			bChild.addLabel(commonLabel);
		else
			ID3(Sb, featuresCopy, bChild);
		
		return root;
	}
	
	/**
	 * Returns the most common label from the given set with the given feature set
	 * @param set
	 * @param features
	 * @return
	 */
	public static String mostCommonLabel(ArrayList<ArrayList<String>> set, ArrayList<Integer> features){
		int positive = 0, negative = 0;
		
		for(int i = 0; i < set.size(); i++){
			if(set.get(i).get(features.size()).equals("positive"))
				positive++;
			else
				negative++;
		}
		
		if(positive > negative)
			return "positive";
		else if(positive < negative)
			return "negative";
		return (Math.random() <= 0.5) ? "positive" : "negative";
	}
	
	/**
	 * Using entropy/information gain, returns the index of the best attribute to split on
	 * @param set
	 * @param features
	 * @return
	 */
	public static int findBestAttribute(ArrayList<ArrayList<String>> set, ArrayList<Integer> features){
		double positive = 0, negative = 0;
		double[] gain = new double[features.size()];
		int maxGainAttribute = 0;
		
		// Tally up number of positive and negative labels
		for(int i = 0; i < set.size(); i++){
			if(set.get(i).get(features.size()).equals("positive"))
				positive++;
			else
				negative++;
		}
		
		// Calculate total entropy for this subset
		double P = positive/(double)set.size();
		double N = negative/(double)set.size();
		double entropy = -1.0*P*Math.log(P)/Math.log(2)-N*Math.log(N)/Math.log(2);
		
		// Calculate information gain for each feature
		for(int j = 0; j < features.size(); j++){
			double numPositiveO = 0, totalO = 0, numPositiveX = 0, totalX = 0, numPositiveB = 0, totalB = 0;
			double Ho = 0, Hx = 0, Hb = 0;
			
			// Tally up the number of o's, x's,and b's with positive label
			for(int i = 0; i < set.size(); i++){
				if(set.get(i).get(j).equals("o")){
					totalO++;
					if(set.get(i).get(features.size()).equals("positive"))
						numPositiveO++;
				}
				else if(set.get(i).get(j).equals("x")){
					totalX++;
					if(set.get(i).get(features.size()).equals("positive"))
						numPositiveX++;
				}
				else{
					totalB++;
					if(set.get(i).get(features.size()).equals("positive"))
						numPositiveB++;
				}
			}
			
			// Calculate P, N, and H for o, x and b
			P = numPositiveO/totalO;
			N = (totalO-numPositiveO)/totalO;
			if(P==1 || N==1 || totalO==0)
				Ho = 0;
			else
				Ho = -1.0*P*Math.log(P)/Math.log(2)-N*Math.log(N)/Math.log(2);
			
			P = numPositiveX/totalX;
			N = (totalX-numPositiveX)/totalX;
			if(P==1 || N==1 || totalX==0)
				Hx = 0;
			else
				Hx = -1.0*P*Math.log(P)/Math.log(2)-N*Math.log(N)/Math.log(2);
			
			P = numPositiveB/totalB;
			N = (totalB-numPositiveB)/totalB;
			if(P==1 || N==1 || totalB==0)
				Hb = 0;
			else
				Hb = -1.0*P*Math.log(P)/Math.log(2)-N*Math.log(N)/Math.log(2);
			
			double proportionO = totalO / (double) set.size();
			double proportionX = totalX / (double) set.size();
			double proportionB = totalB / (double) set.size();
			
			// Calculate information gain
			gain[j] = entropy - (proportionO*Ho + proportionX*Hx + proportionB*Hb);	
		}
		
		// Find the attribute with the max gain
		double max = gain[0];
		for(int i = 1; i < gain.length; i++){
			if(gain[i] > max){
				max = gain[i];
				maxGainAttribute = i;
			}
		}
		
		return maxGainAttribute;
	}
	
	/**
	 * Returns a subset of the table with the given type in the given feature
	 * @param table
	 * @param feature
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<String>> getSubset(ArrayList<ArrayList<String>> table, int feature, String type){
		
		ArrayList<ArrayList<String>> subset = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).get(feature).equals(type)){
				// Remove the feature so we don't have indexing issues later
				ArrayList<String> temp = (ArrayList<String>) table.get(i).clone();
				temp.remove(feature);
				subset.add(temp);
			}
		}
		
		return subset;
	}


}
