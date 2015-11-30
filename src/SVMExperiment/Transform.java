package SVMExperiment;

import java.util.ArrayList;

/**
 *
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 4
 *
 */
public class Transform {

    public static ArrayList<Vector> transform(ArrayList<Vector> dataset){
        ArrayList<Vector> transformedDataset = new ArrayList<Vector>();

        for(Vector v : dataset){
            Double[] oldVector = v.getVector();
            Double[] transformedVector = new Double[(oldVector.length-1)*(oldVector.length-1)+1];
            int k = 1;
            transformedVector[0] = 0.0;
            for(int i = 1; i < oldVector.length; i++){
                for(int j = 1; j < oldVector.length; j++){
                    transformedVector[k] = oldVector[i]*oldVector[j];
                    k++;
                }
            }
            Vector newVector = new Vector(v.getLabel(), transformedVector);
            transformedDataset.add(newVector);
        }

        return transformedDataset;
    }
}
