import java.util.Scanner; //Import Scanner

public class Main {

    // Scanner Object for User Input (Shared Across All Methods)
    static Scanner input = new Scanner(System.in);
    
    //1D Array that Stores the Row Labels (A–E)
    static char[] seatLabels = {'A', 'B', 'C', 'D', 'E'};
    
    //Jagged 2D boolean Array to Store Seat Availability
    //true  = reserved
    //false = available
    static boolean[][] seats = new boolean[5][];


    //This Method Initializes the Jagged Array for Seats.
    //Each Row can Have a Different Number of Seats.
 
    static void seatsInitialize() {
        int[] seatLayout = {10, 6, 6, 7, 4}; // seat count per row

        // Loop through each row and assign its seat count
        for (int i = 0; i < seatLayout.length; i++) {
            seats[i] = new boolean[seatLayout[i]]; 
            // Initialize all seats in this row as available (false by default)
        }
    }
    
    //Displays the welcome banner when the program starts

    static void welcome(){
        System.out.println("|--------------------------------------------------------|");
        System.out.println("|------ Welcome to our Cinema Seats Booking System ------|");
        System.out.println("|--------  CCS103 Prelim Group Research Project  --------|");
        System.out.println("|--------------------------------------------------------|\n");    
    }
    

    //Displays the option menu and validates user choice
    //Returns a Valid Menu Option (1–4)
   
    static int optionMenu(){
        while(true){
            String[] options = {
                "View seating layout",
                "Book a seat",
                "Cancel a seat reservation",
                "Exit the system"
            };

            System.out.println("|-------------------- | OPTION MENU | -------------------|");
            // Display all option menu items with numbering
            for(int i = 0; i < options.length; i++){
                System.out.println("(" + (i+1) + ") " + options[i]);
            }
            System.out.println("|--------------------------------------------------------|");
            System.out.print("Enter option: ");

            int option = input.nextInt();
            System.out.println();
            
            // Validate user input range (1–4)
            if(option > 0 && option < 5) {
                return option;
            } else {
                System.out.println("*[INVALID]: please input 1-4\n");
            }
        }
    }
    
   
    //(1)Displays the seating layout with labels and status   
    
    static void showSeats(){
        System.out.println("|--------------------- | VIEW SEATS | -------------------|");
        getSeats(true); // Calls method to show seat grid and display statistics
        System.out.println("|--------------------------------------------------------|\n");
    }
    

    //(2)Allows the User to Book Available Seats
   
    static void bookSeat() {
        String[] attr = {"Row", "Column"}; // used for user prompts

        while(true) {
            System.out.println("|-------------------- | BOOK SEATS | --------------------|");
            int[] seatsData = getSeats(true); //Get Seating Overview and Counts
            System.out.println("|--------------------------------------------------------|");
            System.out.print("How many seats do you want to book: ");

            int qty = input.nextInt(); //Number of Seats User Wants to Reserve

            //Validate if Quantity is Within Available Range
            if(qty > 0 && qty <= seatsData[2]) {
                System.out.println("|--------------------------------------------------------|\n");
                System.out.println("[PLEASE ENTER SEATS YOU WANT TO RESERVED]");

                //Loop for Booking Multiple Seats
                for(int i = 0; i < qty; ) {
                    int[] seat = new int[2]; //[0] = Row, [1] = Column

                    System.out.println("> Seat " + (i+1) + ": ");
                    for(int j = 0; j < 2; j++) {
                        System.out.print("   > " + attr[j] + ": ");
                        seat[j] = input.nextInt() - 1; 
                    }

                    //Validate that the Seat Selection Exists in the Layout
                    if(seat[0] > -1 && seat[0] < seats.length &&
                       seat[1] > -1 && seat[1] < seats[seat[0]].length) {

                        //Check if Seat is Not Already Reserved
                        if(!seats[seat[0]][seat[1]]) {
                            seats[seat[0]][seat[1]] = true; // reserve the seat
                            System.out.println("   * [" +
                                seatLabels[seat[0]] + (seat[1]+1) +
                                "] reserved!");

                            //Display Updated Seat Layout (without stats)
                            getSeats(false);
                            i++;
                        } else {
                            System.out.println("   * [INVALID]: seat [" +
                                seatLabels[seat[0]] + (seat[1]+1) +
                                "] is already reserved");
                        }
                    } else {
                        System.out.println("   * [INVALID]: seat is not existing");
                    }
                    System.out.println();
                }

                System.out.println("Thankyou for Booking!\n");
                break;
            } else {
                System.out.println("*[INVALID]: please input range between (1 - " +
                    seatsData[2] + ")\n");
            }
        }
    }
    
