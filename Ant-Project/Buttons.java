package drawingTool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Buttons {
    // Color control buttons - ALL PRIVATE
    private JButton greenColorButton = new JButton("Green Ants");
    private JButton yellowColorButton = new JButton("Yellow Ants");
    private JButton redColorButton = new JButton("Red Ants");
    private JButton orangeColorButton = new JButton("Orange Ants");
    private JButton mixedColorsButton = new JButton("Mixed Colors");
    
    // Size control buttons - ALL PRIVATE
    private JButton smallSizeButton = new JButton("Small Size");
    private JButton mediumSizeButton = new JButton("Medium Size");
    private JButton largeSizeButton = new JButton("Large Size");
    private JButton mixedSizesButton = new JButton("Mixed Sizes");
    
    // Pose control buttons - ALL PRIVATE
    private JButton normalPoseButton = new JButton("Normal Pose");
    private JButton standingPoseButton = new JButton("Standing Pose");
    private JButton walkingPoseButton = new JButton("Walking Pose");
    private JButton mixedPosesButton = new JButton("Mixed Poses");
    
    // Reset button - ALL PRIVATE
    private JButton resetButton = new JButton("Original Variations");

    public void addActionListener(ActionListener listener) {
        // Color buttons
        greenColorButton.addActionListener(listener);
        yellowColorButton.addActionListener(listener);
        redColorButton.addActionListener(listener);
        orangeColorButton.addActionListener(listener);
        mixedColorsButton.addActionListener(listener);
        
        // Size buttons
        smallSizeButton.addActionListener(listener);
        mediumSizeButton.addActionListener(listener);
        largeSizeButton.addActionListener(listener);
        mixedSizesButton.addActionListener(listener);
        
        // Pose buttons
        normalPoseButton.addActionListener(listener);
        standingPoseButton.addActionListener(listener);
        walkingPoseButton.addActionListener(listener);
        mixedPosesButton.addActionListener(listener);
        
        // Reset button
        resetButton.addActionListener(listener);
    }

    public void addButtonsToAPanel(JFrame frame) {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(20, 1, 5, 5));

        // Color section
        menu.add(new JLabel("=== ANT COLORS ==="));
        menu.add(greenColorButton);
        menu.add(yellowColorButton);
        menu.add(redColorButton);
        menu.add(orangeColorButton);
        menu.add(mixedColorsButton);
        
        // Size section
        menu.add(new JLabel("=== ANT SIZES ==="));
        menu.add(smallSizeButton);
        menu.add(mediumSizeButton);
        menu.add(largeSizeButton);
        menu.add(mixedSizesButton);
        
        // Pose section
        menu.add(new JLabel("=== ANT POSES ==="));
        menu.add(normalPoseButton);
        menu.add(standingPoseButton);
        menu.add(walkingPoseButton);
        menu.add(mixedPosesButton);
        
        // Reset section
        menu.add(new JLabel("=== RESET ==="));
        menu.add(resetButton);

        menu.setBorder(BorderFactory.createRaisedBevelBorder());
        frame.add(menu, BorderLayout.WEST);
    }

    // Getters for all buttons (PUBLIC methods to access PRIVATE buttons)
    public JButton getGreenColorButton() { return greenColorButton; }
    public JButton getYellowColorButton() { return yellowColorButton; }
    public JButton getRedColorButton() { return redColorButton; }
    public JButton getOrangeColorButton() { return orangeColorButton; }
    public JButton getMixedColorsButton() { return mixedColorsButton; }
    
    public JButton getSmallSizeButton() { return smallSizeButton; }
    public JButton getMediumSizeButton() { return mediumSizeButton; }
    public JButton getLargeSizeButton() { return largeSizeButton; }
    public JButton getMixedSizesButton() { return mixedSizesButton; }
    
    public JButton getNormalPoseButton() { return normalPoseButton; }
    public JButton getStandingPoseButton() { return standingPoseButton; }
    public JButton getWalkingPoseButton() { return walkingPoseButton; }
    public JButton getMixedPosesButton() { return mixedPosesButton; }
    
    public JButton getResetButton() { return resetButton; }
}