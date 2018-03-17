package lgh.property;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import lgh.control.SpinBox;

public class CommonPropPane extends TitledPane {
	GridPane comProp = new GridPane();
	Shape s = null;
	int rowIndex = 0;
	public CommonPropPane(Shape s) {
		this.s = s;
		this.setText("Common Properties");
		this.setContent(comProp);
		comProp.setStyle("-fx-background-color: gray");
		createSX();
		createSY();
		createTX();
		createTY();
		createTZ();
		Separator sep = new Separator();
		GridPane.setColumnSpan(sep, 2);
		comProp.addRow(rowIndex++, sep);
		createFill();
		createStroke();
		createStrokeWidth();
		createLineCap();
		createLineJoin();
		createMiterLimit();
		createStrokeType();
		createDashArray();
		createDashOffset();
		
		ColumnConstraints col1Cons = new ColumnConstraints();
		col1Cons.setHalignment(HPos.LEFT);
		
		ColumnConstraints col2Cons = new ColumnConstraints();
		col2Cons.setHgrow(Priority.ALWAYS);
		col2Cons.setMaxWidth(Double.MAX_VALUE);
		
		comProp.getColumnConstraints().addAll(col1Cons, col2Cons);
		for(Node c: comProp.getChildren()){
			GridPane.setMargin(c, new Insets(5, 0, 0, 5));
		}
	}
	public void createSX(){
		Text sX = new Text("Scale X: ");
		SpinBox scaleX = new SpinBox(s.getScaleX());
		s.scaleXProperty().bindBidirectional(scaleX.valueProperty());
		comProp.addRow(rowIndex++, sX, scaleX);
	}
	
	public void createSY(){
		Text sY = new Text("Scale Y: ");
		SpinBox scaleY = new SpinBox(s.getScaleY());
		s.scaleYProperty().bindBidirectional(scaleY.valueProperty());
		comProp.addRow(rowIndex++, sY, scaleY);
	}
	
	public void createTX(){
		SpinBox transX = new SpinBox(s.getTranslateX());
		Text tX = new Text("Translate X: ");
		s.translateXProperty().bindBidirectional(transX.valueProperty());
		comProp.addRow(rowIndex++, tX, transX);
	}
	
	public void createTY(){
		SpinBox transY = new SpinBox(s.getTranslateY());
		Text tY = new Text("Translate Y: ");
		s.translateYProperty().bindBidirectional(transY.valueProperty());
		comProp.addRow(rowIndex++, tY, transY);
	}
	
	public void createTZ(){
		SpinBox transZ = new SpinBox(s.getTranslateZ());
		Text tZ = new Text("Translate Z: ");
		s.translateZProperty().bindBidirectional(transZ.valueProperty());
		comProp.addRow(rowIndex++, tZ, transZ);
		
	}
	
	public void createFill(){
		/***** Fill: Paint *****/
		Text fillText = new Text("Fill: ");
		ColorPicker fillCol = new ColorPicker();
		if (s.getFill() == null) {
			s.setFill(Color.TRANSPARENT);
			fillCol.setValue(Color.TRANSPARENT);
		}
		fillCol.valueProperty().addListener((prop, o, ne) -> {
			s.setFill(ne);
		});
		if (s.getFill() instanceof Color)
			fillCol.setValue((Color) s.getFill());
		comProp.addRow(rowIndex++, fillText, fillCol);
	}
	
	public void createStroke(){
		/***** Stroke: Paint *****/
		Text strokeText = new Text("Stroke: ");
		ColorPicker strokeCol = new ColorPicker();
		if (s.getStroke() == null) {
			s.setStroke(Color.TRANSPARENT);
			strokeCol.setValue(Color.TRANSPARENT);
		}
		strokeCol.valueProperty().addListener((prop, o, ne) -> {
			s.setStroke(ne);
		});
		if (s.getStroke() instanceof Color)
			strokeCol.setValue((Color) s.getStroke());
		comProp.addRow(rowIndex++, strokeText, strokeCol);
	}
	
