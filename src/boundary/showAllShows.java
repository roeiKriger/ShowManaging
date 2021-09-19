package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import control.ShibutzControl;
import control.orderControl;
import entity.ShowInTheater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class showAllShows 
{
	@FXML
	DatePicker from;

	@FXML
	DatePicker to;

	@FXML
	Button search;

	@FXML
	ListView list;

	@FXML
	Button order;
	@FXML
	Button home;

	ArrayList<ShowInTheater> allEvents; 
	ArrayList<String> allCity; 
	
	@FXML
	private ComboBox combCity;

	@FXML
	public void initialize() 
	{
		allEvents = orderControl.getInstance().getAllEvents();
		allCity = ShibutzControl.getInstance().getAllCities();
		combCity.setItems(FXCollections.observableArrayList(allCity));
		combCity.getSelectionModel().select(0);
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image img = new Image(input);
			ImageView view = new ImageView(img);
			view.setFitHeight(30);
			view.setPreserveRatio(true);
			home.setGraphic(view);
			from.setDayCellFactory(param -> new DateCell() {
		        @Override
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            LocalDate current = LocalDate.now();
		            setDisable(empty || date.compareTo(current)< 0 );
		        }
		    });
			to.setDayCellFactory(param -> new DateCell() {
		        @Override
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            LocalDate current = LocalDate.now();		           
		            setDisable(empty || date.compareTo(current)< 0 );
		        }
		    });
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	public void onSearchClick(MouseEvent event)
	{
		list.setItems(FXCollections.observableArrayList(eventByFilter()));
	}


	//this method return all the events by the filter you choose
	public ArrayList<ShowInTheater> eventByFilter()
	{

		String cityName = null;
		Date fromD = null;
		Date toD =null;
		if(combCity.getSelectionModel().getSelectedItem()!=null)
		{
			cityName = (String) combCity.getSelectionModel().getSelectedItem();
		}
		
		
		if(from.getValue() != null)
			fromD =java.sql.Date.valueOf(from.getValue());
		if(to.getValue() != null)
			toD =java.sql.Date.valueOf(to.getValue());
		ArrayList<ShowInTheater> byfilter = new ArrayList<ShowInTheater>();
		Date today = java.sql.Date.valueOf(LocalDate.now());

		//its can start only from today
		if(fromD !=null && fromD.before(today))
		{
			fromD = today;
		}

		for(ShowInTheater sh : allEvents)
		{
			//case by only dates
			if(cityName==null && fromD!=null & toD!=null)
			{
				if(sh.getDate().after(fromD) && sh.getDate().before(toD))
				{
					byfilter.add(sh);
				}
			}
			//case by all of them
			else if(cityName != null && fromD != null && toD !=null)
			{
				if(sh.getTheater().getCity().equals(cityName) && sh.getDate().after(fromD) && sh.getDate().before(toD))
				{
					byfilter.add(sh);
				}
			}
			//case by only city
			else if(cityName != null && fromD == null && toD ==null)
			{
				fromD = today;
				if(sh.getTheater().getCity().equals(cityName) && sh.getDate().after(fromD))
				{
					byfilter.add(sh);
				}
			}
			//case by only dates
			else if(cityName!=null && fromD!=null & toD==null)
			{
				if(sh.getDate().after(fromD) && sh.getTheater().getCity().equals(cityName) )
				{
					byfilter.add(sh);
				}
			}

		}

		return byfilter;



	}

	//this method on click on invite save the choose event and move to next screen
	@FXML
	public void onOrderBuuton(MouseEvent event) throws IOException
	{
		ShowInTheater myChoose = (ShowInTheater) list.getSelectionModel().getSelectedItem();
		orderControl.getInstance().setChoose(myChoose);
		moveToNextPage();
	}
	
	
	public void moveToNextPage() throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/orderShow.fxml"));
		Stage primaryStage = (Stage) list.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

	public void moveToHomePage(MouseEvent event) throws IOException
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) list.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
