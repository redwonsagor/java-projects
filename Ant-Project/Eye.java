package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Eye {
    private static final Color EYE_COLOR = Color.BLACK;

    private int length;
    private int width;

    public Eye(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(EYE_COLOR);

        int x = left - width / 2;
        int y = bottom - length / 2;

        g.fillOval(x, y, width, length);
    }
    
    // New method for drawing colored eyes using EXACT same logic
    public void drawColoredAt(int left, int bottom, Color color) {
        Graphics g = Drawing.pen();
        g.setColor(color);

        // EXACT same positioning logic as original drawAt method
        int x = left - width / 2;
        int y = bottom - length / 2;

        g.fillOval(x, y, width, length);
    }
    
    // Static method for decorators to use exact Eye positioning
    public static void drawColoredEyeAt(Graphics g, Color color, int left, int bottom, int size) {
        g.setColor(color);
        
        // EXACT same positioning logic as Eye.drawAt method
        int x = left - size / 2;
        int y = bottom - size / 2;
        
        g.fillOval(x, y, size, size);
    }
    
    // Getters for private variables
    public int getLength() { return length; }
    public int getWidth() { return width; }
}