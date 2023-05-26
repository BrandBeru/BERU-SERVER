package org.beru.server.beruserver.view.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Toast {
    public static class FadeDelay{
        public static final int LONG_DURATION = 500;
        public static final int MEDIUM_DURATION = 300;
        public static final int SHORT_DURATION = 100;
    }
    public static class ToastDuration{
        public static final int LONG_DURATION = 2500;
        public static final int MEDIUM_DURATION = 1500;
        public static final int SHORT_DURATION = 500;
    }

    public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay){
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        toastStage.setAlwaysOnTop(true);
        toastStage.setFullScreen(true);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Verdana", 40));
        text.setFill(Color.WHITE);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 50px;");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeLine = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeLine.getKeyFrames().add(fadeInKey1);
        fadeInTimeLine.setOnFinished((ae) -> {
            new Thread(() -> {
                try{
                    Thread.sleep(toastDelay);
                }catch (InterruptedException e){
                    new RuntimeException(e);
                }
                Timeline fadeOutTimeLine = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeLine.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeLine.setOnFinished((ae1) -> toastStage.close());
                fadeOutTimeLine.play();
            }).start();
        });
        fadeInTimeLine.play();
    }public static void makeError(Stage ownerStage, String toastMsg, String toastError, int toastDelay, int fadeInDelay, int fadeOutDelay){
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.setAlwaysOnTop(true);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Verdana", 40));
        text.setFill(Color.WHITE);
        Text error = new Text(toastError);
        error.setFont(Font.font("Verdana", 40));
        error.setFill(Color.RED);
        error.setWrappingWidth(new ScreenInformation(Screen.getPrimary()).getWidth());

        VBox root = new VBox(text,error);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 50px;");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        root.setOnMouseEntered(event -> {});
        root.setOnMouseExited(event -> fade(fadeInDelay,fadeOutDelay, toastDelay, toastStage));


    }
    private static void fade(int fadeInDelay, int fadeOutDelay, int toastDelay, Stage toastStage){
        Timeline fadeInTimeLine = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeLine.getKeyFrames().add(fadeInKey1);
        fadeInTimeLine.setOnFinished((ae) -> {
            new Thread(() -> {
                try{
                    Thread.sleep(toastDelay);
                }catch (InterruptedException e){
                    new RuntimeException(e);
                }
                Timeline fadeOutTimeLine = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeLine.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeLine.setOnFinished((ae1) -> toastStage.close());
                fadeOutTimeLine.play();
            }).start();
        });
        fadeInTimeLine.play();
    }
}
