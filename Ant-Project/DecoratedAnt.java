package drawingTool;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

public class DecoratedAnt implements AntComponent, LocatedRectangle {
    
    // ALL PRIVATE VARIABLES
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
    
    private int totalHeight;
    private int totalWidth;
    private int antennaLength = 20;
    
    private Thorax thorax;
    private Neck neck;
    private Head head;

    private int x;
    private int y;
    private int length;
    private int width;
    
    private AntSize size;
    private AntColor antColor;
    private AntPose pose;

    public DecoratedAnt(int x, int y, int length, int width) {
        this(x, y, length, width, AntSize.MEDIUM, AntColor.GREEN, AntPose.NORMAL);
    }
    
    public DecoratedAnt(int x, int y, int length, int width, AntSize size, AntColor antColor, AntPose pose) {
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
        
        int scaledAntennaLength = (int)(antennaLength * scale);
        int scaledBottomHOffset = (int)(95 * scale);
        
        int actualScaledAbdomenLength = thorax.getScaledAbdomenLength();
        int actualScaledStingerLength = thorax.getScaledStingerLength();
        
        totalHeight = scaledBottomHOffset + scaledHeadLength + scaledAntennaLength + actualScaledAbdomenLength + actualScaledStingerLength;
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
    }
    
    private void drawNormalPose(int left, int bottom) {
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
        double scale = size.getScale();
        int scaledBottomNOffset = (int)(75 * scale);
        int scaledBottomHOffset = (int)(100 * scale);
        int offsetX = (int)(5 * scale);
        
        int bottomN = bottom - scaledBottomNOffset;
        int bottomH = bottom - scaledBottomHOffset;
        int neckLeft = left + offsetX;
        int headLeft = left + offsetX * 2;

        thorax.drawAt(left, bottom);
        neck.drawAt(neckLeft, bottomN);
        head.drawAt(headLeft, bottomH);
    }

    @Override
    public Point address() {
        int left = x - totalWidth / 2;
        
        double scale = size.getScale();
        int scaledAntennaLength = (int)(antennaLength * scale);
        int scaledHeadLength = (int)(headLength * scale);
        int scaledBottomHOffset = (int)(95 * scale);
        
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
    
    @Override
    public String getDescription() {
        return "Basic Ant";
    }
    
    // Getters for decorator access
    public int getX() { return x; }
    public int getY() { return y; }
    public AntSize getSize() { return size; }
    public AntPose getPose() { return pose; }
    public int getHeadLength() { return headLength; }
    public int getHeadWidth() { return headWidth; }
    
    public Point getStingerBottomPosition() {
        int actualScaledAbdomenLength = thorax.getScaledAbdomenLength();
        int actualScaledStingerLength = thorax.getScaledStingerLength();
        
        int stingerBottom = y + actualScaledAbdomenLength + actualScaledStingerLength;
        return new Point(x, stingerBottom);
    }
}