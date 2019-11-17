package es.urjccode.mastercloudapps.adcs.draughts.models;

public class Game {

	private Board board;
	private Turn turn;
	private int eatingMovements;

	public Game() {
		this.turn = new Turn();
		this.board = new Board();
		this.eatingMovements = 0;
		for (int i = 0; i < this.board.getDimension(); i++) {
			for (int j = 0; j < this.board.getDimension(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Pawn pawn = this.getInitialPiece(coordinate);
				if (pawn != null) {
					this.board.put(coordinate, pawn);
				}
			}
		}
	}

	public Game (Board board) {
		this.board = board;
		this.turn = new Turn();
	}

	private Pawn getInitialPiece(Coordinate coordinate) {
		assert coordinate != null;
		if (coordinate.isBlack()) {
			final int row = coordinate.getRow();
			Color color = null;
			if (row <= 2) {
				color = Color.BLACK;
			} else if (row >= 5) {
				color = Color.WHITE;
			}
			if (color != null) {
				return new Pawn(color);
			}
		}
		return null;
	}

	public void move(Coordinate origin, Coordinate target) {
		assert this.isCorrect(origin, target) == null;
		boolean isEatingMovement = false;
		if (origin.diagonalDistance(target) > 1) {
			Coordinate coordinateToRemove = origin.betweenDiagonal(target);
			do {
				Piece piece = this.board.getPiece(coordinateToRemove);
				if (piece != null) {
					this.board.remove(coordinateToRemove);
					this.eatingMovements++;
					isEatingMovement = true;
				}
				coordinateToRemove = coordinateToRemove.betweenDiagonal(target);
			} while(coordinateToRemove.diagonalDistance(target) > 0 );
		}
		if ((!isEatingMovement && eatingMovements > 0)) {
			this.changeTurn();
		} else {
			this.doMovement(isEatingMovement, origin, target);
			if (this.isBlocked(this.turn.getOppositeColor())) {
				this.changeTurn();
			}
		}
	}

	private void doMovement(boolean isEatingMovement, Coordinate origin, Coordinate target) {
		Color colorOriginPiece = this.getColor(origin);
		this.board.move(origin, target);
		if (this.board.getPiece(target).isLimit(target)){
			this.board.remove(target);
			this.board.put(target, new Draught(colorOriginPiece));
		}
		if (!isEatingMovement || this.eatingMovements == 3) {
			this.changeTurn();
		}
	}

	private void changeTurn() {
		this.eatingMovements = 0;
		this.turn.change();
	}

	public Error isCorrect(Coordinate origin, Coordinate target){
		assert origin != null;
		assert target != null;
		if (board.isEmpty(origin)) {
			return Error.EMPTY_ORIGIN;
		}
		if (this.turn.getColor() != this.board.getColor(origin)) {
			return Error.OPPOSITE_PIECE;
		}
		Error error = this.board.getPiece(origin).isCorrect(origin, target, board);
		if (error != null) {
			return error;
		}
		if (origin.diagonalDistance(target) > 2) {
			int piecesBetween = this.obtainPiecesBetween(origin, target);
			if (piecesBetween > 1) {
				return Error.EATING_SEVERAL;
			}
		}
		return null;
	}

	private int obtainPiecesBetween(Coordinate origin, Coordinate target) {
		int piecesBetween = 0;
		Coordinate coordinateToRemove = origin.betweenDiagonal(target);
		do {
			Piece piece = this.board.getPiece(coordinateToRemove);
			if (piece != null) {
				piecesBetween++;
			}
			coordinateToRemove = coordinateToRemove.betweenDiagonal(target);
		} while(coordinateToRemove.diagonalDistance(target) > 0 );
		return piecesBetween;
	}

	public Color getColor(Coordinate coordinate) {
		assert coordinate != null;
		return this.board.getColor(coordinate);
	}

	public Color getColor() {
		return this.turn.getColor();
	}

	public boolean isBlocked() {
		return this.board.getPieces(this.turn.getColor()).isEmpty();
	}

	public boolean isBlocked(Color color) {
		return this.board.getPieces(color).isEmpty();
	}

	public int getDimension() {
		return this.board.getDimension();
	}

	public Piece getPiece(Coordinate coordinate) {
		assert coordinate != null;
		return this.board.getPiece(coordinate);
	}

	@Override
	public String toString() {
		return this.board + "\n" + this.turn;
	}

}