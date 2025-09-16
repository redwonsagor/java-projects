package drawingTool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class EyeColorDecorator extends AntDecorator {
    private final Color eyeColor;

    public EyeColorDecorator(AntComponent inner, Color eyeColor) {
        super(inner);
        this.eyeColor = eyeColor;
    }

    @Override
    public void drawAt(int left, int bottom) {
        // 1) draw the wrapped ant parts
        inner.drawAt(left, bottom);
        // 2) then overlay colored eyes
        drawColoredEyes(left, bottom);
    }

    private void drawColoredEyes(int left, int bottom) {
        Graphics g = Drawing.pen();
        if (g == null) return;

        // unwrap to the base DecoratedAnt
        AntComponent comp = inner;
        while (comp instanceof AntDecorator) {
            comp = ((AntDecorator) comp).getAntComponent();
        }
        if (!(comp instanceof DecoratedAnt)) return;
        DecoratedAnt ant = (DecoratedAnt) comp;

        double scale = ant.getSize().getScale();
        int scaledHeadLength = (int)(ant.getHeadLength() * scale);
        int scaledHeadWidth  = (int)(ant.getHeadWidth()  * scale);

        // compute head bottom and left by pose
        int headBottom, headLeft;
        switch (ant.getPose()) {
            case STANDING:
                headBottom = bottom - (int)(85 * scale);
                headLeft   = left;
                break;
            case WALKING:
                headBottom = bottom - (int)(100 * scale);
                headLeft   = left + (int)(5 * scale) * 2;
                break;
            default: // NORMAL
                headBottom = bottom - (int)(95 * scale);
                headLeft   = left;
        }

        int eyeY    = headBottom - scaledHeadLength + (int)(15 * scale);
        int eyeSize = (int)(10 * scale);
        int eye1X   = headLeft - scaledHeadWidth / 3;
        int eye2X   = headLeft + scaledHeadWidth / 6;

        g.setColor(eyeColor);
        g.fillOval(eye1X - eyeSize/2, eyeY - eyeSize/2, eyeSize, eyeSize);
        g.fillOval(eye2X - eyeSize/2, eyeY - eyeSize/2, eyeSize, eyeSize);
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + ", " + colorName(eyeColor) + " Eyes";
    }

    private String colorName(Color c) {
        if      (Color.RED.equals(c))     return "Red";
        else if (Color.BLUE.equals(c))    return "Blue";
        else if (Color.GREEN.equals(c))   return "Green";
        else if (Color.YELLOW.equals(c))  return "Yellow";
        else if (Color.PINK.equals(c))    return "Pink";
        else if (Color.MAGENTA.equals(c)) return "Magenta";
        else                                return "Colored";
    }

 
    @Override
    public void draw() {
        DecoratedAnt base = getDecoratedAnt();
        if (base != null) {
            drawAt(base.getX(), base.getY());
        }
    }

    @Override
    public Point address() {
        return inner.address();
    }

    @Override
    public int width() {
        return inner.width();
    }

    @Override
    public int height() {
        return inner.height();
    }

    /** Helper to unwrap down to the core DecoratedAnt */
    private DecoratedAnt getDecoratedAnt() {
        AntComponent comp = inner;
        while (comp instanceof AntDecorator) {
            comp = ((AntDecorator) comp).getAntComponent();
        }
        return (comp instanceof DecoratedAnt) ? (DecoratedAnt) comp : null;
    }
}
