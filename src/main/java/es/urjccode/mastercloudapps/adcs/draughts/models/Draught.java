package es.urjccode.mastercloudapps.adcs.draughts.models;

class Draught extends Piece {

    private static final int MAX_DISTANCE = 7;

    Draught(Color color) {
        super(color);
    }

    Error isCorrect(Coordinate origin, Coordinate target, PieceProvider pieceProvider) {
		Error error = super.isCorrect(origin, target, pieceProvider);
        if (error != null) {
            return error;
        }
		int distance = origin.diagonalDistance(target);
		if (distance > Draught.MAX_DISTANCE) {
			return Error.BAD_DISTANCE;
        }
		return null;
	}

}