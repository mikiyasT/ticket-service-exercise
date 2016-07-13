package com.walmart.store.recruiting.ticket.domain;

import java.util.Timer;
import java.util.TimerTask;

/*
 * this class models a seat. 
 * It contains information about a specific seat and timer on it.
 */
public class Seat {
		    	
	
	int rowNo;
	int seatNo;
	boolean hold;
	boolean reserved;
	
	Seat(int rn, int sn,boolean h, boolean r){
		this.rowNo = rn;
		this.seatNo = sn;
		hold = h;
		reserved = r;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		//if it is a reserve request, set timer on seat so that reservation will expire in 5 seconds;
		if(hold == true)
			setTimerOnSeat();
		
		this.hold = hold;
	}

	public boolean isReserved() {
		
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	//Setting a timer for each seat. If the seat is reserved for for more than 10 secnonds 
	// we should unrserve it and make it  available
	//it should unreserve it.
	public void setTimerOnSeat(){
		final Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        //int i = 100;
	        public void run() {
	          
	            if(isHold()){
	            	  //System.out.println("unrserve this seat");
	            	setHold(false);
	            }
	           
	                timer.cancel();
	        }
	    }, 0, 5000);
	}
	
	
	  

}
