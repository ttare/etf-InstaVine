package ba.etf.instavine.filters;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public abstract class ImageFilterBase implements BufferedImageOp {

/*
	  Average: 	gray level = (R + G + B) / 3
    Lightness:  gray level = 0.5 x (max(R,G,B) + min(R,G,B))
        Lumin: 	gray level = 0.21 x R + 0.72 x G + 0.07 x B
    
	
*/
    final static int average = 1;
    final static int lightness = 2;
    final static int lumin = 3;
	
	

    public abstract BufferedImage applyFilter(BufferedImage src, BufferedImage dst);

    protected static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

	
	private int getGrayScaleValue(int red, int green, int blue, int grayAlgorithm) {
 
        if (grayAlgorithm == average) {
            return (red + green + blue) / 3;
        } else if (grayAlgorithm == lumin) {
            return (int) (0.21 * red + 0.72 * green + 0.07 * blue);
        } else if (grayAlgorithm == lightness) {
            return (int) (0.5 * (getMax(red, green, blue) + getMin(red, green, blue)));
        } else {
            return -1;
        }

    }
	
	private int getMax(int r, int g, int b) {
        int max = r;
        if (g > max) {
            max = g;
        }
        if (b > max) {
            max = b;
        }
        return max;
    }
	
	
	
    public final BufferedImage filter(BufferedImage src, BufferedImage dst, int grayAlgorithm = 1) { //dodan parametar algorithm
        if (dst == null) 
            dst = createCompatibleDestImage (src, null);
			
		Color c;
        Color tempColor;
        int red;
        int green;
        int blue;
 
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                c = new Color(src.getRGB(x, y));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                int grayScaleValue = getGrayScaleValue(red, green, blue, grayAlgorithm);
                tempColor = new Color(grayScaleValue, grayScaleValue, grayScaleValue);
                dst.setRGB(x, y, tempColor.getRGB());
            }
        }
		
        dst = applyFilter(src, dst);
        return dst;
    }

    public BufferedImage createCompatibleDestImage (BufferedImage src, ColorModel dst_color_model) { 
        if (dst_color_model == null) 
            dst_color_model = src.getColorModel();

        int width = src.getWidth ();
        int height= src.getHeight ();

        return new BufferedImage (
               dst_color_model,
               dst_color_model.createCompatibleWritableRaster(width, height),
               dst_color_model.isAlphaPremultiplied(),
               null);
    }

    public final Rectangle2D getBounds2D (BufferedImage src) {
        return src.getRaster().getBounds ();
    }

    public Point2D getPoint2D (Point2D source_point, Point2D dest_point) {
        if (dest_point == null) dest_point = new Point2D.Float ();
        dest_point.setLocation (source_point.getX (), source_point.getY ());
        return dest_point;
    }

    public final RenderingHints getRenderingHints () {
        return null;
    }
    
    
}