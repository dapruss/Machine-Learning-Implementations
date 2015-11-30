package SVMExperiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Dasha Pruss
 * u0623749
 * Machine Learning Homework 4
 *
 */

public class Experiment {

    public static void main(String[] args) {

        ArrayList<Vector> trainingVectors = new ArrayList<Vector>();
        ArrayList<Vector> trainingVectorsOriginal = new ArrayList<Vector>();
        ArrayList<Vector> trainingVectorsOriginalTransformed = new ArrayList<Vector>();
        ArrayList<Vector> trainingVectorsScaled = new ArrayList<Vector>();
        ArrayList<Vector> trainingVectorsScaledTransformed = new ArrayList<Vector>();
        ArrayList<Vector> trainingVectorsData = new ArrayList<Vector>();
        ArrayList<Vector> testingVectorsData = new ArrayList<Vector>();
        ArrayList<Vector> testingVectors = new ArrayList<Vector>();
        ArrayList<Vector> testingVectorsOriginal = new ArrayList<Vector>();
        ArrayList<Vector> testingVectorsOriginalTransformed = new ArrayList<Vector>();
        ArrayList<Vector> testingVectorsScaled = new ArrayList<Vector>();
        ArrayList<Vector> testingVectorsScaledTransformed = new ArrayList<Vector>();

        int epoch = 1, k = 1;
        double margin = 0;
        Scanner in;
        Double[] w = null;

        // QUESTION 1: Set up SVM for sanity check
        System.out.println("********Question 2********");
        System.out.println();

        try {
            in = new Scanner(new File(new File("").getAbsolutePath() + "/astro/original/train"));

            k = 4;
            while (in.hasNextLine()){
                String[] temp = in.nextLine().split("\\s+");
                int label = Integer.parseInt(temp[0]);
                if(label == 0)
                    label = -1;
                Double[] vector = new Double[k+1];
                vector[0] = 0.0;
                for(int i = 1; i < temp.length; i++){
                    VectorComponent vc = new VectorComponent(temp[i]);
                    vector[vc.getIndex()] = vc.getValue();
                }
                // Add to training vectors
                trainingVectorsOriginal.add(new Vector(label, vector));
            }
            trainingVectorsOriginalTransformed = Transform.transform(trainingVectorsOriginal);

            in = new Scanner(new File(new File("").getAbsolutePath() + "/astro/scaled/train"));

            while (in.hasNextLine()){
                String[] temp = in.nextLine().split("\\s+");
                int label = Integer.parseInt(temp[0]);
                if(label == 0)
                    label = -1;
                Double[] vector = new Double[k+1];
                vector[0] = 0.0;
                for(int i = 1; i < temp.length; i++){
                    VectorComponent vc = new VectorComponent(temp[i]);
                    vector[vc.getIndex()] = vc.getValue();
                }
                // Add to training vectors
                trainingVectorsScaled.add(new Vector(label, vector));
            }
            trainingVectorsScaledTransformed = Transform.transform(trainingVectorsScaled);

            // Find point in each dataset farthest from the origin
            double maxDistance = 0;
            int maxDistanceIndex = 0;
            for(int i = 0; i < trainingVectorsOriginal.size(); i++){
                double distance = 0;
                for(int j = 1; j < trainingVectorsOriginal.get(i).getVector().length; j++){
                    //Add square of each value
                    distance += (trainingVectorsOriginal.get(i).getVector()[j]*trainingVectorsOriginal.get(i).getVector()[j]);
                }
                distance = Math.sqrt(distance);
                if(distance > maxDistance){
                    maxDistance = distance;
                    maxDistanceIndex = i;
                }
            }

            System.out.println("Maximum distance");

            System.out.println("Original: " + maxDistance + " at index " + maxDistanceIndex + " at points " + Arrays.toString(trainingVectorsOriginal.get(maxDistanceIndex).getVector()));

            // Find point in each dataset farthest from the origin
            maxDistance = 0;
            maxDistanceIndex = 0;
            for(int i = 0; i < trainingVectorsScaled.size(); i++){
                double distance = 0;
                for(int j = 1; j < trainingVectorsScaled.get(i).getVector().length; j++){
                    //Add square of each value
                    distance += (trainingVectorsScaled.get(i).getVector()[j]*trainingVectorsScaled.get(i).getVector()[j]);
                }
                distance = Math.sqrt(distance);
                if(distance > maxDistance){
                    maxDistance = distance;
                    maxDistanceIndex = i;
                }
            }

            System.out.println("Scaled: " + maxDistance + " at index " + maxDistanceIndex + " at points " + Arrays.toString(trainingVectorsScaled.get(maxDistanceIndex).getVector()));

            // Find point in each dataset farthest from the origin
            maxDistance = 0;
            maxDistanceIndex = 0;
            for(int i = 0; i < trainingVectorsOriginalTransformed.size(); i++){
                double distance = 0;
                for(int j = 1; j < trainingVectorsOriginalTransformed.get(i).getVector().length; j++){
                    //Add square of each value
                    distance += (trainingVectorsOriginalTransformed.get(i).getVector()[j]*trainingVectorsOriginalTransformed.get(i).getVector()[j]);
                }
                distance = Math.sqrt(distance);
                if(distance > maxDistance){
                    maxDistance = distance;
                    maxDistanceIndex = i;
                }
            }

            System.out.println("Original transformed: " + maxDistance + " at index " + maxDistanceIndex + " at points " + Arrays.toString(trainingVectorsOriginalTransformed.get(maxDistanceIndex).getVector()));



            // Find point in each dataset farthest from the origin
            maxDistance = 0;
            maxDistanceIndex = 0;
            for(int i = 0; i < trainingVectorsScaledTransformed.size(); i++){
                double distance = 0;
                for(int j = 1; j < trainingVectorsScaledTransformed.get(i).getVector().length; j++){
                    //Add square of each value
                    distance += (trainingVectorsScaledTransformed.get(i).getVector()[j]*trainingVectorsScaledTransformed.get(i).getVector()[j]);
                }
                distance = Math.sqrt(distance);
                if(distance > maxDistance){
                    maxDistance = distance;
                    maxDistanceIndex = i;
                }
            }

            System.out.println("Scaled transformed: " + maxDistance + " at index " + maxDistanceIndex + " at points " + Arrays.toString(trainingVectorsScaledTransformed.get(maxDistanceIndex).getVector()));




            in.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }










        // QUESTION 3:
        System.out.println();
        System.out.println("********Question 3********");
        System.out.println();

        margin = 0;
        epoch = 1;

        try {

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
                trainingVectorsData.add(new Vector(label, vector));
            }

            // Updates w
            w = SVM.SVMAlgorithm(epoch, w, trainingVectorsData, k+1);

//            System.out.println("Sanity check - weight vector is:");
//            System.out.println(Arrays.toString(w));

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
                testingVectorsData.add(new Vector(label, vector));
            }

//            trainingVectorsData.addAll(trainingVectorsDataOriginal);
//            trainingVectorsData.addAll(trainingVectorsDataScaled);
//            trainingVectorsData.addAll(trainingVectorsDataOriginalTransformed);
//            trainingVectorsData.addAll(trainingVectorsDataScaledTransformed);

