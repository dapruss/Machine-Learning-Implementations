package hw1;

public class HammingDistance implements Comparable<HammingDistance>{

	private String label;
	private Integer hammingDist;
	
	
	/**
	 * A little class to store label and hamming distance together to make sorting easier.
	 * @param hammingDist
	 * @param label
	 */
	public HammingDistance(int hammingDist, String label){
		this.hammingDist = hammingDist;
		this.label = label;
	}

	@Override
	public int compareTo(HammingDistance ham2) {
		return hammingDist.compareTo(ham2.getHammingDist());
	}
	
	public Integer getHammingDist(){
		return hammingDist;
	}
	
	public String getLabel(){
		return label;
	}
	
}
