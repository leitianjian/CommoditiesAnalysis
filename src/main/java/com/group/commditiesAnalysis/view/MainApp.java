package com.group.commditiesAnalysis.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane webScrapping;
    private BorderPane rootLayout;
    private AnchorPane timeSetting;

    @Override
    public void start (Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WebScrapping");

        initialWebScrappingLayout();
//        initialRootLayout();
    }

    public void showScrappingLayout (){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("fxml/WebScrappingLayout.fxml"));
            webScrapping = (BorderPane) loader.load();

            // creating stage
            Stage webScrappingStage = new Stage();
            webScrappingStage.setTitle("Web Scrapping Tool");
            webScrappingStage.initModality(Modality.WINDOW_MODAL);
            webScrappingStage.initOwner(primaryStage);
            Scene scene = new Scene(webScrapping);
            webScrappingStage.setScene(scene);

            WebScrappingLayoutController controller = loader.getController();
            controller.setMainApp(this);

            webScrappingStage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showTimerSettingLayout (Integer[] result){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("fxml/TimeSetter.fxml"));
            timeSetting = (AnchorPane) loader.load();

            // creating stage
            Stage webScrappingStage = new Stage();
            webScrappingStage.setTitle("Time Setting");
            webScrappingStage.initModality(Modality.WINDOW_MODAL);
            webScrappingStage.initOwner(primaryStage);
            Scene scene = new Scene(timeSetting);
            webScrappingStage.setScene(scene);

            TimeSetterController controller = loader.getController();
            controller.setMainApp(this);
            controller.getResult(result);

            webScrappingStage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initialWebScrappingLayout (){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().
                    getResource("fxml/WebScrappingLayout.fxml"));

            webScrapping = (BorderPane) loader.load();

            Scene scene = new Scene(webScrapping);
            primaryStage.setScene(scene);

            WebScrappingLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initialRootLayout (){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("fxml/RootLayout.fxml"));

            rootLayout = (BorderPane) loader.load();
            Scene sc = new Scene (rootLayout);
            primaryStage.setScene(sc);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
