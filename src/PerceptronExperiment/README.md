## Data

The training and test data sets I used are in the data0 and data1 folders. They contain files with the following naming convention: trainI.K and testI.K, where K represents the number of features in each data point. So for instance, train0.10 is a training set in data0 with 11 features (ten features, plus a constant bias feature) and the corresponding test data set is test0.10. Each row in the file is a single example and the format of the each row in the data is:

<label> <index1>:<value1> <index2>:<value2> ...

<label> refers to the label for that example. The rest of the row is a sparse vector representing the non-zero values in the feature vector. For example, if the original feature vector is [0, 0, 1, 2, 0, 3], this would be represented as 3:1 4:2 6:3.
