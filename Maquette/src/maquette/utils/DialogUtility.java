package maquette.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogUtility {


    public static Object showModal(String fxmlPath, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(DialogUtility.class.getResource(fxmlPath));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        return loader.getController();
    }

}
