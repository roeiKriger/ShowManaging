package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import control.LoginControl;
import control.ShibutzControl;
import entity.Admin;
import entity.Custumer;
import entity.Person;
import entity.Theater;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogInScreen 
{
	@FXML
	TextField userName;
	
	@FXML
	PasswordField password;
	
	@FXML
	Button btnLogIn;
	@FXML
	Button btnRegister;
	@FXML
	ImageView logoCompany;
	@FXML
	ImageView productImg;
	@FXML
	ImageView helpIM;
	
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image image = new Image(input);
			logoCompany.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/loginImg.jpg");
			Image image2 = new Image(input2);
			productImg.setImage(image2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream input3;
		
		try {
			input3 = new FileInputStream("./photos/help.png");
			Image image3 = new Image(input3);
			helpIM.setImage(image3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tooltip.install(helpIM, new Tooltip("For customers username is mail address"));
		
		
		
	}

	
	//login method
	public void login(MouseEvent event) throws IOException
	{
		if(isCanLogin())
		{
			moveToNextPage();
			
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
			alert.setHeaderText("Login Error");
			alert.setTitle("Login Error");
			alert.showAndWait();
		}
	}
	
	
	
	//this method return true if can login, else false
	public boolean isCanLogin()
	{
		Custumer c= new Custumer(userName.getText(), password.getText());
		ArrayList<Custumer> allCustumers = LoginControl.getInstance().getAllCustumers();
		for(Custumer cus: allCustumers)
		{
			if(cus.equals(c)) //if userName and password exist
			{
				LoginControl.getInstance().setLoginCustumer(cus); //save the login member
				LoginControl.getInstance().setHuman(cus);
				return true;
			}
		}
		
		Admin d= new Admin(userName.getText(), password.getText());
		
		for(Admin ad: LoginControl.getInstance().getAllAdminsUser()) // admin login
		{
			if(ad.equals(d))
			{
				LoginControl.getInstance().setHuman(ad);
				return true;
			}
		}

		return false;
	}
	
	public void moveToNextPage() throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) userName.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	
	public void moveToRegister() throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/Register.fxml"));
		Stage primaryStage = (Stage) userName.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
