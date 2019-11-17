package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.models.Error;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;

class PlayView extends SubView {

    private static final String[] COLORS = { "blancas", "negras" };
    private static final String MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";
    private static final String FORMAT = "xx.xx";
    private static final String FORMAT_ERROR = "Error!!! Formato incorrecto";
    private static final String MOVE = "Mueven las ";

    PlayView() {
        super();
    }

    void interact(PlayController playController) {
        assert playController != null;
        Error errorOption;
        do {
            errorOption = null;
            String command = this.console.readString("Elige opción: ");
            if ("1".equals(command)) {
                Coordinate origin = null;
                Coordinate target = null;
                Error error;
                do {
                    error = null;
                    String color = PlayView.COLORS[playController.getColor().ordinal()];
                    String format = this.console.readString(PlayView.MOVE+ color + ": ");
                    if (format.length() != PlayView.FORMAT.length()) {
                        this.console.write(PlayView.FORMAT_ERROR);
                        error = Error.BAD_FORMAT;
                    } else {
                        origin = Coordinate.getInstance(format.substring(0, 2));
                        target = Coordinate.getInstance(format.substring(3, 5));
                        if (origin == null || target == null) {
                            error = Error.BAD_FORMAT;
                        } 
                    }
                } while (error != null);
                error = playController.isCorrect(origin, target);
                if (error == null) {
                    playController.move(origin, target);
                    if (playController.isBlocked()) {
                        this.console.writeln(PlayView.MESSAGE);
                    } else {
                        new GameView().write(playController);
                    }
                }
            } else if ("2".equals(command)) {
                playController.nextState();
            } else {
                errorOption = Error.WRONG_OPTION;
            }
        } while (errorOption != null);
    }

}