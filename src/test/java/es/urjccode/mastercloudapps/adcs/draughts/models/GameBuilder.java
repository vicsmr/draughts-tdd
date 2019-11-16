package es.urjccode.mastercloudapps.adcs.draughts.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameBuilder {

    private List<String> rowsGame;

    GameBuilder() {
        this.rowsGame = new ArrayList<String>();
    }

    public GameBuilder row(String rowGame) {
        this.rowsGame.add(rowGame);
        return this;
    }

    public Game build() {
        Board board = new Board();
        Map<Character, CloneObjectLambdaFunction> pieceMap = new HashMap<Character, CloneObjectLambdaFunction>();
        pieceMap.put('b', () -> new Pawn(Color.WHITE));
        pieceMap.put('B', () -> new Draught(Color.WHITE));
        pieceMap.put('n', () -> new Pawn(Color.BLACK));
        pieceMap.put('N', () -> new Draught(Color.BLACK));
        for (int i = 0; i < this.rowsGame.size(); i++) {
            for (int j = 0; j < this.rowsGame.size(); j++) {
                char character = this.rowsGame.get(i).charAt(j);
                if (character != ' ') {
                    Piece piece = (Piece) pieceMap.get(character).call();
                    board.put(new Coordinate(i, j), piece);
                }
            }
        }
		return new Game(board);
    }
}