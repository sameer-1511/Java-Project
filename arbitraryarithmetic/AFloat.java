package arbitraryarithmetic;

import java.util.ArrayList;

public class AFloat {
    private ArrayList<Integer> intnums;
    private ArrayList<Integer> decimals;
    private boolean isPositive;

    public AFloat(){
        this.intnums = new ArrayList<>();
        this.decimals = new ArrayList<>();
        this.isPositive = true;
    }

    //String to AFloat object
    public AFloat(String s){
        this.intnums = new ArrayList<>();
        this.decimals = new ArrayList<>();

        s = s.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty");
        }

        if(s.charAt(0) == '-'){
            this.isPositive = false;
            s = s.substring(1);
        }else{
            this.isPositive = true;
        }

        int i = 0;
        while(i < s.length() && s.charAt(i) != '.'){
            this.intnums.add(s.charAt(i) - '0');
            i++;
        }
        for(int j = i+1; j < s.length(); j++){
            this.decimals.add(s.charAt(j) - '0');
        }

        int last = this.decimals.size() - 1;
        while (last >= 0 && this.decimals.get(last) == 0) {
            this.decimals.remove(last);
            last--;
        }
        if(this.decimals.isEmpty()){
            this.decimals.add(0);
        }
    }

    //Copies an AFloat object into another new AFloat object
    public AFloat(AFloat Othernum){
        this.intnums = new ArrayList<>(Othernum.intnums);
        this.decimals = new ArrayList<>(Othernum.decimals);
        this.isPositive = Othernum.isPositive;
    }

    //Converts the AFloat object back to String
    public String toString(){
        StringBuilder sb = new StringBuilder();

        if (!this.isPositive) sb.append('-');

        for (int digit : this.intnums) {
        sb.append(digit);
        }
        sb.append('.');

        if (this.decimals.isEmpty()) sb.append('0');
        else for (int digit : this.decimals) sb.append(digit);
        return sb.toString();
    }

    //Compares to AFloat objects, returns 1 if first greater, 0 if equal, -1 if first is smaller
    public int compare_nums(AFloat num){
        if(this.intnums.size() > num.intnums.size()){
            return 1;
        }
        else if(this.intnums.size() < num.intnums.size()){
            return -1;
        }
        else{
            for(int i = 0; i< this.intnums.size(); i++){
                if(this.intnums.get(i) > num.intnums.get(i)){
                    return 1;
                }
                else if(this.intnums.get(i) < num.intnums.get(i)){
                    return -1;
                }
                else{
                    continue;
                }
            }
            AFloat smaller = (this.decimals.size() < num.decimals.size())? this : num;
            AFloat larger = (this.decimals.size() > num.decimals.size())? this : num;

            for(int i = 0; i< smaller.decimals.size(); i++){
                if(this.decimals.get(i) > num.decimals.get(i)){
                    return 1;
                }
                else if(this.decimals.get(i) < num.decimals.get(i)){
                    return -1;
                }
                else{
                    continue;
                }
            }
            if(larger.decimals.size() > smaller.decimals.size()){
                return (larger == this)? 1 : -1;
            }
            return 0;
        }
    }

    //Adds two AFloat objects using digit-wise addition.
    public AFloat add(AFloat num){
        int carry = 0;
        AFloat answer = new AFloat();
        answer.intnums.clear();
        answer.decimals.clear();

        if(this.isPositive == num.isPositive){
            answer.isPositive = this.isPositive;
        }
        else{
            boolean temp = (this.compare_nums(num) > 0)? this.isPositive : num.isPositive;

            AFloat a = new AFloat(this);
            a.isPositive = true;
            AFloat b = new AFloat(num);
            b.isPositive = true;
            answer = a.sub(b);
            answer.isPositive = temp;
            return answer;
        }

        AFloat smallint = (this.compare_nums(num) < 0)? this : num;
        AFloat bigint = (this.compare_nums(num) > 0)? this : num;

        AFloat longdec = (this.decimals.size() >= num.decimals.size())? this : num;
        AFloat shortdec = (this.decimals.size() < num.decimals.size())? this : num;

        for(int i = longdec.decimals.size()-1; i >= shortdec.decimals.size(); i--){
            answer.decimals.add(0, longdec.decimals.get(i));
        }

        for(int i = shortdec.decimals.size() - 1; i >= 0; i--){
            int temp = longdec.decimals.get(i) + shortdec.decimals.get(i) + carry;
            answer.decimals.add(0, temp%10);
            carry = temp/10;
        }

        for(int i = bigint.intnums.size()-1, j = smallint.intnums.size()-1; i >= 0; i--, j--){
            int sum = (j >= 0)? bigint.intnums.get(i) + smallint.intnums.get(j) + carry : bigint.intnums.get(i) + carry;
            carry = sum/10;
            answer.intnums.add(0, sum%10);
        }

        if(carry != 0){
            answer.intnums.add(0, carry);
        }

        if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
            answer.isPositive = true;
        }

        return answer;
    }

    //Subtracts two AFloat objects using digit-wise subtraction.
    public AFloat sub(AFloat num){
        int borrow = 0;
        AFloat answer = new AFloat();
        answer.intnums.clear();
        answer.decimals.clear();

        if(this.isPositive == num.isPositive){
            answer.isPositive = (this.compare_nums(num) < 0)? !num.isPositive : num.isPositive;
        }else{
            boolean temp = this.isPositive;
            AFloat a = new AFloat(this);
            a.isPositive = true;
            AFloat b = new AFloat(num);
            b.isPositive = true;

            answer = a.add(b);
            answer.isPositive = temp;
            if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
                answer.isPositive = true;
            }
            return answer;
        }

        AFloat big = (this.compare_nums(num) >= 0)? this : num;
        AFloat small = (this.compare_nums(num) < 0)? this : num;

        int maxDec = Math.max(big.decimals.size(), small.decimals.size());
        for (int i = maxDec - 1; i >= 0; i--) {
            int a = (i < big.decimals.size()) ? big.decimals.get(i) : 0;
            int b = (i < small.decimals.size()) ? small.decimals.get(i) : 0;
            int diff = a - b - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else borrow = 0;
            answer.decimals.add(0, diff);
        }

        int i = big.intnums.size() - 1;
        int j = small.intnums.size() - 1;
        while (i >= 0 || j >= 0) {
            int a = (i >= 0) ? big.intnums.get(i) : 0;
            int b = (j >= 0) ? small.intnums.get(j) : 0;
            int diff = a - b - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else borrow = 0;
            answer.intnums.add(0, diff);
            i--; j--;
        }
        while (answer.intnums.size() > 1 && answer.intnums.get(0) == 0) {
            answer.intnums.remove(0);
        }
        if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
            answer.isPositive = true;
        }

        return answer;
    }

    //Multiplies two AFLoat objects using methods from AInteger class.
    public AFloat mult(AFloat num){
        AFloat answer = new AFloat();
        answer.intnums.clear();
        answer.decimals.clear();
        answer.isPositive = true;

        AInteger a = new AInteger();
        a.digits.addAll(this.intnums);
        a.digits.addAll(this.decimals);

        AInteger b = new AInteger();
        b.digits.addAll(num.intnums);
        b.digits.addAll(num.decimals);

        AInteger prod = new AInteger();
        prod = a.mult(b);

        int decCount = this.decimals.size() + num.decimals.size();
        int splitPoint = prod.digits.size() - decCount;
        if (splitPoint < 0) splitPoint = 0;
        answer.intnums = new ArrayList<>(prod.digits.subList(0, splitPoint));
        answer.decimals = new ArrayList<>(prod.digits.subList(splitPoint, prod.digits.size()));

        answer.isPositive = !(this.isPositive ^ num.isPositive);

        if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
            answer.isPositive = true;
        }

        return answer;
    }

    //Divides two AFLoat objects using methods from AInteger class.
    public AFloat div(AFloat num){
        AFloat result = new AFloat();
        result.intnums.clear(); 
        result.decimals.clear();

        ArrayList<Integer> aDigits = new ArrayList<>(this.intnums);
        if(!(this.decimals.size() == 1 && this.decimals.get(0) == 0)){
            aDigits.addAll(this.decimals);
        }

        ArrayList<Integer> bDigits = new ArrayList<>(num.intnums);
        if(!(num.decimals.size() == 1 && num.decimals.get(0) == 0)){
            bDigits.addAll(num.decimals);
        }

        AInteger num1 = new AInteger();
        num1.digits.addAll(aDigits);
        AInteger num2 = new AInteger();
        num2.digits.addAll(bDigits);

        AInteger zero = new AInteger("0");

        if (num2.compare_nums(zero) == 0) {
            throw new ArithmeticException("Division by Zero Error");
        }

        AInteger quo = new AInteger();

        int deccount = 0;

        if(!(this.decimals.size() == 1 && this.decimals.get(0) == 0) && !(num.decimals.size() == 1 && num.decimals.get(0) == 0)){
            deccount = this.decimals.size() - num.decimals.size();
        }else if((this.decimals.size() == 1 && this.decimals.get(0) == 0) && !(num.decimals.size() == 1 && num.decimals.get(0) == 0)){
            deccount = 0 - num.decimals.size();
        }else if((num.decimals.size() == 1 && num.decimals.get(0) == 0) && !(this.decimals.size() == 1 && this.decimals.get(0) == 0)){
            deccount = this.decimals.size();
        }else{
            deccount = 0;
        }

        for (int i = 0; i < 30; i++) {
            num1.digits.add(0);
        }
    
        quo = num1.div(num2);
    
        int intPartLength = quo.digits.size() - 30 - deccount;
        if (intPartLength < 0) intPartLength = 0;
    
        result.intnums = new ArrayList<>(quo.digits.subList(0, intPartLength));
        result.decimals = new ArrayList<>(quo.digits.subList(intPartLength, quo.digits.size()));
    
        if (result.intnums.isEmpty()) {
            result.intnums.add(0);
        }

        result.isPositive = !(this.isPositive ^ num.isPositive);
        return result;
    }

}