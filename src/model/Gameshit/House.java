package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class House extends GraphicalObject {

    private String housePng;
    private int x, y;

    public House(String housePng, int x, int y){
        this.x = x;
        this.y = y;
        this.housePng = housePng;
        createAndSetNewImage("assets/images/"+housePng+".png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(getMyImage(),x,y,150,150 );
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if (e.getX() > x && e.getX() < x + getMyImage().getWidth() && e.getY() > y && e.getY() < y+ getMyImage().getHeight()) {

        }
    }
}
