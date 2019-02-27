// This package means we are working in the same file called hotel
package hotel;
// This are all the imports necessary for a few functions
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Calendar;
import java.text.*;
import java.Writer.*;

/**
 * The main class HotelImpl that implements the Hotel.java interface.
 *
 * @author Josh and Lucas Martin
 * @version 26/02/2019
 *
 */


// The public class HotelImpl has a <contract> to follow, that is, it has to declare and initialize all Hotel's methods.
public class HotelImpl implements Hotel {

    /**
     * Imports all data from the rooms, guests, boookings and payments text files.
     *
     * @param roomsTxtFileName   the rooms txt file
     * @param guestsTxtFileName     the guests txt file
     * @param bookingsTxtFileName        the bookings txt file
     * @param paymentsTxtFileName     the payments txt file
     * @return             void (This means it returns nothing)
     */

    public void HotelImple(String roomsTxtFileName, String guestsTxtFileName,
                           String bookingsTxtFileName, String paymentsTxtFileName){
        importAllData(roomsTxtFileName, guestsTxtFileName, bookingsTxtFileName, paymentsTxtFileName) // This is where the magic happens
    }

    /**
     * Removes a room according to the toom number
     *
     * @param roomNumber   Integer representing the room number
     * @return             boolean-type parameter representing true if the room was successfully removed or false if it was not
     */

