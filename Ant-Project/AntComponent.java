package drawingTool;

import java.awt.Point;


public interface AntComponent extends LocatedRectangle {
    void drawAt(int left, int bottom);
    String getDescription();
}