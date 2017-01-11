package com.ttt.board;

import java.util.Vector;

/**
 * @author harisugu
 * 
 * Representation of a cell in the Tic Tac Toe board
 *
 */
public class TTTCell 
{
	public static final String EMPTY = " ";
	public static final String HUMAN = "X";
	public static final String COMP = "O";
	public static Vector<String> row = new Vector<String>();
	public static Vector<String> column = new Vector<String>(); 
	
	int colVal = 0;
	int rowVal = 0;
	char colUserIndex = '0';
	char rowUserIndex = 'A';
	String value = EMPTY;
	
	static
	{
		row.add("A");
		row.add("B");
		row.add("C");
		column.add("1");
		column.add("2");
		column.add("3");
	}
	
	/**
	 * Null constructor
	 */
	public TTTCell()
	{
		
	}
	
	/**
	 * 
	 * create cell with useridex for row and column. Value will be defaulted
	 * 
	 * @param rowColIndex
	 */
	public TTTCell(String rowColIndex)
	{
		setRowUserIndex(rowColIndex.charAt(0));
		setColUserIndex(rowColIndex.charAt(1));
	}
	
	/**
	 * 
	 * create cell with userindex and value
	 * 
	 * @param rowColIndex
	 * @param value
	 */
	public TTTCell(String rowColIndex, String value)
	{
		setRowUserIndex(rowColIndex.charAt(0));
		setColUserIndex(rowColIndex.charAt(1));
		setValue(value);
	}
	
	/**
	 * 
	 * create cell with row and col. Value will be defaulted
	 * 
	 * @param rowVal
	 * @param colVal
	 */
	public TTTCell(int rowVal, int colVal)
	{
		setRowVal(rowVal);
		setColVal(colVal);
	}
	
	/**
	 * 
	 * create cell with row, col, value
	 * 
	 * @param rowVal
	 * @param colVal
	 * @param value
	 */
	public TTTCell(int rowVal, int colVal, String value)
	{
		setRowVal(rowVal);
		setColVal(colVal);
		setValue(value);
	}

	public int getColVal() {
		return colVal;
	}

	public void setColVal(int colVal) {
		this.colVal = colVal;
		colUserIndex = column.get(colVal).charAt(0);
	}

	public int getRowVal() {
		return rowVal;
	}

	public void setRowVal(int rowVal) {
		this.rowVal = rowVal;
		rowUserIndex = row.get(rowVal).charAt(0);
	}

	public char getColUserIndex() {
		return colUserIndex;
	}

	public void setColUserIndex(char colUserIndex) {
		this.colUserIndex = colUserIndex;
		colVal = column.indexOf(colUserIndex+"");
	}

	public char getRowUserIndex() {
		return rowUserIndex;
	}

	public void setRowUserIndex(char rowUserIndex) {
		this.rowUserIndex = rowUserIndex;
		rowVal = row.indexOf(rowUserIndex+"");
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * return userindex
	 * 
	 * @return
	 */
	public String getRowColUserIndex()
	{
		return rowUserIndex + "" + colUserIndex;
	}
	
	public boolean equalsIncludeValue(Object o) {
		return (((TTTCell)o).getRowVal() == rowVal &&
				((TTTCell)o).getColVal() == colVal &&
				((TTTCell)o).getValue() == value);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * This equals method will compare the cells based ONLY on row and col values with out considering the actual value
	 * Implemented this way so that vector.contains() and vector.indexOf() will use this method for comparing
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		// by not including value in equals, its easier to compare against history
		return (((TTTCell)o).getRowVal() == rowVal &&
				((TTTCell)o).getColVal() == colVal);
	}
	
	@Override
	public String toString() {
		return rowUserIndex + "" + colUserIndex + " = " + value;
	}
}
