package drawingTool;

public enum AntSize {
    SMALL(0.8), MEDIUM(1.0), LARGE(1.4);
    
    private final double scale;
    
    AntSize(double scale) { 
        this.scale = scale; 
    }
    
    public double getScale() { 
        return scale; 
    }
}
