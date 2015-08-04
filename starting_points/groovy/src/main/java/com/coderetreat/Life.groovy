package com.coderetreat

import java.util.Set;

class Life {
	Set<Cell> board = []
	
	void age() {
        board = board.collect { Cell cell ->
            neighbours(cell).findAll { Cell neighbour ->
                nextCellState(neighbour)
            }
        }.flatten()
    }

    boolean nextCellState(Cell cell) {
        boolean alive = board.contains(cell)
        int liveNeighbours = liveNeighbours(cell).size()
        if (alive && liveNeighbours < 2) return false // under population
        if (alive && liveNeighbours in [2,3]) return true // stays alive
        if (alive && liveNeighbours > 3) return false // over population
        if (!alive && liveNeighbours == 3) return true // reproduces
        return false // stays dead
    }

    Set<Cell> liveNeighbours(Cell cell) {
        board.intersect(neighbours(cell))
    }
	
	Set<Cell> neighbours(Cell cell) {
		(cell.y-1..cell.y+1)
				.collect { y -> (cell.x-1..cell.x+1)
					.collect { x ->
						new Cell(x, y) } }
				.flatten()
				.minus (cell)
	}
	
	static Set<Cell> boardFromString(String coords) {
		coords.split(",").collect {
			String[] xy = it.split(":")
			return new Cell(xy[0] as int, xy[1] as int)
		} as Set<Cell>
	}
	
	static Set<Cell> boardFromPattern(String pattern) {
		Set<Cell> cells = []
		pattern.split("\n").eachWithIndex { String line, int row ->
			line.split("").eachWithIndex { String dot, int col ->
				if (dot == "O") {
					cells += new Cell(col,row)
				}
			}
		}
		return cells
	}
}
