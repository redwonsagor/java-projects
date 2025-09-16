package drawingTool;

import java.util.ArrayList;
import java.awt.Color;

public class Scene {
    private static final int ANT_COUNT = 8;

    // Grid-based placement with fixed Y positions to prevent overlaps
    private static final int X_MIN = 180;  
    private static final int X_MAX = 1000; 
    private static final int LENGTH_MIN = 80;
    private static final int LENGTH_MAX = 80;
    private static final int WIDTH_MIN = 250;
    private static final int WIDTH_MAX = 250;

    private ArrayList<AntComponent> decoratedAnts;

    public Scene() {
        decoratedAnts = new ArrayList<>();
        generateDecoratedAnts();
    }
    
    private void generateDecoratedAnts() {
        System.out.println("Creating 8 decorated ants...");
        
        // Create 8 ants in a 4x2 grid with fixed spacing to prevent overlaps
        int cols = 4;
        int rows = 2;
        int spacingX = (X_MAX - X_MIN) / (cols - 1);
        
        // Fixed Y positions with perfect gap between rows
        int topRowY = 280;    // Keep first row position
        int bottomRowY = 640; // Move second row down a bit more for final clearance
        
        for (int i = 0; i < ANT_COUNT; i++) {
            int row = i / cols;
            int col = i % cols;
            
            // Grid position with small random offset
            int baseX = X_MIN + col * spacingX;
            int baseY = (row == 0) ? topRowY : bottomRowY; // Fixed Y positions
            int x = baseX + RandomNumber.between(-8, 8); // Very small random offset
            int y = baseY + RandomNumber.between(-3, 3);
            
            AntComponent ant = createDecoratedAnt(i, x, y, LENGTH_MIN, WIDTH_MIN);
            decoratedAnts.add(ant);
            
            System.out.println("Created ant " + (i+1) + ": " + getAntTypeDescription(i) + " at (" + x + "," + y + ")");
        }
        
        System.out.println("All 8 decorated ants created successfully!");
    }
    
    private String getAntTypeDescription(int index) {
        switch (index % 8) {
            case 0: return "Basic Ant";
            case 1: return "Red Eyes";
            case 2: return "Blue Eyes + Yellow Face";
            case 3: return "Black Hat";
            case 4: return "Red Bow Tie + Green Eyes";
            case 5: return "Blue Hat + Pink Face";
            case 6: return "All Decorations";
            case 7: return "Pink Eyes + Black Bow Tie";
            default: return "Unknown";
        }
    }
    
    private AntComponent createDecoratedAnt(int index, int x, int y, int length, int width) {
        // Varied base properties for visual interest
        AntSize[] sizes = {AntSize.MEDIUM, AntSize.MEDIUM, AntSize.LARGE, AntSize.SMALL, 
                          AntSize.MEDIUM, AntSize.LARGE, AntSize.LARGE, AntSize.MEDIUM};
        AntColor[] colors = {AntColor.GREEN, AntColor.YELLOW, AntColor.RED, AntColor.ORANGE,
                           AntColor.GREEN, AntColor.YELLOW, AntColor.RED, AntColor.ORANGE};
        AntPose[] poses = {AntPose.NORMAL, AntPose.NORMAL, AntPose.NORMAL, AntPose.STANDING,
                          AntPose.WALKING, AntPose.NORMAL, AntPose.NORMAL, AntPose.STANDING};
        
        // Create base ant
        AntComponent ant = new DecoratedAnt(x, y, length, width, 
                                          sizes[index % sizes.length], 
                                          colors[index % colors.length], 
                                          poses[index % poses.length]);
        
        // Apply decorations based on index
        switch (index % 8) {
            case 0: // Basic ant - no decorations
                break;
            case 1: // Red eyes
                ant = new EyeColorDecorator(ant, Color.RED);
                break;
            case 2: // Blue eyes + Yellow face
                ant = new EyeColorDecorator(ant, Color.BLUE);
                ant = new FaceColorDecorator(ant, Color.YELLOW);
                break;
            case 3: // Black hat
                ant = new HatDecorator(ant, Color.BLACK);
                break;
            case 4: // Red bow tie + Green eyes
                ant = new BowTieDecorator(ant, Color.RED);
                ant = new EyeColorDecorator(ant, Color.GREEN);
                break;
            case 5: // Blue hat + Pink face
                ant = new HatDecorator(ant, Color.BLUE);
                ant = new FaceColorDecorator(ant, Color.PINK);
                break;
            case 6: // All decorations
                ant = new EyeColorDecorator(ant, Color.MAGENTA);
                ant = new FaceColorDecorator(ant, Color.GREEN);
                ant = new HatDecorator(ant, Color.RED);
                ant = new BowTieDecorator(ant, Color.BLUE);
                break;
            case 7: // Pink eyes + Black bow tie
                ant = new EyeColorDecorator(ant, Color.PINK);
                ant = new BowTieDecorator(ant, Color.BLACK);
                break;
        }
        
        return ant;
    }

