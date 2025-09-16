package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Abdomen {
    private static final int DEFAULT_STINGER_LENGTH = 15;
    private static final int DEFAULT_STINGER_WIDTH = 10;
    private static final Color ABDOMEN_COLOR = Color.LIGHT_GRAY;

    private Stinger stinger;
    private int length;
    private int width;
    private double scale;

    public Abdomen(int length, int width) {
        this(length, width, 1.0);
    }
    
    public Abdomen(int length, int width, double scale) {
        this.length = length;
        this.width = width;
        this.scale = scale;
        
        int scaledStingerLength = (int)(DEFAULT_STINGER_LENGTH * scale);
        int scaledStingerWidth = (int)(DEFAULT_STINGER_WIDTH * scale);
        this.stinger = new Stinger(scaledStingerLength, scaledStingerWidth);
    }

    public void drawAt(Graphics g, int left, int bottom) {
        g.setColor(ABDOMEN_COLOR);
        g.fillOval(left - width / 2, bottom, width, length);
        stinger.drawAt(g, left, bottom + length);
    }
    
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public Stinger getStinger() { return stinger; }
    public int getScaledStingerLength() { return (int)(DEFAULT_STINGER_LENGTH * scale); }
}