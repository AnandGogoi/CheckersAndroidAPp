/**
 * @author Aashish Anand, Anand Gogoi, Caitlin Ching, Cian Murray
 * Cancel Move Action class - deselects the piece the player selected
 *
 * CS301A
 * @version 04/11/2021
 */

package com.example.checkers.CheckersGameFramework.checkersActionMessage;

import com.example.checkers.game.GameFramework.actionMessage.GameAction;
import com.example.checkers.game.GameFramework.players.GamePlayer;

public class CheckersCancelMoveAction extends GameAction {
    // variables for row and column. Not needed. I do not want to delete it though becuase I would have to make edits
    // a lot things
    private int selectedRow;
    private int selectedCol;


    /**
     * constructor for CheckersCancelMoveAction
     *
     * @param player
     *      the player who created the action
     * @param selectedRow y location
     * @param selectedCol x location
     */
    public CheckersCancelMoveAction(GamePlayer player, int selectedRow, int selectedCol) {
        super(player);
        this.selectedRow = selectedRow;
        this.selectedCol = selectedCol;
    } //CheckersCancelMoveAction

    // getter method for variables
    public int getSelectedRow() {
        return selectedRow;
    }
    public int getSelectedCol() {
        return selectedCol;
    }
}