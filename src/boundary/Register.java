package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.LoginControl;
import entity.Custumer;
import exceptions.EmptyFieldException;
import exceptions.EqualMailException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Register 
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
	TextField mail;
	@FXML
	PasswordField password;
	@FXML
	Button btnBack;
	@FXML
	Button btnRegister;
	
	
	@FXML
	public void registerToSystem(MouseEvent event) throws IOException
	{
		try 
		{
			if(!(fname.getText()!=null && lname.getText()!=null && phoneNum.getText()!=null&& city.getText()!=null && mail.getText()!=null&password.getText()!=null))
			{
				throw new EmptyFieldException();
			}
			if(fname.getLength()==0 || lname.getLength()==0 || phoneNum.getLength()!=10 || city.getLength()==0 ||mail.getLength()==0 ||password.getLength()==0)
			{
				throw new EmptyFieldException();
			}
			Custumer c = new Custumer(fname.getText(), lname.getText(), phoneNum.getText(), city.getText(), mail.getText(), password.getText());
			if(LoginControl.getInstance().checkMail(c))
			{
				throw new EqualMailException();
			}
			//adding the custumer to DB//
			LoginControl.getInstance().addNewCutsumerToDB(c);
			Alert alert = new Alert(AlertType.INFORMATION,"You are Succseed to Register, Please LogIn");
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
		} catch (EqualMailException e) {
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
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
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
		Stage primaryStage = (Stage) lname.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	public void backButton(MouseEvent event) throws IOException
	{
		moveToNextPage();
	}

}
