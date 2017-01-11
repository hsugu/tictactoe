package com.ttt.board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author harisugu
 * 
 * This class takes care of maintaining the history of winning combinations for the user
 * We are storing the winning combinations for the user -- which is the loosing combination for computer
 * 
 */
public class TTTHistoryManager 
{
	Vector<Vector<TTTCell>> history = new Vector<Vector<TTTCell>>();
	private static TTTHistoryManager instance;
	
	private TTTHistoryManager()throws Exception
	{
		initializeHistory();
	}
	
	/**
	 * 
	 * getInstance for getting the only available manager object for this singletop
	 * 
	 * @return
	 * @throws Exception
	 */
	public static TTTHistoryManager getInstance() throws Exception
	{
		if(instance == null)
			instance = new TTTHistoryManager();
		return instance;
	}
	
	void initializeHistory() throws Exception
	{
		File f = new File("./ttt.history");
		if(f.exists())
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while(line != null)
			{
				StringTokenizer st = new StringTokenizer(line,","); 
				Vector<TTTCell> winCombo = new Vector<TTTCell>();
				while(st.hasMoreTokens())
				{
					 winCombo.add(new TTTCell(st.nextToken()));
				}
				history.add(winCombo);
				line = br.readLine();
			}
			br.close();
		}
	}
	
	/**
	 * 
	 * Returns the win cell if valid looking at the winning combo history and moves made till now based on moveslist
	 * 
	 * @param movesList
	 * @return
	 */
	public TTTCell getWinningCell(Vector<TTTCell> movesList)
	{
		// 2 of the cells from movesList should be part of any one willing combo
		// if so, return the 3rd value from combo
		// else return null
		// TTTCell.equal() takes care of comparing only row,col ignoring the value
		for(int i=0;i<history.size();i++) 
		{
			Vector<TTTCell> combo = history.get(i); 
			int matchCellIndex = -1;
			for(int j=0;j<movesList.size();j++)
			{
				TTTCell curMove = movesList.get(j);
				if(combo.contains(curMove))
				{
					if(matchCellIndex == -1)
					{
						matchCellIndex = combo.indexOf(curMove);
					}
					else
					{
						switch(matchCellIndex)
						{
						case 0: if(j == 1) return combo.get(2); else return combo.get(1);
						case 1: if(j == 0) return combo.get(2); else return combo.get(0);
						case 2: if(j == 1) return combo.get(0); else return combo.get(1);
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Returns the win cell if valid looking at the winning combo history and moves made till now based on moveslist.
	 * In addition, this also looks to see if the win cell is a valid move cell else will return null
	 * 
	 * @param movesList
	 * @param board
	 * @return
	 */
	public TTTCell getWinningCell(Vector<TTTCell> movesList, TTTBoard board)
	{
		// get the winning cell from history if possible
		// if the willing cell is already filled in board return null
		//		we can choose a random valid cell as next move else the winning cell
		TTTCell winCell = getWinningCell(movesList);
		if(winCell != null && board.getEmptyCells().contains(winCell.getRowColUserIndex()) == false)
		{
			winCell = null;
		}
		return winCell;
	}
	
	/**
	 * 
	 * Persist the winning combo in the history file
	 * 
	 * @param winCombo
	 * @throws Exception
	 */
	public void persistWinCombo(TTTCell[] winCombo)throws Exception
	{
		Vector<TTTCell> newCombo = new Vector<TTTCell>();
		newCombo.add(winCombo[0]);
		newCombo.add(winCombo[1]);
		newCombo.add(winCombo[2]);
		if(history.contains(newCombo) == false) // add the new winning combo only if its not in history already
		{
			File f = new File("./ttt.history");
			PrintWriter pw = new PrintWriter(f);
			history.add(newCombo);
			for(int i = 0; i< history.size();i++)
			{
				Vector<TTTCell> wCombo = history.get(i);
				pw.append(wCombo.get(0).getRowUserIndex() + "" +wCombo.get(0).getColUserIndex() + "," +
						wCombo.get(1).getRowUserIndex() + wCombo.get(1).getColUserIndex() + "," +
						wCombo.get(2).getRowUserIndex() + wCombo.get(2).getColUserIndex() + "\n");
			}
			pw.close();
		}
	}
}
