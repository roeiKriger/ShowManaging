package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import control.ReportControl;
import control.ShibutzControl;
import entity.Person;
import entity.ShowInTheater;
import entity.Theater;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.functions.standard.ReportCategory;

public class FrmFoodReport 
{
	@FXML
	ComboBox events;
	
	@FXML
	Button btnReport;
	
	@FXML
	ImageView productImg;
	
	@FXML
	Button home;
	
	@FXML
	public void initialize() 
	{
		//adding events to combox
		ArrayList<ShowInTheater> allEvents = ShibutzControl.getInstance().getAllEventsToShibutz();
		events.setItems(FXCollections.observableArrayList(allEvents));
		
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/foodReport.png");
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
	
	
	@FXML
	public void doReport(MouseEvent event)
	{
		ShowInTheater sh = (ShowInTheater)events.getSelectionModel().getSelectedItem();
		if(sh!=null)
		{
			JFrame reportFrame =ReportControl.getInstance().produceReport(sh);
			if(reportFrame!=null)
				reportFrame.setVisible(true);
		}

	}

	
	//moving to home screen
	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) btnReport.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
