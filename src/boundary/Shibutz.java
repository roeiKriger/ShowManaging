package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import control.ShibutzControl;
import control.orderControl;
import entity.Item;
import entity.Seat;
import entity.ShowInTheater;
import entity.TicketBuyers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Shibutz 
{
	@FXML
	ListView eventsList;
	
	@FXML
	ListView custumersList;
	@FXML
	TextField seatTxt;
	@FXML
	TextField rowTxt;
	@FXML
	ListView avList;
	@FXML
	Button home;
	
	@FXML
	public void initialize() 
	{
		//seeing all events he can to shibutz
		ArrayList<ShowInTheater> events = ShibutzControl.getInstance().getAllEventsToShibutz();
		Collections.sort(events, new Comparator<ShowInTheater>() {
			  public int compare(ShowInTheater o1, ShowInTheater o2) {
			      if (o1.getDate() == null || o2.getDate() == null)
			        return 0;
			      return o1.getDate().compareTo(o2.getDate());
			  }
			});
		eventsList.setItems(FXCollections.observableArrayList(events));
		
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
	}
	
	
	
	@FXML
	public void getAllBuyers(MouseEvent event) throws IOException
	{
		//all custumer the admin need to shibutz
		ShowInTheater showEvent = (ShowInTheater) eventsList.getSelectionModel().getSelectedItem();
		ArrayList<TicketBuyers> buyers = ShibutzControl.getInstance().getAllTicketBuyers(showEvent);
		custumersList.setItems(FXCollections.observableArrayList(buyers));
		ArrayList<Seat> avaliableSeats = ShibutzControl.getInstance().allAvalibleSeats(showEvent.getTheater());
		avList.setItems(FXCollections.observableArrayList(avaliableSeats));
	}
	
	
	@FXML
	public void doingShibutz(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
	{
		int row = Integer.parseInt(rowTxt.getText());
		//System.out.println(row);
		int seat = Integer.parseInt(seatTxt.getText());
		ShowInTheater showEvent = (ShowInTheater) eventsList.getSelectionModel().getSelectedItem();
		//Seat s= new Seat(row, seat, showEvent.getTheater());
		Boolean flag = ShibutzControl.getInstance().doShibutz(showEvent.getTheater(), row, seat);
		if(flag)
		{//sucssesful shibutz
			Alert alert = new Alert(AlertType.INFORMATION,"We sucssed to shibutz");
			alert.setHeaderText("Shibutz Sucssed");
			alert.setTitle("Shibutz Sucssed");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"Shibutz Faild, please check your row and seat");
			alert.setHeaderText("Shibutz Error");
			alert.setTitle("Shibutz Error");
			alert.showAndWait();
		}
		
	}
	
	
	//moving to home screen
	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) eventsList.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
