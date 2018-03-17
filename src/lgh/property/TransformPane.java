package lgh.property;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
public class TransformPane extends TitledPane{
	Shape s = null;
	public TransformPane(Shape s){
		this.s = s;
		this.setText("Transform");
	}
}
