package src.com.om.videostore;

import java.util.Vector;
import java.util.Enumeration;
import java.lang.StringBuilder;

public class Customer 
{
	/**
	 *
	 */
	private static final String TAB_SYMBOL = "\t";
	private static final String EOL_SYMBOL = "\n";

	public Customer (String name) {
		this.name = name;
	}
	
	public void addRental (Rental rental) {
		rentals.addElement (rental);
	}
	
	public String getName () {
		return name;
	}
	
	public String statement () {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentals = this.rentals.elements ();
		StringBuilder result = new StringBuilder("Rental Record for ");
		result.append(getName ()).append(EOL_SYMBOL);
		
		while (rentals.hasMoreElements ()) {
			double thisAmount = 0;
			Rental each = rentals.nextElement ();
			
			// determines the amount for each line
			thisAmount = getRentalCost(each);
			
			frequentRenterPoints += getFrequentRenterPoints(each);
				
			result.append(TAB_SYMBOL).append(each.getMovie ().getTitle ()).append(TAB_SYMBOL).append(thisAmount).append(EOL_SYMBOL);

			totalAmount += thisAmount;
				
		}
		
		result.append("You owed ").append(totalAmount).append(EOL_SYMBOL);
		result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points").append(EOL_SYMBOL);
		
		
		return result.toString();
	}

	private int getFrequentRenterPoints(Rental each) {
		int frequentRenterPoints=0;
		frequentRenterPoints++;

		if (each.getMovie ().getPriceCode () == Movie.NEW_RELEASE 
				&& each.getDaysRented () > 1)
			frequentRenterPoints++;
		return frequentRenterPoints;
	}

	private double getRentalCost(Rental each) {
		double rentalCost=0.0;
		switch (each.getMovie ().getPriceCode ()) {
			case Movie.REGULAR:
				rentalCost += 2;
				if (each.getDaysRented () > 2)
					rentalCost += (each.getDaysRented () - 2) * 1.5;
				break;
			case Movie.NEW_RELEASE:
				rentalCost += each.getDaysRented () * 3;
				break;
			case Movie.CHILDRENS:
				rentalCost += 1.5;
				if (each.getDaysRented () > 3)
					rentalCost += (each.getDaysRented () - 3) * 1.5;
				break;
		}
		return rentalCost;
	}
	

	private String name;
	private Vector<Rental> rentals = new Vector<Rental> ();
}