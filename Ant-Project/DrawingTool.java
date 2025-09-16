package drawingTool;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DrawingTool extends JFrame implements ActionListener {
    private DrawingArea drawing;
    private Buttons buttons = new Buttons();

    public DrawingTool(String title) {
        super(title);
        
        setLayout(new BorderLayout());

        constructButtonMenu();
        constructDrawingArea();

        Dimension screenSize = getToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    
    private void constructButtonMenu() {
        buttons.addButtonsToAPanel(this);
        buttons.addActionListener(this);
    }

    private void constructDrawingArea() {
        drawing = new DrawingArea();
        add(drawing, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Color controls - Updated to use separate enum files
        if (e.getSource() == buttons.getGreenColorButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllColors(AntColor.GREEN);
        } else if (e.getSource() == buttons.getYellowColorButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllColors(AntColor.YELLOW);
        } else if (e.getSource() == buttons.getRedColorButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllColors(AntColor.RED);
        } else if (e.getSource() == buttons.getOrangeColorButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllColors(AntColor.ORANGE);
        } else if (e.getSource() == buttons.getMixedColorsButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setMixedColors();
        }
        
        // Size controls - Updated to use separate enum files
        else if (e.getSource() == buttons.getSmallSizeButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllSizes(AntSize.SMALL);
        } else if (e.getSource() == buttons.getMediumSizeButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllSizes(AntSize.MEDIUM);
        } else if (e.getSource() == buttons.getLargeSizeButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllSizes(AntSize.LARGE);
        } else if (e.getSource() == buttons.getMixedSizesButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setMixedSizes();
        }
        
        // Pose controls - Updated to use separate enum files
        else if (e.getSource() == buttons.getNormalPoseButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllPoses(AntPose.NORMAL);
        } else if (e.getSource() == buttons.getStandingPoseButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllPoses(AntPose.STANDING);
        } else if (e.getSource() == buttons.getWalkingPoseButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setAllPoses(AntPose.WALKING);
        } else if (e.getSource() == buttons.getMixedPosesButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setMixedPoses();
        }
        
        // Reset
        else if (e.getSource() == buttons.getResetButton()) {
            tidyUpDrawingArea();
            drawing.getScene().setOriginalVariations();
        }
    }

    private void tidyUpDrawingArea() {
        drawing.removeAll();
        drawing.revalidate();
        drawing.repaint();        
    }
    
    public static void main(String[] args) {
        new DrawingTool("My Ant Colony");
    }
}