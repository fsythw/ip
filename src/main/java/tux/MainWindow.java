package tux;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Tux tux;

    private Image tuxImage = new Image(this.getClass().getResourceAsStream("/images/tux.png"));
    private Image toadImage = new Image(this.getClass().getResourceAsStream("/images/goose.png"));

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getTuxDialog("Hello! I'm Tux. What can I do for you?", tuxImage) // add duke's greeting
        );
    }

    /** Injects the Tux instance */
    public void setTux(Tux t) {
        tux = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Tux's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = tux.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, toadImage),
                DialogBox.getTuxDialog(response, tuxImage)
        );
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        }
    }


}
