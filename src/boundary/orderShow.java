package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import control.orderControl;
import exceptions.NegativeNumberException;
import exceptions.ticketNumberException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class orderShow 
{
	@FXML
	TextField ticketNum;
	@FXML
	Button btnInvite;
	
	@FXML
	ImageView productImg;
	@FXML
	Button home;
	
	
	@FXML
	public void initialize() 
	{
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/tickets.png");
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
	}
	
	
	
	//this method do the order of the tickets
	public void setOrder(MouseEvent event) throws ClassNotFoundException, SQLException, IOException
	{
		try 
		{
			int numberOfTickets = Integer.parseInt(ticketNum.getText());
			if(numberOfTickets<=0) // the numbers needs to be positive
			{
				throw new NegativeNumberException();
			}
			Boolean flag = orderControl.getInstance().orderShow(numberOfTickets, orderControl.getInstance().getChoose());
			if(flag)
			{
				Alert alert = new Alert(AlertType.INFORMATION,"The Order is scussed");
				alert.setHeaderText("Order Sucssesful");
				alert.setTitle("Order Sucssesful");
				alert.showAndWait();
				moveToNextPage();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR,"We have problem with this order, please try again");
				alert.setHeaderText("Order Canceled");
				alert.setTitle("Order Canceledr");
				alert.showAndWait();
			}
		}
		catch (ticketNumberException e) 
		{
			Alert alert = new Alert(AlertType.ERROR,"You Canot chose " + Integer.parseInt(ticketNum.getText()) + " on this event");
			alert.setHeaderText("Order Canceled");
			alert.setTitle("Order Canceledr");
			alert.showAndWait();
		}
		catch(NegativeNumberException e)
		{
			Alert alert = new Alert(AlertType.ERROR,"You can only possitive numbers");
			alert.setHeaderText("Order Canceled");
			alert.setTitle("Order Canceledr");
			alert.showAndWait();
		}
	}
	
	//moving to orderItem
	public void moveToNextPage() throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/orderItem.fxml"));
		Stage primaryStage = (Stage) btnInvite.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//moving to home screen
	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) btnInvite.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	
	

}
