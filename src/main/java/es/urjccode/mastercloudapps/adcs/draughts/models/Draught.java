package es.urjccode.mastercloudapps.adcs.draughts.models;

class Draught extends Piece {

    private static final int MAX_DISTANCE = 7;

    Draught(Color color) {
        super(color);
    }

    Error isCorrect(Coordinate origin, Coordinate target, PieceProvider pieceProvider) {
		if (!origin.isDiagonal(target)) {
			return Error.NOT_DIAGONAL;
		}
		if (!pieceProvider.isEmpty(target)) {
			return Error.NOT_EMPTY_TARGET;
		}
		int distance = origin.diagonalDistance(target);
		if (distance > Draught.MAX_DISTANCE) {
			return Error.BAD_DISTANCE;
		}
		return null;
	}

}