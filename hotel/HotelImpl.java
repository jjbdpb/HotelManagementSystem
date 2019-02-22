import java.io.*;
import java.util.ArrayList;
import java.util.Date;
package hotel; // means this class belongs to the hotel package
public class HotelImpl implements Hotel {
	public void HotelImple(String roomsTxtFileName, String guestsTxtFileName,
	String bookingsTxtFileName, String paymentsTxtFileName){
	}
	public boolean importRoomsData(String roomsTxtFileName)
	{
		try
		{
			File file = new File(roomsTxtFileName);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String st;

	        ArrayList<Room> roomList = new ArrayList<Room>();
	        while ((st = br.readLine()) != null) 
	        {
	            String[] room_info = st.split(",");
	            Room room = new Room();
	            room.roomNumber = Long.valueOf(room_info[0]);
	            room.roomType = room_info[1];
	            room.roomPrice = Double.valueOf(room_info[2]);
	            room.capacity = Integer.valueOf(room_info[3]);
	            room.facilities = room_info[4];
	            roomList.add(room);
	        }
	        file.close();
	    }
    	catch(Exception e)
    	{
    		System.out.println("Error Occured when reading rooms data...");
    		file.close();
    		return false;
    	}

		return true;
	}
	public boolean importGuestsData(String guestsTxtFileName){
		try{
			File file = new File(guestsTxtFileName)
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
	        while ((st = br.readLine()) != null) {
	            String[] guest_info = st.split(",");
	            Guest guest = new Guest();
	            guest.guestID = Long.valueOf(guest_info[0]);
	            guest.fName = guest_info[1];
	            guest.lName = guest_info[2];
	            guest.dateJoin = ft.parse(guest_info[3]);
	            try {
	                guest.VIPstartDate = ft.parse(guest_info[4]);
	                guest.VIPexpiryDate = ft.parse(guest_info[5]);
	            } catch (ArrayIndexOutOfBoundsException exception) {
	                guest.VIPstartDate = null;
	                guest.VIPexpiryDate = null;
	            }
	            guestList.add(guest);
	        }
	    }catch(Exception e){
	        	System.out.println("Error Occured when reading Guests data...");
	        	file.close();
	        	return false;
		}
		file.close();	
		return true;
	}
	public boolean importBookingsData(String bookingsTxtFileName){
		try{
			File file = new File(bookingsTxtFileName);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String st;
	        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	        ArrayList<Booking> bookingList = new ArrayList<Booking>();
	        while ((st = br.readLine()) != null) {
	            String[] booking_info = st.split(",");
	            Booking booking = new Booking();
	            booking.bookingID = Long.valueOf(booking_info[0]);
	            booking.guestID = Long.valueOf(booking_info[1]);
	            booking.roomNumber = Long.valueOf(booking_info[2]);
	            booking.bookingDate = ft.parse(booking_info[3]);
	            booking.checkInDate = ft.parse(booking_info[4]);
	            booking.checkOutDate = ft.parse(booking_info[5]);
	            booking.totalAmount = Double.valueOf(booking_info[6]);
	            bookingList.add(booking);
	        }
    	}
    	catch(Exception e){
    		System.out.println("Error Occured when reading booking data...");
    		file.close();
    		return false;
    	}
    	file.close();
		return true;
	}
	public boolean importPaymentsData(String paymentsTxtFileName){
		try{
			File file = new File(paymentsTxtFileName);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String st;
	        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	        ArrayList<Payment> PaymentList = new ArrayList<Payment>();
		    while ((st = .readLine()) != null){
		        String[] payment_info = st.split(",");
		        Payment payment = new Payment();
		        payment.date = ft.parse(payment_info[0]);
		        payment.guestID = Long.valueOf(payment_info[1]);
		        payment.amount= Double.valueOf(payment_info[2]);
		        payment.payReason = String.valueOf(payment_info[3]);
		        paymentList.add(payment);
		    }
		    }catch(Exception e){
		    	System.out.println("Error Occured when reading payment data...");
		    	file.close();
		    	return false;
		    }
		file.close();
		return true;
	}
	public void displayAllRooms(){ }
	public boolean savePaymentsData(String paymentsTxtFileName){return
	true;}
	

	static class Room {
        private long roomNumber;
        private String roomType;
        private double roomPrice;
        private int capacity;
        private String facilities;

        public long getRoomNumber() {return this.roomNumber;}
        public String getRoomType(){return this.roomType;}
        public double getRoomPrice(){return this.roomPrice;}
        public int getCapacity(){return this.capacity;}
        public String getFacilities(){return this.facilities;}
    }

    static class Booking{
    	private long bookingID;
    	private long guestID;
    	private long roomNumber;
    	private Date bookingDate;
    	private Date checkInDate;
    	private Date checkOutDate;
    	private double totalAmount;

    	public long getBookingID(){return this.bookingID;}
    	public long getGuestID(){return this.guestID;}
    	public long getRoomNumber(){return this.roomNumber;}
    	public Date getBookingDate(){return this.bookingDate;}
    	public Date getCheckInDate(){return this.checkInDate;}
    	public Date getCheckOutDate(){return this.checkOutDate;}
    	public double getTotalAmount(){return this.totalAmount;}
    }

    static class Guest {
        private long guestID;
        private String fName;
        private String lName;
        private Date dateJoin;
        private Date VIPstartDate;
        private Date VIPexpiryDate;

        public long getGuestID() {return this.guestID;}
        public String getfName() {return this.fName;}
        public String getlName() {return this.lName;}
        public Date getDateJoin() {return this.dateJoin;}
        public Date getVIPstartDate() {return this.VIPstartDate;}
        public Date getVIPexpiryDate() {return this.VIPexpiryDate;}
    }

    static class Payment {
        private Date date;
        private long guestID;
        private double amount;
        private String payReason;

        public Date getDate() {return this.date;}
        public long getGuestID() {return this.guestID;}
        public double getAmount() {return this.amount;}
        public String getPayReason() {return this.payReason;}
    }