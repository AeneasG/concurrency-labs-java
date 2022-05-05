package assignment4.exercise3;

/**
 * Convinience class to hold the values of a matrix
 * provides a method to fill the Matrix at random with values between 0 and 100
 */
public class Matrix {
    private int rows, cols;

    public long[][] matrixFields;

    public Matrix(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
        this.matrixFields = new long[rows][cols];
    }

    /**
     * Overrides every single field of the matrix with a random value between 0 and 100
     */
    public void fillMatrixRandom() {
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                this.matrixFields[i][j] = Math.round((Math.random() * 100));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                stringBuilder.append(this.matrixFields[i][j] + ", ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
