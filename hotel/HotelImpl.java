package hotel;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Calendar;
import java.text.*;
import java.Writer.*;

// Means this class has to implement all the hotel's interface methods to compile successfully.
public class HotelImpl implements Hotel {

    public void HotelImple(String roomsTxtFileName, String guestsTxtFileName,
                           String bookingsTxtFileName, String paymentsTxtFileName){
        importAllData(roomsTxtFileName, guestsTxtFileName, bookingsTxtFileName, paymentsTxtFileName)
    }

    public boolean removeRoom(int roomNumber) {
        for (Booking book : bookingList ) {
            Date d1 = new Date();
            if (book.getRoomNumber() == roomNumber && d1.after(book.getCheckOutDate())) {
                bookingList.remove(book);
                return true;
            }
        }
        return false;
    }

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

			    VIPGuest vipGuest = new VIPGuest();
			    Calendar end_Date = Calendar.getInstance();
	            end_Date.setTime(new Date());
	            end_Date.add(Calendar.YEAR,1);
	            String formated = ft.format(end_Date);
	            Date end_Date = ft.parse(formated);
			    vipGuest.VIPGuest(getGuestID, fName, lName, new Date(), new Date(), end_Date);
			    vipGuestList.add(vipGuest);
	        }else{
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

    public boolean removeGuest(long guestID) {
    	try{
    		for(Booking book: bookingList){
    			if(book.getGuestID() == guestID && new Date().after(book.getCheckOutDate())){
		    		for(Guest guest : guestList){
		    			if(guest.getGuestID == guestID){
		    				guestList.remove(guest);
		    			}
		    		}
    				return true;
    			}
    		}return false;

    	}catch(Exception e){
    		System.out.print("An Error Has Occured... ");
	    	System.out.print(e + "\n");
	    	return false;
    	}
    }
    public boolean addRoom(long roomNumber, String roomType, double roomPrice, int capacity, String facilities){
        for(Room room : roomList) {
            if (room.getRoomNumber() == roomNumber) return false;
        }
        Room room = new Room();
        room.roomNumber = roomNumber;
        room.roomType = roomType;
        room.roomPrice = roomPrice;
        room.capacity = capacity;
        room.facilities = facilities;
        roomList.add(room);
        return true;
    }

    public boolean checkRoomAvailable(long roomNumber, Date checkInDate, Date checkOutDate){
    	for (Booking book : bookingList){
    		if(book.getRoomNumber() == roomNumber && checkInDate.before(book.getCheckOutDate()) && checkInDate.after(book.getCheckInDate())){
    			return false;
    		}
    	}
    	return true;
    }

    public ArrayList findAvailableRooms(String roomType, Date checkInDate, Date checkOutDate){
    	ArrayList<Long> availableRooms = new ArrayList<Long>();
    	for(Room room: roomList){
    		if( room.getRoomType() == roomType && checkRoomAvailable(room.getRoomNumber(), checkInDate, checkOutDate)){
    			availableRooms.add(room.getRoomNumber());
    		}
    	}
    	return availableRooms;
    }

    public boolean makeBooking(String roomType, long guestID, Date checkInDate, Date checkOutDate){

    	boolean guestExists = false;
    	for(Guest guest: guestList){
    		if(guest.getGuestID() == guestID){guestExists = true;}}
    	if(guestExists == false){return false;}


    	if(new Date().after(checkInDate)){return false;}


    	availableRooms = findAvailableRooms(roomType, checkInDate, checkOutDate);
    	try{
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


        Booking booking = new Booking();
        booking.bookingID = bookingID;
        booking.guestID = guestID;
        booking.roomNumber = roomNumber;
        booking.bookingDate = new Date();
        booking.checkInDate = checkInDate;
        booking.checkOutDate = checkOutDate;
        long diff = checkOutDate.getTime() - checkInDate.getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        booking.totalAmount = daysDiff*bookedRoom.getRoomPrice();
        for(VIPguest guest : VIPGuestList){
        	if(guest.getGuestID() == guestID && checkOutDate.before(guest.getVIPexpiryDate())){
        		boooking.totalAmount = booking.totalAmount * 0.9;
        	}
        }

        Payment payment = new Payment();
        payment.date = new Date();
        payment.guestID = guestID;
        payment.ammount = booking.totalAmount;
        pament.payReason = 'booking';

        paymentList.add(payment);
        bookingList.add(booking);
        return true;

    }
    public boolean importAllData(String roomsTxtFileName, String guestsTxtFileName, String bookingsTxtFileName, String paymentsTxtFileName){
        try{
            importRoomsData(roomsTxtFileName);
            importGuestsData(guestsTxtFileName);
            importBookingsData(bookingsTxtFileName);
            importPaymentsData(paymentsTxtFileName);
        }catch(Exception e){
            System.out.println("ERROR: an issue occured importing data")
            return false;
        }
        return true;
    }

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
            public ArrayList<Guest> guestList = new ArrayList<Guest>();
            public ArrayList<VIPGuest> vipGuestList = new ArrayList<VIPGuest>();
            while ((st = br.readLine()) != null) {
                String[] guest_info = st.split(",");
                if(guest_info.length > 4){
                	VIPGuest vipGuest = new VIPGuest;
                	vipGuest.VIPGuest(Long.valueOf(guest_info[0]), guest_info[1], guest_info[2], ft.parse(guest_info[3]), ft.parse(guest_info[4]), ft.parse(guest_info[5]));
                	vipGuestList.add(vipGuest);
                }else{
                	Guest guest = new Guest();
                	guest.Guest(Long.valueOf(guest_info[0]), guest_info[1], guest_info[2], ft.parse(guest_info[3]));
                	guestList.add(guest);
                }

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
            public ArrayList<Booking> bookingList = new ArrayList<Booking>();
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
            public ArrayList<Payment> PaymentList = new ArrayList<Payment>();
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


    public boolean checkOut(long bookingID){
    	for(Booking book: bookingList){
    		if(book.getBookingID()==bookingID){booking=book;}
    	}

    	if(new Date().after(booking.getCheckOutDate())|| new Date().before(checkInDate)){return false;}

    	bookingList.remove(booking);
    	return true;
    }

    public int [] searchGuest(String firstName, String lastName) {
        int [] result;
        for(Guest guest : guestList) {
            if (guest.getfName == firstName && guest.getlName == lastName) {
                result.add(guest.getGuestID());
            }
        }
        for(VIPGuest guest : vipGuestList) {
            if(guest.getfName() == firstName && guest.getlName() == lastName) {
                result.add(guest.getGuestID());
            }
        }
        return result;
    }

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

    public void displayAllGuests() {
    }

    public void displayAllRooms(){ }

    public boolean savePaymentsData(String paymentsTxtFileName){
        return true;
    }

    public void displayAllBookings() {}

    public void displayAllPayments() {}





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

        public Booking(long bookingID, long guestID, long roomNumber, Date bookingDate, Date checkInDate, Date checkOutDate) {
            this.bookingID = bookingID;
            this.guestID = guestID;
            this.roomNumber = roomNumber;
            this.bookingDate = bookingDate;
            this.checkInDate = checkInDate;
            this.checkOutDate;
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