package maquette;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Maquette extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load the LoginView.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/maquette/view/DashboardView.fxml"));

            // Create the scene and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Add a listener to center the stage after it's shown and fully initialized

            // Set the title and icon
            stage.setTitle("Gestion des spectacles - Jasser Gorsia et Nidhal Chelhi");

            // Show the stage
            stage.show();

            // Add a close request event
            stage.setOnCloseRequest(event -> System.out.println("Application is closing..."));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load the login screen.");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
