package drawingTool;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JPanel;

public class DecoratorTestArea extends JPanel {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Drawing.set(g);
        
        // Draw title first
        drawTitle(g);
        
        // Create test ants with better spacing - includes all 7 test cases
        createAndDrawTestAnts(g);
    }
    
    private void drawTitle(Graphics g) {
        // Draw title with proper spacing
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Decorator Pattern Test - Various Ant Decorations", 50, 40);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("This demonstrates the Decorator Pattern with different eye colors, face colors, and accessories", 50, 65);
    }
    
    private void createAndDrawTestAnts(Graphics g) {
        int startX = 120; // Reduced start position to fit 7 ants
        int startY = 350;
        int spacing = 120; // Reduced spacing to fit 7 ants
        
        // Test Case 1: Basic ant
        AntComponent ant1 = new DecoratedAnt(startX, startY, 80, 250, AntSize.MEDIUM, AntColor.GREEN, AntPose.NORMAL);
        ant1.drawAt(startX, startY);
        drawLabelBelow(g, ant1, startX, "Basic Ant");
        
        // Test Case 2: Ant with red eyes
        AntComponent ant2 = new DecoratedAnt(startX + spacing, startY, 80, 250, AntSize.MEDIUM, AntColor.YELLOW, AntPose.NORMAL);
        ant2 = new EyeColorDecorator(ant2, Color.RED);
        ant2.drawAt(startX + spacing, startY);
        drawLabelBelow(g, ant2, startX + spacing, "Red Eyes");
        
        // Test Case 3: Ant with blue eyes and yellow face
        AntComponent ant3 = new DecoratedAnt(startX + 2*spacing, startY, 80, 250, AntSize.MEDIUM, AntColor.RED, AntPose.NORMAL);
        ant3 = new EyeColorDecorator(ant3, Color.BLUE);
        ant3 = new FaceColorDecorator(ant3, Color.YELLOW);
        ant3.drawAt(startX + 2*spacing, startY);
        drawLabelBelow(g, ant3, startX + 2*spacing, "Blue Eyes + Yellow Face");
        
        // Test Case 4: Ant with black hat - FIXED: Changed to NORMAL pose to fix eye positioning
        AntComponent ant4 = new DecoratedAnt(startX + 3*spacing, startY, 80, 250, AntSize.LARGE, AntColor.ORANGE, AntPose.NORMAL);
        ant4 = new HatDecorator(ant4, Color.BLACK);
        ant4.drawAt(startX + 3*spacing, startY);
        drawLabelBelow(g, ant4, startX + 3*spacing, "Black Hat");
        
        // Test Case 5: Ant with red bow tie and green eyes
        AntComponent ant5 = new DecoratedAnt(startX + 4*spacing, startY, 80, 250, AntSize.SMALL, AntColor.GREEN, AntPose.WALKING);
        ant5 = new BowTieDecorator(ant5, Color.RED);
        ant5 = new EyeColorDecorator(ant5, Color.GREEN);
        ant5.drawAt(startX + 4*spacing, startY);
        drawLabelBelow(g, ant5, startX + 4*spacing, "Red Bow Tie + Green Eyes");
        
        // Test Case 6: Ant with all decorations
        AntComponent ant6 = new DecoratedAnt(startX + 5*spacing, startY, 80, 250, AntSize.LARGE, AntColor.YELLOW, AntPose.NORMAL);
        ant6 = new EyeColorDecorator(ant6, Color.MAGENTA);
        ant6 = new FaceColorDecorator(ant6, Color.GREEN);
        ant6 = new HatDecorator(ant6, Color.BLUE);
        ant6 = new BowTieDecorator(ant6, Color.RED);
        ant6.drawAt(startX + 5*spacing, startY);
        drawLabelBelow(g, ant6, startX + 5*spacing, "All Decorations");
        
        // Test Case 7: Added the missing 7th ant: Pink eyes + Black bow tie (STANDING pose)
        AntComponent ant7 = new DecoratedAnt(startX + 6*spacing, startY, 80, 250, AntSize.MEDIUM, AntColor.RED, AntPose.STANDING);
        ant7 = new EyeColorDecorator(ant7, Color.PINK);
        ant7 = new BowTieDecorator(ant7, Color.BLACK);
        ant7.drawAt(startX + 6*spacing, startY);
        drawLabelBelow(g, ant7, startX + 6*spacing, "Pink Eyes + Black Bow Tie");
    }
    
    private void drawLabelBelow(Graphics g, AntComponent ant, int centerX, String title) {
        // Get the bottom position of the ant to place text below it
        DecoratedAnt decoratedAnt = extractDecoratedAnt(ant);
        if (decoratedAnt != null) {
            Point stingerBottom = decoratedAnt.getStingerBottomPosition();
            int textY = stingerBottom.y + 40;
            
            // Draw title with clear, readable font
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 11)); // Slightly smaller font to fit better
            
            // Center the title text
            int titleWidth = g.getFontMetrics().stringWidth(title);
            g.drawString(title, centerX - titleWidth/2, textY);
            
            // Draw description
            g.setFont(new Font("Arial", Font.PLAIN, 9)); // Smaller description font
            String description = ant.getDescription();
            String[] lines = description.split(", ");
            
            int lineHeight = 10; // Reduced line height
            int currentY = textY + 15;
            
            for (String line : lines) {
                int lineWidth = g.getFontMetrics().stringWidth(line);
                g.drawString(line, centerX - lineWidth/2, currentY);
                currentY += lineHeight;
            }
        }
    }
    
    private DecoratedAnt extractDecoratedAnt(AntComponent component) {
        while (component instanceof AntDecorator) {
            component = ((AntDecorator) component).getAntComponent();
        }
        return (component instanceof DecoratedAnt) ? (DecoratedAnt) component : null;
    }
}