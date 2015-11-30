package SVMExperiment;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 4
 *
 */

public class CrossValidation {

    private static double accuracyValue = 0;

    /**
     *
     * @param train
     * @param C
     * @param r
     * @param k
     * @return
     */
    public static double CrossValidation(ArrayList<Vector> train, double C, double r, int k){

        double[] accuracy = new double[10];

        SVM.setC(C);
        SVM.setR(r);

        double maxAccuracy = 0;
        int maxIndex = 0;
        String pattern = "##.#####";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        int foldSize = train.size()/10;
        ArrayList<Vector> trainingSet = new ArrayList<Vector>();
        ArrayList<Vector> testingSet = new ArrayList<Vector>();

        for(int i = 0; i < 10; i++){
            // Train
            for(int j = 0; j < i*foldSize; j++){
                trainingSet.add(train.get(j));
            }
            // Validate
            for(int j = i*foldSize; j < i*foldSize+foldSize; j++){
                testingSet.add(train.get(j));
            }
            // Train
            for(int j = i*foldSize+foldSize; j < train.size(); j++){
                trainingSet.add(train.get(j));
            }

            double sum = 0;
            //System.out.print((i+1));
            Double[] w = null;

            w = SVM.SVMAlgorithm(10, w, trainingSet, k+1);

            // Updates mistakes
            SVM.predict(w, testingSet);

            accuracy[i] = (double)(testingSet.size()-SVM.getTestMistakes())/(double)testingSet.size();
        }

        double sum = 0;
        for(int i = 0; i < accuracy.length; i++)
            sum += accuracy[i];

        return sum /= 10.0;
    }

    /**
     * Returns the accuracy of the best size K
     * @return
     */
    public static double getAccuracy(){
        return accuracyValue;
    }

}
