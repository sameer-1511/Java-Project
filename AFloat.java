import java.util.ArrayList;
//import java.util.Scanner;

public class AFloat {
    private ArrayList<Integer> intnums;
    private ArrayList<Integer> decimals;
    private boolean isPositive;

    public AFloat(){
        this.intnums = new ArrayList<>();
        this.decimals = new ArrayList<>();
        this.isPositive = true;
    }

    public AFloat(String s){
        this.intnums = new ArrayList<>();
        this.decimals = new ArrayList<>();
        s = s.trim();

        if(s.charAt(0) == '-'){
            this.isPositive = false;
            s = s.substring(1);
        }else{
            this.isPositive = true;
        }

        int i = 0;
        while(s.charAt(i) != '.'){
            this.intnums.add(s.charAt(i) - '0');
            i++;
        }
        s.replaceAll("0+$", "");
        for(int j = this.intnums.size(); j < s.length(); j++){
            this.decimals.add(s.charAt(j) - '0');
        }
    }

    public AFloat(AFloat Othernum){
        this.intnums = new ArrayList<>(Othernum.intnums);
        this.decimals = new ArrayList<>(Othernum.decimals);
        this.isPositive = Othernum.isPositive;
    }

    public String toString(AFloat Num){
        String answer = "";

        for(int i = 0; i < this.intnums.size(); i++){
            answer = answer + this.intnums.get(i);
        }
        answer = answer + '.';
        for(int j = 0; j < this.decimals.size(); j++){
            answer = answer + this.decimals.get(j);
        }

        if(this.isPositive = false){
            answer = '-' + answer;
        }

        return answer;
    }

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

        AFloat longdec = (this.decimals.size() > num.decimals.size())? this : num;
        AFloat shortdec = (this.decimals.size() < num.decimals.size())? this : num;

        for(int i = longdec.decimals.size()-1; i >= shortdec.decimals.size(); i--){
            answer.decimals.add(0, longdec.decimals.get(i));
        }

        for(int i = shortdec.decimals.size() - 1; i >= 0; i--){
            int temp = longdec.decimals.get(i) + shortdec.decimals.get(i) + carry;
            answer.decimals.add(0, temp%10);
            carry = temp/10;
        }

        for(int i = bigint.intnums.size()-1, j = smallint.intnums.size()-1; i >= 0 || j >= 0; i--, j--){
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

        AFloat longdec = (this.decimals.size() > num.decimals.size())? this : num;
        AFloat shortdec = (this.decimals.size() < num.decimals.size())? this : num;

        for(int i = longdec.decimals.size()-1; i >= shortdec.decimals.size(); i--){
            answer.decimals.add(0, longdec.decimals.get(i));
        }

        for(int i = shortdec.decimals.size() - 1; i >= 0; i--){
            int temp = longdec.decimals.get(i) - shortdec.decimals.get(i) - borrow;
            borrow = ((longdec.decimals.get(i) - borrow) < shortdec.decimals.get(i))? 1 : 0;
            int diff = temp + 10*borrow;
            answer.decimals.add(0, diff);
        }

        AFloat smallint = (this.compare_nums(num) < 0)? this : num;
        AFloat bigint = (this.compare_nums(num) > 0)? this : num;

        for(int i = bigint.intnums.size()-1, j = smallint.intnums.size()-1; i>=0 || j>=0; i--, j--){
            if(j >= 0){
                int temp = bigint.intnums.get(i) - smallint.intnums.get(j) - borrow;
                borrow = ((bigint.intnums.get(i) - borrow) < smallint.intnums.get(j))? 1 : 0;
                int diff = temp + 10*borrow;
                answer.intnums.add(0, diff);
            }else{
                int diff = bigint.intnums.get(i) - borrow;
                borrow = 0;
                if(i != 0 || diff != 0){
                    answer.intnums.add(0, diff);
                }
            }
        }

        while (answer.intnums.size() > 1 && answer.intnums.get(0) == 0) {
            answer.intnums.remove(0);
        }
        if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
            answer.isPositive = true;
        }

        return answer;
    }

    public AFloat mult(AFloat num){
        AFloat answer = new AFloat();
        answer.intnums.clear();
        answer.decimals.clear();
        answer.isPositive = true;

        for(int i = this.intnums.size()+this.decimals.size()-1; i >= 0; i--){
            int carry = 0;
            AFloat prod = new AFloat();
            prod.isPositive = true;

            for (int k = 0; k < this.intnums.size() + this.decimals.size() - 1 - i; k++) {
                prod.intnums.add(0); 
            }

            ArrayList<Integer> dummy = (i < this.intnums.size())? this.intnums : this.decimals;
            int n = (i < this.intnums.size())? i : i - this.intnums.size();

            for(int a = num.intnums.size()+num.decimals.size()-1, b = num.decimals.size()-1; a >= 0 || b >=0; a--, b-- ){
                if(b >= 0){
                    int temp = dummy.get(n)*num.decimals.get(b) + carry;
                    carry = temp/10;
                    prod.intnums.add(0, temp%10);
                }
                else{
                    int temp = dummy.get(n)*num.intnums.get(b) + carry;
                    carry = temp/10;
                    prod.intnums.add(0, temp%10);
                }
            }

            if(carry != 0){
                prod.intnums.add(0, carry);
            }

            answer = answer.add(prod);
        }
        //answer.decimals = answer.intnums.subList((answer.intnums.size() - this.decimals.size()-num.decimals.size()), (answer.intnums.size()-1));
        //answer.intnums = answer.intnums.subList(0, (answer.intnums.size()-answer.decimals.size()-1));

        int decCount = this.decimals.size() + num.decimals.size();
        int splitPoint = answer.intnums.size() - decCount;

        if (splitPoint < 0) splitPoint = 0;
        answer.decimals = new ArrayList<>(answer.intnums.subList(splitPoint, answer.intnums.size()));
        answer.intnums = new ArrayList<>(answer.intnums.subList(0, splitPoint));

        if((answer.intnums.size() == 1 && answer.intnums.get(0) == 0) && (answer.decimals.size() == 1 && answer.decimals.get(0) == 0)){
            answer.isPositive = true;
        }

        return answer;
    }

    public AFloat div(AFloat num){

        
    }

}
