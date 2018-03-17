package lgh.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Util {
	public static void saveImage(Image img, Stage owner){
		FileChooser fc = new FileChooser();
		File file = fc.showSaveDialog(owner);
	
		if(file == null)return;
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		try {
			ImageIO.write(bimg, "png", file);
		} catch (IOException e) {

		}
	}
}
