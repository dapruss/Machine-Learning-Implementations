package DTAndKNNExperiment;

import java.util.ArrayList;

public class DecisionTree {
	
	DecisionTree parent;
	String incomingState; 					// e.g. x, o, b
	int feature; 							// e.g. 0, 1, 2...
	ArrayList<DecisionTree> children;
	String label; 							// e.g. positive, negative
	
	public DecisionTree(String state, int feature){
		this.incomingState = state;
		this.feature = feature;
		children = new ArrayList<DecisionTree>();
	}
	
	public DecisionTree(String state, String label){
		this.incomingState = state;
		this.label = label;
		children = new ArrayList<DecisionTree>();
	}
	
	public ArrayList<DecisionTree> getChildren(){
		return children;
	}
	
	public int getFeature(){
		return feature;
	}
	
	public String getAttribute(){
		return incomingState;
	}
	
	public String getLabel(){
		return label;
	}
	
	public boolean hasLabel(){
		return !(label == null);
	}
	
	public void addLabel(String label){
		this.label = label;
		feature = -1;
	}
	
	public void setFeature(int feature){
		this.feature = feature;
	}
	
	public void addChild(String state, int feature){
		DecisionTree child = new DecisionTree(state, feature);
		child.setParent(this);
		children.add(child);
	}
	
	public void addChild(DecisionTree child){
		child.setParent(this);
		children.add(child);
	}
	
	public void setParent(DecisionTree parent){
		this.parent = parent;
	}
	
}
