package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import control.ExportControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FrmXMLExport 
{
	@FXML
	Button home;//back home page button
	@FXML
	DatePicker startDP; //date picker of start date
	@FXML
	DatePicker endDP; //date picker of end date
	@FXML
	Button btnExport; //export button
	@FXML
	ImageView productImg;
	
	@FXML
	public void initialize() 
	{
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/xmlExport.png");
			Image image2 = new Image(input2);
			productImg.setImage(image2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image img = new Image(input);
			ImageView view = new ImageView(img);
			view.setFitHeight(30);
			view.setPreserveRatio(true);
			home.setGraphic(view);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startDP.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate current = LocalDate.now();
	            setDisable(empty || date.compareTo(current)>0);
	        }
	    });
		
		endDP.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate current = LocalDate.now();
	            setDisable(empty || date.compareTo(current)>0);
	        }
	    });
	}
	
	public void doingExport(MouseEvent event) 
	{
		try {
		java.sql.Date start = Date.valueOf(startDP.getValue());
		java.sql.Date end = Date.valueOf(endDP.getValue());
		Boolean flag = ExportControl.getInstance().exportEntranceToXML(start, end);
		if(flag)
		{
			Alert alert = new Alert(AlertType.INFORMATION, "Sucsses Export");
			alert.setHeaderText("Sucsses Export");
			alert.setTitle("Sucsses Export");
			alert.showAndWait();
		}
		else
		{
			if(start==null || end == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please check the values");
			alert.setHeaderText("Failed Export");
			alert.setTitle("Failed Export");
			alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "There are not hall enter to update");
				alert.setHeaderText("No Export");
				alert.setTitle("No Export");
				alert.showAndWait();
			}
			
		}
		}
		catch(NullPointerException e )
		{
			Alert alert = new Alert(AlertType.ERROR, "Please check the values");
			alert.setHeaderText("Failed Export");
			alert.setTitle("Failed Export");
			alert.showAndWait();
		}
		catch(Exception e )
		{
			Alert alert = new Alert(AlertType.ERROR, "Please check the values");
			alert.setHeaderText("Failed Export");
			alert.setTitle("Failed Export");
			alert.showAndWait();
		}
	}
	
	//moving to home screen
	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) home.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
