package sample.main;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.Controller;

public class Main extends Application {

    /**
     * project给出的默认值
     * @throws Exception
     */
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }

    @Override
    public void start(Stage window) throws Exception{
        new Controller().exec();
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 1400, 700));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

