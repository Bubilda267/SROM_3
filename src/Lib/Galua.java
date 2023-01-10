package Lib;

import java.math.BigInteger;
import java.util.*;

public class Galua {
    private static final ArrayList<Integer> GENERATOR = new ArrayList<>(Arrays.asList(239, 15, 2, 1, 0));
    private static final int DIMENSION = 240;

    public static ArrayList<Integer> UnitsArrToArrBinVec(ArrayList<Integer> elem){ // [1,2] -> [0,1,1,0]
        ArrayList<Integer> finishedElem = new ArrayList<>(240);
        for (int i = DIMENSION - 1; i >= 0; i--) {
            if(elem.contains(i)){
                finishedElem.add(1);
            }
            else{
                finishedElem.add(0);
            }
        }
        return finishedElem;
    }

    public static ArrayList<Integer> ArrBinVecToUnitsArr(int[] elem){ // [0,0,1,1] -> [1,0]
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < DIMENSION; i++) {
            if(elem[i] == 1){
                res.add(DIMENSION-1-i);
            }
        }
        return res;
    }

    public static ArrayList<Integer> StrBinVecToUnitsArr(String elem){ // 0011 -> [1,0]
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < DIMENSION; i++){
            if(elem.charAt(i) == '1'){
                res.add(DIMENSION-1-i);
            }
        }
        return res;
    }

    public static String UnitsArrToStrBin(ArrayList<Integer> elem){ //[1,0] -> 0011
        StringBuilder res = new StringBuilder(240);
        for (int i = DIMENSION - 1; i >= 0; i--){
            if(elem.contains(i)){
                res.append("1");
            }
            else{
                res.append("0");
            }
        }
        return res.toString();
    }
    public static ArrayList<Integer> mod(ArrayList<Integer> elem){
        if(elem.get(0) > GENERATOR.get(0)){ //check difference between leader term of specific polynomial and generator
            ArrayList<Integer> TempGen = new ArrayList<>();
            int diff = elem.get(0) - GENERATOR.get(0);
            for (int i: GENERATOR) {
                TempGen.add(i+diff); // added this difference to all powers of generator`s elements
            }
            elem = add(TempGen,elem); // add = subtract in characteristics 2
            return mod(elem); // recursion until 43 line is false
        }
        else{
            return elem;
        }
    }

    // adding in characteristics 2 can describe as merging 2 arrays and delete duplicates
    public static ArrayList<Integer> add(ArrayList<Integer> elem1, ArrayList<Integer> elem2){
        ArrayList<Integer> res = new ArrayList<>(elem1);
        res.addAll(elem2);
        res = mod2(res);
        res.sort(Collections.reverseOrder());
        return res;
    }

    //multiplying in characteristics 2 can describe as usual multiplying with mod 2 and after that mod generator
    public static ArrayList<Integer> multiply(ArrayList<Integer> elem1, ArrayList<Integer> elem2) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i: elem1) {
            for (int k: elem2) {
                res.add(i+k);
            }
        }
        ArrayList<Integer> sortedRes = new ArrayList<>(mod2(res));
        sortedRes.sort(Collections.reverseOrder());

        return mod(sortedRes);
    }

    private static ArrayList<Integer> MultiplyWithoutMod(ArrayList<Integer> elem1, ArrayList<Integer> elem2){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i: elem1) {
            for (int k: elem2) {
                res.add(i+k);
            }
        }
        return new ArrayList<>(mod2(res));
    }

    private static ArrayList<Integer> mod2(ArrayList<Integer> elem){
        Set<Integer> setElem = new HashSet<>(elem);
        setElem.removeIf(integer -> Collections.frequency(elem, integer) % 2 == 0);

        return new ArrayList<>(setElem);
    }

    public static ArrayList<Integer> exponentiate(ArrayList<Integer> elem, BigInteger power){
        ArrayList<BigInteger> res = new ArrayList<>();
        if(power.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
            if(elem.size()%2==0){
                for (int i:elem) {
                    res.add(power.multiply(BigInteger.valueOf(i)));
                }
            }
        }
        return elem; //!!!
    }

}

