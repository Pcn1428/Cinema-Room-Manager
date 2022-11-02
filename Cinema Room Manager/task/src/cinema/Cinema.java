package cinema;
import java.util.*;

public class Cinema {

    public void startCinema() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(s.nextLine());
        System.out.println("Enter the number of seats in each row:");
        int columns = Integer.parseInt(s.nextLine());
        System.out.println();

        int seatsSold = 0;
        int income = 0;
        int option = -1;
        boolean running = true;
        String[][] seating = generateSeating(rows, columns);

        while (running) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            option = s.nextInt();
            System.out.println();

            if (option == 1) {
                printSeating(seating);
                System.out.println();
            }
            else if (option == 2) {
                System.out.println("Enter a row number:");
                int rowNum = s.nextInt();
                System.out.println("Enter a seat number in that row:");
                int columnNum = s.nextInt();

                while (!isSeatAvailable(seating,rowNum,columnNum) || !isSeatValid(rows,columns,rowNum,columnNum)) {
                    if (!isSeatValid(rows,columns,rowNum,columnNum)) {
                        System.out.println("Wrong input!");
                        System.out.println();
                    }
                    else if (!isSeatAvailable(seating,rowNum,columnNum)) {
                        System.out.println("That ticket has already been purchased!");
                        System.out.println();
                    }
                    System.out.println("Enter a row number:");
                    rowNum = s.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    columnNum = s.nextInt();
                }
                System.out.println("Ticket Price: $" + ticketPrice(rows, columns, rowNum));
                markSeating(seating, rowNum, columnNum);
                income += ticketPrice(rows, columns, rowNum);
                seatsSold++;
                System.out.println();
            }
            else if (option == 3){
                showStatistics(seatsSold, income, rows, columns);
            }else {
                running = false;
            }
        }

    }

    public void showStatistics(int seatsSold, int income, int rows, int columns) {
        double percentage = ((seatsSold / (double) (rows * columns)) * 100);
        int totalIncome = ((rows*columns) <= 60) ? (rows*columns) * 10 :
                            (rows % 2 == 0) ? ((rows/2)*10 + (rows/2)*8)*columns : ((rows/2)*10 + ((rows/2)+1)*8)*columns;

        System.out.println("Number of purchased tickets: " + seatsSold);
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }

    public String[][] generateSeating(int rows, int columns) {
        if (rows > 9 || columns > 9) return null;
        String[][] seating = new String[rows+1][columns+1];
        seating[0][0] = " ";
        for (int i = 1; i < seating[0].length; i++) seating[0][i] = Integer.toString(i);
        for (int i = 1; i < seating.length; i++) seating[i][0] = Integer.toString(i);
        for (int i = 1; i < seating.length; i++) {
            for (int j = 1; j < seating[i].length; j++) {
                seating[i][j] = "S";
            }
        }
        return seating;
    }

    public static void printSeating(String[][] seating) {
        System.out.println("Cinema:");
        for (int i = 0; i < seating.length; i++) {
            String row = "";
            for (String s: seating[i]) row += s + " ";
            System.out.println(row);
        }
    }

    public int ticketPrice(int rows, int columns, int rowNum) {
        int totalSeats = rows * columns;
        int price = (totalSeats <= 60) ? 10 : (rowNum <= rows/2) ? 10 : 8;
        return price;
    }

    public boolean isSeatAvailable(String[][] seating, int rowNum, int columnNum) {
        try {
            return seating[rowNum][columnNum].equals("S");
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isSeatValid(int rows, int columns, int rowNum, int columnNum) {
        return rows != 0 && columns != 0 && rowNum <= rows && columnNum <= columns;
    }

    public void markSeating(String[][] seating, int rowNum, int columnNum) {
        seating[rowNum][columnNum] = "B";
    }

    public static void main(String[] args) {
        Cinema c = new Cinema();
        c.startCinema();
    }
}