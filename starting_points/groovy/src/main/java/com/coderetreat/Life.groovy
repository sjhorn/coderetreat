package com.coderetreat

class Life {
	Set<Cell> board = []
	
	void age() {
		Set<Cell> nextBoard = []
		board.each { Cell aliveCell ->
			Set<Cell> cells = neighbours(aliveCell)
			int liveNeighbours  = liveCells(cells).size()
			if ( liveNeighbours == 2 || liveNeighbours == 3) {
				nextBoard += aliveCell
			}
			deadCells(cells).each { Cell deadCell ->
				if ( liveCells(neighbours(deadCell)).size() == 3 ) {
					nextBoard += deadCell
				}
			}
		}
		board = nextBoard
	}	
	
	Set<Cell> neighbours(Cell cell) {
		return ( (cell.y-1..cell.y+1)
			.collect { y -> (cell.x-1..cell.x+1).collect { new Cell(it, y) } }
			.flatten()
			.findAll { it != cell } )  
	}
 	
	Set<Cell> liveCells(Set<Cell> cells) {
		return cells.intersect(board)
	}
	
	Set<Cell> deadCells(Set<Cell> cells) {
		return cells.minus(board)
	}
	
	
}
