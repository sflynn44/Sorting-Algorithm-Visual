package se2203.assignment1;

import javafx.application.Platform;

public class MergeSort implements SortingStrategy {

    //private fields for array and controller
    private static int [] list;
    private static SortingHubController controller;

    //constructor for merge sort that accepts an array and controller
    public MergeSort(int[] list, SortingHubController temp){
        this.list = list;
        this.controller = temp;
    }

    //run method that will run this sort method when the thread is started
    @Override
    public void run(){this.sort(list);}

    //sort method that accepts an array (like listed in instructions) and is called on
    public static void sort(int [] numbers){
        //calls on another merge sort method that accepts the array, left most value, and right most value
        mergeSort(numbers,0,numbers.length-1);
    }

    //merge sort that is called on inside the sort method
    public static void mergeSort(int arr[], int left, int right)
    {
        //will only operate if the left value is less than the right value
        if (left<right) {

            //finds the middle point of the array
            int mid = (left + right) / 2;

            //recursive call to sort first and second halves
            //from zero to middle
            mergeSort(arr, left, mid);
            //from middle to the end of the array
            mergeSort(arr, mid + 1, right);

            //calls on the merge method to merge the split array back together
            merge(arr, left, mid, right);
        }
    }

    //merge method that is called on in the mergesort method
    static void merge(int arr[], int sLeft, int mid, int end) {

        //finds the starting index for the second array to the right
        int sRight = mid + 1;

        //two pointers to maintain start of both arrays to merge
        //while loop that ensure the left start is less than the mid and the right start is less than the end
        while (sLeft <= mid && sRight <= end) {

            //will run if element 1 is in right place
            if (arr[sLeft] <= arr[sRight]) {
                //increment left starting point
                sLeft++;
            }
            else {
                //temp variables
                int value = arr[sRight];
                int tempI = sRight;

                //shift the elements into the correct order
                while (tempI != sLeft) {
                    arr[tempI] = arr[tempI - 1];
                    tempI--;
                }
                arr[sLeft] = value;

                //update all the index pointers
                sLeft++;
                mid++;
                sRight++;
            }

            //using the platform runlater to call on the update graph method and the try catch to update it slowly
            Platform.runLater(() -> controller.updateGraph(list));

            try{
                Thread.sleep(20);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
