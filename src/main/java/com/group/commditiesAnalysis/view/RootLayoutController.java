package com.group.commditiesAnalysis.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class RootLayoutController {

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private ToggleGroup chartGroup;


    private MainApp mainApp;

    @FXML
    private void initialize (){

    }



    @FXML
    private void handleOpenScrapping (){
        mainApp.showScrappingLayout();
    }

    public void setMainApp (MainApp mainApp){
        this.mainApp = mainApp;
    }


}