    public void draw() {
        for (AntComponent ant : decoratedAnts) {
            ant.draw();
        }
    }
    
    // Methods for GUI control
    public void setAllColors(AntColor color) {
        regenerateWithNewBaseProperties(color, null, null);
    }
    
    public void setAllSizes(AntSize size) {
        regenerateWithNewBaseProperties(null, size, null);
    }
    
    public void setAllPoses(AntPose pose) {
        regenerateWithNewBaseProperties(null, null, pose);
    }
    
    private void regenerateWithNewBaseProperties(AntColor newColor, AntSize newSize, AntPose newPose) {
        for (int i = 0; i < decoratedAnts.size(); i++) {
            AntComponent currentAnt = decoratedAnts.get(i);
            
            // Extract base ant properties
            DecoratedAnt baseAnt = extractBaseAnt(currentAnt);
            if (baseAnt != null) {
                // Use new properties or keep current
                AntColor useColor = (newColor != null) ? newColor : getCurrentColor(i);
                AntSize useSize = (newSize != null) ? newSize : getCurrentSize(i);
                AntPose usePose = (newPose != null) ? newPose : getCurrentPose(i);
                
                // Recreate with same position but new properties
                AntComponent newAnt = new DecoratedAnt(baseAnt.getX(), baseAnt.getY(), 
                                                     LENGTH_MIN, WIDTH_MIN, 
                                                     useSize, useColor, usePose);
                
                // Reapply decorations
                newAnt = reapplyDecorations(newAnt, i);
                decoratedAnts.set(i, newAnt);
            }
        }
    }
    
    private DecoratedAnt extractBaseAnt(AntComponent component) {
        while (component instanceof AntDecorator) {
            component = ((AntDecorator) component).getAntComponent();
        }
        return (component instanceof DecoratedAnt) ? (DecoratedAnt) component : null;
    }
    
    private AntColor getCurrentColor(int index) {
        AntColor[] colors = {AntColor.GREEN, AntColor.YELLOW, AntColor.RED, AntColor.ORANGE,
                           AntColor.GREEN, AntColor.YELLOW, AntColor.RED, AntColor.ORANGE};
        return colors[index % colors.length];
    }
    
    private AntSize getCurrentSize(int index) {
        AntSize[] sizes = {AntSize.MEDIUM, AntSize.MEDIUM, AntSize.LARGE, AntSize.SMALL, 
                          AntSize.MEDIUM, AntSize.LARGE, AntSize.LARGE, AntSize.MEDIUM};
        return sizes[index % sizes.length];
    }
    
    private AntPose getCurrentPose(int index) {
        AntPose[] poses = {AntPose.NORMAL, AntPose.NORMAL, AntPose.NORMAL, AntPose.STANDING,
                          AntPose.WALKING, AntPose.NORMAL, AntPose.NORMAL, AntPose.STANDING};
        return poses[index % poses.length];
    }
    
    private AntComponent reapplyDecorations(AntComponent baseAnt, int index) {
        // Reapply the same decorations based on index
        switch (index % 8) {
            case 0: // Basic ant
                break;
            case 1: // Red eyes
                baseAnt = new EyeColorDecorator(baseAnt, Color.RED);
                break;
            case 2: // Blue eyes + Yellow face
                baseAnt = new EyeColorDecorator(baseAnt, Color.BLUE);
                baseAnt = new FaceColorDecorator(baseAnt, Color.YELLOW);
                break;
            case 3: // Black hat
                baseAnt = new HatDecorator(baseAnt, Color.BLACK);
                break;
            case 4: // Red bow tie + Green eyes
                baseAnt = new BowTieDecorator(baseAnt, Color.RED);
                baseAnt = new EyeColorDecorator(baseAnt, Color.GREEN);
                break;
            case 5: // Blue hat + Pink face
                baseAnt = new HatDecorator(baseAnt, Color.BLUE);
                baseAnt = new FaceColorDecorator(baseAnt, Color.PINK);
                break;
            case 6: // All decorations
                baseAnt = new EyeColorDecorator(baseAnt, Color.MAGENTA);
                baseAnt = new FaceColorDecorator(baseAnt, Color.GREEN);
                baseAnt = new HatDecorator(baseAnt, Color.RED);
                baseAnt = new BowTieDecorator(baseAnt, Color.BLUE);
                break;
            case 7: // Pink eyes + Black bow tie
                baseAnt = new EyeColorDecorator(baseAnt, Color.PINK);
                baseAnt = new BowTieDecorator(baseAnt, Color.BLACK);
                break;
        }
        return baseAnt;
    }
    
    public void setMixedColors() {
        regenerateWithNewBaseProperties(null, null, null);
    }
    
    public void setMixedSizes() {
        regenerateWithNewBaseProperties(null, null, null);
    }
    
    public void setMixedPoses() {
        regenerateWithNewBaseProperties(null, null, null);
    }
    
    public void setOriginalVariations() {
        decoratedAnts.clear();
        generateDecoratedAnts();
    }
}