/**
 * 
 */
/**
 * 
 */
module saeMusee {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires java.desktop;
	requires java.logging;
	
    exports application;  // Cette ligne permet l'accès aux classes de ce package pour d'autres modules
    opens application.controleur to javafx.fxml;  // Permet l'accès réfléchi aux contrôleurs JavaFX
}