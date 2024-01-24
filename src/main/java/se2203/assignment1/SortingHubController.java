package se2203.assignment1;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.fxml.Initializable;

public class SortingHubController{

    //private fields from provided diagram
    private int [] intArray;
    private SortingStrategy sortingMethod;

    //all the scene builder fields
    @FXML
    private Label algLabel;

    @FXML
    private Label arrayLabel;

    @FXML
    private Slider slider;

    @FXML
    private ComboBox comboBox;

    @FXML
    private Button sortB;

    @FXML
    private Button resetB;

    @FXML
    private Label sizeLabel;

    @FXML
    private Pane recFrame;

    //the set sort strategy will be activated when the sort button is pressed
    @FXML
    public void setSortStrategy() {

        //if else statement to select the correct sorting method based on the combo box value
        if(comboBox.getValue()=="Merge Sort") {
            //creates a mergesort object
            MergeSort temp = new MergeSort(intArray, this);
            //creates a new thread and starts it
            Thread t1 = new Thread(temp);
            t1.start();
        }else{
            //creates a selectionsort object
            SelectionSort temp= new SelectionSort(intArray,this);
            //creates a new thread and starts it
            Thread t1 = new Thread(temp);
            t1.start();
        }
    }

    //update graph method to implement the red rectangles onto the pane based on the array created
    @FXML
    public void updateGraph(int[] data){

        //clears the pane to allow the new rectangles to be put in
        recFrame.getChildren().clear();

        //gets the height and width of the pane the rectangles are going in
        double widthOfPane = recFrame.getPrefWidth();
        double heightOfPane = recFrame.getPrefHeight();
        double size = data.length;

        //gets the width of the rectangles which will be constant for them all based on the array size
        double width = ((widthOfPane/size)-2);

        //creates new rectangle objects for the length of the array
        Rectangle[] rectangle = new Rectangle[data.length];

        //for loop to create and place the rectangles onto the pane
        for(int i=0; i<size; i++){
            //finds the height for each rectangle based on its value
            double height = (data[i]*heightOfPane)/size;
            //finds the x and y coordinate of each rectangle
            double x = (i*(width+2));
            double y = heightOfPane-height;
            //creates the rectangle will the correct values, makes it red, and places it on the pane
            rectangle[i] = new Rectangle(x,y,width,height);
            rectangle[i].setFill(Color.RED);
            recFrame.getChildren().add(rectangle[i]);
        }
    }

    //this will change the graph
    @FXML
    void changeGraph(){

        //clears the pane
        recFrame.getChildren().clear();

        //creates a new array with the correct length from the slider value
        intArray = new int[(int) slider.getValue()];
        //populates array with values from 1 to the array length
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i+1;
        }
        //randomizes the array values
        randomizeArray(intArray);

        //updates the graph with the new array
        updateGraph(intArray);
    }

    //randomizes the array
    @FXML
    void randomizeArray(int[] array){
        //calls on the random class
        Random random = new Random();

        //for loop to swap the values of the array around so that they are in random order
        for (int i = 0; i < array.length; i++) {
            int swapI = random.nextInt(array.length);
            int temp = array[swapI];
            array[swapI] = array[i];
            array[i] = temp;
        }
    }

    //this will be called when the reset button is pressed
    @FXML
    public void reset(){

        //resets the slider to the right value
        slider.setValue(64);

        //updates the graph to the original array size
        changeGraph();

        //puts the combo box to merge sort
        comboBox.getSelectionModel().selectFirst();
    }

    //initialize method
    @FXML
    public void initialize(){

        //sets the initial slider value
        slider.setValue(64);

        //changes the graph to match the array size
        changeGraph();

        //text property to correspond to the slider value
        sizeLabel.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));

        //adds the options to the combo box
        comboBox.getItems().addAll("Merge Sort", "Selection Sort");

        //puts the combo box to merge sort to start
        comboBox.getSelectionModel().selectFirst();
    }


}