	public void createLineCap(){
		/***** LineCap: StrokeLineCap *****/
		Text capText = new Text("Line cap: ");
		ChoiceBox<StrokeLineCap> capChoice = new ChoiceBox<StrokeLineCap>();
		capChoice.setValue(s.getStrokeLineCap());
		s.strokeLineCapProperty().bindBidirectional(capChoice.valueProperty());
		capChoice.getItems().addAll(StrokeLineCap.BUTT, StrokeLineCap.ROUND, StrokeLineCap.SQUARE);
		comProp.addRow(rowIndex++, capText, capChoice);
	}
	
	public void createLineJoin(){
		/***** LineJoin: StrokeLineJoin *****/
		Text joinText = new Text("Line join: ");
		ChoiceBox<StrokeLineJoin> joinChoice = new ChoiceBox<StrokeLineJoin>();
		joinChoice.setValue(s.getStrokeLineJoin());
		s.strokeLineJoinProperty().bindBidirectional(joinChoice.valueProperty());
		joinChoice.getItems().addAll(StrokeLineJoin.ROUND, StrokeLineJoin.MITER, StrokeLineJoin.BEVEL);
		comProp.addRow(rowIndex++, joinText, joinChoice);
	}
	
	public void createMiterLimit(){
		/***** MiterLimit: double *****/
		Text miterText = new Text("Mitter limit: ");
		SpinBox miterSpin = new SpinBox(s.getStrokeMiterLimit());
		s.strokeMiterLimitProperty().bindBidirectional(miterSpin.valueProperty());
		comProp.addRow(rowIndex++, miterText, miterSpin);
	}
	
	public void createStrokeType(){
		/***** Type: StrokeType *****/
		Text typeText = new Text("Stroke type: ");
		ChoiceBox<StrokeType> typeChoice = new ChoiceBox<StrokeType>();
		typeChoice.setValue(s.getStrokeType());
		s.strokeTypeProperty().bindBidirectional(typeChoice.valueProperty());
		typeChoice.getItems().addAll(StrokeType.CENTERED, StrokeType.INSIDE, StrokeType.OUTSIDE);
		comProp.addRow(rowIndex++, typeText, typeChoice);
	}
	
	public void createStrokeWidth(){
		/***** StrokeWidth: double *****/
		Text widthText = new Text("Stroke width: ");
		SpinBox widthSpin = new SpinBox(s.getStrokeWidth());
		s.strokeWidthProperty().bindBidirectional(widthSpin.valueProperty());
		comProp.addRow(rowIndex++, widthText, widthSpin);
	}
	
	public void createDashArray(){
		/***** DashArray: ObservableList<Double> *****/
		Text dashArrayText = new Text("Dash array: ");
		ListView<HBox> dashView = new ListView<HBox>();
		dashView.setMaxHeight(200);
		ObservableList<Double> array = s.getStrokeDashArray();
		if (array != null) {
			for (Double d : array) {
				addDash(dashView, array, d);
			}
		}
		Button addBtn = new Button("+");
		addBtn.setOnAction(e -> {
			addDash(dashView, array, 10.0);
		});
		HBox hb = new HBox(addBtn);
		dashView.getItems().add(hb);
		comProp.addRow(rowIndex++, dashArrayText, dashView);
	}
	
	public void createDashOffset(){
		/***** DashOffset: double *****/
		Text offsetText = new Text("Dash offset: ");
		SpinBox offsetSpin = new SpinBox(s.getStrokeDashOffset());
		s.strokeDashOffsetProperty().bindBidirectional(offsetSpin.valueProperty());
		comProp.addRow(rowIndex++, offsetText, offsetSpin);
	}
	
	public void addDash(ListView<HBox> dashView, ObservableList<Double> dashArray, double val) {
		dashArray.add(val);
		SpinBox dSpin = new SpinBox(val);
		dSpin.setMin(0);
		Button delBtn = new Button("-");
		HBox hb = new HBox(5, dSpin, delBtn);
		dashView.getItems().add(hb);
		delBtn.setOnAction(e -> {
			int index = dashView.getItems().indexOf(hb);
			dashView.getItems().remove(hb);
			dashArray.remove(index - 1);
		});
		dSpin.valueProperty().addListener((prop, o, n) -> {
			int index = dashView.getItems().indexOf(hb);
			dashArray.set(index - 1, (Double) n);
		});
	}
}
