package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.image.BufferedImage;

public class Background extends GraphicalObject {

    //Referenzen
    private BufferedImage image;

    public Background(BufferedImage image){
        this.image = image;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(image,0,0,800,800);
}
}