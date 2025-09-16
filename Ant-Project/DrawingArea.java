package drawingTool;

import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawingArea extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private Scene scene = new Scene();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Drawing.set(g); 
        scene.draw();    
    }
    
    public Scene getScene() {
        return scene;
    }
}