package ba.etf.instavine.filters;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.color.ColorSpace;

public class NullImageFilter extends ImageFilterBase  {
    public BufferedImage applyFilter(BufferedImage src, BufferedImage dst) {
        return src;
    }
}