package br.com.true_promotion;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arr[][] = new int[6][6];
        for(int arr_i=0; arr_i < 6; arr_i++){
            for(int arr_j=0; arr_j < 6; arr_j++){
                arr[arr_i][arr_j] = in.nextInt();
            }
        }
        int sum = 0;
        for(int line = 0; line < 4; line ++){
            int sumAux = 0;
            sumAux = Solution.findHourGlasses(arr,line);
            if(sumAux > sum){
                sum = sumAux;
            }
        }
        
        System.out.println(sum);
        
    }
    
    public static int findHourGlasses(int array[][], int lineStart){
        int sum = 0;
        for(int i=0; i<4; i++){
            int sumAux = 0;
            sumAux = Solution.getHourGlasses(i,i+1,i,array, lineStart);
            if(sumAux > sum){
                sum = sumAux;
            }
        }
        return sum;
    }
    
    public static int getHourGlasses(int top, int middle, int bottom,int array[][], int lineStart){
        
        int sum = 0;
        
        for(int i = top; i<top+3; i++){
            sum = sum + array[lineStart][i];
        }
        
        for(int i = middle; i<middle+1; i++){
            sum = sum + array[lineStart+1][i];
        }
        
        for(int i = bottom; i<bottom+3; i++){
            sum = sum + array[lineStart+2][i];
        }
        
        return sum;
    }
}
