## Decision Trees and K-Nearest-Neighbors

I used decision trees and the k-nearest-neighbor algorithm to classify whether a 3x3 tic-tac-toe board's X-player is the winner of not.
 
I created a simple Decision Tree data structure and populated it using the ID3 algorithm. For the K-Nearest-Neighbors (KNN) algorithm, I simply computed the hamming distance of each data point. To find the ideal hyper-parameter K for KNN (K = number of neighbors), I cross validated on the tic-tac-toe data using 6-fold cross validation.
 
## The Data

Each tic-tac-toe data file, e.g. tic-tac-toe-test.txt, has rows that look like this: "b,b,o,x,x,x,b,b,o,positive" where b = blank, x = marked with an X, and o = marked with an O. The first 3 letters represent the 1st row of the board, the 4th-6th letters represent the second row, the 7th-9th letters represent the 3rd row of the board, and the label 'positive' or 'negative' denotes whether X is the winner or not. So for instance, the example above has X as the winner and would look like this in board form:

--- |---- | ---
  |   | O
X	| X | X
  |   | O
