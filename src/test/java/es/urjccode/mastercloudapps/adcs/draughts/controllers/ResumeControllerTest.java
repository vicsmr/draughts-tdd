package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Game;
import es.urjccode.mastercloudapps.adcs.draughts.models.State;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;

public class ResumeControllerTest {

    ResumeController resumeController;
    State state;

    @Before
    public void initialize() {
        Game game = new Game();
        state = new State();
        resumeController = new ResumeController(game, state);
    }

    @Test
    public void givenResumeControllerWhenResumeGameMoveToInitialStateRequiereCorrectThenNotError() {
        assertEquals(StateValue.INITIAL, state.getValueState());
        resumeController.next();
        assertEquals(StateValue.IN_GAME, state.getValueState());
        resumeController.next();
        assertEquals(StateValue.FINAL, state.getValueState());
        resumeController.reset();
        assertEquals(StateValue.INITIAL, state.getValueState());
    }

    @Test(expected = AssertionError.class)
    public void givenResumeControllerWhenResumeGameMoveOutThenError() {
        for (StateValue stateValue : StateValue.values()) {
            assertEquals(stateValue, state.getValueState());
            resumeController.next();
        }
    }
}