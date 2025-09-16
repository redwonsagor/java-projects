package drawingTool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class HatDecorator extends AntDecorator {
    private final Color hatColor;

    public HatDecorator(AntComponent inner, Color hatColor) {
        super(inner);
        this.hatColor = hatColor;
    }

    @Override
    public void drawAt(int left, int bottom) {
        // 1) draw the wrapped ant body
        inner.drawAt(left, bottom);
        // 2) then draw the hat
        drawHat(left, bottom);
    }

    private void drawHat(int left, int bottom) {
        Graphics g = Drawing.pen();
        if (g == null) return;

        // unwrap to the core DecoratedAnt
        AntComponent comp = inner;
        while (comp instanceof AntDecorator) {
            comp = ((AntDecorator) comp).getAntComponent();
        }
        if (!(comp instanceof DecoratedAnt)) return;
        DecoratedAnt ant = (DecoratedAnt) comp;

        double scale = ant.getSize().getScale();
        int scaledHeadLength = (int)(ant.getHeadLength() * scale);
        int scaledHeadWidth  = (int)(ant.getHeadWidth()  * scale);

        // compute head position based on pose
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

        int headTop = headBottom - scaledHeadLength;
        int hatW    = (int)(scaledHeadWidth * 0.9);
        int hatH    = (int)(15 * scale);

        // draw hat body just above head
        g.setColor(hatColor);
        g.fillRect(headLeft - hatW/2, headTop - hatH, hatW, hatH);

        // draw brim
        int brimW = (int)(hatW * 1.4);
        int brimH = (int)(4 * scale);
        g.fillRect(headLeft - brimW/2, headTop - brimH/2, brimW, brimH);
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + ", " + colorName(hatColor) + " Hat";
    }

    private String colorName(Color c) {
        if      (Color.RED.equals(c))    return "Red";
        else if (Color.BLUE.equals(c))   return "Blue";
        else if (Color.GREEN.equals(c))  return "Green";
        else if (Color.YELLOW.equals(c)) return "Yellow";
        else if (Color.BLACK.equals(c))  return "Black";
        else                              return "Colored";
    }

    @Override
    public void draw() {
        // ensure we draw at the original ant's stored position
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
