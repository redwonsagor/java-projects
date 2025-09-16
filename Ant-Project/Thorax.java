package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Thorax {
    
    private Color thoraxColor;
    private static final int LEG_LENGTH = 40;
    private static final int LEG_WIDTH = 5;
    private static final int LEFT_LEG1_X_OFFSET = -10;
    private static final int LEFT_LEG2_X_OFFSET = -5;
    private static final int LEFT_LEG3_X_OFFSET = 0;
    private static final int RIGHT_LEG1_X_OFFSET = 0;
    private static final int RIGHT_LEG2_X_OFFSET = 5;
    private static final int RIGHT_LEG3_X_OFFSET = 10;
    private static final int LEG1_Y_OFFSET = 10;
    private static final int LEG2_Y_OFFSET = 30;
    private static final int LEG3_Y_OFFSET = 50;
    private static final int ABDOMEN_LENGTH = 70;
    private static final int ABDOMEN_WIDTH = 50;

    private Leg leg1, leg2, leg3, leg4, leg5, leg6;
    private Abdomen abdomen;
    private int length;
    private int width;
    private double scale;

    public Thorax(int length, int width) {
        this(length, width, Color.DARK_GRAY);
    }
    
    public Thorax(int length, int width, Color thoraxColor) {
        this.length = length;
        this.width = width;
        this.thoraxColor = thoraxColor;
        this.scale = length / 90.0;

        leg1 = new LeftLeg(LEG_LENGTH, LEG_WIDTH);
        leg2 = new LeftLeg(LEG_LENGTH, LEG_WIDTH);
        leg3 = new LeftLeg(LEG_LENGTH, LEG_WIDTH);
        leg4 = new RightLeg(LEG_LENGTH, LEG_WIDTH);
        leg5 = new RightLeg(LEG_LENGTH, LEG_WIDTH);
        leg6 = new RightLeg(LEG_LENGTH, LEG_WIDTH);

        int scaledAbdomenLength = (int)(ABDOMEN_LENGTH * scale);
        int scaledAbdomenWidth = (int)(ABDOMEN_WIDTH * scale);
        abdomen = new Abdomen(scaledAbdomenLength, scaledAbdomenWidth, scale);
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(thoraxColor);
        g.fillOval(left - width / 2, bottom - length, width, length);

        int baseLeft = left - width / 2;
        int baseRight = left + width / 2;
        int top = bottom - length;

        leg1.drawAt(baseLeft + LEFT_LEG1_X_OFFSET, top + LEG1_Y_OFFSET);
        leg2.drawAt(baseLeft + LEFT_LEG2_X_OFFSET, top + LEG2_Y_OFFSET);
        leg3.drawAt(baseLeft + LEFT_LEG3_X_OFFSET, top + LEG3_Y_OFFSET);
        leg4.drawAt(baseRight + RIGHT_LEG1_X_OFFSET, top + LEG1_Y_OFFSET);
        leg5.drawAt(baseRight + RIGHT_LEG2_X_OFFSET, top + LEG2_Y_OFFSET);
        leg6.drawAt(baseRight + RIGHT_LEG3_X_OFFSET, top + LEG3_Y_OFFSET);

        abdomen.drawAt(g, left, bottom);
    }
    
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public Color getThoraxColor() { return thoraxColor; }
    public double getScale() { return scale; }
    public int getScaledAbdomenLength() { return (int)(ABDOMEN_LENGTH * scale); }
    public int getScaledStingerLength() { return abdomen.getScaledStingerLength(); }
}
