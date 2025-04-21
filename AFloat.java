import java.util.ArrayList;
import java.util.Scanner;

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

    public int compare_nums(AInteger num){
        if(this.intnums.size() > num.intnums.size()){
            return 1;
        }
        else if(this.intnums.size() < num.intnums.size()){
            return -1
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
            if()
        }
    }
}
