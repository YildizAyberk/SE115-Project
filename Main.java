// Main.java — Students version

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Random;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    static int[][][] profits = new int[MONTHS][DAYS][COMMS];


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner reader = null;
        for (int i = 0; i < months.length; i++) {
            try {
                reader = new Scanner(Paths.get("Data_Files", months[i] + ".txt"));
                reader.nextLine();
                for (int day = 0; day < DAYS; day++) {
                    for (int comm = 0; comm < COMMS; comm++) {
                        String line = reader.nextLine();
                        String[] parts = line.split(",");
                        String value = parts[2];
                        int valueInt = Integer.parseInt(value);
                        profits[i][day][comm] = valueInt;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }


    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month <=11 && month >=0) {
            int gold = 0;
            int oil = 0;
            int silver = 0;
            int wheat = 0;
            int copper = 0;
            for (int i = 0; i < DAYS; i++) {
                gold += profits[month][i][0];
                oil += profits[month][i][1];
                silver += profits[month][i][2];
                wheat += profits[month][i][3];
                copper += profits[month][i][4];
            }
            int[] a = {gold, oil, silver, wheat, copper};
            int most = a[0];
            String maxProfitCom = commodities[0];
            for (int i = 0; i < a.length; i++) {
                if (a[i] > most) {
                    most = a[i];
                    maxProfitCom = commodities[i];
                }
            }
            String returnstring = maxProfitCom + ", " + (Integer.toString(most));
            return returnstring;
        }
        else{
            return "INVALID_MONTH";
        }
    }

    public static int totalProfitOnDay(int month, int day) {
        int totalProfit = 0;
        if(0>month || 11<month || 1>day || 28<day){
            return -99999;
        }
        for(int i = 0; i<COMMS;i++){
                totalProfit += profits[month][day-1][i];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        Random rn = new Random();
        Boolean isComm = false ;
        int comNumber = 0;
        for(int i = 0; i<COMMS; i++){
            if( commodities[i] == commodity){
                isComm=true;
                comNumber = i;
            }
        }
        if(from>to || from>28 || from<1 || to>28 || to<1 || isComm==false){
            return -99999;
        }

        int profitinrange= 0;
        for(int i = from-1; i<=to-1; i++){
            profitinrange += profits[rn.nextInt(11)][i][comNumber];
        }
        return profitinrange;
    }

    public static int bestDayOfMonth(int month) {
        if(month<0 || month>11){
            return -1;
        }
        int[] dayProfit = new int[DAYS];
        for(int i = 0; i<DAYS; i++){
            for(int j = 0; j<COMMS; j++) {
                dayProfit[i] += profits[month][i][j];
            }
        }
        int most = dayProfit[0];
        int dayNumber = 0;
        for (int i = 0; i<dayProfit.length; i++){
            if(most<dayProfit[i]){
                most = dayProfit[i];
                dayNumber = i + 1;
            }
        }
        return dayNumber;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        String a = mostProfitableCommodityInMonth(0);
        System.out.println(a);
        int b = totalProfitOnDay(0,1);
        System.out.println(b);
        int c = commodityProfitInRange("Gold", 1, 1);
        System.out.println(c);
        int d = bestDayOfMonth(0);
        System.out.println(d);
    }
}