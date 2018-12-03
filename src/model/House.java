package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

/**
 * BEISPIELKLASSE:
 * Es wird ein Haus gezeichnet
 */
public class House extends GraphicalObject {

    //Attribute
    private int hausnummer;

    //Referenzen

    //Konstruktor: Diese Methode wird bei Erzeugung eines Hauses aufgerufen
    public House(){
        hausnummer = 7;
        this.createAndSetNewImage("assets/images/dog_down.png");
    }

    @Override
    /**
     * Diese Methode wird wiederholt, über 25 mal pro Sekunde aufgerufen und zeichnet
     * in schneller Folge immer wieder ihren Inhalt, bevor er vom nächsten Bild ersetzt
     * wird.
     */
    public void draw(DrawTool drawTool) {

    }

}
