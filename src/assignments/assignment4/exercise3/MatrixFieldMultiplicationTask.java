package assignment4.exercise3;

import java.util.concurrent.Callable;

/**
 * Multiplication Task implementing Callable;
 * It takes two matrices (represented as two-dimensional long array), a row and col index, and
 * then calculates the product of the matrix field of the two given matrices at the given field with <rowIndex, colIndex>
 */
public class MatrixFieldMultiplicationTask implements Callable<Long> {

    long[][] a, b;
    private int rowIndex, colIndex;

    public MatrixFieldMultiplicationTask(long[][] a, long[][] b, int rowIndex, int colIndex) {
        this.a = a;
        this.b = b;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    @Override
    public Long call() {
        long sum = 0;
        for(int i=0;i<b.length;i++){
            sum += (this.a[this.rowIndex][i] * this.b[i][this.colIndex]);
        }
        return sum;
    }
}
