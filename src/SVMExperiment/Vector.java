package hw4;

/**
 * 
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 4
 * 
 */

public class Vector {

	private int label;
	private Double[] vector;
	
	public Vector(int label, Double[] vector){
		this.label = label;
		this.vector = vector;
	}
	
	public int getLabel(){
		return label;
	}
	
	public Double[] getVector(){
		return vector;
	}
	
}
