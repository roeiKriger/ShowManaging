package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import control.ShibutzControl;
import control.orderControl;
import entity.Item;
import entity.Person;
import entity.Theater;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class enterToTheater 
{
	@FXML
	Button home;
	@FXML
	Button btnEnter;
	@FXML
	ComboBox peopleCom;
	@FXML
	ComboBox hallCom;
	@FXML
	TextField rowTxt;
	@FXML
	TextField seatTxt;
	@FXML
	DatePicker dp;

	@FXML
	public void initialize() 
	{
		//adding people to combox
		ArrayList<Person> people = ShibutzControl.getInstance().getAllPersons();
		peopleCom.setItems(FXCollections.observableArrayList(people));
		
		//adding all Theaters to combo
		ArrayList<Theater> halls = ShibutzControl.getInstance().getAllTheaters();
		hallCom.setItems(FXCollections.observableArrayList(halls));
		
		dp.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate current = LocalDate.now();
	            LocalDate x = current.minusDays(3);
	            setDisable(empty || date.compareTo(x)< 0 || date.compareTo(current)>0);
	        }
	    });
		
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
	
	public void doEnterance(MouseEvent event) throws ClassNotFoundException, SQLException
	{
		try 
		{
			Person p = (Person) peopleCom.getSelectionModel().getSelectedItem();
			Theater t =(Theater) hallCom.getSelectionModel().getSelectedItem();
			int row = Integer.parseInt(rowTxt.getText());
			int col = Integer.parseInt(seatTxt.getText());
			java.sql.Date testDate = Date.valueOf(dp.getValue());
			java.sql.Date today = Date.valueOf(LocalDate.now());
			ShibutzControl.getInstance().addEnterance(today, p, t, row, col, testDate);
			Alert alert = new Alert(AlertType.INFORMATION,"Succseed to Register");
			alert.setHeaderText("Register Sucssed");
			alert.setTitle("Register Sucssed");
			alert.showAndWait();
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR,"failed to add, fill all the fields");
			alert.setHeaderText("add failed");
			alert.setTitle("adding failed");
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
	
	//move to add person
	public void moveToAddPerson(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/FrmAddPerson.fxml"));
		Stage primaryStage = (Stage) home.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	

}
