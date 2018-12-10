package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class House extends GraphicalObject {

    private String housePng;
    private int x, y;

    public House(String housePng, int x, int y){
        this.x = x;
        this.y = y;
        this.housePng = housePng;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("assets/images/"+housePng+".png"),x,y,150,150 );
    }
}
