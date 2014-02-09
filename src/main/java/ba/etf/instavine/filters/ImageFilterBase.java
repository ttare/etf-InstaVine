package ba.etf.instavine.filters;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public abstract class ImageFilterBase implements BufferedImageOp {

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

    public final BufferedImage filter(BufferedImage src, BufferedImage dst) {
        if (dst == null) 
            dst = createCompatibleDestImage (src, null);
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