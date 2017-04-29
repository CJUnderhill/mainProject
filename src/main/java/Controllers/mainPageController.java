package Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Status: needs review.
 * TODO: change about link to button? - have a function setDisplayToAboutPage in UIController
 */
public class mainPageController extends UIController {
    ArrayList<ImageView> imageArrayList = new ArrayList<>();
    int count = 0;

    @FXML
    private Button loginButton; // This is a different loginButton from UIController's? double check
    @FXML
    private HBox imagebox;
    @FXML
    private Hyperlink aboutLink;

    @FXML
    private void setDisplayToSearch() throws IOException{
        Stage stage;
        stage=(Stage) searchButton.getScene().getWindow();
        BorderPane root = super.main.getBorderPane();

        URL searchPageURL = getClass().getResource("searchPage.fxml");
        FXMLLoader loader = new FXMLLoader();
        ScrollPane pane = loader.load(searchPageURL);
        root.setTop(menuBarSingleton.getInstance().getBar());
        root.setBottom(pane);

        Scene scene = root.getScene();
        stage.setScene(root.getScene());
        stage.show();
        searchPageController controller = loader.getController();
        controller.init(super.main);

        System.out.println("FUCK ME USE THIS!");
    }


    /**
     * Redirects to loginPage.fxml
     * @throws IOException - throws exception
     */
    @FXML
    private void setDisplayToLoginPage() throws IOException{
        BorderPane borderPane = main.getBorderPane();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginPage.fxml"));
        System.out.println(loader.getLocation().getPath());
        AnchorPane anchorPane = loader.load();
        Stage stage;
        stage=(Stage) loginButton.getScene().getWindow();
        Scene scene = borderPane.getScene();
        stage.setScene(scene);
        borderPane.setTop(menuBarSingleton.getInstance().getBar());
        borderPane.setBottom(anchorPane);
        stage.show();
        loginPageController controller = loader.getController();
        controller.init(super.main);
        controller.setActionOnEnter();
    }

    /**
     * Redirects to aboutPage.fxml
     * @throws IOException - throws exception
     */
    @FXML
    private void setDisplayToAboutPageTemp() throws IOException{
        Stage stage;
        Parent root;
        stage=(Stage) aboutLink.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("aboutPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initSlideshow() {
        // load the images
        imageArrayList.add(new ImageView("file:images/AZOI.jpg"));
        System.out.println(imageArrayList.get(0).getImage().isError());
        imageArrayList.get(0).setFitWidth(500);
        imageArrayList.get(0).setFitHeight(500);
        imageArrayList.add(new ImageView("file:images/BIG BLONDE.jpg"));
        System.out.println(imageArrayList.get(1).getImage().isError());
        imageArrayList.get(1).setFitWidth(500);
        imageArrayList.get(1).setFitHeight(500);
        imageArrayList.add(new ImageView("file:images/CHEVALLON.jpg"));
        System.out.println(imageArrayList.get(2).getImage().isError());
        imageArrayList.get(2).setFitWidth(500);
        imageArrayList.get(2).setFitHeight(500);
        imageArrayList.add(new ImageView("file:images/founders.jpg"));
        System.out.println(imageArrayList.get(3).getImage().isError());
        imageArrayList.get(3).setFitWidth(500);
        imageArrayList.get(3).setFitHeight(500);
        imageArrayList.add(new ImageView("file:images/FALORNI.jpg"));
        System.out.println(imageArrayList.get(4).getImage().isError());
        imageArrayList.get(4).setFitWidth(500);
        imageArrayList.get(4).setFitHeight(500);

        imagebox.getChildren().addAll(imageArrayList);

        imagebox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && imageArrayList.get(count).getImage().impl_getUrl().equals("file:src/sample/image/bacardi.jpg")) {
                final Stage dialog = new Stage();
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("This is a Dialog"));
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
    }

    public void startAnimation() {
        //error occurred on (ActionEvent t) line
        //slide action
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            count++;
            System.out.println("slide");
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), imagebox);
            trans.setByX(-500);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
        //eventHandler
        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            count = 0;
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), imagebox);
            trans.setByX((imageArrayList.size() - 1) * 500);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };

        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= imageArrayList.size(); i++) {
            if (i == imageArrayList.size()) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * 5), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * 5), slideAction));
            }
        }

        //add timeLine
        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[imageArrayList.size()]));

        loginButton.setOnMouseClicked(event -> {
            System.out.println("stopping anim");
            if (event.getClickCount() == 1) {
                anim.stop();
                try {
                    setDisplayToLoginPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();

    }
}
