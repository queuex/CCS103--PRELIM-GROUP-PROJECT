import java.util.Scanner;
public class main {
	//Scanner to handle user inputs	
	static Scanner input = new Scanner(System.in);
	
	//1D array for seat labels and 2D arrays to store seats data
	static char[]seatLabels = {'A', 'B', 'C', 'D', 'E'};
	static boolean[][]seats = new boolean[5][5];
	
	//Greet users
	static void welcome(){
		System.out.println(" ------------------------------------------------");
		System.out.println("|-- Welcome to our Cinema Seats Booking System --|");
		System.out.println("|--  Here is the Current Cinema Seats Layout   --|");
		System.out.println(" ------------------------------------------------\n");	
	}
	
	//Show menu and choose option
	static int optionMenu(){
		while(true){
			String[] options = {"View seating layout", "Book a seat", "Cancel a seat reservation", "Exit the system"};
			System.out.println("====== | OPTION MENU | ======");
			for(int i = 0; i < options.length; i++){
				System.out.println("("+(i+1)+") "+options[i]);
			}
			System.out.println("-----------------------------");
			System.out.print("Enter option: ");
			int option = input.nextInt();
			System.out.println();
			
			if(option > 0 && option < 5) {
				return option;
			}
			else {
				System.out.println("*[INVALID]: please input 1-4");
				System.out.println();
			}
		}
	}
	
	// (1) Show available seats
	static void showSeats(){
		System.out.println("==== | VIEW SEATS | ====");
		getSeats(true);
		System.out.println("------------------------\n");
	}
	
	// (2) Book a seat
	static void bookSeat() {
		String[] attr = {"Row", "Column"};
		while(true) {
			System.out.println("==== | BOOK SEATS | ====");
			int[] seatsData = getSeats(true);
			System.out.println("------------------------");
			System.out.print("How many seats do you want to book: ");
			int qty = input.nextInt();
			if(qty > 0 && qty <= seatsData[2]) {
				System.out.println("------------------------\n");
				System.out.println("[PLEASE ENTER SEATS YOU WANT TO RESERVED]");
				for(int i = 0; i < qty; ) {
					int[] seat = new int[2];
					System.out.println("> Seat "+(i+1)+": ");
					for(int j = 0; j < 2; j++) {
						System.out.print("   > "+attr[j]+": ");
						seat[j] = input.nextInt()-1;
					}
					
					if(seat[0] > -1 && seat[0] < seats.length && seat[1] > -1 && seat[1] < seats[seat[0]].length) {
						if(!seats[seat[0]][seat[1]]) {
							seats[seat[0]][seat[1]] = true;
							System.out.println("   * ["+seatLabels[seat[0]]+(seat[1]+1)+"] reserved!");
							
							getSeats(false);
							
							i++;
						}
						else {
							System.out.println("   * [INVALID]: seat ["+seatLabels[seat[0]]+(seat[1]+1)+"] is already reserved");
						}
					}
					else {
						System.out.println("   * [INVALID]: seat is not existing");
					}
					System.out.println();
				}
				System.out.println("Thankyou for booking!");
				System.out.println();
				break;
			}
			else {
				System.out.println("*[INVALID]: please input range between (1 - "+seatsData[2]+")\n");
			}
		}
	}
	
	// (3) Cancel a seat reservation
	static void cancelSeat() {
		while(true) {
			System.out.println("=| CANCEL RESERVATION |=");
			int rSeatCount = getSeats(false)[1];
			if(rSeatCount > 0) {
				System.out.println("Reserved seats: " + rSeatCount);
				System.out.println("------------------------");
				System.out.println("Are you sure you want to cancel your seat reservation?: ");
				System.out.println("(1) Yes");
				System.out.println("(2) No");
				System.out.println("------------------------");
				System.out.print("Enter option: ");
				int option = input.nextInt();
				System.out.println("------------------------\n");
				if(option == 1) {
					for(int i = 0; i < seats.length; i++) {
						for(int j = 0; j < seats[i].length; j++) {
							if(seats[i][j]) {seats[i][j] = false;}
						}
					}
					System.out.println("You successfully cancelled your seat reservation!\n");
					break;
				}
				else if(option == 2) {
					break;
				}
				else {
					System.out.println("*[INVALID]: please choose between 1 and 2\n");
				}
				
			}
			else {
				System.out.println("You don't have any seat reservation!\n");
			}
		}
	}
	
	//Get and print seats data
	static int[] getSeats(boolean showData) {
		int[] data = new int[3];
		System.out.println("------------------------");
		for(int i = 0; i < seats.length; i ++){
		    for(int j =0; j < seats[i].length; j ++){
		    	
		    	//Print labels
		        if(!seats[i][j]) {
		        	System.out.print("["+seatLabels[i] + (j+1) + "] ");
		        	data[2] ++;
		        }
		        else {
		        	System.out.print("[##] ");
		        	data[1] ++;
		        }
		        data[0] ++;
		    }
		System.out.println();
		}
		System.out.println("------------------------");
		if(showData) {
			System.out.println("Total seats: " + data[0]);
			System.out.println("Reserved seats: " + data[1]);
			System.out.println("Available seats: " + data[2]);
		}
		return data;
	}
	
	
	//Main method
	public static void main(String[] args) {
		//local variables
		boolean running = true;
		int option = 0;
		
		welcome();
		while(running) {
			option = optionMenu();
			
			//switch case
			switch(option){
				case 1:
					showSeats();
					break;
					
				case 2:
					bookSeat();
					break;
				case 3:
					cancelSeat();
					break;
					
				case 4:
					running = false;
					System.out.println("[PROGRAM TERMINATED!]");
					break;
			}
		}
			
	}
}
