/**
 * 
 */
package org.bitbucket.beatngu13.pdfbookmarkwizard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches the Wizard.
 * 
 * @author danielkraus1986@gmail.com
 *
 */
public class Launcher extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Controller controller = new Controller();
		
		primaryStage.setTitle("PDF Bookmark Wizard");
        primaryStage.setScene(new Scene(controller.getView(), 400.0, 185.0));
        primaryStage.show();
	}

}