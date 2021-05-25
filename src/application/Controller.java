package application;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private VBox vbox;

    @FXML
    private TextField tf;

    @FXML
    private Button go;

    @FXML
    private Button run;

    @FXML
    private Label info;

    @FXML
    public Pane pane;
    
    @FXML
    public ChoiceBox<OrderType> cb;
    
    private ProcessVBox arr[];
    private CPU cpu;
    public Stage stage;
    
    public void setup() {
		VBox.setMargin(pane, new Insets(10));
		cb.getItems().addAll(OrderType.values());
		cb.setValue(OrderType.FCFS);
    	cb.setOnAction(ev->{
    		if (arr==null) return;
    		for (ProcessVBox vb:arr) {
    	    	vb.tqLabel.setVisible(cb.getValue()==OrderType.RR);
    	    	vb.timeQuantum.setVisible(cb.getValue()==OrderType.RR);
    		}
    	});
    	tf.setOnAction(ev->start(null));
    }
    
    @FXML
    void start(MouseEvent event) {
    	info.setText("");
    	int numPc;
    	try {
    		numPc = ProcessVBox.toInt(tf.getText());
    		if(numPc <= 0) throw new Exception("Invalid num value");
    	} catch(Exception e) {
//    		info.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(15), new Insets(0))));
    		info.setAlignment(Pos.CENTER);
//    		info.setStyle("-fx-font-weight: bold");
    		info.setText("Invalid number of process");
//    		e.printStackTrace();
    		return;
    	}
    	info.setStyle(null);
    	if (arr!=null)
    		for (ProcessVBox tmp:arr)
    			vbox.getChildren().remove(tmp);
    	arr = new ProcessVBox[numPc];
    	for (int i=0;i<arr.length;i++) {
    		arr[i] = new ProcessVBox(i+1);
    		arr[i].tqLabel.setVisible(cb.getValue()==OrderType.RR);
    		arr[i].timeQuantum.setVisible(cb.getValue()==OrderType.RR);
    		vbox.getChildren().add(arr[i]);
    	}
    	vbox.setAlignment(Pos.TOP_CENTER);
    	stage.sizeToScene();
    }
    
    @FXML
    void runCPU(MouseEvent event) {
    	if(arr == null) return;
    	Process pc[] = new Process[this.arr.length];
    	for(int i=0;i<arr.length;i++) {
    		pc[i] = arr[i].getPc();
    		if(pc[i] == null) {
    			info.setText("Invalid Input");
    			info.setAlignment(Pos.CENTER);
//    			info.setStyle("-fx-font-weight: bold");
//    			info.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(15), new Insets(0))));
    			return;
    		}
    	}
    	cpu = new CPU(cb.getValue(),pc);
    	info.setText(String.format("The average is %.2fms",cpu.run()));
    	for (ProcessVBox tmp:arr)
    		tmp.updatePc();
    	stage.sizeToScene();
    }
    
}
	


