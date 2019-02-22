import java.io.*;
import java.util.ArrayList;
import java.util.Date;
package hotel; // means this class belongs to the hotel package
public class HotelImpl implements Hotel {
	public void HotelImple(String roomsTxtFileName, String guestsTxtFileName,
	String bookingsTxtFileName, String paymentsTxtFileName){
	}
	public boolean importRoomsData(String roomsTxtFileName){
		try{
			File file = new File(roomsTxtFileName);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String st;

	        ArrayList<Room> roomList = new ArrayList<Room>();
	        while ((st = br.readLine()) != null) {
	            String[] room_info = st.split(",");
	            Room room = new Room();
	            room.room_number = Double.valueOf(room_info[0]);
	            room.room_type = room_info[1];
	            room.room_price = Double.valueOf(room_info[2]);
	            room.room_sleeps = Integer.valueOf(room_info[3]);
	            room.bathroom_type = room_info[4];
	            roomList.add(room);
	        }
	        file.close();
	        for(Room room : roomList) {
	            System.out.println(room.getRoomNumber());
	        }
    	}
    	catch(Exception e){
    		System.out.println("Error Occured when reading rooms data...");
    		return false;
    	}

		return true;}
	public boolean importGuestsData(String guestsTxtFileName){return true;}
	public boolean importBookingsData(String bookingsTxtFileName){return
	true;}
	public boolean importPaymentsData(String paymentsTxtFileName){return
	true;}
	public void displayAllRooms(){ }
	public boolean savePaymentsData(String paymentsTxtFileName){return
	true;}
	

	static class Room {
        double room_number;
        String room_type;
        double room_price;
        int room_sleeps;
        String bathroom_type;
        public double getRoomNumber() {return this.room_number;}
        public String getRoomType(){return this.room_type;}
        public double getRoomPrice(){return this.room_price;}
        public int getRoomSleeps(){return this.room_sleeps;}
        public String getBathroomType(){return this.bathroom_type;}
    }