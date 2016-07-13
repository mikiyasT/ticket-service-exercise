package com.walmart.store.recruiting.ticket.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





/**
 * This POJO contains the data relevant to a successful seat hold request, including the seat hold id which
 * may be used later to permanently reserve the seats.
 */
public class SeatHold {

    private String id;
    private int numSeats;
    
    //We need to have an array of seats that are hold
    //We need to have a map of keys and Seat list for retriveal as well.
    //So When a user holds seats,we need to keep id for that hold and list of seats reserved 
    //This map will be common and adds all hold request information from all users.
    public static HashMap<String,List<Seat>> UserHoldSeatsMap = new HashMap<String,List<Seat>>();
    
    

    /**
     * Constructor.
     *
     * @param id the unique hold identifier
     * @param numSeats the number of seats that were held.
     */
    public SeatHold(String id, int numSeats, List<Seat> seats) {
        this.id = id;
        this.numSeats = numSeats;
        //every time a new SeatHold object is created, we need to add information about the Hold seats.
        UserHoldSeatsMap.put(this.id, seats);
        
    }

    /**
     * @return the seat hold (reservation) id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the number of seats that are being held
     */
    public int getNumSeats(String holdId) {
        //search through the map and count all those seats that are held
    	int heldSeats = 0;
    	//for(String k:UserHoldSeatsMap.keySet()){
    		List<Seat> seatList = getHoldSeats(holdId);
    		if(seatList != null)
    		heldSeats += seatList.size();
    	//}
    	
    	return heldSeats;
    	//return numSeats;
    }
    
    public HashMap<String,List<Seat>> getHeldSeatsMap(){
		return UserHoldSeatsMap; 
	}
	
	public String getSeatHoldId(){
		return id;
	}
	
	//given a string id, this method will return the list of seats reserved under that id.
	static public List<Seat> getHoldSeats(String id){
		return UserHoldSeatsMap.get(id);
	}  
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatHold seatHold = (SeatHold) o;

        return id.equals(seatHold.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
