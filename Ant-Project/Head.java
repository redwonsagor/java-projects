package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Head {
    
    private Color headColor;

    // Constants for part sizes - ALL PRIVATE
    private static final int EYE_SIZE = 10;
    private static final int ANTENNA_LENGTH = 20;
    private static final int ANTENNA_WIDTH = 5;
    private static final int MOUTH_LENGTH = 15;
    private static final int MOUTH_WIDTH = 8;

    // Offsets - ALL PRIVATE
    private static final int EYE_OFFSET_Y = 15;
    private static final int ANTENNA_OFFSET_Y = 5;
    private static final int MOUTH_OFFSET_BOTTOM = 10;

    // Random smile config - ALL PRIVATE
    private static final int MIN_SMILE = 0;
    private static final int MAX_SMILE = 1;

    // Composites - ALL PRIVATE
    private Eye eye1;
    private Eye eye2;
    private Antenna antenna1;
    private Antenna antenna2;
    private Mouth mouth;

    private int length;
    private int width;

    public Head(int length, int width) {
        this(length, width, Color.GRAY);
    }
    
    public Head(int length, int width, Color headColor) {
        this.length = length;
        this.width = width;
        this.headColor = headColor;

        eye1 = new Eye(EYE_SIZE, EYE_SIZE);
        eye2 = new Eye(EYE_SIZE, EYE_SIZE);
        antenna1 = new Antenna(ANTENNA_LENGTH, ANTENNA_WIDTH);
        antenna2 = new Antenna(ANTENNA_LENGTH, ANTENNA_WIDTH);
        mouth = new Mouth(MOUTH_LENGTH, MOUTH_WIDTH);
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(headColor);

        int headX = left - width / 2;
        int headY = bottom - length;
        g.fillOval(headX, headY, width, length);

        // Eyes
        int eye1X = left - width / 3;
        int eye2X = left + width / 6;
        int eyeY = bottom - length + EYE_OFFSET_Y;
        eye1.drawAt(eye1X, eyeY);
        eye2.drawAt(eye2X, eyeY);

        // Antennas
        int antenna1X = left - width / 4;
        int antenna2X = left + width / 4;
        int antennaY = bottom - length + ANTENNA_OFFSET_Y;
        antenna1.drawAt(antenna1X, antennaY);
        antenna2.drawAt(antenna2X, antennaY);

        // Mouth (smile or frown randomly)
        int mouthBottom = bottom - MOUTH_OFFSET_BOTTOM;
        boolean smiling = RandomNumber.between(MIN_SMILE, MAX_SMILE) == MAX_SMILE;
        mouth.drawAt(left, mouthBottom, smiling);
    }
    
    // Getters for private variables (if needed)
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public Color getHeadColor() { return headColor; }
    
    // Static getters for decorator positioning
    public static int getEyeSize() { return EYE_SIZE; }
    public static int getEyeOffsetY() { return EYE_OFFSET_Y; }
    public static int getAntennaLength() { return ANTENNA_LENGTH; }
    public static int getAntennaOffsetY() { return ANTENNA_OFFSET_Y; }
}
