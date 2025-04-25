package arbitraryarithmetic;

import java.util.ArrayList;
//import java.util.Scanner;

public class AInteger {
    public ArrayList<Integer> digits;
    public boolean isPositive;

    public AInteger() {
        this.digits = new ArrayList<>();
        this.isPositive = true;
    }

    public AInteger(String s) {
        this.digits = new ArrayList<>();
        s = s.trim();

        if(s.charAt(0) == '-'){
            this.isPositive = false ;
            s = s.substring(1);
        }
        else {
            this.isPositive = true;
        }

        for(int i = 0; i < s.length(); i++){
            digits.add(s.charAt(i) - '0');
        }
    }

    public AInteger(AInteger OtherNum) {
        this.digits = new ArrayList<>(OtherNum.digits);
        this.isPositive  = OtherNum.isPositive;
    }

    // Parse method
    public static AInteger parse(String s) {
        return new AInteger(s);
    }

    public String toString(){
        String answer = "";

        for(int i = 0; i < this.digits.size(); i++){
            answer += this.digits.get(i);
        }

        if(!this.isPositive){
            answer = '-' + answer;
        }

        return answer;
    }

    public int compare_nums(AInteger number){
        if(this.digits.size() > number.digits.size()){
            return 1;
        }
        else if( this.digits.size() < number.digits.size()){
            return -1;
        }
        else{
            for(int i=0; i < this.digits.size(); i++){
                if((this.digits.get(i) < number.digits.get(i))){
                    return -1;
                }
                else if(this.digits.get(i) == number.digits.get(i)){
                    continue;
                }else{
                    return 1;
                }
            }
        }
        return 0;
    }

    public AInteger divByTwo() {
        AInteger result = new AInteger();
        result.digits.clear();
    
        int carry = 0;
        for (int i = 0; i < this.digits.size(); i++) {
            int current = carry * 10 + this.digits.get(i);
            result.digits.add(current / 2);
            carry = current % 2;
        }
    
        // Remove leading zeros
        while (result.digits.size() > 1 && result.digits.get(0) == 0) {
            result.digits.remove(0);
        }
    
        result.isPositive = this.isPositive;
        return result;
    }
    

    public AInteger add(AInteger that){

        int carry = 0;
        AInteger result = new AInteger();
        result.digits.clear();

        if(this.isPositive == that.isPositive){
            result.isPositive = this.isPositive;
        }else{
            boolean temp = (this.compare_nums(that) == 1)? this.isPositive : that.isPositive;
            AInteger a = new AInteger(this);
            a.isPositive = true;
            AInteger b = new AInteger(that);
            b.isPositive = true;

            result = a.sub(b);
            result.isPositive = temp;
            if(result.digits.size() == 1 && result.digits.get(0) == 0){
                result.isPositive = true;
            }
            return result;
        }

        AInteger larger = (this.compare_nums(that) == 1)? this : that;
        AInteger smaller = (larger == this)? that : this;
    
        for(int i = larger.digits.size()-1, j = smaller.digits.size()-1; i >= 0 || j >= 0; i--, j--){
            int sum = (j >= 0)? larger.digits.get(i)+smaller.digits.get(j)+carry : larger.digits.get(i)+carry;
            carry = sum/10;
            result.digits.add(0, sum%10);
        }

        if(carry != 0){
            result.digits.add(0, carry);
        }

        if(result.digits.size() == 1 && result.digits.get(0) == 0){
            result.isPositive = true;
        }
        return result;
    }

