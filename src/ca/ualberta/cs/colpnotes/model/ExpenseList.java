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
