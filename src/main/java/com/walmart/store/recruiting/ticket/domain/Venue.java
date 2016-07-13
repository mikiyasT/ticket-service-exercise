package com.walmart.store.recruiting.ticket.domain;



/**
 * This class represents a venue where customers can buy tickets to see a show.
 * Our Venue seating is rectangular to simplify things.
 */
public class Venue {

    private final int id;
    private final int rows;
    private final int seatsPerRow;
    
    //venue contains seats which are rows * seatsPerRow in number
    Seat[] allseats;

    public Venue(int id, int rows, int seatsPerRow) {
        this.id = id;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        
        //initialize all seats and give them seat number;
        allseats = new Seat[rows * seatsPerRow];
        for(int r = 0; r < rows; r++){
        	for(int c = 0 ; c< seatsPerRow; c++){
        		
        		//seat number will be a factor of the row_number and the seats_per_row
        		allseats[r*seatsPerRow + c] = new Seat(r,r*seatsPerRow + c,false,false);
        	}
        }
    }


    /**
     * @return the number of rows of seats in the venue
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the number of seats in each row
     */
    public int getSeatsPerRow() {
        return seatsPerRow;
    }
    
    /*
     * Return all the seats in the venu with all the information.
     */
    
    public Seat[] getAllseats() {
		return allseats;
	}


	/**
     * @return the total number of seats in the venue that are available
     */
    public int getAvailableSeats() {
        int availableSeats = 0;
    	for(Seat s: allseats)
        {
    		if(!s.isHold() && !s.isReserved())
    			availableSeats++;
        }
    	return availableSeats;
    }
    /**
     * @return the total number of seats that are reserved(They are first hold and then reserved)
     */
    
    public int getNumberOfReservedSeats() {
        int reservedSeats = 0;
    	for(Seat s: allseats)
        {
    		if(s.isHold() && s.isReserved())
    			reservedSeats++;
        }
    	return reservedSeats;
    }

    /**
     * @return the total number of seats in the venue
     */
    public int getMaxSeats() {
        return rows * seatsPerRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        return id == venue.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
