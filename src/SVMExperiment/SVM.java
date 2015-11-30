package SVMExperiment;

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

public class SVM {

    // initial learning rate
    private static double initialR = 0.001;
    // C: A hyper-parameter that controls the tradeoff between a large margin and a small hinge-loss
    private static double C = 1.0;
    private static int mistakesTrain = 0;
    private static int mistakesTest = 0;
    private static Random rand = new Random();


    public static Double[] SVMAlgorithm(int epoch, Double[] w, ArrayList<Vector> trainingVectors, int length){
        mistakesTrain = 0;
        w = new Double[length];

        // Initialize w to 0's
        for(int i = 0; i < w.length; i++){
            w[i] = 0.0;
        }

        int t = 0;
        for(int i = 0; i < epoch; i++){
            if(epoch != 0)
                Collections.shuffle(trainingVectors);
            for(Vector v : trainingVectors){
                int y = v.getLabel();
                Double[] x = v.getVector();
                double dotProduct = dotProduct(w, x);

                // If the predictions are different, update w
                double r = calculateR(t);
                //System.out.println("Learning rate\t" + r);
                // TODO: 1
                if(y*dotProduct <= 0){
                    w = updateWMistake(w, x, y, r);// w <- (1-r)*w + r*C*y_i*x_i
                    mistakesTrain++;
                }
                else {
                    // add rw
                    w = updateWCorrect(w, r);
                    //w = addVectors(updateWCorrect(w, r), multiplyVector(w, r)); // w <- (1-r)*w
                }
                t++;
            }
        }

        return w;
    }

    public static void predict(Double[] w, ArrayList<Vector> testingVectors){
        mistakesTest = 0;
        for(Vector v : testingVectors){
            int y = v.getLabel();
            Double[] x = v.getVector();
            double dotProduct = dotProduct(w, x);

            // If the predictions are different, update w
            if(dotProduct*y <= 0){
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

    private static double calculateR(double t){
        return initialR/(1.0+(initialR*t/C));
    }

    private static Double[] multiplyVector(Double[] w, double r){
        Double[] newW = new Double[w.length];
        for(int i = 0; i < w.length; i++){
            newW[i] *= r;
        }
        return newW;
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

    // w <- (1-r)*w + r*C*y_i*x_i
    private static Double[] updateWMistake(Double[] w, Double[] x, int y, double r){
        Double[] newX = new Double[x.length];
        Double[] newW = new Double[w.length];
        for(int i = 0; i < w.length; i++){
            newX[i] = r*C*y*x[i];
            newW[i] = (1.0-r)*w[i];
        }
        return addVectors(newW, newX);
    }

    private static Double[] updateWCorrect(Double[] w, double r){
        Double[] newW = new Double[w.length];
        for(int i = 0; i < w.length; i++){
            newW[i] = w[i]*(1.0-r);
        }
        return newW;
    }


    public static void setR(double r){
        initialR = r;
    }

    public static void setC(double c){
        C = c;
    }


}