            // Updates mistakes
            SVM.predict(w, testingVectorsData);

            System.out.println("Training Mistakes (updates): " + SVM.getTrainMistakes());
            System.out.println("Testing Mistakes: " + SVM.getTestMistakes());
            System.out.print("Accuracy: " + (testingVectorsData.size()-SVM.getTestMistakes()) + "/" + testingVectorsData.size());
            System.out.println(" = " + ((double)(testingVectorsData.size()-SVM.getTestMistakes())/testingVectorsData.size()));


            in.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }



        testingVectors.clear();

        epoch = 10;
        k = 4;
        w = null;

        try {
            in = new Scanner(new File(new File("").getAbsolutePath() + "/astro/original/test"));

            while (in.hasNextLine()){
                String[] temp = in.nextLine().split("\\s+");
                int label = Integer.parseInt(temp[0]);
                if(label == 0)
                    label = -1;
                Double[] vector = new Double[k+1];
                vector[0] = 0.0;
                for(int i = 1; i < temp.length; i++){
                    VectorComponent vc = new VectorComponent(temp[i]);
                    vector[vc.getIndex()] = vc.getValue();
                }
                // Add to testing vectors
                testingVectorsOriginal.add(new Vector(label, vector));
            }
            testingVectorsOriginalTransformed = Transform.transform(testingVectorsOriginal);

            in = new Scanner(new File(new File("").getAbsolutePath() + "/astro/scaled/test"));

            while (in.hasNextLine()){
                String[] temp = in.nextLine().split("\\s+");
                int label = Integer.parseInt(temp[0]);
                if(label == 0)
                    label = -1;
                Double[] vector = new Double[k+1];
                vector[0] = 0.0;
                for(int i = 1; i < temp.length; i++){
                    VectorComponent vc = new VectorComponent(temp[i]);
                    vector[vc.getIndex()] = vc.getValue();
                }
                // Add to testing vectors
                testingVectorsScaled.add(new Vector(label, vector));
            }
            testingVectorsScaledTransformed = Transform.transform(testingVectorsScaled);

            testingVectors.clear();
            testingVectors.addAll(testingVectorsOriginal);
            System.out.println("Original");

            double r = 10.0;
            double C = 100.0;
            System.out.println("C\tr\taccuracy");
            for(int i = 0; i < 4; i++){
                r /= 10.0;
                C = 100.0;
                for(int j = 0; j < 5; j++){
                    C /= 10.0;
                    System.out.print(C + "\t" + r);
                    System.out.println("\t" + CrossValidation.CrossValidation(testingVectors, C, r, k));
                }
            }

            testingVectors.clear();
            testingVectors.addAll(testingVectorsScaled);

            System.out.println("Scaled");
            r = 10.0;
            C = 100.0;
            System.out.println("C\tr\taccuracy");
            for(int i = 0; i < 4; i++){
                r /= 10.0;
                C = 100.0;
                for(int j = 0; j < 5; j++){
                    C /= 10.0;
                    System.out.print(C + "\t" + r);
                    System.out.println("\t" + CrossValidation.CrossValidation(testingVectors, C, r, k));
                }
            }

            testingVectors.clear();
            testingVectors.addAll(testingVectorsOriginalTransformed);

            k = 16;

            System.out.println("Original Transformed");
            r = 10.0;
            C = 100.0;
            System.out.println("C\tr\taccuracy");
            for(int i = 0; i < 4; i++){
                r /= 10.0;
                C = 100.0;
                for(int j = 0; j < 5; j++){
                    C /= 10.0;
                    System.out.print(C + "\t" + r);
                    System.out.println("\t" + CrossValidation.CrossValidation(testingVectors, C, r, k));
                }
            }

            testingVectors.clear();
            testingVectors.addAll(testingVectorsScaledTransformed);

            System.out.println("Scaled Transformed");
            r = 10.0;
            C = 100.0;
            System.out.println("C\tr\taccuracy");
            for(int i = 0; i < 4; i++){
                r /= 10.0;
                C = 100.0;
                for(int j = 0; j < 5; j++){
                    C /= 10.0;
                    System.out.print(C + "\t" + r);
                    System.out.println("\t" + CrossValidation.CrossValidation(testingVectors, C, r, k));
                }
            }

            testingVectors.clear();
            testingVectors.addAll(testingVectorsData);
            k = 10;

            System.out.println("test0.10");
            r = 10.0;
            C = 100.0;
            System.out.println("C\tr\taccuracy");
            for(int i = 0; i < 4; i++){
                r /= 10.0;
                C = 100.0;
                for(int j = 0; j < 5; j++){
                    C /= 10.0;
                    System.out.print(C + "\t" + r);
                    System.out.println("\t" + CrossValidation.CrossValidation(testingVectors, C, r, k));
                }
            }

            in.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        System.out.println("********* Question 4 *********");
        //*** QUESTION 4 ***
        epoch = 30;
        margin = 0;

        System.out.println("Original");
        w = null;
        k = 4;
        w = SVM.SVMAlgorithm(epoch, w, trainingVectorsOriginal, k+1);
        margin = getMargin(w);

        // Updates mistakes
        SVM.predict(w, testingVectorsOriginal);

        System.out.println("Accuracy: " + (testingVectorsOriginal.size()-SVM.getTestMistakes())/(double)testingVectorsOriginal.size());
        System.out.println("Margin: " + margin);

        System.out.println();




        System.out.println("Scaled");
        w = null;
        k = 4;
        w = SVM.SVMAlgorithm(epoch, w, trainingVectorsScaled, k+1);
        margin = getMargin(w);

        // Updates mistakes
        SVM.predict(w, testingVectorsScaled);

        System.out.println("Accuracy: " + (testingVectorsScaled.size()-SVM.getTestMistakes())/(double)testingVectorsScaled.size());
        System.out.println("Margin: " + margin);

        System.out.println();


        System.out.println("Original Transformed");
        w = null;
        k = 16;
        w = SVM.SVMAlgorithm(epoch, w, trainingVectorsOriginalTransformed, k+1);
        margin = getMargin(w);

        // Updates mistakes
        SVM.predict(w, testingVectorsOriginalTransformed);

        System.out.println("Accuracy: " + (testingVectorsOriginalTransformed.size()-SVM.getTestMistakes())/(double)testingVectorsOriginalTransformed.size());
        System.out.println("Margin: " + margin);

        System.out.println();


        System.out.println("Scaled Transformed");
        w = null;
        k = 16;
        w = SVM.SVMAlgorithm(epoch, w, trainingVectorsScaledTransformed, k+1);
        margin = getMargin(w);

        // Updates mistakes
        SVM.predict(w, testingVectorsScaledTransformed);

        System.out.println("Accuracy: " + (testingVectorsScaledTransformed.size()-SVM.getTestMistakes())/(double)testingVectorsScaledTransformed.size());
        System.out.println("Margin: " + margin);

        System.out.println();


        System.out.println("Data0.10");
        w = null;
        k = 10;
        w = SVM.SVMAlgorithm(epoch, w, trainingVectorsData, k+1);
        margin = getMargin(w);

        // Updates mistakes
        SVM.predict(w, testingVectorsData);

        System.out.println("Accuracy: " + (testingVectorsData.size()-SVM.getTestMistakes())/(double)testingVectorsData.size());
        System.out.println("Margin: " + margin);

        System.out.println();

    }

    public static double getMargin(Double[] w){
        double margin = 0;
        for(int i = 0; i < w.length; i++){
            margin += (w[i]*w[i]);
        }
        margin = Math.sqrt(margin);
        return margin;
    }


}
