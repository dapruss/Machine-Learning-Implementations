## Perceptron implementation                                                                                                 Back to 

First, I implemented a regular perceptron. I perform an update on an example (x, y) (note: bold-face denotes vectors) if [perceptron](pictures/perceptron.jpg) This is the equivalent of checking if  the sign of the true label is the same as the predicted label; if they are different signs (positive and negative), their product is always negative. When the prediction is incorrect, we update the weight vector:
![update](pictures/update.jpg)
Note that the data set includes a bias term, so the bias term does not need to be explicitly mentioned above. Here, w is a weight vector that I initialize to random values between 0 and 1 and r is a hyper-parameter that can be found using cross-validation on the data.

## Margin Perceptron

This uses the same implementation as Perceptron; the only difference is that it checks if the product of the actual label and predicted label is less than or equal to the margin, rather than 0, so we update if ![margin](pictures/margin.jpg), where μ is another hyper-parameter that is found through cross-validation.

## Data

The training and test data sets I used are in the data0 and data1 folders. They contain files with the following naming convention: trainI.K and testI.K, where K represents the number of features in each data point. So for instance, train0.10 is a training set in data0 with 11 features (ten features, plus a constant bias feature) and the corresponding test data set is test0.10. Each row in the file is a single example and the format of the each row in the data is:

[label] [index1]:[value1] [index2]:[value2] ...

<label> refers to the label for that example. The rest of the row is a sparse vector representing the non-zero values in the feature vector. For example, if the original feature vector is [0, 0, 1, 2, 0, 3], this would be represented as 3:1 4:2 6:3.
