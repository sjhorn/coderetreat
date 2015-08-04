package com.coderetreat

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@EqualsAndHashCode()
@Canonical
@Sortable
class Cell {
	int x
	int y
	
	String toString() {
		return "$x:$y"
	}
	
	
}
