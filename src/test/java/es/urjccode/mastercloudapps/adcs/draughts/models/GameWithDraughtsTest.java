package es.urjccode.mastercloudapps.adcs.draughts.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class GameWithDraughtsTest {

    Game game;

    @Test
    public void testGivenGameWhenWhitePawnAtLimitThenNewDraugts() {
        Coordinate origin = new Coordinate(1, 0);
        Coordinate target = new Coordinate(0, 1);
        this.game = new GameBuilder()
            .row("        ")
            .row("b       ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.WHITE, this.game.getColor(target));
        assertEquals(this.game.getPiece(target).getClass(), Draught.class);
    }

    @Test
    public void testGivenGameWhenWhitePawnAtLimitAndEatingThenNewDraugts() {
        Coordinate origin = new Coordinate(2, 1);
        Coordinate target = new Coordinate(0, 3);
        this.game = new GameBuilder()
            .row("        ")
            .row("  n     ")
            .row(" b      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNull(this.game.getPiece(new Coordinate(1, 2)));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.WHITE, this.game.getColor(target));
        assertEquals(this.game.getPiece(target).getClass(), Draught.class);
    }
    
    @Test
    public void testGivenGameWhenDraughtMoveOneSquareThenCorrect() {
        Coordinate origin = new Coordinate(0, 7);
        Coordinate target = new Coordinate(1, 6);
        this.game = new GameBuilder()
            .row("       B")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.WHITE, this.game.getColor(target));
    }

    @Test
    public void testGivenGameWhenDraughtMoveSevenSquaresThenCorrect() {
        Coordinate origin = new Coordinate(0, 7);
        Coordinate target = new Coordinate(7, 0);
        this.game = new GameBuilder()
            .row("       B")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.WHITE, this.game.getColor(target));
    }

    @Test
    public void testGivenGameWhenDraughtEatingAPieceWithThreePositionsMovementThenCorrect() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNull(this.game.getPiece(new Coordinate(2, 1)));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.WHITE, this.game.getColor(target));
    }

    @Test
    public void testGivenGameWhenDraughtEatingAPieceMovementThenNextTurnNotChange() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        assertEquals(Color.WHITE, this.game.getColor());
    }

    @Test
    public void testGivenGameWhenDraughtEatingTwoMovementThenNextTurnNotChange() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        Coordinate secondTarget = new Coordinate(6, 3);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        this.game.move(target, secondTarget);
        assertEquals(Color.WHITE, this.game.getColor());
    }

    @Test
    public void testGivenGameWhenDraughtEatingThreeMovementThenNextTurnChange() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        Coordinate secondTarget = new Coordinate(5, 2);
        Coordinate thirdTarget = new Coordinate(2, 5);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row(" n n    ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        this.game.move(target, secondTarget);
        this.game.move(secondTarget, thirdTarget);
        assertEquals(Color.BLACK, this.game.getColor());
    }

    @Test
    public void testGivenGameWhenDraughtEatingOneMovementAndMoveWithoutEatingThenNextTurnChange() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        Coordinate secondTarget = new Coordinate(5, 2);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("        ")
            .row(" n      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        this.game.move(origin, target);
        this.game.move(target, secondTarget);
        assertEquals(Color.BLACK, this.game.getColor());
    }

    @Test()
    public void testGivenGameWhenDraughtEatingTwoPiecesWithThreePositionsMovementThenEatingEmptyError() {
        Coordinate origin = new Coordinate(0, 3);
        Coordinate target = new Coordinate(3, 0);
        this.game = new GameBuilder()
            .row("   B    ")
            .row("  n     ")
            .row(" n      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .build();
        assertEquals(Error.EATING_SEVERAL, this.game.isCorrect(origin, target));
    }

    @Test
    public void testGivenGameWhenBlackPawnAtLimitThenNewDraugts() {
        Coordinate origin = new Coordinate(6, 3);
        Coordinate target = new Coordinate(7, 2);
        this.game = new GameBuilder()
            .row("        ")
            .row("        ")
            .row(" b      ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("   n    ")
            .row("        ")
            .build();
        this.game.move(new Coordinate(2, 1), new Coordinate(1, 2));
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.BLACK, this.game.getColor(target));
        assertEquals(this.game.getPiece(target).getClass(), Draught.class);
    }

    @Test
    public void testGivenGameWhenBlackPawnAtLimitAndEatingThenNewDraugts() {
        Coordinate origin = new Coordinate(5, 4);
        Coordinate target = new Coordinate(7, 2);
        this.game = new GameBuilder()
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("        ")
            .row("    n   ")
            .row("        ")
            .row("  b     ")
            .build();
        this.game.move(target, new Coordinate(6, 3));
        this.game.move(origin, target);
        assertNull(this.game.getPiece(origin));
        assertNull(this.game.getPiece(new Coordinate(1, 2)));
        assertNotNull(this.game.getPiece(target));
        assertEquals(Color.BLACK, this.game.getColor(target));
        assertEquals(this.game.getPiece(target).getClass(), Draught.class);
    }
}