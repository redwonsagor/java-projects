package drawingTool;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

public class DecoratorPatternTest extends JFrame {
    
    public DecoratorPatternTest(String title) {
        super(title);
        
        setLayout(new BorderLayout());
        
        // Create a special drawing area for decorator testing
        DecoratorTestArea testArea = new DecoratorTestArea();
        add(testArea, BorderLayout.CENTER);

        Dimension screenSize = getToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    
    public static void main(String[] args) {
        new DecoratorPatternTest("Decorator Pattern Test - Ant Variations");
    }
}