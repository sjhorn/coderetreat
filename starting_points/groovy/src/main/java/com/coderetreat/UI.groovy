package com.coderetreat

import java.util.concurrent.TimeUnit

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener.*
import org.eclipse.swt.SWT
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.events.PaintListener
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

import static com.coderetreat.Life.boardFromPattern

class UI  implements PaintListener, Runnable {
	static int MAGNIFIER = 10
	Display display
	Shell shell
	Canvas canvas
	Life life = new Life()
	
	UI() {
		Display.setAppName("Game of Life")
		display = new Display()
		configureShell()
		
		life.board = boardFromPattern('''\
................O...................
.................O..................
...............OOO..................
....................................
.......................O.O..........
.....................O...O..........
.............O.......O..............
............OOOO....O....O........OO
...........OO.O.O....O............OO
OO........OOO.O..O...O...O..........
OO.........OO.O.O......O.O..........
............OOOO....................
.............O......................
''') 
	}
	
	void run() {
		life.age()
		canvas.redraw()
		canvas.update()
		display.timerExec(10, this)
	}
	
	void configureShell() {
		shell = new Shell(display)
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED)
		canvas.addPaintListener(this)
		
		shell.setLayout(new FillLayout())
		shell.setSize(576,440)
		shell.layout()
		
		run()
	}
	
	void paintControl(PaintEvent pe) {
		if(shell && !shell.isDisposed() && canvas && !canvas.isDisposed()) {
			GC gc = pe.gc
			gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK))
			Rectangle bounds = canvas.getBounds()
			gc.fillRectangle(bounds)
			gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
			life.board.each { Cell cell ->
				gc.fillRectangle( cell.x * MAGNIFIER, cell.y * MAGNIFIER, MAGNIFIER, MAGNIFIER)
			}
		}
	}


	
	void msgLoop() {
		shell.open()
		while (!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep()
			}
		}
		display.dispose()
	}
	
	static main(args) {
		UI ui = new UI()
		ui.msgLoop()
	}
	

}