    public AInteger sub(AInteger num){
        int borrow = 0;
        AInteger result = new AInteger();
        result.digits.clear();

        if(!(this.isPositive ^ num.isPositive)){
            result.isPositive = (this.compare_nums(num) == -1)? !num.isPositive : num.isPositive;
        }else{
            boolean temp = this.isPositive;
            AInteger a = new AInteger(this);
            a.isPositive = true;
            AInteger b = new AInteger(num);
            b.isPositive = true;

            result = a.add(b);
            result.isPositive = temp;
            if(result.digits.size() == 1 && result.digits.get(0) == 0){
                result.isPositive = true;
            }
            return result;
        }

        AInteger larger = (this.compare_nums(num) == 1)? this : num;
        AInteger smaller = (larger == this)? num : this;

        for(int i = larger.digits.size()-1, j = smaller.digits.size()-1; i>=0 || j>=0; i--, j--){
            if(j >= 0){
                int temp = larger.digits.get(i) - smaller.digits.get(j) - borrow;
                borrow = ((larger.digits.get(i) - borrow) < smaller.digits.get(j))? 1 : 0;
                int diff = temp + 10*borrow;
                result.digits.add(0, diff);
            }else{
                int diff = larger.digits.get(i) - borrow;
                borrow = 0;
                if(i != 0 || diff != 0){
                    result.digits.add(0, diff);
                }
            }
        }
        while (result.digits.size() > 1 && result.digits.get(0) == 0) {
            result.digits.remove(0);
        }
        if(result.digits.size() == 1 && result.digits.get(0) == 0){
            result.isPositive = true;
        }
        
        return result;
    }

    public AInteger mult(AInteger num){
        AInteger result = new AInteger();


        for(int i=this.digits.size()-1; i>=0; i--){
            int carry = 0;
            AInteger prod = new AInteger();
            prod.isPositive = true;

            for (int k = 0; k < this.digits.size() - 1 - i; k++) {
                prod.digits.add(0); 
            }
            
            for(int j=num.digits.size()-1; j>=0; j--){
                int temp = this.digits.get(i) * num.digits.get(j) + carry;
                prod.digits.add(0, temp%10);
                carry = temp/10;
            }
            if(carry != 0){
                prod.digits.add(0, carry);
            }

            result = result.add(prod);
        }

        result.isPositive = !(this.isPositive ^ num.isPositive);

        while (result.digits.size() > 1 && result.digits.get(0) == 0) {
            result.digits.remove(0);
        }

        return result;
    }

   
   /*public AInteger div(AInteger num) {
        AInteger result = new AInteger();
        result.digits.clear(); 
    
        if (num.digits.get(0) == 0) {
            throw new ArithmeticException("Division by zero");
        }
    
        AInteger dividend = new AInteger(this);
        AInteger divisor = new AInteger(num);
        AInteger one = new AInteger("1");
        one.isPositive = true;
        AInteger quotient = new AInteger("0");
        quotient.isPositive = true;

        AInteger temp = dividend.compare_nums(divisor);
        boolean check = false;
        if(temp == dividend){
            check = true;
        }

        while (check) {
            dividend = dividend.sub(divisor);
            quotient = quotient.add(one);

            temp = dividend.compare_nums(divisor);
            if(temp != dividend){
                check = false;
            }
        }
        

        result.isPositive = (this.isPositive == num.isPositive);
    
        return quotient;
    }
        */

    public AInteger div(AInteger num) {
        if (num.digits.get(0) == 0) {
            throw new ArithmeticException("Division by zero");
        }
    
        AInteger low = new AInteger("0");
        AInteger high = new AInteger(this);
        high.isPositive = true;
        AInteger mid, product;
        AInteger result = new AInteger("0");
        AInteger divisor = new AInteger(num);
        divisor.isPositive = true;
    
        while (low.compare_nums(high) <= 0) {
            mid = low.add(high).divByTwo(); // You'll need to implement divByTwo
            product = mid.mult(divisor);
    
            int cmp = product.compare_nums(this);
            if (cmp == 0) {
                result = mid;
                break;
            } else if (cmp == -1) {
                result = mid;
                low = mid.add(new AInteger("1"));
            } else {
                high = mid.sub(new AInteger("1"));
            }
        }
    
        result.isPositive = !(this.isPositive ^ num.isPositive);
        if(result.digits.size() == 1 && result.digits.get(0) == 0){
            result.isPositive = true;
        }
        return result;
    }


    /*public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String Num1 = input.nextLine();
        String Num2 = input.nextLine();
        input.close();

        AInteger num_1 = new AInteger(Num1);
        AInteger num_2 = new AInteger(Num2);

        AInteger sum = num_1.add(num_2);
        AInteger diff = num_1.sub(num_2);
        AInteger prod = num_1.mult(num_2);
        AInteger quot = num_1.div(num_2);
       

        System.out.println("Sum:" + sum.toString());
        System.out.println("Diff:" + diff.toString());
        System.out.println("Prod:"+ prod.toString());
        System.out.println("quot:" + quot.toString());
        }*/
}



