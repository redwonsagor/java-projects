package drawingTool;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

public class Ant implements LocatedRectangle {
    
    // ALL PRIVATE VARIABLES - Issue 1 Fix
    private int neckLength = 40;
    private int neckWidth = 10;
    private int headLength = 60;
    private int headWidth = 50;
    private int thoraxLength = 90;
    private int thoraxWidth = 60;
    private int abdomenLength = 70;
    private int abdomenWidth = 50;
    private int stingerLength = 15;
    private int stingerWidth = 10;
    
    private int leftLeg1XOffset = -10;
    private int rightLeg3XOffset = 10;
    private int legWidthConst = 5;
    
    private int maxLegOffset = Math.max(
            Math.abs(leftLeg1XOffset),
            Math.abs(rightLeg3XOffset)
        );
    private int halfLegWidth = legWidthConst / 2;
    private int legExtent = maxLegOffset + halfLegWidth;
    
    // Issue 2 Fix: Proper height and width calculation
    private int totalHeight;
    private int totalWidth;
    private int antennaLength = 20; // From Head class
    
    private Thorax thorax;
    private Neck neck;
    private Head head;

    private int x;
    private int y;
    private int length;
    private int width;
    
    // New properties for variations - Using separate enum files
    private AntSize size;
    private AntColor antColor;
    private AntPose pose;

    public Ant(int x, int y, int length, int width) {
        this(x, y, length, width, AntSize.MEDIUM, AntColor.GREEN, AntPose.NORMAL);
    }
    
    public Ant(int x, int y, int length, int width, AntSize size, AntColor antColor, AntPose pose) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.size = size;
        this.antColor = antColor;
        this.pose = pose;

