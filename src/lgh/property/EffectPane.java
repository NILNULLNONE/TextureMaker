package lgh.property;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
public class EffectPane extends TitledPane{
	Shape s = null;
	public EffectPane(Shape s){
		this.s = s;
		this.setText("Effect");
	}
}
