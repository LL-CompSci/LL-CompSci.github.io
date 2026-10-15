import java.util.Scanner;

public class MadLibs {
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter a name: ");
        String name = input.nextLine();

        System.out.print("Enter a whole number: ");
        int number = input.nextInt();
        input.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Enter an adjective: ");
        String adjective = input.nextLine();

        System.out.print("Enter a place: ");
        String place = input.nextLine();

        System.out.printf("%s went to %s and saw %s object that was %d feet tall.%n", name, place, adjective, number);
        input.close();
    }
}
