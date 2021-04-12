/**
 * @author Aashish Anand, Anand Gogoi, Caitlin Ching, Cian Murray
 * Local game class - creates a new game state, implement the move,
 * capture, and turn to king actions, and update it to the new game state
 *
 * CS301A
 * @version 04/11/2021
 */

package com.example.checkers.CheckersGame;

import com.example.checkers.CheckersGame.Actions.CheckersCancelMoveAction;
import com.example.checkers.CheckersGame.Actions.ChooseAction;
import com.example.checkers.CheckersGame.infoMessage.CheckersGameState;
import com.example.checkers.game.GameFramework.LocalGame;
import com.example.checkers.game.GameFramework.actionMessage.GameAction;
import com.example.checkers.game.GameFramework.players.GamePlayer;

public class CheckersLocalGame extends LocalGame {

    //CheckersGameState checkersGameState;//I added this

    /**
     * Constructor for the CheckersLocalGame.
     */
    public CheckersLocalGame() {
        super();
        super.state = new CheckersGameState();
        //checkersGameState = new CheckersGameState();
    } //CheckersLocalGame

    /**
     * Constructor for the CheckersLocalGame with loaded checkersGameState.
     * @param checkersGameState
     */
    public CheckersLocalGame(CheckersGameState checkersGameState) {
        super();
        super.state = new CheckersGameState(checkersGameState);
    } //CheckersLocalGame

    /**
     * method sendUpdatedStateTo
     * notifies the players that the state of the game has changed
     * should involve sending a GameInfo object to the player
     *
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new CheckersGameState((CheckersGameState) state));
    } //sendUpdatedStateTo

    /**
     * method canMove
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     * 		true iff the player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((CheckersGameState) state).getPlayerTurn();
    } //canMove

    /**
     * method checkIfGameOver
     * checks if the game is over. If it's over, return a message
     * showing who won the game. If not, return null
     *
     * @return
     *          a String message showing the winner of the game
     *          or null
     */
    @Override
    protected String checkIfGameOver() {
        CheckersGameState state = (CheckersGameState) super.state;
        if (state.getP1NumPieces() == 0) {
            return "Player 2 won!";
        } else if (state.getP2NumPieces() == 0) {
            return "Player 1 won!";
        } else {
            return null;
        }
    } //checkIfGameOver

    /**
     * Makes a move on behalf of a player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof CheckersCancelMoveAction){
            CheckersCancelMoveAction cancelMoveAction = (CheckersCancelMoveAction) action;
            CheckersGameState state = (CheckersGameState) super.state;
            int cancelRow = cancelMoveAction.getSelectedRow();
            int cancelCol = cancelMoveAction.getSelectedCol();
            //deselects the piece to undo an action
            state.setPieceSelectedPieceAndPieceSelectedBoolean();
            state.setMessage("The piece at " + cancelRow + ", " + cancelCol +
                    " has been unselected.\nPlease select another piece.");
            return true;
        }
        else if(action instanceof ChooseAction){

            ChooseAction ca = (ChooseAction) action;
            CheckersGameState state = (CheckersGameState) super.state;

            int x = ca.getX();
            int y = ca.getY();

            // get player id
            int playerId = state.getPlayerTurn();

            if(!state.isPieceSelectedBoolean()){
                // checks if spot is empty
                if(state.isEmpty(x,y)){
                    state.setMessage("This tile is empty. Pick another square");
                    return false;
                }
                else {
                    // checks if piece belongs to player
                    if (state.hasEnemyPieces(x,y)) {
                        state.setMessage("This piece is not yours. Please try again.");
                    }
                    // if conditions are true, piece is chosen
                    else{
                        state.setMessage("This piece can be moved. Click on the spot where you want to move it." );
                        state.setPieceSelectedPieceAndPieceSelectedBoolean(x,y);
                        return true;
                    }
                }
            }
            else{
                int xDire=x-state.getPieceSelectedPiece().getXcoordinate();
                int yDire=y-state.getPieceSelectedPiece().getYcoordinate();
                    if(state.hasEnemyPieces(x,y)){
                        if(state.CaptureEnemyPiece(x,y,state.getPieceSelectedPiece().getXcoordinate(),state.getPieceSelectedPiece().getYcoordinate())){
                            return true;
                        }
                    }
                if(state.canMove(state.getPieceSelectedPiece(),xDire,yDire,state.getPlayerTurn())){

                    state.move(xDire,yDire);
                    state.setMessage("This is a valid move." );
                    return true;
                }
            }
        }
        return false;
    } //makeMove

}
