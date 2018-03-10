import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class TextureMaker extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	BorderPane root = new BorderPane();
	Pane canvas = new Pane();
	GridPane propPane = new GridPane();
	Scene scene = new Scene(root);
	MenuBar menuBar = new MenuBar();
	SplitPane canvasView = new SplitPane();
	Menu fileMenu = new Menu("File");
	MenuItem openMenuItem = new MenuItem("Open");
	MenuItem newMenuItem = new MenuItem("New");
	MenuItem saveMenuItem = new MenuItem("Save");
	MenuItem saveAsMenuItem = new MenuItem("Save as...");
	MenuItem shotSceneMenuItem = new MenuItem("Shot scene");
	
	Menu editMenu = new Menu("Edit");
	Menu addItem = new Menu("Add");
	MenuItem arcItem = new MenuItem("Arc");
	MenuItem circleItem = new MenuItem("Circle");
	MenuItem cubicCurveItem = new MenuItem("Cubic curve");
	MenuItem ellipseItem = new MenuItem("Ellipse");
	MenuItem lineItem = new MenuItem("Line");
	MenuItem pathItem = new MenuItem("Path");
	MenuItem polygonItem = new MenuItem("Polygon");
	MenuItem polylineItem = new MenuItem("Polyline");
	MenuItem quadCurveItem = new MenuItem("Quad curve");
	MenuItem rectangleItem = new MenuItem("Rectangle");
	MenuItem SVGPathItem = new MenuItem("SVGPath");
	MenuItem textItem = new MenuItem("Text");
	
	SimpleObjectProperty activeObject = new SimpleObjectProperty();
	Stage stage = new Stage();
	@Override
	public void start(Stage s) {
//		SpinBox test = new SpinBox();
//		StackPane sp = new StackPane();
//		sp.getChildren().add(test);
//		sp.setStyle("-fx-background-color: green");
//		sp.setPrefWidth(800);
//		sp.setPrefHeight(600);
//		sp.setOnMousePressed(e -> {
//			sp.requestFocus();
//		});
//		Scene sc = new Scene(sp);
//		s.setScene(sc);
//		s.show();
		
		ini();
		stage.setScene(scene);
		stage.setTitle("TextureMaker");
		stage.show();
	}

	public void ini() {
		canvas.setPrefWidth(800);
		canvas.setPrefHeight(600);

		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			activeObject.set(null);
		});
		
		canvasView.getItems().addAll(canvas, propPane);
		root.setTop(menuBar);
		root.setCenter(canvasView);
		root.setRight(propPane);
		propPane.setStyle("-fx-background-color: gray");
		propPane.setPadding(new Insets(5, 5, 5, 5));
		propPane.maxWidthProperty().bind(canvasView.widthProperty().multiply(0.3));
		propPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			propPane.requestFocus();
		});

	//	propPane.minWidthProperty().bind(canvasView.widthProperty().multiply(0.25));
		activeObject.addListener((prop, o, n) -> {
			propPane.getChildren().clear();
			if(n != null){
				switch(n.getClass().getSimpleName()){
				case "Circle":
					Circle c = (Circle)n;
					SpinBox centerX = new SpinBox();
					centerX.setValue(c.getCenterX());
					c.centerXProperty().bindBidirectional(centerX.valueProperty());
					propPane.addRow(0, new Text("Center X: "), centerX);
				//	propPane.setGridLinesVisible(true);
					GridPane.setHgrow(centerX, Priority.ALWAYS);
				}
			}
		});
		
		
		fileMenu.getItems().addAll(openMenuItem, newMenuItem, saveMenuItem, saveAsMenuItem, shotSceneMenuItem);
		
		editMenu.getItems().addAll(addItem);
		
		addItem.getItems().addAll(arcItem, circleItem, cubicCurveItem, ellipseItem, lineItem,
								pathItem, polygonItem, polylineItem, quadCurveItem, rectangleItem, 
								SVGPathItem, textItem);
		
		menuBar.getMenus().addAll(fileMenu, editMenu);
		
		
		shotSceneMenuItem.setOnAction(e -> shot());
		
		circleItem.setOnAction(e -> {
			Circle c = new Circle();
			activeObject.set(c);
			c.setOnMouseClicked(e2 -> {
				if(e2.getButton().equals(MouseButton.PRIMARY)){
					activeObject.set(c);
				}
				e2.consume();
			});
			c.setRadius(30);
			c.setCenterX(canvas.getWidth() / 2.0);
			c.setCenterY(canvas.getHeight() / 2.0);
			canvas.getChildren().add(c);
		});
	}

	public void shot() {
		root.setCenter(null);
		Scene s = new Scene(canvas);
		s.setFill(Color.TRANSPARENT);
		WritableImage image = s.snapshot(null);
		Util.saveImage(image, stage);
		s = null;
		root.setCenter(canvas);
	}
	
	
}