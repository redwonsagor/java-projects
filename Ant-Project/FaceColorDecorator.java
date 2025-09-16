package drawingTool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Point;

public class FaceColorDecorator extends AntDecorator {
    private final Color faceColor;

    public FaceColorDecorator(AntComponent inner, Color faceColor) {
        super(inner);
        this.faceColor = faceColor;
    }

    @Override
    public void drawAt(int left, int bottom) {
        // 1) draw the wrapped ant body
        inner.drawAt(left, bottom);
        // 2) overlay the semi-transparent face color
        drawColoredFace(left, bottom);
    }

    private void drawColoredFace(int left, int bottom) {
        Graphics g = Drawing.pen();
        if (!(g instanceof Graphics2D)) return;
        Graphics2D g2d = (Graphics2D) g;

        // unwrap to core DecoratedAnt for size & pose
        AntComponent comp = inner;
        while (comp instanceof AntDecorator) {
            comp = ((AntDecorator) comp).getAntComponent();
        }
        if (!(comp instanceof DecoratedAnt)) return;
        DecoratedAnt ant = (DecoratedAnt) comp;

        double scale = ant.getSize().getScale();
        int scaledHeadW = (int)(ant.getHeadWidth()  * scale);
        int scaledHeadH = (int)(ant.getHeadLength() * scale);

        // compute head position by pose
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

        int headX = headLeft - scaledHeadW / 2;
        int headY = headBottom - scaledHeadH;

        // apply semi-transparency
        AlphaComposite orig = (AlphaComposite) g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(faceColor);
        g2d.fillOval(headX, headY, scaledHeadW, scaledHeadH);
        // restore composite
        g2d.setComposite(orig);
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + ", " + colorName(faceColor) + " Face";
    }

    private String colorName(Color c) {
        if      (Color.RED.equals(c))    return "Red";
        else if (Color.BLUE.equals(c))   return "Blue";
        else if (Color.GREEN.equals(c))  return "Green";
        else if (Color.YELLOW.equals(c)) return "Yellow";
        else if (Color.PINK.equals(c))   return "Pink";
        else                               return "Colored";
    }

    @Override
    public void draw() {
        // draw at the original ant’s stored position
        DecoratedAnt base = getCoreAnt();
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

    /** Helper to unwrap decorators down to the core DecoratedAnt */
    private DecoratedAnt getCoreAnt() {
        AntComponent comp = inner;
        while (comp instanceof AntDecorator) {
            comp = ((AntDecorator) comp).getAntComponent();
        }
        return (comp instanceof DecoratedAnt) ? (DecoratedAnt) comp : null;
    }
}