        createBodyParts();
    }
    
    private void createBodyParts() {
        // Scale dimensions based on size
        double scale = size.getScale();
        int scaledNeckLength = (int)(neckLength * scale);
        int scaledNeckWidth = (int)(neckWidth * scale);
        int scaledHeadLength = (int)(headLength * scale);
        int scaledHeadWidth = (int)(headWidth * scale);
        int scaledThoraxLength = (int)(thoraxLength * scale);
        int scaledThoraxWidth = (int)(thoraxWidth * scale);
        
        neck = new Neck(scaledNeckLength, scaledNeckWidth, antColor.getNeckColor());
        head = new Head(scaledHeadLength, scaledHeadWidth, antColor.getHeadColor());
        thorax = new Thorax(scaledThoraxLength, scaledThoraxWidth, antColor.getThoraxColor());
        
        // Issue 2 Fix: Calculate exact dimensions based on actual drawing positions
        int scaledAntennaLength = (int)(antennaLength * scale);
        int scaledBottomHOffset = (int)(95 * scale);
        
        // Get the actual scaled abdomen and stinger lengths from thorax
        int actualScaledAbdomenLength = thorax.getScaledAbdomenLength();
        int actualScaledStingerLength = thorax.getScaledStingerLength();
        
        // Total height calculation:
        // From antenna tip to stinger tip
        // - Antenna tip: y - scaledBottomHOffset - scaledHeadLength - scaledAntennaLength
        // - Stinger tip: y + actualScaledAbdomenLength + actualScaledStingerLength
        // Total = stinger_tip - antenna_tip
        totalHeight = scaledBottomHOffset + scaledHeadLength + scaledAntennaLength + actualScaledAbdomenLength + actualScaledStingerLength;
        
        // Total width: from leftmost leg to rightmost leg
        totalWidth = (int)((thoraxWidth + 2 * legExtent) * scale);
    }
    
    public void drawAt(int left, int bottom) {
        switch(pose) {
            case STANDING:
                drawStandingPose(left, bottom);
                break;
            case WALKING:
                drawWalkingPose(left, bottom);
                break;
            default:
                drawNormalPose(left, bottom);
                break;
        }
        
        // Issue 2 Fix: Draw bounding box for debugging (professor's requirement)
        drawBoundingBox();
    }
    
    private void drawBoundingBox() {
        Graphics g = Drawing.pen();
        g.setColor(Color.BLUE);
        Point addr = address();
        g.drawRect(addr.x, addr.y, width(), height());
    }
    
    private void drawNormalPose(int left, int bottom) {
        // Scale the offsets based on ant size
        double scale = size.getScale();
        int scaledBottomNOffset = (int)(70 * scale);
        int scaledBottomHOffset = (int)(95 * scale);
        
        int bottomN = bottom - scaledBottomNOffset;
        int bottomH = bottom - scaledBottomHOffset;

        thorax.drawAt(left, bottom);
        neck.drawAt(left, bottomN);
        head.drawAt(left, bottomH);
    }
    
    private void drawStandingPose(int left, int bottom) {
        // Standing pose: body parts more vertically aligned
        double scale = size.getScale();
        int scaledBottomNOffset = (int)(65 * scale);
        int scaledBottomHOffset = (int)(85 * scale);
        
        int bottomN = bottom - scaledBottomNOffset;
        int bottomH = bottom - scaledBottomHOffset;

        thorax.drawAt(left, bottom);
        neck.drawAt(left, bottomN);
        head.drawAt(left, bottomH);
    }
    
    private void drawWalkingPose(int left, int bottom) {
        // Walking pose: body parts slightly offset to show movement
        double scale = size.getScale();
        int scaledBottomNOffset = (int)(75 * scale);
        int scaledBottomHOffset = (int)(100 * scale);
        int offsetX = (int)(5 * scale); // Slight forward lean - also scaled
        
        int bottomN = bottom - scaledBottomNOffset;
        int bottomH = bottom - scaledBottomHOffset;

        thorax.drawAt(left, bottom);
        neck.drawAt(left + offsetX, bottomN);
        head.drawAt(left + offsetX * 2, bottomH);
    }

    @Override
    public Point address() {
        // Issue 2 Fix: Calculate the top-left corner of the bounding box
        // The box should start from the antenna tip (top) and leftmost leg (left)
        
        int left = x - totalWidth / 2;
        
        // Calculate the exact top position based on how we actually draw the ant
        // Now using scaled offsets for proper size alignment
        
        double scale = size.getScale();
        int scaledAntennaLength = (int)(antennaLength * scale);
        int scaledHeadLength = (int)(headLength * scale);
        int scaledBottomHOffset = (int)(95 * scale); // This is now scaled!
        
        // From the ant's y position (thorax bottom), calculate where antenna tip is
        // Head bottom is at: y - scaledBottomHOffset (scaled offset)
        // Head top is at: (y - scaledBottomHOffset) - scaledHeadLength  
        // Antenna tip is at: (y - scaledBottomHOffset) - scaledHeadLength - scaledAntennaLength
        int top = (y - scaledBottomHOffset) - scaledHeadLength - scaledAntennaLength;
        
        return new Point(left, top);
    }

    @Override
    public int width() {
        return totalWidth;
    }

    @Override
    public int height() {
        return totalHeight;
    }

    @Override
    public void draw() {
        drawAt(x, y);
    }
    
    // Getters and setters for variations - Updated to use separate enum files
    public AntSize getSize() { return size; }
    public void setSize(AntSize size) { 
        this.size = size; 
        createBodyParts(); // Recreate body parts with new size
    }
    
    public AntColor getAntColor() { return antColor; }
    public void setAntColor(AntColor antColor) { 
        this.antColor = antColor; 
        createBodyParts(); // Recreate body parts with new colors
    }
    
    public AntPose getPose() { return pose; }
    public void setPose(AntPose pose) { this.pose = pose; }
    
    // Getters for private variables (if needed by other classes)
    public int getX() { return x; }
    public int getY() { return y; }
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public int getNeckLength() { return neckLength; }
    public int getNeckWidth() { return neckWidth; }
    public int getHeadLength() { return headLength; }
    public int getHeadWidth() { return headWidth; }
    public int getThoraxLength() { return thoraxLength; }
    public int getThoraxWidth() { return thoraxWidth; }
    public int getAbdomenLength() { return abdomenLength; }
    public int getAbdomenWidth() { return abdomenWidth; }
    public int getStingerLength() { return stingerLength; }
    public int getStingerWidth() { return stingerWidth; }
}