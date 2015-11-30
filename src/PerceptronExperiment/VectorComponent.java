package hw2;

/**
 * 
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 2
 * 
 */

public class VectorComponent {

	private int index;
	private double value;
	
	public VectorComponent(int index, double value){
		this.index = index;
		this.value = value;
	}
	
	public VectorComponent(String both){
		String[] values = both.split(":");
		index = Integer.parseInt(values[0]);
		value = Double.parseDouble(values[1]);
	}
	
	public int getIndex(){
		return index;
	}
	
	public double getValue(){
		return value;
	}
}
