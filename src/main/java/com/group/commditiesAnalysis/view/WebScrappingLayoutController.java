package com.group.commditiesAnalysis.view;

import com.group.commditiesAnalysis.Utils.WebScrapping.CommentScrapping;
import com.group.commditiesAnalysis.Utils.WebScrapping.Search;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WebScrappingLayoutController {
//    CheckBoxTr
    @FXML
    private Button scrappingButton;

    @FXML
    private TextField keywordField;

    @FXML
    private TextArea cookieField;

    @FXML
    private TextArea consoleField;
    private PrintStream out;

    @FXML
    private TreeView<String> resultView;

    @FXML
    private ComboBox<String> sortParameterBox;

    private CommentScrapping scrapping;
    private String targetDirectory;
    private String sortParam;


    private ArrayList<String> searchResultTB;
    private ArrayList<String> searchResultTM;

    private ArrayList<CheckBoxTreeItem<String>> targetAddrTB;
    private ArrayList<CheckBoxTreeItem<String>> targetAddrTM;

//    private Integer[] timePeriod;

    private MainApp mainApp;

//    private Task<Void> scrappingTargetAddrTask;

    @FXML
    private void initialize (){
        targetDirectory = System.getProperty("user.dir");
        System.out.println("initial");
        cookieField.setWrapText(true);

        targetAddrTB = new ArrayList<>();
        targetAddrTM = new ArrayList<>();

        sortParameterBox.getItems().clear();
        sortParameterBox.getItems().addAll("sale", "default", "credit");

        out = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        }, true);
        System.setOut(out);

        // root
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>();
//        result.setValue("Scrapping result");
        root.setExpanded(true);

        // result
        CheckBoxTreeItem<String> result = makeCheckBranch("result", root);
        resultView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        resultView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultView.setRoot(root);
        resultView.setShowRoot(false);



//        scrappingTargetAddrTask = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                scrappingTarget(this);
//                return null;
//            }
//        };
    }

    @FXML
    private void handleSetExportPath (){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        directoryChooser.setTitle("Choose the directory to store the scrapping result");
        File selectedDirectory = directoryChooser.showDialog(scrappingButton.getScene().getWindow());
        if (selectedDirectory != null){
            if (selectedDirectory.isDirectory()){
                if (selectedDirectory.canWrite() && selectedDirectory.canRead()) {
                    targetDirectory = selectedDirectory.getPath();
                } else {
                    System.out.println("Permission delay!!!");
                }
            } else {
                System.out.println("It is not a directory");
            }
        } else {
            System.out.println("Indicate new directory failed");
        }
    }

//    private TreeItem<String> makeBranch (String title, TreeItem<String> parent){
//        TreeItem<String> item = new TreeItem<>(title);
//        item.setExpanded(true);
//        parent.getChildren().add(item);
//        return item;
//    }

    private CheckBoxTreeItem<String> makeCheckBranch (String title, TreeItem<String> parent){
        CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(title);
        parent.getChildren().add(item);
        item.setExpanded(true);
        return item;
    }

    private void appendText (String str){
        Platform.runLater(() -> consoleField.appendText(str));
    }
//
//    private void handleComboBox (){
//        sortParameterBox.setOnAction(e -> setSortParam(sortParameterBox.getValue()));
//    }

    @FXML
    private void handleRemove (){
        // remove single address
        resultView.getRoot().getChildren().forEach(
                i -> i.getChildren().forEach(
                        i1 -> i1.getChildren().forEach(
                                i2-> i2.getChildren().removeIf(
                                        i3 -> ((CheckBoxTreeItem)i3).isSelected()
                                )
                        )
                )
        );

        // remove taobao tmall directory
        resultView.getRoot().getChildren().forEach(
                i -> i.getChildren().forEach(
                        i2-> i2.getChildren().removeIf(
                                i1 -> ((CheckBoxTreeItem)i1).isSelected())));

        // we can remove the big catagory
        resultView.getRoot().getChildren().forEach(
                i-> i.getChildren().removeIf(
                        i2 -> ((CheckBoxTreeItem)i2).isSelected()
                )
        );
    }

    @FXML
    private void handleSearch (){
        String keyword = keywordField.getText();
        String cookie = cookieField.getText();
        Search s = new Search(keyword, sortParam, cookie);
        searchResultTB = s.getTBCommoditiesList();
        searchResultTM = s.getTMCommoditiesList();
        CheckBoxTreeItem<String> searchName, taobao, tmall;
        searchName = makeCheckBranch(keyword, resultView.getRoot().getChildren().get(0));
        taobao = makeCheckBranch("taobao", searchName);
        for (String tb : searchResultTB){
            // add all the checkBoxTreeItem to arraylist
            targetAddrTB.add(makeCheckBranch(tb, taobao));
        }

        tmall = makeCheckBranch("tmall", searchName);
//        System.out.println(tmall.getParent());
        for (String tm : searchResultTM){
            targetAddrTM.add(makeCheckBranch(tm, tmall));
        }

        System.out.println("Search complete, please select the item address you want to scrapping");
    }

//    private void scrappingTarget (Task<Void> task){
//        while (true) {
//            if (task.isCancelled()){
//                System.out.println("Cancelling");
//                break;
//            } else {
//                System.out.println("Working");
//            }
//        }
//
//    }
    @FXML
    private void handleScrapping (){
        scrappingButton.setDisable(true);

        Timer timer = new Timer("task", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<String> listTB = new ArrayList<>();
                ArrayList<String> listTM = new ArrayList<>();
                for (CheckBoxTreeItem<String> t : targetAddrTB) {
                    if (t.isSelected())
                        listTB.add(t.getValue());
                }
                for (CheckBoxTreeItem<String> t : targetAddrTM) {
                    if (t.isSelected())
                        listTM.add(t.getValue());
                }

                System.out.println("Number of link selected on TaoBao: " + listTB.size());
                System.out.println("Number of link selected on TMall: " + listTM.size());

                try {
                    CommentScrapping cs = new CommentScrapping(listTB, listTM, targetDirectory, cookieField.getText(), keywordField.getText());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        scrappingButton.setDisable(false);
                    }
                });
            }
        } , 1000, 1000 * 60 * 60 * 24);

    }

    public void setMainApp (MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handlePeriod (){

    }

    @FXML
    private void setSortParam () {
        String sortParam = sortParameterBox.getValue();
        if (sortParam.equals("credit")){
            this.sortParam = "credit-desc";
            System.out.println("Set sort Parameter to credit-desc");
        } else if (sortParam.equals("default")){
            this.sortParam = "default";
            System.out.println("Set sort parameter to default");
        } else {
            this.sortParam = "sale-desc";
            System.out.println("Set sort parameter to sale-desc");
        }
    }
}
