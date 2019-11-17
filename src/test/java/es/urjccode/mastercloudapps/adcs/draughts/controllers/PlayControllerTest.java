package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;
import es.urjccode.mastercloudapps.adcs.draughts.models.Game;
import es.urjccode.mastercloudapps.adcs.draughts.models.GameBuilder;
import es.urjccode.mastercloudapps.adcs.draughts.models.State;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;
import es.urjccode.mastercloudapps.adcs.draughts.models.Color;

public class PlayControllerTest {

    @Test
    public void givenPlayControllerWhenMovementRequiereCorrectThenNotError() {
        Game game = new Game();
        State state = new State();
        PlayController playController = new PlayController(game, state);
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(4, 1);
        assertNull(playController.isCorrect(origin, target));
        playController.move(origin, target);
        assertNull(playController.getPiece(origin));
        assertEquals(playController.getColor(target), Color.WHITE);
    }

    @Test
    public void givenPlayControllerWhenMovementRequiereCorrectThenNotErro() {
        Game game = new GameBuilder()
        .row("        ")
        .row("        ")
        .row("        ")
        .row(" n      ")
        .row("b       ")
        .row("        ")
        .row("        ")
        .row("        ")
        .build();
        State state = new State();
        state.next();
        PlayController playController = new PlayController(game, state);
        playController.move(new Coordinate(4, 0), new Coordinate(2, 2));
        assertEquals(StateValue.FINAL, state.getValueState());
    }

}