package lgh.core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.*;
import lgh.property.CommonPropPane;
import lgh.property.EffectPane;
import lgh.property.IndividualPane;
import lgh.property.TransformPane;
import lgh.util.Util;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class TextureMaker extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	BorderPane root = new BorderPane();
	Pane canvas = new Pane();
	ScrollPane rightView = new ScrollPane();
	VBox propPane = new VBox();
	Scene scene = new Scene(root);
	MenuBar menuBar = new MenuBar();
	SplitPane canvasView = new SplitPane();
	Menu fileMenu = new Menu("File");
	MenuItem openMenuItem = new MenuItem("Open");
	MenuItem newMenuItem = new MenuItem("New");
	MenuItem saveMenuItem = new MenuItem("Save");
	MenuItem saveAsMenuItem = new MenuItem("Save as...");
	MenuItem shotCanvasMenuItem = new MenuItem("Shot canvas");
	
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
		ini();
		stage.setScene(scene);
		stage.setTitle("TextureMaker");
		stage.show();
	}

	public Point2D midPoint(){
		return new Point2D(canvas.getWidth() / 2.0, canvas.getHeight() / 2.0);
	}
	
	public void ini() {
		canvas.setMaxWidth(8000);
		canvas.setMaxHeight(6000);
		canvas.setMinWidth(8000);
		canvas.setMinHeight(6000);
		canvas.setOnScroll(e -> {
			if(e.getDeltaY() > 0){
				canvas.setScaleX(Math.min(canvas.getScaleX() * 1.1, 5));
				canvas.setScaleY(Math.min(canvas.getScaleY() * 1.1, 5));
			}else{
				canvas.setScaleX(Math.max(canvas.getScaleX() * 0.9, 0.1));
				canvas.setScaleY(Math.max(canvas.getScaleY() * 0.9, 0.1));
			}
		});
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			activeObject.set(null);
		});
		canvas.setStyle("-fx-border-color: black");
		canvasView.setMinWidth(800);
		canvasView.setMinHeight(600);
		ScrollPane sp = new ScrollPane(canvas);
		rightView.setContent(propPane);
		rightView.maxWidthProperty().bind(canvasView.widthProperty().multiply(0.3));
		rightView.setMinWidth(1);
		propPane.prefWidthProperty().bind(canvasView.widthProperty().multiply(0.3));
		canvasView.getItems().addAll(sp, rightView);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVvalue(0.5);
		sp.setHvalue(0.5);
		sp.vvalueProperty().addListener((prop, o, n) -> {
			sp.setVvalue(0.5);
		});
		sp.hvalueProperty().addListener((prop, o, n) -> {
			sp.setHvalue(0.5);
		});
		
		
		root.setTop(menuBar);
		root.setCenter(canvasView);
		propPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			propPane.requestFocus();
		});
		
		activeObject.addListener((prop, o, n) -> {
			propPane.getChildren().clear();
			changeActive(n);
		});
		
		
		fileMenu.getItems().addAll(openMenuItem, newMenuItem, saveMenuItem, saveAsMenuItem, shotCanvasMenuItem);
		
		editMenu.getItems().addAll(addItem);
		
		addItem.getItems().addAll(arcItem, circleItem, cubicCurveItem, ellipseItem, lineItem,
								pathItem, polygonItem, polylineItem, quadCurveItem, rectangleItem, 
								SVGPathItem, textItem);
		
		menuBar.getMenus().addAll(fileMenu, editMenu);
		
		
		shotCanvasMenuItem.setOnAction(e -> shot());
		
		installAddItemAction();
	}
	
	public void installAddItemAction(){
		circleItem.setOnAction(e -> {
			addCircle();
		});
		
		arcItem.setOnAction(e -> {
			addArc();
		});
		
		cubicCurveItem.setOnAction(e -> {
			addCubicCurve();
		});
		
		ellipseItem.setOnAction(e -> {
			addEllipse();
		});
		
		lineItem.setOnAction(e -> {
			addLine();
		});
		
		pathItem.setOnAction(e -> {
			addPath();
		});
		
		polygonItem.setOnAction(e -> {
			addPolygon();
		});
		
		polylineItem.setOnAction(e -> {
			addPolyline();
		});
		
		quadCurveItem.setOnAction(e -> {
			addQuadCurve();
		});
		
		rectangleItem.setOnAction(e -> {
			addRectangle();
		});
		
		SVGPathItem.setOnAction(e -> {
			addSVGPath();
		});
		
		textItem.setOnAction(e -> {
			addText();
		});
	}
	
	public void changeActive(Object n){
		if(n != null){
			if(n instanceof Shape){
				activeTo((Shape)n);
			}
		}else{
			
		}
	}
	
	public void addArc(){
		Arc a = new Arc();
		activeObject.set(a);
		a.setRadiusX(100);
		a.setRadiusY(50);
		a.setCenterX(canvas.getWidth() / 2.0);
		a.setCenterY(canvas.getHeight() / 2.0);
		a.setStartAngle(45);
		a.setLength(90);
		a.setType(ArcType.ROUND);
		enableMove(a);
		enableActive(a);
		canvas.getChildren().add(a);
	}
	
	public void addCircle(){
		Circle c = new Circle();
		activeObject.set(c);
		
		c.setRadius(50);
		c.setCenterX(canvas.getWidth() / 2.0);
		c.setCenterY(canvas.getHeight() / 2.0);
		enableMove(c);
		enableActive(c);
		canvas.getChildren().add(c);
	}
	
	public void addCubicCurve(){
		CubicCurve cc = new CubicCurve();
		activeObject.set(cc);
		
		Point2D mid = midPoint();
		double len = 100;
		cc.setStartX(mid.getX() - len);
		cc.setEndX(mid.getX() + len);
		cc.setStartY(mid.getY());
		cc.setEndY(mid.getY());
		cc.setControlX1(mid.getX() - len / 2.0);
		cc.setControlX2(mid.getX() + len / 2.0);
		cc.setControlY1(mid.getY() - len);
		cc.setControlY2(mid.getY() + len);
		enableMove(cc);
		enableActive(cc);
		canvas.getChildren().add(cc);
	}
	
	public void addEllipse(){
		Ellipse el = new Ellipse();
		activeObject.set(el);
		
		Point2D mid = midPoint();
		double lenX = 100;
		double lenY = 50;
		el.setCenterX(mid.getX());
		el.setCenterY(mid.getY());
		el.setRadiusX(lenX);
		el.setRadiusY(lenY);
		enableMove(el);
		enableActive(el);
		canvas.getChildren().add(el);
	}
	
	public void addLine(){
		Line l = new Line();
		activeObject.set(l);
		
		Point2D mid = midPoint();
		double len = 50;
		l.setStartX(mid.getX() - len);
		l.setEndX(mid.getX() + len);
		l.setStartY(mid.getY());
		l.setEndY(mid.getY());
		enableMove(l);
		enableActive(l);
		canvas.getChildren().add(l);
	}
	
	public void addPath(){
		Path p = new Path();
		activeObject.set(p);
		
		Point2D mid = midPoint();
		double len = 50;
		p.getElements().add(new MoveTo(mid.getX() - len, mid.getY() - len));
		p.getElements().add(new LineTo(mid.getX() + len, mid.getY() + len));
		enableMove(p);
		enableActive(p);
		canvas.getChildren().add(p);
	}
	
	public void addPolygon(){
		Polygon pg = new Polygon();
		activeObject.set(pg);
		
		Point2D mid = midPoint();
		pg.getPoints().addAll(new Double[]{
			    mid.getX() - 50, mid.getY() - 50,
			    mid.getX() + 50, mid.getY() - 50,
			    mid.getX(), mid.getY() + 50 });
		enableMove(pg);
		enableActive(pg);
		canvas.getChildren().add(pg);
	}
	
	public void addPolyline(){
		Polyline pl = new Polyline();
		activeObject.set(pl);
		
		Point2D mid = midPoint();
		pl.getPoints().addAll(new Double[]{
			    mid.getX() - 50, mid.getY() - 50,
			    mid.getX() + 50, mid.getY() - 50,
			    mid.getX(), mid.getY() + 50 });
		enableMove(pl);
		enableActive(pl);
		canvas.getChildren().add(pl);
	}
	
	public void addQuadCurve(){
		QuadCurve qc = new QuadCurve();
		activeObject.set(qc);	
		Point2D mid = midPoint();
		double len = 100;
		qc.setStartX(mid.getX() - len);
		qc.setEndX(mid.getX() + len);
		qc.setStartY(mid.getY());
		qc.setEndY(mid.getY());
		qc.setControlX(mid.getX());
		qc.setControlY(mid.getY() - len);
		enableMove(qc);
		enableActive(qc);
		canvas.getChildren().add(qc);
	}
	
	public void addRectangle(){
		Rectangle r = new Rectangle();
		activeObject.set(r);
		
		Point2D mid = midPoint();
		double len = 100;
		r.setX(mid.getX() - len);
		r.setY(mid.getY() - len / 2);
		r.setWidth(len * 2);
		r.setHeight(len);
		enableMove(r);
		enableActive(r);
		canvas.getChildren().add(r);
	}
	
	public void addSVGPath(){
		SVGPath sp = new SVGPath();
		activeObject.set(sp);
		
		Point2D mid = midPoint();
		double len = 100;
		String tr = (mid.getX() + len) + "," + (mid.getY() - len);
		String bl = (mid.getX() - len) + "," + (mid.getY() + len);
		String br = (mid.getX() + len) + "," + (mid.getY() + len);
		String cont = "M" + tr + " L" + bl + " " + br;
		sp.setContent(cont);
		enableMove(sp);
		enableActive(sp);
		canvas.getChildren().add(sp);
	}
	
	public void addText(){
		Text t = new Text("Text");
		activeObject.set(t);
		Point2D mid = midPoint();
		t.setX(mid.getX());
		t.setY(mid.getY());
		enableMove(t);
		enableActive(t);
		canvas.getChildren().add(t);
	}
		
	public void actToCanvas(){
		
	}
	
	public void enableMove(Node n){
		n.setOnMousePressed(e -> {
			n.getProperties().put("pos", new Point2D(e.getScreenX(), e.getScreenY()));
		});
		n.setOnMouseDragged(e -> {
			Point2D lastPos = (Point2D)(n.getProperties().get("pos"));
			Point2D nowPos = new Point2D(e.getScreenX(), e.getScreenY());
			double dx = nowPos.getX() - lastPos.getX();
			double dy = nowPos.getY() - lastPos.getY();
			n.setTranslateX(n.getTranslateX() + dx / canvas.getScaleX());
			n.setTranslateY(n.getTranslateY() + dy / canvas.getScaleY());
			n.getProperties().put("pos", nowPos);
		});
	}
	
	public void enableActive(Node n){
		n.addEventHandler(MouseEvent.MOUSE_PRESSED, e2 -> {
			if(e2.getButton().equals(MouseButton.PRIMARY)){
				activeObject.set(n);
			}
			e2.consume();
		});
	}
	
	public void activeTo(Shape s){
		CommonPropPane cpp = new CommonPropPane(s);
		EffectPane ep = new EffectPane(s);
		TransformPane tp = new TransformPane(s);
		propPane.getChildren().addAll(IndividualPane.getIndividualPane(s), cpp, ep, tp);
	}
	
	public void shot() {
		SnapshotParameters sp = new SnapshotParameters();
		sp.setFill(Color.TRANSPARENT);
		WritableImage image = canvas.snapshot(sp, null);
		Util.saveImage(image, stage);
	}
	

	
	
}