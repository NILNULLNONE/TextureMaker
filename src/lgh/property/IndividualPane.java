package lgh.property;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
public class IndividualPane{
	public static TitledPane getIndividualPane(Shape s){
		if(s instanceof Arc){
			return new ArcPane();
		}else if(s instanceof Circle){
			return new CirclePane();
		}else if(s instanceof CubicCurve){
			return new CubicCurvePane();
		}else if(s instanceof Ellipse){
			return new EllipsePane();
		}else if(s instanceof Line){
			return new LinePane();
		}else if(s instanceof Path){
			return new PathPane();
		}else if(s instanceof Polygon){
			return new PolygonPane();
		}else if(s instanceof Polyline){
			return new PolylinePane();
		}else if(s instanceof QuadCurve){
			return new QuadCurvePane();
		}else if(s instanceof Rectangle){
			return new RectanglePane();
		}else if(s instanceof SVGPath){
			return new SVGPathPane();
		}else if(s instanceof Text){
			return new TextPane();
		}
		return null;
	}
	
	static class ArcPane extends TitledPane{
		
	}
	
	static class CirclePane extends TitledPane{
		
	}
	
	static class CubicCurvePane extends TitledPane{
		
	}
	
	static class EllipsePane extends TitledPane{
		
	}
	
	static class LinePane extends TitledPane{
		
	}
	
	static class PathPane extends TitledPane{
		
	}
	
	static class PolygonPane extends TitledPane{
		
	}
	
	static class PolylinePane extends TitledPane{
		
	}
	
	static class QuadCurvePane extends TitledPane{
		
	}
	
	static class RectanglePane extends TitledPane{
		
	}
	
	static class SVGPathPane extends TitledPane{
		
	}
	
	static class TextPane extends TitledPane{
		
	}
}
