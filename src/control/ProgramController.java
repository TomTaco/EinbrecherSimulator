package control;

import control.framework.UIController;
import model.Mainmenu;
import model.Overlay;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    // Attribute

    // Referenzen
    private UIController uiController;
    private Mainmenu menu;

    public ProgramController(UIController uiController){
        this.uiController = uiController;
    }


    public void startProgram(){

        menu = new Mainmenu(this);
        uiController.drawObject(menu);

    }

    public void startGame(){
        uiController.removeObject(menu);
        menu = null;
        Overlay ov = new Overlay(uiController);
        uiController.drawObject(ov);
    }

    public  void updateProgram(double dt){

    }


}
