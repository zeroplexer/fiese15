package ch.bfh.fiese15;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = loadFxmlLoader("loadNewGame");
						
			Parent root = (Parent) loader.load();
			
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Fiese15");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/logo.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    private static FXMLLoader loadFxmlLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scene/" + fxml + ".fxml"));
        return fxmlLoader;
    }
		
	public static void main(String[] args) {
		launch(args);
	}

}
