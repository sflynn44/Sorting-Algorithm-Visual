package se2203.assignment1;

import javafx.application.Platform;

public class SelectionSort implements SortingStrategy {

    //private fields for list and controller
    private static int [] list;
    private static SortingHubController controller;

    //constructor that accepts an array and controller
    public SelectionSort(int[] list, SortingHubController temp){
        this.list = list;
        this.controller = temp;
    }

    //run method that will run this sort method when the thread is started
    @Override
    public void run() {this.sort(list);}

    //selection sort method to sort the array values
    public void sort(int[] numbers){

        //gets the array size
        int n = numbers.length;

        //one by one moves the boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            //finds the minimum index in the unsorted array
            int minI = i;
            for (int j = i + 1; j < n; j++)
                if (numbers[j] < numbers[minI])
                    minI = j;

            //swap the found minimum element with the correct element
            int temp = numbers[minI];
            numbers[minI] = numbers[i];
            numbers[i] = temp;

            //using the platform runlater to call on the update graph method and the try catch to update it slowly
            Platform.runLater(() -> controller.updateGraph(list));

            try{
                Thread.sleep(90);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
