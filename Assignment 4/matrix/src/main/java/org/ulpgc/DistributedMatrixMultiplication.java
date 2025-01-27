package org.ulpgc;

import com.hazelcast.core.*;
import com.hazelcast.config.Config;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.IMap;
import org.openjdk.jmh.annotations.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class DistributedMatrixMultiplication {
    @Param({"1000"})
    private int size;

    private HazelcastInstance hazelcastInstance;
    private IMap<Integer, Task> tasks;
    private int[][] matrixA;
    private int[][] matrixB;

    @Setup(Level.Trial)
    public void setup() {
        Config config = new Config();
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);

        Random random = new Random(1234);
        // Define matrices
        matrixA = new int[size][size];
        matrixB = new int[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                matrixA[i][j] = random.nextInt();
                matrixB[i][j] = random.nextInt();
            }
        }

        tasks = hazelcastInstance.getMap("tasks");
    }

    @TearDown(Level.Trial)
    public void teardown() {
        hazelcastInstance.shutdown();
    }

    @Benchmark
    public void testDistributedMatrixMultiplication() throws ExecutionException, InterruptedException {
        // Calculate product
        int[][] result = distributedMatrixMultiplication(hazelcastInstance, matrixA, matrixB);

        // Display result
        // System.out.println("Result:");
        // for (int[] row : result) {
            // for (int value : row) {
                 // System.out.print(value + " ");
            // }
            // System.out.println();
        // }
    }

    public static int[][] distributedMatrixMultiplication(HazelcastInstance hazelcastInstance, int[][] matrixA, int[][] matrixB)
            throws ExecutionException, InterruptedException {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        if (colsA != matrixB.length) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication");
        }

        // Use a distributed map to assign tasks
        IMap<Integer, Task> tasks = hazelcastInstance.getMap("tasks");

        // Submit tasks for each row
        for (int i = 0; i < rowsA; i++) {
            tasks.put(i, new Task(i, matrixA[i], matrixB, colsB));
        }

        // Execute tasks and gather results
        int[][] result = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            TaskResult taskResult = tasks.executeOnKey(i, new MatrixMultiplicationExecutor());
            result[i] = taskResult.getResultRow();
        }

        return result;
    }

    // Task to represent computation for a single row
    public static class Task implements java.io.Serializable {
        private int rowIndex;
        private int[] rowA;
        private int[][] matrixB;
        private int colsB;

        public Task(int rowIndex, int[] rowA, int[][] matrixB, int colsB) {
            this.rowIndex = rowIndex;
            this.rowA = rowA;
            this.matrixB = matrixB;
            this.colsB = colsB;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int[] getRowA() {
            return rowA;
        }

        public int[][] getMatrixB() {
            return matrixB;
        }

        public int getColsB() {
            return colsB;
        }
    }

    // Result of a task
    public static class TaskResult implements java.io.Serializable {
        private int rowIndex;
        private int[] resultRow;

        public TaskResult(int rowIndex, int[] resultRow) {
            this.rowIndex = rowIndex;
            this.resultRow = resultRow;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int[] getResultRow() {
            return resultRow;
        }
    }

    // Hazelcast Executor for matrix multiplication
    public static class MatrixMultiplicationExecutor
            implements EntryProcessor<Integer, Task, TaskResult>, java.io.Serializable {

        @Override
        public TaskResult process(Map.Entry<Integer, Task> entry) {
            Task task = entry.getValue();
            int[] rowA = task.getRowA();
            int[][] matrixB = task.getMatrixB();
            int colsB = task.getColsB();

            int[] resultRow = new int[colsB];
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < rowA.length; k++) {
                    resultRow[j] += rowA[k] * matrixB[k][j];
                }
            }
            return new TaskResult(task.getRowIndex(), resultRow);
        }
    }
}