    public boolean removeRoom(int roomNumber) {
        for (Booking book : bookingList ) {
            Date d1 = new Date();
            if (book.getRoomNumber() == roomNumber && d1.after(book.getCheckOutDate())) { // The if condition makes sure that the checkout date is before the current day
                bookingList.remove(book);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a guest, either a VIP guest in the VIP guest list or a regular guest in the hotel
     *
     * @param fName   String representing the first name of the guest
     * @param lName     String representing the last name of the guest
     * @param vipState        boolean-type parameter to indicate if the guest is VIP or not
     * @return             true if the guest was successfully added or false if it was not
     */

    public boolean addGuest(String fName, String lName, boolean vipState){
        boolean unique = true;
        try{
	        if(vipState==true){
			   	while(true){
			        long guestID = new Random().nextLong();
			        for(VIPGuest guest: vipGuestList){
			            if(guest.getGuestID() == guestID){unique = false;}
			        }
			        if(unique == true){break;}
			    }

			   	// The next 5 lines work out when the following year from the current day will be

			    VIPGuest vipGuest = new VIPGuest();
			    Calendar end_Date = Calendar.getInstance();
	            end_Date.setTime(new Date());
	            end_Date.add(Calendar.YEAR,1);
	            String formated = ft.format(end_Date);
	            Date end_Date = ft.parse(formated);
			    vipGuest.VIPGuest(getGuestID, fName, lName, new Date(), new Date(), end_Date);
			    Payment payment = new Payment(); // Here we create a new Payment object
			    payment.Payment(new Date(), vipGuest.getGuestID(). 50.00, 'VIPmembership'); // And here we initilize the object using the defined constructor

			    paymentList.add(payment);
			    vipGuestList.add(vipGuest);
	        }else{ // Else if the guest is not VIP
		        while(true){
		            long guestID = new Random().nextLong();
		            for(Guest guest: guestList){
		                if guest.getGuestID() == guestID unique = false;
		            }
		            if(unique == true){break;}
		        }
		        Guest guest = new Guest();
		        guest.Guest(guestID, fName, lName, new Date());
		        guestList.add(guest);

	        }
	    }catch(Exception e){
	    	System.out.print("An Error Has Occured... ");
	    	System.out.print(e + "\n");
	    	return false;
	    }
        return true;
       }

    /**
     * Removes a guest from the hotel, regardless if they are VIP or not. We first have to make sure that
     * the said guest does not have any future bookings at the time of the removal. If so, it will not be deleted
     * from the list and the function will return false as clarified below:
     *
     * @param guestID   Long integer representing the guest ID number
     * @return          Boolean type parameter that is true if the guest was successfully removed or false if it was not
     */

    public boolean removeGuest(long guestID) {
    	try{
    		for(Booking book: bookingList){
    			if(book.getGuestID() == guestID && new Date().after(book.getCheckOutDate())){ // Checks the guest ID and then the corresponding date of future bookings
		    		for(Guest guest : guestList){
		    			if(guest.getGuestID == guestID){
		    				guestList.remove(guest); // This is where the regular guest is removed from the list.
		    			}
		    		}
		    		for(Guest guest : vipGuestList) {
		    		    if (guest.getGuestID() == guestID) {
		    		        vipGuestList.remove(guest); // This is where the VIP guest is removed from the list.
                        }
                    }
    				return true;
    			}
    		}return false;

    	}catch(Exception e){ //If there is an error, we print it out to notify our client that an error has occurred
    		System.out.print("An Error Has Occured... ");
	    	System.out.print(e + "\n");
	    	return false;
    	}

        /**
         * Adds a room in the hotel with certain characteristics
         *
         * @param roomNumber   Long integer representing the romm ID number
         * @param roomType     String representing the room type
         * @param roomPrice    Double representing the room price
         * @param capacity     Integer representing the capacity
         * @param facilities   String representing the facilities needed
         * @return             boolean type parameter that is true if the room was successfully added or false if it was not
         */

    }
    public boolean addRoom(long roomNumber, String roomType, double roomPrice, int capacity, String facilities){
        for(Room room : roomList) {
            if(room.getRoomNumber() == roomNumber){return false;}
        }
        Room room = new Room();
        room.Room(roomNumber, roomType, roomPrice, capacity, facilities); // Adds a rooms using the constructor of Room defined in the Room class.
        roomList.add(room);
        return true;
    }

    /**
     * This method checks if the room is available or not, and it returns a boolean type value corresponding to the success of the operation
     *
     * @param roomNumber   Long integer representing the romm ID number
     * @param checkInDate  Date object representing the date when the guest checks in
     * @param checkOutDate Date object representing the date when the guest checks out
     * @return             boolean type parameter that is true if the room was successfully checked available or false if it was not
     */

    public boolean checkRoomAvailable(long roomNumber, Date checkInDate, Date checkOutDate){
    	for (Booking book : bookingList){
    	    // The following line checks if room ID number and if the checkin date is before the guest's check-out date and the booking info is correct in time
    		if(book.getRoomNumber() == roomNumber && checkInDate.before(book.getCheckOutDate()) && checkInDate.after(book.getCheckInDate())){
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * This method returns an array of available rooms
     *
     * @param roomNumber   Long integer representing the romm ID number
     * @param checkInDate  Date object representing the date when the guest checks in
     * @param checkOutDate Date object representing the date when the guest checks out
     * @return             This method returns an array of available rooms
     */

    public ArrayList findAvailableRooms(String roomType, Date checkInDate, Date checkOutDate){
    	ArrayList<Long> availableRooms = new ArrayList<Long>();
    	for(Room room: roomList){
    		if( room.getRoomType() == roomType && checkRoomAvailable(room.getRoomNumber(), checkInDate, checkOutDate)){ //Checks the room type as well as the availability between the check in dates and check out dates
    			availableRooms.add(room.getRoomNumber());
    		}
    	}
    	if (availableRooms.length == 0) {
    	    System.out.println("There are no available rooms!");
    	    return availableRooms;
        } else {
            return availableRooms; // returns an array with all the room numbers that are avilable,
        }
    }

    /**
     * This method makes a booking
     *
     * @param roomType     String representing the romm type
     * @param guestID      Long integer representing the ID of the guest
     * @param checkOutDate Date object representing the date when the guest checks in
     * @param checkOutDate Date object representing the date when the guest checks out
     * @return             This method returns a boolean-type true if the booking was successful or false if not
     */

    public boolean makeBooking(String roomType, long guestID, Date checkInDate, Date checkOutDate){

    	boolean guestExists = false;
    	for(Guest guest: guestList){
    		if(guest.getGuestID() == guestID){guestExists = true;}} // This checks if the user exists
    	if(guestExists == false){return false;}


    	if(new Date().after(checkInDate)){return false;} // This checks if the check in date is before today


    	availableRooms = findAvailableRooms(roomType, checkInDate, checkOutDate);
    	try{ // With a try and catch we can catch an error if there is any
    		roomNumber = availableRooms.get(new Random().nextInt(availableRooms.size()));
    	}catch(Exception e){
    		System.out.println("Error occured while making a booking");
    		return false;
    	}

        boolean unique = true;
        while(true){
            long bookingID = new Random().nextLong();
            for(Booking book: bookingList){
                (if book.getBookingID() == bookingID){unique = false;}
            }
            if(unique == true){break;}
        }

        for(Room room: roomList){
        	if(room.getRoomNumber() == roomNumber){bookedRoom = room;}
        }


        Booking booking = new Booking(); // We create a new object Booking
        long diff = checkOutDate.getTime() - checkInDate.getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        totalAmount = daysDiff*bookedRoom.getRoomPrice();
        
        booking.Booking(bookingID, guestId, roomNumber, new Date(), checkInDate, checkOutDate, totalAmount); // We initialize it using a constructor from the class method at the bottom

        for(VIPguest guest : VIPGuestList){
        	if(guest.getGuestID() == guestID && checkOutDate.before(guest.getVIPexpiryDate())){
        		boooking.totalAmount = booking.totalAmount * 0.9;
        	}
        }

        Payment payment = new Payment();
        payment.Payment(new Date(), guestID, totalAmount, "booking");

        paymentList.add(payment);
        bookingList.add(booking);
        return true; // Returns a boolean

    }

    /**
     * This method imports all data
     *
     * @param roomsTxtFileName   the rooms txt file
     * @param guestsTxtFileName     the guests txt file
     * @param bookingsTxtFileName        the bookings txt file
     * @param paymentsTxtFileName     the payments txt file
     * @return             This method returns a boolean-type true if the data import process was successful, false if not
     */

    public boolean importAllData(String roomsTxtFileName, String guestsTxtFileName, String bookingsTxtFileName, String paymentsTxtFileName){
        try{
            importRoomsData(roomsTxtFileName);
            importGuestsData(guestsTxtFileName);
            importBookingsData(bookingsTxtFileName);
            importPaymentsData(paymentsTxtFileName);
        }catch(Exception e){
            System.out.println("ERROR: an issue occured importing data");
            System.out.println(e); // Here is when we print the error
            return false;
        }
        return true; // Boolean return type
    }

    /**
     * This method imports all room data
     *
     * @param roomsTxtFileName   The rooms txt file
     * @return                   This method returns a boolean-type true if the data import process was successful, false if not
     */

    public boolean importRoomsData(String roomsTxtFileName){
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
                room.Room(Long.valueOf(room_info[0]), room_info[1], Double.valueOf(room_info[2]), Integer.valueOf(room_info[3]), room_info[4]); // We initialize the object using its own-defined constructor
                roomList.add(room);
            }
            file.close();
        }
        catch(Exception e) // We can the error here
        {
            System.out.println("Error Occured when reading rooms data...");
            file.close();
            return false;
        }

        return true;
    }

    /**
     * This method imports all guests data
     *
     * @param guestsTxtFileName     the guests txt file
     * @return             This method returns a boolean-type true if the guest data import process was successful, false if not
     */

    public boolean importGuestsData(String guestsTxtFileName){
        try{
            File file = new File(guestsTxtFileName)
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            public ArrayList<Guest> guestList = new ArrayList<Guest>();
            public ArrayList<VIPGuest> vipGuestList = new ArrayList<VIPGuest>();
            while ((st = br.readLine()) != null) {
                String[] guest_info = st.split(",");
                if(guest_info.length > 4){
                	VIPGuest vipGuest = new VIPGuest;
                	vipGuest.VIPGuest(Long.valueOf(guest_info[0]), guest_info[1], guest_info[2], ft.parse(guest_info[3]), ft.parse(guest_info[4]), ft.parse(guest_info[5])); // We use its own-defined constructor
                	vipGuestList.add(vipGuest);
                }else{
                	Guest guest = new Guest();
                	guest.Guest(Long.valueOf(guest_info[0]), guest_info[1], guest_info[2], ft.parse(guest_info[3]));
                	guestList.add(guest);
                }

            }
        }catch(Exception e){ // We catch an error if there is ever an error
            System.out.println("Error Occured when reading Guests data...");
            file.close();
            return false;
        }
        file.close();
        return true;
    }

    /**
     * This method imports all booking data
     *
     * @param bookingsTxtFileName         The bookings txt file
     * @return                            This method returns a boolean-type true if the booking data import process was successful, false if not
     */

    public boolean importBookingsData(String bookingsTxtFileName){
        try{
            File file = new File(bookingsTxtFileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            public ArrayList<Booking> bookingList = new ArrayList<Booking>();
            while ((st = br.readLine()) != null) {
                String[] booking_info = st.split(",");
                Booking booking = new Booking();

                booking.Booking(Long.valueOf(booking_info[0]), Long.valueOf(booking_info[1]),
                	Long.valueOf(booking_info[2]),ft.parse(booking_info[3]),
                	ft.parse(booking_info[4]), ft.parse(booking_info[5]),
                	Double.valueOf(room_info[6])); // This initialized the object booking calling the constructor in the class Booking
                bookingList.add(booking);
            }
        }
        catch(Exception e){ // This catches an error, if any
            System.out.println("Error Occured when reading booking data...");
            file.close();
            return false;
        }
        file.close(); // fclose() is  a file method that closes the previously opened and created file
        return true;
    }

    /**
     * This method imports all data
     *
     * @param paymentsTxtFileName     the payments txt file
     * @return             This method returns a boolean-type true if the payment data import process was successful, false if not
     */

    public boolean importPaymentsData(String paymentsTxtFileName){
        try{
            File file = new File(paymentsTxtFileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            public ArrayList<Payment> PaymentList = new ArrayList<Payment>();
            while ((st = .readLine()) != null){
                String[] payment_info = st.split(",");
                Payment payment = new Payment();
                payment.Payment(ft.parse(payment_info[0]), Long.valueOf(payment_info[1]),
                	Double.valueOf(payment_info[2]), payment_info[3]); // This initialized the object payment using its constructor
                paymentList.add(payment);
            }
        }catch(Exception e){ // Catches an error if any
            System.out.println("Error Occured when reading payment data...");
            file.close(); // This closes the file in the case there was an error
            return false;
        }
        file.close(); // This closes the file if there are no errors
        return true;
    }

    /**
     * This method checks out
     *
     * @param bookingID This is a long integer representing the booking ID number
     * @return             This method returns a boolean-type true if check out was successful, false if not
     */

    public boolean checkOut(long bookingID){
    	for(Booking book: bookingList){
    		if(book.getBookingID()==bookingID){booking=book;}
    	}

    	if(new Date().after(booking.getCheckOutDate())|| new Date().before(checkInDate)){return false;} //This makes sure the checkinDate if after the current date

    	bookingList.remove(booking); // Removes the element from the bookings list
    	return true;
    }

    /**
     * This method seaches guests and returns a string of the long integers of the values of their IDs
     *
     * @param firstName    String representing the first name of the guest
     * @param lastName     String representing the second name of the guestthe guests txt file
     * @return             This method seaches guests and returns a string of the long integers of the values of their IDs
     */

    public int [] searchGuest(String firstName, String lastName) {
        int [] result;
        for(Guest guest : guestList) {
            if (guest.getfName.lower() == firstName.lower() && guest.getlName.lower() == lastName.lower()) {
                result.add(guest.getGuestID());
            }
        }
        for(VIPGuest guest : vipGuestList) {
            if(guest.getfName() == firstName && guest.getlName() == lastName) { // Conditions have to do this
                result.add(guest.getGuestID());
            }
        }
        return result; // This is an array of long integers
    }

    /**
     * This method seaches guests and returns a string of the long integers of the values of their IDs
     *
     * @param firstName    String representing the first name of the guest
     * @param lastName     String representing the second name of the guestthe guests txt file
     * @return             This method seaches guests and returns a string of the long integers of the values of their IDs
     */

    public Guest searchGuestByID(long guestID) {
        for(Guest guest : guestList) {
            if(guest.getGuestID() == guestID) {
                return guest;
            }
        }
        for(VIPGuest guest : vipGuestList) {
            if(guest.getGuestID() == guestID) {
                return guest;
            }
        }
    }

    public boolean saveRoomsData(String roomsTxtFileName) {
        File fnew = new File(roomsTxtFileName + ".txt");
        String source = textArea.getText();
        try {
            FileWriter roomsTxtFileName = new FileWriter(fnew, false);
            roomsTxtFileName.write(source);
            roomsTxtFileName.close();
            return true;
        }
        return false;
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveGuestsData(String guestsTxtFileName) {
        File fnew = new File(guestsTxtFileName + ".txt");
        String source = textArea.getText();
        try {
            FileWriter guestsTxtFileName = new FileWriter(fnew, false);
            guestsTxtFileName.write(source);
            guestsTxtFileName.close();
            return true;
        }
        return false;
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveBookingsData(String bookingsTxtFileName) {
        File fnew = new File(bookingsTxtFileName + ".txt");
        String source = textArea.getText();
        try {
            FileWriter bookingsTxtFileName = new FileWriter(fnew, false);
            bookingsTxtFileName.write(source);
            bookingsTxtFileName.close();
            return true;
        }
        return false;
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean savePaymentsData(String paymentsTxtFileName) {
        File fnew = new File(paymentsTxtFileName + ".txt");
        String source = textArea.getText();
        try {
            FileWriter paymentsTxtFileName = new FileWriter(fnew, false);
            paymentsTxtFileName.write(source);
            paymentsTxtFileName.close();
            return true;
        }
        return false;
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean cancelBooking(long bookingID){
    	try{
	    	for(Booking book : bookingList){
	    		if(book.getBookingID() == bookingID){booking = book;}
	    	}
	    	
	    	long diff = new Date().getTime() - long diff = checkOutDate.getTime() - checkInDate.getTime();
	        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);;
	        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    	if(daysDiff>2){
	    		Payment refund = new Payment();
	    		Payment.Payment(new Date(), booking.getGuestID(), booking.getTotalAmount()*(-1), 'refund');
	    		paymentList.add(payment);
	    	}
	    	bookingList.remove(booking);
	    	return true;
	    }catch(Exception e){
	    	System.out.print("An error occured while canceling a booking....")
	    	System.out.print(e + '\n')
	    	return false;
	    }
    }



    public void displayBookingsOnDate(Date date){
    	for(Booking book: bookingList){
    		if(book.getCheckOutDate().before(date) && book.getCheckInDate().after(date)){
    			Guest guest = searchGuestByID(book.getGuestID());
    			for(Room room: roomList){
    				if(room.getRoomNumber()==book.getRoomNumber()){booked_room = room;}
    			}
    			System.out.print("bookingID: " + book.getBookingID() + "Name: " + guest.getlName() +' ' + guest.getfName() + "Room Number" + book.getRoomNumber() + "Room Type: " + booked_room.getRoomType() + "Room Price: " + booked_room.getRoomPrice() + "Payment Price: " + book.getTotalAmount() +'\n');

    		}
    	}
    }
    public void displayPaymentsOnDate(Date date){
    	for(Payment payment: paymentList){
    		if(payment.getDate() == date){
    			System.out.print("Guest ID: "+ payment.getGuestID() + "Payment Ammount: " +payment.getAmount() + "Payment Reason:" + payment.getPayReason());

    		}
    	}
    }
    public void displayAllGuests(){
    	System.out.println('Displaying Guests: ');
    	for(Guest guest: guestList){
    		System.out.print("Guest ID: "+ guest.getGuestID() + "Guest Name: "+ guest.getlName() + ' ' guest.getfName() + "Date Joined: " + guest.getDateJoin() +'\n');
    	}
    	System.out.println('Displaying VIP Guests: ');
    	for(VIPGuest guest: vipGuestList){
			System.out.print("Guest ID: "+ guest.getGuestID() + "Guest Name: "+ guest.getlName() + ' ' guest.getfName() + "Date Joined: " + guest.getDateJoin() + "VIP Start Date: " + guest.getVIPStartDate() + "VIP End Date: " + guest.getVIPExpiryDate() +'\n')
    	}
    }

    public void displayAllRooms(){
    	System.out.println('Displaing Rooms: ');
    	for(Room room: roomList){
    		System.out.print("Room Number: " +room.getRoomNumber() + "Room Type: " + room.getRoomType() + "Room Price: " + room.getRoomPrice() + "Room Capacity: " + room.getCapacity() + "Facilities: " +room.getFacilities() +'\n');
    	}
    }

    public void displayAllBookings(){
    	System.out.println('Displaying Bookings: ');
    	for(Booking book: BookingList){
    	    Guest guest = searchGuestByID(book.getGuestID());
    		for(Room room: roomList){
    			if(room.getRoomNumber()==book.getRoomNumber()){booked_room = room;}}
    		System.out.print("bookingID: " + book.getBookingID() + "Name: " + guest.getlName() +' ' + guest.getfName() + "Room Number" + book.getRoomNumber() + "Room Type: " + booked_room.getRoomType() + "Room Price: " + booked_room.getRoomPrice() + "Payment Price: " + book.getTotalAmount() +'\n');	
    	}
    }

    public void displayAllPayments(){
    	System.out.println('Displaying Payments: ');
    	for(Payment payment: paymentList){
    		System.out.print("Guest ID: "+ payment.getGuestID() + "Payment Ammount: " +payment.getAmount() + "Payment Reason:" + payment.getPayReason());

    	}
    }

    static class Room {
        private long roomNumber;
        private String roomType;
        private double roomPrice;
        private int capacity;
        private String facilities;

        public Room(long roomNumber, String roomType, double roomPrice, String facilities) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.roomPrice = roomPrice;
            this.facilities = facilities;
        }

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

        public Booking(long bookingID, long guestID, long roomNumber, Date bookingDate, Date checkInDate, Date checkOutDate, double totalAmount) {
            this.bookingID = bookingID;
            this.guestID = guestID;
            this.roomNumber = roomNumber;
            this.bookingDate = bookingDate;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.totalAmount = totalAmount;
        }

        public long getBookingID(){return this.bookingID;}
        public long getGuestID(){return this.guestID;}
        public long getRoomNumber(){return this.roomNumber;}
        public Date getBookingDate(){return this.bookingDate;}
        public Date getCheckInDate(){return this.checkInDate;}
        public Date getCheckOutDate(){return this.checkOutDate;}
        public double getTotalAmount(){return this.totalAmount;}
    }

	class Guest {
	    private long guestID;
	    private String fName;
	    private String lName;
	    private Date dateJoin;

	    public Guest(long guestID, String fName, String lName, Date dateJoin) {
	        this.guestID = guestID;
	        this.fName = fName;
	        this.lName = lName;
	        this.dateJoin = dateJoin;
	    }

	    public long getGuestID() {return this.guestID;}
	    public String getfName() {return this.fName;}
	    public String getlName() {return this.lName;}
	    public Date getDateJoin() {return this.dateJoin;}
	}

    class VIPGuest extends Guest {
        private Date VIPStartDate;
        private Date VIPExpiryDate;

        public VIPGuest (long guestID, String fName, String lName, Date dateJoin, Date VIPStartDate, Date VIPExpiryDate) {
            super(guestID, fName, lName, dateJoin);
            this.VIPStartDate = VIPStartDate;
            this.VIPExpiryDate = VIPExpiryDate;
        }

        public void setVIP(Date VIPStartDate, Date VIPExpiryDate) {
            this.VIPStartDate = VIPStartDate;
            this.VIPExpiryDate = VIPExpiryDate;
        }

        public Date getVIPStartDate() { return this.VIPStartDate; }
        public Date getVIPExpiryDate() { return this.VIPExpiryDate; }

    }

    static class Payment {
        private Date date;
        private long guestID;
        private double amount;
        private String payReason;

        public Payment(Date date, long guestID, double amount, String payReason) {
            this.date = date;
            this.guestID = guestID;
            this.amount = amount;
            this.payReason = payReason;
        }

        public Date getDate() {return this.date;}
        public long getGuestID() {return this.guestID;}
        public double getAmount() {return this.amount;}
        public String getPayReason() {return this.payReason;}
    }
}