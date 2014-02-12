package ba.etf.instavine.filters;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public abstract class ImageFilterBase implements BufferedImageOp {

	private static final int[][] filter_matrix9 = { {1, 1, 1},
											       {1, 1, 1},
											       {1, 1, 1} };
											
    private static final int[][] filter_matrix16 = { {1, 2, 1},
												    {2, 4, 2},
                                                    {1, 2, 1} };



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

    public final BufferedImage filter(BufferedImage src, BufferedImage dst) { //iteration number bi mozda trebao biti parametar
        if (dst == null) 
            dst = createCompatibleDestImage (src, null);
			
			
		int count = 0;
		iterationNum = 1;
        while (count < iterationNum) {
            for (int y = 1; y + 1 < src.getHeight(); y++) {
                for (int x = 1; x + 1 < src.getWidth(); x++) {
                    Color tempColor = getFilteredValue(src, y, x, filter_matrix9); //filter_matrix9 ili filter_matrix16
                    dst.setRGB(x, y, tempColor.getRGB());

                }
            }
            count++;
        }
		
		
        dst = applyFilter(src, dst);
        return dst;
    }
	
	private Color getFilteredValue(final BufferedImage src, int y, int x, int[][] filter) { //parametar filter je filter9 ili filter16 matrica
        int r = 0, g = 0, b = 0;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
 
                r += (filter[1 + j][1 + k] * (new Color(src.getRGB(x + k, y + j))).getRed());
                g += (filter[1 + j][1 + k] * (new Color(src.getRGB(x + k, y + j))).getGreen());
                b += (filter[1 + j][1 + k] * (new Color(src.getRGB(x + k, y + j))).getBlue());
            }
        }
        r = r / sum(filter);
        g = g / sum(filter);
        b = b / sum(filter);
        return new Color(r, g, b);
    }
	
	private int sum(int[][] filter) {
        int sum = 0;
        for (int y = 0; y < filter.length; y++) {
            for (int x = 0; x < filter[y].length; x++) {
                sum += filter[y][x];
            }
        }
        return sum;
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