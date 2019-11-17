package es.urjccode.mastercloudapps.adcs.draughts.views;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.models.Color;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Console;

@RunWith(MockitoJUnitRunner.class)
public class PlayViewTest {

    @Mock
    PlayController playController;

    @Mock
    Console console;

    @InjectMocks
    PlayView playView;

    private static final String MOVE = "Mueven las negras: ";
    private static final String CHOOSE = "Elige opción: ";

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGivenPlayViewWhenCorrectFormatThenOk() {
        this.prepareTurnAndDimension();
        when(console.readString(PlayViewTest.MOVE)).thenReturn("32.41");
        interactAndVerify();
    }

    @Test
    public void testGivenPlayViewWhenInteractWithEmptyThenError() {
        this.prepareTurnAndDimension();
        when(console.readString(PlayViewTest.MOVE)).thenReturn("").thenReturn("32.41");
        interactAndVerify();
    }

    @Test
    public void testGivenPlayViewWhenInteractWithBadFormatThenError() {
        this.prepareTurnAndDimension();
        when(console.readString(PlayViewTest.MOVE)).thenReturn("a3.42").thenReturn("32.41");
        interactAndVerify();
    }

    @Test
    public void testGivenPlayViewWhenInteractWithBadRangeThenError() {
        this.prepareTurnAndDimension();
        when(console.readString(PlayViewTest.MOVE)).thenReturn("93.49").thenReturn("32.41");
        interactAndVerify();
    }

    private void interactAndVerify() {
        playView.interact(playController);
        verify(playController).move(new Coordinate(2, 1), new Coordinate(3, 0));
    }

    private void prepareTurnAndDimension() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(playController.getDimension()).thenReturn(8);
        when(console.readString(PlayViewTest.CHOOSE)).thenReturn("1");
    }

    @Test
    public void givenPlayViewWhenCancelGameThenIsCancelled() {
        when(console.readString(PlayViewTest.CHOOSE)).thenReturn("2");
        interactAndNextState();
    }

    @Test
    public void givenPlayViewWhenIsWrongOptionThenRepeatOptionsAndCancelGameThenIsCancelled() {
        when(console.readString(PlayViewTest.CHOOSE))
        .thenReturn("3")
        .thenReturn("2");
        interactAndNextState();
    }

    private void interactAndNextState() {
        playView.interact(playController);
        verify(playController).nextState();
    }

}