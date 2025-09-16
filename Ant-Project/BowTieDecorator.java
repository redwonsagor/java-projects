package drawingTool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class BowTieDecorator extends AntDecorator {
    private final Color bowTieColor;

    public BowTieDecorator(AntComponent inner, Color bowTieColor) {
        super(inner);
        this.bowTieColor = bowTieColor;
    }

    @Override
    public void drawAt(int left, int bottom) {
        // 1) draw the wrapped ant body
        inner.drawAt(left, bottom);
        // 2) then draw the bow tie
        drawBowTie(left, bottom);
    }

    private void drawBowTie(int left, int bottom) {
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
        int bowW = (int)(20 * scale);
        int bowH = (int)(8  * scale);

        // determine neck position by pose
        int neckBottom, neckLeft;
        switch (ant.getPose()) {
            case STANDING:
                neckBottom = bottom - (int)(65 * scale);
                neckLeft   = left;
                break;
            case WALKING:
                neckBottom = bottom - (int)(75 * scale);
                neckLeft   = left + (int)(5 * scale);
                break;
            default: // NORMAL
                neckBottom = bottom - (int)(70 * scale);
                neckLeft   = left;
        }

        // draw the bow-tie shape
        int[] xs = {
            neckLeft - bowW/2,
            neckLeft - bowW/4,
            neckLeft,
            neckLeft + bowW/4,
            neckLeft + bowW/2,
            neckLeft + bowW/4,
            neckLeft,
            neckLeft - bowW/4
        };
        int[] ys = {
            neckBottom - bowH/2,
            neckBottom - bowH/4,
            neckBottom,
            neckBottom - bowH/4,
            neckBottom - bowH/2,
            neckBottom + bowH/4,
            neckBottom,
            neckBottom + bowH/4
        };

        g.setColor(bowTieColor);
        g.fillPolygon(xs, ys, xs.length);

        // draw the center knot darker
        g.setColor(bowTieColor.darker());
        int knotW = (int)(4 * scale);
        int knotH = (int)(6 * scale);
        g.fillRect(
            neckLeft - knotW/2,
            neckBottom - (int)(3 * scale),
            knotW,
            knotH
        );
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + ", " + colorName(bowTieColor) + " Bow Tie";
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
