package com.ttt.board;

import java.util.Vector;

/**
 * 
 * Tic Tac Toe board representation
 * 
 * @author harisugu
 *
 */
public class TTTBoard {

	TTTCell[][] cell_values = new TTTCell[3][3];
	TTTHistoryManager history; 
	
	TTTCell[][] winningCombo = {
									{new TTTCell(0,0),new TTTCell(0,1),new TTTCell(0,2)},
									{new TTTCell(1,0),new TTTCell(1,1),new TTTCell(1,2)},
									{new TTTCell(2,0),new TTTCell(2,1),new TTTCell(2,2)},
									{new TTTCell(0,0),new TTTCell(1,0),new TTTCell(2,0)},
									{new TTTCell(0,1),new TTTCell(1,1),new TTTCell(2,1)},
									{new TTTCell(0,2),new TTTCell(1,2),new TTTCell(2,2)},
									{new TTTCell(0,0),new TTTCell(1,1),new TTTCell(2,2)},
									{new TTTCell(2,0),new TTTCell(1,1),new TTTCell(0,2)}
								};
	
	/**
	 * Null constructor
	 * 
	 * @throws Exception
	 */
	public TTTBoard()throws Exception
	{
		initializeCells();
		history = TTTHistoryManager.getInstance(); 
	}
	
	void initializeCells()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				cell_values[i][j] = new TTTCell(i,j,TTTCell.EMPTY);
	}
	
	void updateWinningCombo()
	{
		for(int i=0;i<winningCombo.length;i++)
			for(int j=0;j<winningCombo[i].length;j++)
			{
				TTTCell curCell = winningCombo[i][j];
				curCell.setValue(cell_values[curCell.getRowVal()][curCell.getColVal()].getValue());
			}
	}
	
	/**
	 * 
	 * Returns a list of valid moves - cells that are empty
	 * 
	 * @return
	 */
	public Vector<String> getEmptyCells()
	{
		Vector<String> vecRet = new Vector<String>();
		for(int i=0;i<3;i++)
		{
			if(cell_values[i][0].getValue().equals(TTTCell.EMPTY))
				vecRet.add(cell_values[i][0].getRowUserIndex()+"" + cell_values[i][0].getColUserIndex());
			if(cell_values[i][1].getValue().equals(TTTCell.EMPTY))
				vecRet.add(cell_values[i][1].getRowUserIndex()+"" + cell_values[i][1].getColUserIndex());
			if(cell_values[i][2].getValue().equals(TTTCell.EMPTY))
				vecRet.add(cell_values[i][2].getRowUserIndex()+"" + cell_values[i][2].getColUserIndex());
		}
		return vecRet;
	}
	
	/**
	 * 
	 * Sets the value for the cell. In addition to setting value is this also determines if the game is over 
	 * 
	 * @param cellIndex
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String setCellValue(String cellIndex, String value) throws Exception
	{
		char[] rowCal = cellIndex.toCharArray();
		int rowVal = 0;
		int colVal = 0;
		if(rowCal[0] == 'A')
			rowVal = 0;
		if(rowCal[0] == 'B')
			rowVal = 1;
		if(rowCal[0] == 'C')
			rowVal = 2;
		colVal = Integer.parseInt(rowCal[1]+"") - 1;
		cell_values[rowVal][colVal].setValue(value);
		updateWinningCombo();
		printBoard();
		return checkWinner();
	}
	
	String checkWinner()throws Exception
	{
		int i = 0;
		while (i < winningCombo.length)
		{
			if(winningCombo[i][0].getValue().equals(winningCombo[i][1].getValue()) &&
					winningCombo[i][1].getValue().equals(winningCombo[i][2].getValue()) &&
					winningCombo[i][0].getValue().equals(TTTCell.HUMAN))
			{
				persistWinCombo(winningCombo[i]);
				return TTTCell.HUMAN;
			} else if(winningCombo[i][0].getValue().equals(winningCombo[i][1].getValue()) &&
					winningCombo[i][1].getValue().equals(winningCombo[i][2].getValue()) &&
					winningCombo[i][0].getValue().equals(TTTCell.COMP))
			{
				persistWinCombo(winningCombo[i]);
				return TTTCell.COMP;
			}
			i++;
		}
		return TTTCell.EMPTY;
	}
	
	void persistWinCombo(TTTCell[] winCombo) throws Exception
	{
		System.out.println(winCombo[0] + "," + winCombo[1] + "," + winCombo[2]);
		history.persistWinCombo(winCombo);
	}
	
	/**
	 * 
	 * Prints board in the console
	 * 
	 */
	public void printBoard()
	{
		System.out.println();
		System.out.println("   1   2   3  ");
		System.out.println("--------------");
		System.out.println("A| " + cell_values[0][0].getValue() + " | " + cell_values[0][1].getValue() + " | " + cell_values[0][2].getValue() + " |");
		System.out.println("B| " + cell_values[1][0].getValue() + " | " + cell_values[1][1].getValue() + " | " + cell_values[1][2].getValue() + " |");
		System.out.println("C| " + cell_values[2][0].getValue() + " | " + cell_values[2][1].getValue() + " | " + cell_values[2][2].getValue() + " |");
		System.out.println("--------------");
	}
	
	public static void main(String[] args)
	{
		try
		{
			TTTBoard board = new TTTBoard();
			board.printBoard();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
