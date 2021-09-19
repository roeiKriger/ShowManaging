package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import control.orderControl;
import entity.Item;
import exceptions.NegativeNumberException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

public class orderItem 
{
	@FXML
	Button btnNoBuy;
	
	@FXML
	Button btnBuy;
	
	@FXML
	ListView list;
	
	@FXML
	Button home;
	@FXML
	TextField amount;
	@FXML
	ImageView productImg;
	
	@FXML
	public void initialize() 
	{
		ArrayList<Item> items = orderControl.getInstance().getAllItems();
		list.setItems(FXCollections.observableArrayList(items));
		
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
	
	//doing the buy of the item
	public void doItemOrder(MouseEvent event) throws ClassNotFoundException, SQLException
	{
		try {
			Item choose = (Item) list.getSelectionModel().getSelectedItem();
			int amountOfProduct = Integer.parseInt(amount.getText());
			if(amountOfProduct<=0)
				throw new NegativeNumberException();
			orderControl.getInstance().inviteTheItem(choose, amountOfProduct);
			Alert alert = new Alert(AlertType.INFORMATION,"your order is sucssesful");
			alert.setHeaderText("Order Sucssesful");
			alert.setTitle("Order Sucssesful");
			alert.showAndWait();
		}
		catch(NegativeNumberException e)
		{
			Alert alert = new Alert(AlertType.ERROR,"Please Choose Positive Number");
			alert.setHeaderText("Order Item Failed");
			alert.setTitle("Order Item Failed");
			alert.showAndWait();
		}
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR,"Please Choose Integer Number");
			alert.setHeaderText("Order Item Failed");
			alert.setTitle("Order Item Failed");
			alert.showAndWait();
		}
		
	}
	
	public void noBuy(MouseEvent event) throws IOException
	{
		Alert alert = new Alert(AlertType.INFORMATION,"Your order is done, we back to home page");
		alert.setHeaderText("Order Sucssesful");
		alert.setTitle("Order Sucssesful");
		alert.showAndWait();
		moveToHomePage(event);
	}
	
	public void autumaticImage(MouseEvent event)
	{
		Item choose = (Item) list.getSelectionModel().getSelectedItem();
		if(choose!=null)
		{
			FileInputStream input;
			try {
				input = new FileInputStream("./photos/"+choose.getPic());
				Image image = new Image(input);
				productImg.setImage(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@FXML
	public void showImage(MouseEvent event)
	{
		Item choose = (Item) list.getSelectionModel().getSelectedItem();
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/"+choose.getPic());
			Image image = new Image(input);
			productImg.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) btnBuy.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
