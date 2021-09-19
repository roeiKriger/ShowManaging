package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/*
 * Created by Matan Mayerowicz & Roei Kriger
 */


public class Main extends Application 
{
		public static void main(String[] args) 
		{
			org.apache.log4j.BasicConfigurator.configure();
			launch(args);
		}


		public void start(Stage primaryStage) throws Exception 
		{
			//which page we want to load next
			Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
			Scene scene = new Scene(root);  //size of window
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);  // resizeable
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setTitle("Show Managing");
			FileInputStream input;
			try {
				input = new FileInputStream("./photos/showManagingICON.JPG");
				Image img = new Image(input);
				primaryStage.getIcons().add(img);  //icon
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) 
				{
					Platform.exit();
					System.exit(0);
				}
			});

		}



} 
