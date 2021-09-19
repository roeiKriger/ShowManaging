package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import control.ImportControl;
import entity.ShowInTheater;
import entity.TicketBuyers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FrmImportUpdates 
{

	@FXML
	ListView peopleList;
	
	@FXML
	Button importJson;
	
	@FXML
	Button home;
	
	@FXML
	Button btnSend;
	
	@FXML
	Button notifyButton;
	
	@FXML
	Label messageLbl;
	
	@FXML
	Button btnMail;

	
	
	@FXML
	public void initialize() 
	{
		btnMail.setVisible(false);
		home.setVisible(true); 
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
	
	//this method will happen after pressing the import JSON button
	@FXML
	public void onButtonClick(MouseEvent event)
	{
		ArrayList<TicketBuyers> tbList = ImportControl.getAllNeedToCall();
		addToList(tbList);		
	}
	
	//this method is for adding to listView the customers
	@FXML
	private void addToList(ArrayList<TicketBuyers> tbList)
	{
		peopleList.setItems(FXCollections.observableArrayList(tbList));	
	}
	
	//on pressing of the button, we will notify with a message on a label to the customer all the changes
	@FXML
	private void notifyUser(MouseEvent event)
	{
		messageLbl.setText("");
		String message;
		String updateMessage;
		
		if(peopleList.getSelectionModel().getSelectedItem()!=null) // select from list
		{
			TicketBuyers selectBuyer = (TicketBuyers) peopleList.getSelectionModel().getSelectedItem();
		
			if(ImportControl.getCustmersCantSeat().contains(selectBuyer)|| selectBuyer.getMyShow().getStatus().equals("canceled"))
			{//if the show is canceled or he didnt have place after the new Corona changes
				updateMessage="Unfornatnatly we had to cancel your order";			
			}
			else
			{
				updateMessage="Unfornatnatly we had to update your order Details. \nThis is the new details: \n" +selectBuyer.getMyShow();
			}
			message = "Dear " + selectBuyer.getFname() + ",\n"+updateMessage+"\nThis is your show recommendations if you would like to choose a new show: \n";
			ArrayList<ShowInTheater> recoList = ImportControl.recommendUserNewDetails(selectBuyer);
			
			for(ShowInTheater sIn:recoList)
			{
				message+=sIn+"\n";
			}
			
			if(recoList.isEmpty())
			{
				message="Dear " + selectBuyer.getFname() + ",\n"+updateMessage+"\nUnfortently we don't have recommendations for you :( \n";
			}
		}
		else
		{
			message="Please select custumer to call";
		}
		
		messageLbl.setText(message);	
	}
	
	@FXML
	public void moveToNextPage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) messageLbl.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
