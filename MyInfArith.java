import java.util.Scanner;

import arbitraryarithmetic.AFloat;
import arbitraryarithmetic.AInteger;

public class MyInfArith {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String datatype = input.nextLine().trim();
        String operation = input.nextLine().trim();
        String firstnum = input.nextLine().trim();
        String secondnum = input.nextLine().trim();

        input.close();

        if(datatype.equals("int")){

            AInteger a = new AInteger(firstnum);
            AInteger b = new AInteger(secondnum);

            AInteger answer = new AInteger();

            switch (operation) {
                case "add":
                answer = a.add(b);
                break;

                case "sub":
                answer = a.sub(b);
                break;

                case "mult":
                answer = a.mult(b);
                break;

                case "div":
                answer = a.div(b);
                break;
            }

            System.out.println("The Answer is " + answer.toString());
        }
        else if(datatype.equals("float")){
            AFloat a = new AFloat(firstnum);
            AFloat b = new AFloat(secondnum);

            AFloat answer = new AFloat();

            switch (operation) {
                case "add":
                    answer = a.add(b);
                    break;

                case "sub":
                    answer = a.sub(b);
                    break;

                case "mult":
                    answer = a.mult(b);
                    break;

                case "div":
                    answer = a.divi(b);
                    break;
            
                default:
                System.out.println("Invalid Operation");
                    break;
            }

            System.out.println("The Answer is " + answer.toString());
        }
        else{
            System.out.println("Invalid Datatype");
        }
    }
}
