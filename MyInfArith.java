

import arbitraryarithmetic.AFloat;
import arbitraryarithmetic.AInteger;

public class MyInfArith {
    public static void main(String[] args){

        String datatype = args[0];
        String operation = args[1];
        String firstnum = args[2];
        String secondnum = args[3];


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
                    answer = a.div(b);
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
