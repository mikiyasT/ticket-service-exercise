package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.Seat;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A ticket service implementation.
 */
public class TicketServiceImpl implements TicketService {

    private int seatsAvailable;
    private int seatsReserved;
    private Map<String, SeatHold> seatHoldMap = new HashMap<>();
    
    /*
     * We need to get the venue information here to get the number of rows and seats per row
     */
    private Venue venueInstance;
   

    public TicketServiceImpl(Venue venue) {
        seatsAvailable = venue.getMaxSeats();
        
        //instantiate the venue instance here
        venueInstance = venue;
    }
    
    

    @Override
    public int numSeatsAvailable() {
        //this should return the seats that are not held and not reserved.
    	return venueInstance.getAvailableSeats();
    	
    	//return seatsAvailable;
    }

    public int numSeatsReserved() {
        return this.seatsReserved;
    }

    @SuppressWarnings("null")
	@Override
    public Optional<SeatHold> findAndHoldSeats(int numSeats) {
        
    	/*
    	 * for our venue instance, scan each row and for each seat on that row 
    	 * hold sequence of seats that are available
    	 *  
    	 */
    	Optional<SeatHold> optionalSeatHold = Optional.empty();
    	
    	//this method returns only those that are available
    	int seatsThatAreAvailable = venueInstance.getAvailableSeats();
    	int seatsThatWeNeed = numSeats;
    	String holdId = "";
    	
    	//Assumption : we can make reservation only if seats that are 
    	// available are greater than seats that are required
    	List<Seat> storeTheSeatsWeNeed = new ArrayList<Seat>();
    	
    	
        if (seatsThatAreAvailable >= seatsThatWeNeed) {
            //search seats row by row and reserve them
        	Seat[] allSeats = venueInstance.getAllseats();
        	int rows = venueInstance.getRows();
        	int cols = venueInstance.getSeatsPerRow();
        	for(int r = 0 ; r < rows ; r++){
        		for(int c = 0; c < cols; c++){
        			//check if this specific seat is available
        			Seat thisSeat = allSeats[r*cols + c];
        			if(!thisSeat.isHold() && !thisSeat.isReserved()){
        				storeTheSeatsWeNeed.add(thisSeat);
        				//mark this seat as hold (will not be available any more).
        				//will also set timer on it.
        				thisSeat.setHold(true);
        			}
        		}
        	}
        	
        	
        	holdId = generateId();
        	//create a new seatHold object that contains seatId,num of seats and all the list of seats hold
        	SeatHold seatHold = new SeatHold(holdId, numSeats,storeTheSeatsWeNeed);
            optionalSeatHold = Optional.of(seatHold);
            seatHoldMap.put(holdId, seatHold);
            //seatsAvailable -= numSeats;
        }

        return optionalSeatHold;

    }

    @Override
    public Optional<String> reserveSeats(String seatHoldId) {
        //for a given setHold id, we need to get the list of seats that are hold.
    	List<Seat> holdSeats = SeatHold.getHoldSeats(seatHoldId);
    	//make sure that the list is not empty and then mark each seat as reserved
    	if(holdSeats != null)
    	for(Seat s: holdSeats){
    		s.setReserved(true);
    		seatsReserved++;
    	} 	
    	
    	Optional<String> optionalReservation = Optional.empty();;
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        if (seatHold != null) {
            //seatsReserved += seatHold.getNumSeats();
            optionalReservation =  Optional.of(seatHold.getId());
            seatHoldMap.remove(seatHoldId);
        }

        return optionalReservation;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
