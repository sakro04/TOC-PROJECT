import java.util.Scanner;


    public class Main {
        
    public static void main(String[] args) {
        
     Scanner inputScanner = new Scanner(System.in);      

        System.out.println("Available patterns:");
        System.out.println("1. a|b");
        System.out.println("2. a*b");
        System.out.println("3. ab+");
        System.out.println("4. 0+");
        System.out.println("5. 01*");
        System.out.print("Select pattern (1-5): ");

        int choice = inputScanner.nextInt();
        
        String selectedPattern = "";

           switch(choice) {
                   
            case 1: selectedPattern = "a|b"; break;
            case 2: selectedPattern = "a*b"; break;
            case 3: selectedPattern = "ab+"; break;
            case 4: selectedPattern = "0+"; break;
            case 5: selectedPattern = "01*"; break;
            default:
    System.out.println("Invalid choice");     
                inputScanner.close();
                return;
                   
        }

        DFAtoTMConverter.FiniteAutomaton fa = DFAtoTMConverter.createAutomaton(selectedPattern);

        System.out.println("\nAutomaton Transitions:");
        for (DFAtoTMConverter.StateTransition t : fa.transitions) {
            System.out.println(t.getTransition());
        }

      System.out.println("Accepting States: " + fa.acceptingStates);   
        

        DFAtoTMConverter.TuringMachine tm = DFAtoTMConverter.convertToTM(fa);

        System.out.println("\nTuring Machine Rules:");
        for (DFAtoTMConverter.TransitionRule r : tm.rules) {
            System.out.println(r.getRule());
        }

        System.out.print("\nEnter string to test: ");
        String testString = inputScanner.next();

        System.out.println(fa.validate(testString) ? "Accepted" : "Rejected");

        inputScanner.close();
       }
       }
