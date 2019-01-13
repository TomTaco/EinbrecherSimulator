package control;

import control.framework.UIController;
import model.Gameshit.Player;
import model.Mainmenu;
import model.Overlay;
import model.framework.SQLController;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    // Attribute

    // Referenzen
    private UIController uiController;
    private Mainmenu menu;
    private SQLController sql;


    public ProgramController(UIController uiController){
        this.uiController = uiController;
    }


    public void startProgram(){

        menu = new Mainmenu(this);
        uiController.drawObject(menu);
        sql = new SQLController();

    }

    public void startGame(){
        Player player = new Player(sql.getStatement(),uiController);
        uiController.drawObject(player);
        uiController.removeObject(menu);
        menu = null;
        Overlay ov = new Overlay(uiController, sql.getStatement(), player);
        uiController.drawObject(ov);

    }

    public  void updateProgram(double dt){

    }


}
