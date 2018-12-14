package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Background extends GraphicalObject {

    public Background(){
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/district1.png"),0,0);
    }
}