package maquette.controller;

import maquette.utils.NavigationUtility;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;


    @FXML
    public void initialize() {
        NavigationUtility.loadContent("/maquette/view/LieuxView.fxml", contentArea);
    }


    @FXML
    private void showLieuxPage() {
        NavigationUtility.loadContent("/maquette/view/LieuxView.fxml", contentArea);
    }

    @FXML
    private void showSpectaclesPage() {
        NavigationUtility.loadContent("/maquette/view/SpectaclesView.fxml", contentArea);
    }

    @FXML
    private void showArtistsPage() {
        NavigationUtility.loadContent("/maquette/view/LieuxView.fxml", contentArea);
    }

    @FXML
    private void showRubriquesPage() {
        NavigationUtility.loadContent("/maquette/view/LieuxView.fxml", contentArea);
    }

    @FXML
    private void showBilletsPage() {
        NavigationUtility.loadContent("/maquette/view/LieuxView.fxml", contentArea);
    }
}