    //(3) Cancels All Reserved Seats After User Confirmation
    //Prompts User for Confirmation Before Resetting All Reservations
   
    static void cancelSeat() {
        while(true) {
            System.out.println("|---------------- | CANCEL RESERVATION | ------------------|");

            int rSeatCount = getSeats(false)[1]; // get number of reserved seats

            //Check if There are any Reserved Seats Before Allowing Cancel
            if(rSeatCount > 0) {
                System.out.println("Reserved seats: " + rSeatCount);
                System.out.println("|--------------------------------------------------------|");
                System.out.println("Are you sure you want to cancel your seat reservation?: ");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                System.out.println("|--------------------------------------------------------|");
                System.out.print("Enter option: ");

                int option = input.nextInt();
                System.out.println("|--------------------------------------------------------|\n");

                //User Confirms Cancellation
                if(option == 1) {
                    // Reset All Seats to Available (false)
                    for(int i = 0; i < seats.length; i++) {
                        for(int j = 0; j < seats[i].length; j++) {
                            seats[i][j] = false;
                        }
                    }
                    System.out.println("You successfully cancelled your seat reservation!\n");
                    break;
                } else if(option == 2) {
                    //If User Decides Not to Cancel
                    break;
                } else {
                    //Invalid Selection
                    System.out.println("*[INVALID]: please choose between 1 and 2\n");
                }
            } else {
                System.out.println("You don't have any seat reservation!\n");
            }
        }
    }
    

  
    // data[0] = Total Seats
    // data[1] = Reserved Seats
    // data[2] = Available Seats
    // If showData == true, then Displays Seat and Count Data
    // If showData == false, then Only Shows Seat Map Without Summary

    static int[] getSeats(boolean showData) {
        int[] data = new int[3];

        System.out.println("|--------------------------------------------------------|");
        // Loop Through Each Row of the Jagged Array
        for(int i = 0; i < seats.length; i++){
            for(int j = 0; j < seats[i].length; j++){

                // Print Available or Reserved Seat Symbol
                if(!seats[i][j]) {
                    System.out.print("[" + seatLabels[i] + (j+1) + "] ");
                    data[2]++; //Count Available Seats
                } else {
                    System.out.print("[##] "); 
                    data[1]++; //Count Reserved Seats
                }
                data[0]++; //Count Total Seats
            }
            System.out.println(); //New Line After Each Row
        }
        System.out.println("|--------------------------------------------------------|");

        // Display seat statistics if requested
        if(showData) {
            System.out.println("Total seats: " + data[0]);
            System.out.println("Reserved seats: " + data[1]);
            System.out.println("Available seats: " + data[2]);
        }
        return data;
    }
    
  
    //Main method: Controls the Program Flow
       public static void main(String[] args) {

        boolean running = true; // Main Loop Control Variable

        welcome(); // Call the Welcome Message for the Users
        seatsInitialize(); // Call the Method for Initializing the Seat Layout (Jagged Array)

        //Main Program Execution loop; Runs Until the User Exits
        while(running) {
            int option = optionMenu(); // prompt and get menu choice
            
            //Switch Case to Handle Each Menu Option
            //Break after each case to return to the main loop
            switch(option){
                case 1: 
                	showSeats(); //Call the Method for Displaying Seat Layout
                	break;
                	
                case 2:
                	bookSeat(); //Call the Method for Booking a Seat
                	break;
                	
                case 3:
                	cancelSeat(); //Call the Method for Canceling a Seat Reservation
                	break;
                	
                case 4:
                    running = false; //Exit the Program
                    
                    // Thank You Message to Users for Using the System
                    System.out.println("|--------------------------------------------------------|");
                    System.out.println("|---- Thank You for Using our Cinema Booking System! ----|");
                    System.out.println("|----------------- [PROGRAM TERMINATED!] ----------------|");
                    System.out.println("|________________________________________________________|");
                    break;
            }
        }
        input.close();
    }
}
