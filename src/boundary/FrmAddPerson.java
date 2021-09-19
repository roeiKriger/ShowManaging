package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import control.LoginControl;
import control.ShibutzControl;
import entity.Custumer;
import entity.Person;
import entity.Theater;
import exceptions.EmptyFieldException;
import exceptions.EqualMailException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FrmAddPerson 
{
	@FXML
	TextField fname;
	@FXML
	TextField lname;
	@FXML
	TextField phoneNum;
	@FXML
	TextField city;
	@FXML
	Button btnBack;
	@FXML
	ImageView personIMG;
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/back.jpg");
			Image img = new Image(input);
			ImageView view = new ImageView(img);
			view.setFitHeight(30);
			view.setPreserveRatio(true);
			btnBack.setGraphic(view);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream input3;
		
		try {
			input3 = new FileInputStream("./photos/person.jpg");
			Image image3 = new Image(input3);
			personIMG.setImage(image3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	@FXML
	public void registerToSystem(MouseEvent event) throws IOException
	{
		try 
		{
			if(!(fname.getText()!=null && lname.getText()!=null && phoneNum.getText()!=null&& city.getText()!=null))
			{
				throw new EmptyFieldException();
			}
			if(fname.getLength()==0 || lname.getLength()==0 || phoneNum.getLength()!=10 || city.getLength()==0)
			{
				throw new EmptyFieldException();
			}
			Person c = new Person(fname.getText(), lname.getText(), phoneNum.getText(), city.getText());
			//adding the Person to DB//
			Person p = new Person(fname.getText(), lname.getText(), phoneNum.getText(), city.getText());
			LoginControl.getInstance().addNewPersonToDB(p);
			Alert alert = new Alert(AlertType.INFORMATION,"Succseed to Register Person");
			alert.setHeaderText("Register Sucssed");
			alert.setTitle("Register Sucssed");
			alert.showAndWait();
			moveToNextPage(); //moving back to login
			
			
		}
		catch (EmptyFieldException e) 
		{
			String s = "One or More Fields are empty";
			Alert alert = new Alert(AlertType.ERROR,s);
			alert.setHeaderText("Register Error");
			alert.setTitle("Register Error");
			alert.showAndWait();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR,"We have problem now, please try again later");
			alert.setHeaderText("Register Error");
			alert.setTitle("Register Error");
			alert.showAndWait();
		}

	}
	
	
	
	//moving to login
	public void moveToNextPage() throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/enterToTheater.fxml"));
		Stage primaryStage = (Stage) lname.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	public void backButton(MouseEvent event) throws IOException
	{
		moveToNextPage();
	}

}
