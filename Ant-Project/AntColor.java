package drawingTool;

import java.awt.Color;

public enum AntColor {
	GREEN(Color.GREEN, Color.DARK_GRAY, Color.GRAY),
    YELLOW(Color.YELLOW, Color.ORANGE, Color.LIGHT_GRAY),
    RED(Color.RED, Color.DARK_GRAY, Color.GRAY),
    ORANGE(Color.ORANGE, new Color(139, 69, 19), Color.LIGHT_GRAY);
    
    private final Color thoraxColor;
    private final Color neckColor;
    private final Color headColor;
    
    AntColor(Color thoraxColor, Color neckColor, Color headColor) {
        this.thoraxColor = thoraxColor;
        this.neckColor = neckColor;
        this.headColor = headColor;
    }
    
    public Color getThoraxColor() { 
        return thoraxColor; 
    }
    
    public Color getNeckColor() { 
        return neckColor; 
    }
    
    public Color getHeadColor() { 
        return headColor; 
    }
}
