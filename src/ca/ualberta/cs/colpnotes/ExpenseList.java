package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
	
	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	public void add(Expense expense) {
		expenses.add(expense);
	}
	
	public Expense get(int index) {
	    return expenses.get(index);
    }

	public void removeExpense(Expense expense) {
		expenses.remove(expense);
	}
	
	public Expense getExpense(int index) {
		return expenses.get(index);
	}
	
	public boolean contains(Expense expense) {
		return expenses.contains(expense);
	}
	
	public int size() {
		return expenses.size();
	}

	public int indexOf(Object object) {
	    return expenses.indexOf(object);
    }
}
