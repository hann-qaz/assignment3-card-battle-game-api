import exception.*;

public class Main {
    public static void main(String[] args) {
        try {
            throw new InvalidInputException("Invalid card level");
        } catch (InvalidInputException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            throw new ResourceNotFoundException("Card not found");
        } catch (ResourceNotFoundException e) {
            System.out.println ("Caught: " + e.getMessage());
        }
    }
}