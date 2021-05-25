package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main extends Application {
	
	/*
	 *	Shlomi Fridman - ID: 318187002
	 *	Nativ Matuk - ID: 205907090
	 *	Nabih Amer - ID: 205754344
	 *  Elior Libilya - ID: 313442865
	 *  Ashraf Kherbawy - ID: 207261272
	 *  Gal Swisa - ID: 204396170
	 */
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("app.fxml"));
			VBox  root = loader.load();
			Controller controller = loader.getController();
			controller.stage=primaryStage;
			controller.setup();
			primaryStage.setTitle("CPU Calculator");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
//		OrderType t = OrderType.FCFS;
//		LinkedList<Process> l = new LinkedList<>();
//		l.add(new Process("P1",5,6));
//		l.add(new Process("P2",0,2));
//		l.add(new Process("P3",3,4));
//		l.add(new Process("P4",8,3));
//		CPU c = new CPU(t);
//		c.addAll(l);
//		System.out.println("Average = "+c.run());
//		System.out.println("Average = "+c.run());
//		Iterator<Process> it = c.getOriginal().iterator();
//		while (it.hasNext())
//			System.out.println(it.next());
	}
}
