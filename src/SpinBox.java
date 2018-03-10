import java.awt.AWTException;
import java.awt.Robot;



import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.event.*;
public class SpinBox extends HBox{
	CustomTF valTf = new CustomTF();
	CustomTF stpTf = new CustomTF();
	CustomTF facTf = new CustomTF();
	boolean swi = true;
	public SpinBox(){
		super();
		valTf.steplen.bindBidirectional(stpTf.val);
		valTf.factor.bindBidirectional(facTf.val);
		stpTf.val.set(1.0);
		facTf.val.set(1.0);
		valTf.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
		stpTf.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
		facTf.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
		
		valTf.setStyle("-fx-background-color: #999999;-fx-background-radius: 20px 0 0 20px;"
				+ "-fx-border-color: black; -fx-border-radius: 20px 0 0 20px;");
		stpTf.setStyle("-fx-background-color: #999999;"
				+ "-fx-border-color: black;");
		facTf.setStyle("-fx-background-color: #999999;-fx-background-radius: 0 20px 20px 0;"
				+ "-fx-border-color: black; -fx-border-radius: 0 20px 20px 0;");
		this.getChildren().addAll(valTf, stpTf, facTf);
		HBox.setHgrow(valTf, Priority.ALWAYS);
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			if(e.getButton().equals(MouseButton.SECONDARY)){
				if(swi){
					this.getChildren().removeAll(stpTf, facTf);
					valTf.setStyle("-fx-background-color: #999999;-fx-background-radius: 20px 20px 20px 20px;"
							+ "-fx-border-color: black; -fx-border-radius: 20px 20px 20px 20px;");
				}else{
					valTf.setStyle("-fx-background-color: #999999;-fx-background-radius: 20px 0 0 20px;"
							+ "-fx-border-color: black; -fx-border-radius: 20px 0 0 20px;");
					this.getChildren().addAll(stpTf, facTf);
				}
				swi = !swi;
			}
		});
	}
	
	public SimpleDoubleProperty valueProperty(){
		return valTf.val;
	}
	
	public void setValue(double val){
		valTf.val.set(val);
	}
	
	class CustomTF extends TextField{
		SimpleDoubleProperty val = new SimpleDoubleProperty(0);
		SimpleDoubleProperty steplen = new SimpleDoubleProperty(1);
		SimpleDoubleProperty factor = new SimpleDoubleProperty(1);
		Double lastX = null;
		Double lastY = null;
		Double lastVal = null;
		public CustomTF(){
			this.setText(String.valueOf(val.get()));
			val.addListener(e -> {
				this.setText(String.valueOf(val.get()));
			});
			this.setEditable(false);
			ContextMenu cm = new ContextMenu();
			this.setContextMenu(cm);
			
			this.selectedTextProperty().addListener(e -> {
				this.deselect();
			});
			final double opa = 0.6;
			this.setOpacity(opa);
			this.focusedProperty().addListener((prop, o, n) -> {
				if(n.equals(false)){
					this.setEditable(false);
					String t = this.getText();
					if(t.equals("")){
						this.setText(t = "0.0");
					}
					val.set(Double.valueOf(t));
					this.setOpacity(opa);
				}else{
					this.setOpacity(1);
				}
			});
			this.setOnMouseEntered(e -> {
				this.setOpacity(1.0);
			});
			this.setOnMouseExited(e -> {
				if(!this.isFocused()){
					this.setOpacity(opa);
				}
			});

			//interact with keyboard action
			this.setOnKeyTyped(e -> {
				String t = this.getText() + e.getCharacter();
				if(t != null){
					String reg = "(\\d)*((\\.(\\d)*)?)";
					if(!t.matches(reg)){
						e.consume();
					}
				}
			});
			
			this.setOnKeyPressed(e -> {
				if(!this.isEditable()){
					if(e.getCode().equals(KeyCode.RIGHT) || e.getCode().equals(KeyCode.UP) || e.getCode().equals(KeyCode.D) || e.getCode().equals(KeyCode.W)){
						//val.set(val.get() + steplen.get() * factor.get());
						valInc();
					}else if(e.getCode().equals(KeyCode.LEFT) || e.getCode().equals(KeyCode.DOWN) || e.getCode().equals(KeyCode.A) || e.getCode().equals(KeyCode.S)){
						//val.set(val.get() - steplen.get() * factor.get());
						valDec();
					}else if(e.getCode().equals(KeyCode.ESCAPE)){
						val.set(lastVal);
					}
				}	
			});
			
			//fobid Chinese...
			this.setOnInputMethodTextChanged(e -> {
			});
			
			//interact with mouse action
			this.setOnMouseClicked(e -> {
				if(e.getClickCount() == 2 && e.getButton().equals(MouseButton.PRIMARY)){
					this.setEditable(true);
				}
			});
			
			this.setOnMousePressed(e -> {
				if(e.getButton().equals(MouseButton.SECONDARY)){
					e.consume();
					return;
				}
				lastX = e.getScreenX();
				lastY = e.getScreenY();
				lastVal = val.get();
			});
			
			this.setOnMouseDragged(e -> {
				if(!this.isEditable() && e.getButton().equals(MouseButton.PRIMARY)){
					this.deselect();
					this.setCursor(Cursor.NONE);
					Double nowX = e.getScreenX();
					if(nowX - lastX < 0){
						//val.set(val.get() - steplen.get() * factor.get());
						valDec();
					}else if(nowX - lastX > 0){
						//val.set(val.get() + steplen.get() * factor.get());
						valInc();
					}
					try {
						Robot r = new Robot();
						r.mouseMove((int)(lastX.doubleValue()), (int)(lastY.doubleValue()));
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			this.setOnMouseReleased(e -> {
				if(e.getButton().equals(MouseButton.PRIMARY))
					this.setCursor(Cursor.TEXT);
			});		
		}
		public void valInc(){
			steplen.set(steplen.get() * factor.get());
			val.set(val.get() + steplen.get());
		}
		public void valDec(){
			steplen.set(steplen.get() * factor.get());
			val.set(val.get() - steplen.get());
		}
	}
}
