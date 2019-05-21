package com.group.commditiesAnalysis.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane webScrapping;

    @Override
    public void start (Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WebScrapping");

        initialWebScrappingLayout();
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
    public static void main(String[] args) {
        launch(args);
    }
}
