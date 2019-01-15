package control;

import control.framework.UIController;
import model.Gameshit.Inventory;
import model.Gameshit.Player;
import model.Gameshit.Shop;
import model.Mainmenu;
import model.Overlay;
import model.framework.GraphicalObject;
import model.framework.SQLController;

import java.awt.event.KeyEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController extends GraphicalObject {

    // Attribute

    // Referenzen
    private UIController uiController;
    private Mainmenu menu;
    private SQLController sql;
    private Inventory inventory;


    public ProgramController(UIController uiController){
        this.uiController = uiController;
    }


    public void startProgram(){

        menu = new Mainmenu(this);
        uiController.drawObject(menu);
        sql = new SQLController();

    }

    public void startGame(){
        Player player = new Player(sql.getCon(),uiController);
        uiController.removeObject(menu);
        menu = null;
        Overlay ov = new Overlay(uiController, sql.getStmt(), player);
        uiController.drawObject(ov);
        player.setOverlay(ov);
        uiController.drawObject(player);
        inventory = new Inventory(sql.getCon(), player);
        uiController.drawObject(inventory);
        Shop shop = new Shop(sql.getCon(),uiController,player);
        uiController.drawObject(shop);

    }

    public  void updateProgram(double dt){

    }

    @Override
    public void keyReleased(int key) {
        super.keyReleased(key);
        if(inventory != null && key == KeyEvent.VK_I){
            uiController.removeObject(inventory);
            uiController.drawObject(inventory);
        }
    }
}
