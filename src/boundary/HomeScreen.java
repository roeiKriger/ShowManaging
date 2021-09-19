package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import control.LoginControl;
import entity.Custumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeScreen 
{
	@FXML
	Label hii;

	@FXML
	Button btnJSONImp;
	
	@FXML
	Button btnOrderEvent;
	
	@FXML
	Button btnReport;
	@FXML
	Button btnShibutz;
	@FXML
	Button btnExportXML;
	@FXML
	Button btnEnterHall;
	@FXML
	ImageView logoCompany;
	
	
	
	@FXML
	public void initialize() 
	{
		String helloText =""; 
		hii.setText(helloText);
		boolean adminLogin;
		if(LoginControl.getInstance().getHuman() instanceof Custumer) // custumer login
		{
			adminLogin = false;
			helloText = "Hello dear " + LoginControl.getInstance().getHuman().getfName() + ", welcome, time to order an event!";
		}
		else // admin login
		{
			adminLogin = true;
			helloText = "Hello dear " + LoginControl.getInstance().getHuman().getfName() + ", select the action you would like to do";
		}
		btnJSONImp.setVisible(adminLogin);
		btnOrderEvent.setVisible(!adminLogin);
		btnReport.setVisible(adminLogin);
		btnShibutz.setVisible(adminLogin);
		btnExportXML.setVisible(adminLogin);
		btnEnterHall.setVisible(adminLogin);
	
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image image = new Image(input);
			logoCompany.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@FXML
	public void jsonPage(MouseEvent event) throws IOException
	{
		movePage("FrmImportUpdates");
	}
	
	@FXML
	public void orderPage(MouseEvent event) throws IOException
	{
		movePage("showAllShows");
	}
	
	@FXML
	public void shibutzPage(MouseEvent event) throws IOException
	{
		movePage("Shibutz");
	}
	
	@FXML
	public void enterHallPage(MouseEvent event) throws IOException
	{
		movePage("enterToTheater");
	}
	
	@FXML
	public void foodReportPage(MouseEvent event) throws IOException
	{
		movePage("FrmFoodReport");
	}
	
	@FXML
	public void XMLExportPage(MouseEvent event) throws IOException
	{
		movePage("FrmXMLExport");
	}
	
	@FXML
	public void movePage(String page) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+page+".fxml"));
		Stage primaryStage = (Stage) hii.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
}
