package com.coderetreat

import java.util.Set;

import spock.lang.Specification

class GameOfLifeTest extends Specification {
	Life life = new Life()
	
	static Set<Cell> boardFromString(String coords) {
		coords.split(",").collect {
			String[] xy = it.split(":")
			return new Cell(xy[0] as int, xy[1] as int)
		} as Set<Cell>
	}
	
    def "Board Saves"() {
		setup:
			life.board = boardFromString("1:1")
        expect:
			life.board == boardFromString("1:1")
        
    }
	
	def "Life dies"() {
		setup:
			life.board = boardFromString("1:1")
			life.age()
		expect: 
			life.board == [] as Set
	}
	
	def "Life Blinks"() {
		setup:
			Life life = new Life()
			life.board = boardFromString("1:0,1:1,1:2")
			life.age()
			
		expect:
			life.board == boardFromString("0:1,1:1,2:1")
	}
	
	def "Life Blocks"() {
		setup:
			Life life = new Life()
			life.board = boardFromString("1:1,2:1,1:2,2:2")
			life.age()
					
		expect:
			life.board == boardFromString("1:1,2:1,1:2,2:2")
	}
	
	def "Life Beehive"() {
		setup:
			Life life = new Life()
			life.board = boardFromString("2:1,3:1,1:2,4:2,2:3,3:3")
			life.age()
			
		expect:
			life.board == boardFromString("2:1,3:1,1:2,4:2,2:3,3:3")	
	}
	
	def "Life Loaf"() {
		setup:
			Life life = new Life()
			life.board = boardFromString("2:1,3:1,1:2,4:2,2:3,4:3,3:4")
			life.age()
			
		expect:
			life.board == boardFromString("2:1,3:1,1:2,4:2,2:3,4:3,3:4")
	}
	
	
	
}
