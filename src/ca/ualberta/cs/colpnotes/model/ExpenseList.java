/*
 * This file is part of travel-expense by Elliot Colp.
 *
 *  travel-expense is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  travel-expense is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with travel-expense.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.ualberta.cs.colpnotes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds Expenses. Wherever possible, use delegate functions implemented here
 * instead of directly calling ExpenseList.getExpenses().<function>.
 */
public class ExpenseList implements Serializable {
	/**
	 * Generated ID
	 */
    private static final long serialVersionUID = 6126907162385255618L;
	private ArrayList<Expense> expenses = null;
	
	public ExpenseList() {
		expenses = new ArrayList<Expense>();
	}
	
	public ExpenseList(ExpenseList el) {
		expenses = new ArrayList<Expense>(el.getExpenses());
	}
	
	/**
	 * Get the underlying ArrayList of Expenses.
	 * @return The ArrayList.
	 */
	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	/**
	 * Add an Expense to the list.
	 * @param expense The Expense to add.
	 */
	public void addExpense(Expense expense) {
		expenses.add(expense);
	}
	
	/**
	 * Remove an Expense from the list.
	 * @param expense The Expense to remove.
	 * @return Whether the list was modified.
	 */
	public boolean removeExpense(Expense expense) {
		return expenses.remove(expense);
	}
	
	/**
	 * Get an Expense in the list.
	 * @param index The index in the list.
	 * @return The Expense at that index.
	 */
	public Expense getExpense(int index) {
		return expenses.get(index);
	}
	
	/**
	 * Determine whether an Expense is in the list.
	 * @param expense The Expense to check.
	 * @return Whether it is in the list.
	 */
	public boolean contains(Expense expense) {
		return expenses.contains(expense);
	}
	
	/**
	 * Get the size of the list.
	 * @return The size of the list.
	 */
	public int size() {
		return expenses.size();
	}

	/**
	 * Get the index of an Expense in the list.
	 * @param expense The Expense to check.
	 * @return Its index in the list.
	 */
	public int indexOf(Expense expense) {
	    return expenses.indexOf(expense);
    }
}
