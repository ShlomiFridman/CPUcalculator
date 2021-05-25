package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProcessVBox extends HBox {
	
	TextField enter,runtime,timeQuantum;
	Label name,wait,enLabel,rtLabel,tqLabel;
	private Process pc;
	
	public ProcessVBox(int num) {
		name = new Label(String.format("Process %d", num));
		enLabel = new Label("\tEntered: ");
		rtLabel = new Label("\tRuntime: ");
		tqLabel = new Label("\tTQ: ");
		wait = new Label("\tWait: -");
		enter = new TextField();
		runtime = new TextField();
		timeQuantum = new TextField();
		this.enter.setPrefSize(30,20);
		this.runtime.setPrefSize(30,20);
		this.timeQuantum.setPrefSize(30,20);
		this.getChildren().addAll(name,enLabel,enter,rtLabel,runtime,tqLabel,timeQuantum,wait);
		this.setAlignment(Pos.CENTER);
		VBox.setMargin(this, new Insets(8));
	}
	
	public static int toInt(String str) {
		if (str.isEmpty()) return -1;
		try {
			int res = Integer.parseInt(str);
			return res;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public void updatePc() {
		this.wait.setText("\twait: "+pc.getWait()+"");
	}
	
	public Process getPc() {
		int en,rt,tq;
		en=toInt(enter.getText());
		rt=toInt(runtime.getText());
		tq=toInt(timeQuantum.getText());
		if (en<0 || rt<1)
			return null;
		this.pc = new Process(this.name.getText(),en,rt,tq==-1? rt:tq);
		return pc;
	}

}
