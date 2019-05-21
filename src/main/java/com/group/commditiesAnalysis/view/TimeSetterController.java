package com.group.commditiesAnalysis.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TimeSetterController {

    @FXML
    private ComboBox<Integer> hoursBox;

    private Integer hours;

    @FXML
    private ComboBox<Integer> minsBox;

    private Integer mins;

    @FXML
    private ComboBox<Integer> daysBox;

    private Integer days;

    private Integer[] result;

    private MainApp mainApp;
    private Stage thisStage;

    @FXML
    private void initialize (){
        Integer[] hoursArray = new Integer[24];
        for (int i = 0; i < 24; ++ i){
            hoursArray[i] = i;
        }

        Integer[] minsArray = new Integer[60];
        for (int i = 0; i < 60; ++ i){
            minsArray[i] = i;
        }

        Integer[] daysArray = new Integer[7];
        for (int i = 0; i < 7; ++ i){
            daysArray[i] = i;
        }

        hoursBox.getItems().addAll(hoursArray);

        minsBox.getItems().addAll(minsArray);

        daysBox.getItems().addAll(daysArray);

        result = new Integer[4];
    }

    public void setDialogStage(Stage dialogStage) {
        this.thisStage = dialogStage;
    }

    @FXML
    private void handleHoursBox () {
        hours = hoursBox.getValue();
    }

    @FXML
    private void handleMinsBox (){
        mins = minsBox.getValue();
    }

    @FXML
    private void handleDaysBox (){
        days = daysBox.getValue();
    }

    public void getResult (Integer[] result){
        result = this.result;
    }

    @FXML
    private void handleOkButton (){
        result[0] = hours;
        result[1] = mins;
        result[2] = days;
        result[3] = 1;

        thisStage.close();
    }

    @FXML
    private void handleCancelButton (){
        result[3] = 0;
        thisStage.close();
    }

    public void setMainApp (MainApp mainApp){
        this.mainApp = mainApp;
    }
}
