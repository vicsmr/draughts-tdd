package es.urjccode.mastercloudapps.adcs.draughts.models;

public class Game {

	private Board board;
	private Turn turn;

	public Game() {
		this.turn = new Turn();
		this.board = new Board();
		for (int i = 0; i < this.board.getDimension(); i++) {
			for (int j = 0; j < this.board.getDimension(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Piece piece = this.getInitialPiece(coordinate);
				if (piece != null) {
					this.board.put(coordinate, piece);
				}
			}
		}
	}

	public Game (Board board) {
		this.board = board;
		this.turn = new Turn();
	}

	private Piece getInitialPiece(Coordinate coordinate) {
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
				return new Piece(color);
			}
		}
		return null;
	}

	public void move(Coordinate origin, Coordinate target) {
		assert this.isCorrect(origin, target) == null;
		if (origin.diagonalDistance(target) > 1) {
			Coordinate coordinateToRemove = origin.betweenDiagonal(target);
			do {
				Piece piece = this.board.getPiece(coordinateToRemove);
				if (piece != null) {
					this.board.remove(coordinateToRemove);
				}
				coordinateToRemove = coordinateToRemove.betweenDiagonal(target);
			} while(coordinateToRemove.diagonalDistance(target) > 0 );
		}
		Color colorOriginPiece = this.getColor(origin);
		this.board.move(origin, target);
		if (this.board.getPiece(target).isLimit(target)){
			this.board.remove(target);
			this.board.put(target, new Draught(colorOriginPiece));
		}
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
			int piecesBetween = 0;
			Coordinate coordinateToRemove = origin.betweenDiagonal(target);
			do {
				Piece piece = this.board.getPiece(coordinateToRemove);
				if (piece != null) {
					piecesBetween++;
				}
				coordinateToRemove = coordinateToRemove.betweenDiagonal(target);
			} while(coordinateToRemove.diagonalDistance(target) > 0 );
			if (piecesBetween > 1) {
				return Error.EATING_SEVERAL;
			}
		}
		return null;
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