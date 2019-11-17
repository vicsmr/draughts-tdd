package es.urjccode.mastercloudapps.adcs.draughts.models;

class Pawn extends Piece {

    private static final int MAX_DISTANCE = 2;

    Pawn(Color color) {
        super(color);
    }

    Error isCorrect(Coordinate origin, Coordinate target, PieceProvider pieceProvider) {
        Error error = super.isCorrect(origin, target, pieceProvider);
        if (error != null) {
            return error;
        }
        if (!this.isAdvanced(origin, target)) {
			return Error.NOT_ADVANCED;
		}
        int distance = origin.diagonalDistance(target);
		if (distance > Pawn.MAX_DISTANCE) {
			return Error.BAD_DISTANCE;
		}
		if (distance == Pawn.MAX_DISTANCE) {
			if (pieceProvider.getPiece(origin.betweenDiagonal(target)) == null) {
				return Error.EATING_EMPTY;
			}
        }
        return null;
    }

}