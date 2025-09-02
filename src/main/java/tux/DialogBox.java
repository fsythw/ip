package tux;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class DialogBox extends HBox {

    @FXML
    private Label dialog;
//    @FXML
//    private ImageView displayPicture;

    public DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);

    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    public static DialogBox getUserDialog(String s) {
        return new DialogBox(s);
    }

    public static DialogBox getDukeDialog(String s) {
        var db = new DialogBox(s);
        db.flip();
        return db;
    }

//    private void changeDialogStyle(String commandType) {
//        switch(commandType) {
//        case "AddCommand":
//            dialog.getStyleClass().add("add-label");
//            break;
//        case "ChangeMarkCommand":
//            dialog.getStyleClass().add("marked-label");
//            break;
//        case "DeleteCommand":
//            dialog.getStyleClass().add("delete-label");
//            break;
//        default:
//            // Do nothing
//        }
//    }


}
