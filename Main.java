// Main.java — Students version

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

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
        if (month <= 11 && month >= 0) {
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
            String returnstring = maxProfitCom + " " + (Integer.toString(most));
            return returnstring;
        } else {
            return "INVALID_MONTH";
        }
    }

    public static int totalProfitOnDay(int month, int day) {
        int totalProfit = 0;
        if (0 > month || 11 < month || 1 > day || 28 < day) {
            return -99999;
        }
        for (int i = 0; i < COMMS; i++) {
            totalProfit += profits[month][day - 1][i];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        Boolean isComm = false;
        int comNumber = 0;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i] == commodity) {
                isComm = true;
                comNumber = i;
            }
        }
        if (from > to || from > 28 || from < 1 || to > 28 || to < 1 || isComm == false) {
            return -99999;
        }

        int profitinrange = 0;
        for(int j = 0; j<MONTHS; j++) {
            for (int i = from - 1; i <= to - 1; i++) {
                profitinrange += profits[j][i][comNumber];
            }
        }
        return profitinrange;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month > 11) {
            return -1;
        }
        int[] dayProfit = new int[DAYS];
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < COMMS; j++) {
                dayProfit[i] += profits[month][i][j];
            }
        }
        int most = dayProfit[0];
        int dayNumber = 0;
        for (int i = 0; i < dayProfit.length; i++) {
            if (most < dayProfit[i]) {
                most = dayProfit[i];
                dayNumber = i + 1;
            }
        }
        return dayNumber;
    }

    public static String bestMonthForCommodity(String comm) {
        Boolean isComm = false;
        int comNumber = 0;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i] == comm) {
                isComm = true;
                comNumber = i;
            }
        }
        int[] allMonths = new int[MONTHS];
        if (isComm == false) {
            return "INVALID_COMMODITY";
        }
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                allMonths[i] += profits[i][j][comNumber];
            }
        }
        int mostMonth = allMonths[0];
        String monthNumber = months[0];
        for(int i = 0; i<months.length; i++)
            if (mostMonth<allMonths[i]){
                mostMonth = allMonths[i];
                monthNumber = months[i];
            }
        return monthNumber;
    }

    public static int consecutiveLossDays(String comm) {
        Boolean isComm = false;
        int comNumber = 0;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i] == comm) {
                isComm = true;
                comNumber = i;
            }
        }
        if(isComm == false){
            return -1;
        }
        int maxStreak = 0;
        int currentStreak = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                if (profits[i][j][comNumber] < 0) {
                    currentStreak++;
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                }else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        Boolean isComm = false;
        int comNumber = 0;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i] == comm) {
                isComm = true;
                comNumber = i;
            }
        }
        if(isComm == false){
            return -1;
        }
        int moreThreshold = 0;
        for(int i = 0; i<MONTHS; i++){
            for(int j = 0; j<DAYS; j++){
                if (profits[i][j][comNumber]>threshold){
                    moreThreshold++;
                }
            }
        }
        return moreThreshold;
    }

    public static int biggestDailySwing(int month) {
        if(month<0 || month>11){
            return -99999;
        }
        int[] dayProfit = new int[DAYS];
        for(int i = 0; i<DAYS; i++){
            for(int j = 0; j<COMMS; j++){
                dayProfit[i] += profits[month][i][j];
            }
        }
        int maxSwing = 0;
        for (int i = 1; i < DAYS; i++) {
            int diff = dayProfit[i] - dayProfit[i - 1];
            if (diff < 0) {
                diff = diff * -1;
            }
            if (diff > maxSwing) {
                maxSwing = diff;
            }
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int c1No = -1;
        int c2No = -1;
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i] == c1) {
                c1No = i;
            }
            if (commodities[i] == c2) {
                c2No = i;
            }
        }
        if (c1No == -1 || c2No == -1) {
            return "INVALID_COMMODITY";
        }
        int totalProfit1 = 0;
        int totalProfit2 = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                totalProfit1 += profits[i][j][c1No];
                totalProfit2 += profits[i][j][c2No];
            }
        }
        if (totalProfit1 > totalProfit2) {
            int diff = totalProfit1 - totalProfit2;
            return c1 + " is better by " + diff;
        } else if (totalProfit2 > totalProfit1) {
            int diff = totalProfit2 - totalProfit1;
            return c2 + " is better by " + diff;
        } else {
            return "Equal";
        }
    }

    public static String bestWeekOfMonth(int month) {
        if(month<0 || month>11){
            return "INVALID_MONTH";
        }
        int[] weekProfits = new int[4];
        for (int week = 0; week < 4; week++) {
            int startDay = week * 7;
            int endDay = startDay + 7;
            int currentWeekTotal = 0;
            for (int day = startDay; day < endDay; day++) {
                for (int comm = 0; comm < COMMS; comm++) {
                    currentWeekTotal += profits[month][day][comm];
                }
            }
            weekProfits[week] = currentWeekTotal;
        }
        int maxProfit = weekProfits[0];
        int bestWeek = 0;
        for (int i = 1; i < 4; i++) {
            if (weekProfits[i] > maxProfit) {
                maxProfit = weekProfits[i];
                bestWeek = i;
            }
        }
        return "Week " + (bestWeek + 1);
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        String a = mostProfitableCommodityInMonth(0);
        System.out.println(a);
        int b = totalProfitOnDay(0, 1);
        System.out.println(b);
        int c = commodityProfitInRange("Gold", 1, 1);
        System.out.println(c);
        int d = bestDayOfMonth(0);
        System.out.println(d);
        String e = bestMonthForCommodity("Gold");
        System.out.println(e);
        int f = consecutiveLossDays("Gold");
        System.out.println(f);
        int g = daysAboveThreshold("Gold" , 0);
        System.out.println(g);
        int h = biggestDailySwing(0);
        System.out.println(h);
        String i = compareTwoCommodities("Copper" , "Gold");
        System.out.println(i);
        String k = bestWeekOfMonth(0);
        System.out.println(k);
    }
}