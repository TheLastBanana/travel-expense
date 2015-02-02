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
import java.util.Calendar;

/**
 * Holds information about a claim, including its corresponding Expenses.
 * Also responsible for validating this data in setter functions.
 */
public class Claim implements Serializable {
	/**
	 * Generated ID
	 */
    private static final long serialVersionUID = -5147034485394091896L;
    
	private String name;
	private String destination;
	private String reason;
	private ClaimStatus status;
	private Calendar from;
	private Calendar to;
	private ExpenseList expenses;
	
	public Claim() {
		setName("");
		setDestination("");
		setReason("");
		setStatus(ClaimStatus.IN_PROGRESS);
		setFrom(Calendar.getInstance());
		setTo(Calendar.getInstance());
		expenses = new ExpenseList();
	}
	
	public Claim (Claim c) {
		copyFrom(c);
	}
	
	/**
	 * Copies all attributes from the given claim.
	 * @param c The claim to copy from.
	 */
	public void copyFrom(Claim c) {
		setName(new String(c.getName()));
		setDestination(new String(c.getDestination()));
		setReason(new String(c.getReason()));
		setStatus(c.getStatus());
		setFrom((Calendar) c.getFrom().clone());
		setTo((Calendar) c.getTo().clone());
		expenses = new ExpenseList(c.getExpenseList());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Calendar getFrom() {
		return from;
	}

	public void setFrom(Calendar from) {
		this.from = from;
	}

	public Calendar getTo() {
		return to;
	}

	public void setTo(Calendar to) {
		this.to = to;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	public ExpenseList getExpenseList() {
		return expenses;
	}
}
