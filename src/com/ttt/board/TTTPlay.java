package com.ttt.board;

import java.io.Console;
import java.util.Random;
import java.util.Vector;

/**
 * 
 * Play controller that controls the game
 * 
 * @author harisugu
 *
 */
public class TTTPlay 
{
	TTTBoard board = new TTTBoard();
	TTTHistoryManager history; 
	Vector<TTTCell> compMoves = new Vector<TTTCell>();
	Vector<TTTCell> userMoves = new Vector<TTTCell>();
	
	/**
	 * 
	 * Null constructor
	 * 
	 * @throws Exception
	 */
	public TTTPlay() throws Exception
	{
		history = TTTHistoryManager.getInstance();
	}
	
	/**
	 * Entry point for playing the game
	 * Always first move will be user move
	 * User symbol is X and comp symbol is o
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception
	{
		board.printBoard();
		while(true)
		{
			String strEmptyVals = "";
			for(int i=0;i<board.getEmptyCells().size();i++)
				strEmptyVals = strEmptyVals + board.getEmptyCells().get(i) + ",";
			if(strEmptyVals.trim().equals("") == false)
			{
				strEmptyVals = strEmptyVals.substring(0,(strEmptyVals.length()-1));
			}
			else
			{
				System.out.println("No more valid moves - Game ends");
				return;
			}
			System.out.println("\nPossible moves : " + strEmptyVals);
			System.out.print("Enter your intput: ");
			Console c = System.console();
			String strInput = c.readLine();
			while(board.getEmptyCells().contains(strInput) == false)
			{
				System.out.println("Enter valid intput !!!");
				System.out.println("\nPossible moves : " + strEmptyVals);
				System.out.print("Enter your intput: ");
				strInput = c.readLine();
			}
			userMoves.add(new TTTCell(strInput,TTTCell.HUMAN));
			if(board.setCellValue(strInput, TTTCell.HUMAN) == TTTCell.HUMAN)
			{
				System.out.println("You Win !!!");
				return;
			}
			else if(board.getEmptyCells().size() == 0)
			{
				System.out.println("No more valid moves. Game Draw !!!");
				return;
			}
			String strCompMove = getNewMove();
			System.out.println("I choose value: " + strCompMove);
			compMoves.add(new TTTCell(strCompMove,TTTCell.HUMAN));
			if(board.setCellValue(strCompMove, TTTCell.COMP) == TTTCell.COMP)
			{
				System.out.println("I Win !!!");
				return;
			}
			else if(board.getEmptyCells().size() == 0)
			{
				System.out.println("No more valid moves. Game Draw !!!");
				return;
			}
		}
	}
	
	String getNewMove()
	{
		Random rand = new Random();
		String strCompMove = "";
		if(compMoves.size() == 0)
		{
			strCompMove = board.getEmptyCells().get(rand.nextInt(board.getEmptyCells().size()));
		}
		else
		{
			// check first if the comp is going to win if so comp needs to choose the winning cell as next move
			// else check if user is going to win, if so comp needs to choose the willing cell as next move
			// else choose a random move
			TTTCell winCell = history.getWinningCell(compMoves, board);
			if(winCell == null)
			{
				winCell = history.getWinningCell(userMoves, board);
				if(winCell == null)
				{
					strCompMove = board.getEmptyCells().get(rand.nextInt(board.getEmptyCells().size()));
				}
				else
				{
					strCompMove = winCell.getRowUserIndex()+ "" +winCell.getColUserIndex();
				}
			}
			else
			{
				strCompMove = winCell.getRowUserIndex()+ "" +winCell.getColUserIndex();
			}
		}
		return strCompMove;
	}
	
	public static void main(String[] args)
	{
		try
		{
			TTTPlay tttPlay = new TTTPlay();
			tttPlay.play();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
