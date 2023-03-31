package Bowling;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String test = "10:10,10,10,10,10,10,10,10,10,10,10,10";

        String[] split = test.split(":");
        int rounds = Integer.parseInt(split[0]);
        int getBonus = 0;

        split = split[1].split(",");
        ArrayList<ArrayList<Integer>> eachRoundScores = new ArrayList<>();
        int strikes = 0;

        for (int i = 0; i < split.length; i++) {
            if ((i+strikes) % 2 == 0) {
                ArrayList<Integer> round = new ArrayList<>();
                round.add(Integer.parseInt(split[i]));
                if (Integer.parseInt(split[i]) == 10) {
                    strikes++;
                    round.add(0);
                }
                eachRoundScores.add(round);
            } else {
                eachRoundScores.get((i+strikes) / 2).add(Integer.parseInt(split[i]));
            }
        }

        ArrayList<Integer> finalRoundScores = new ArrayList<>();
        System.out.println(eachRoundScores);
        boolean secondStrike = false;

        int index = 0;
        for (ArrayList<Integer> i : eachRoundScores) {
            if (getBonus > 0) {
                if (secondStrike) {
                    finalRoundScores.set(index-2, finalRoundScores.get(index-2)+i.get(0));
                    finalRoundScores.set(index-1, finalRoundScores.get(index-1)+i.get(0));
                    secondStrike = false;
                }

                if (getBonus > 1 && i.get(0) == 10) {
                    secondStrike = true;
                }

                int scoreRoundBefore = finalRoundScores.get(index - 1);

                for (int j = 0; j < getBonus; j++) {
                    scoreRoundBefore += i.get(j);
                }
                getBonus = 0;
                finalRoundScores.set(index - 1, scoreRoundBefore);
            }

            if(index == rounds) {
                if (secondStrike) {
                    finalRoundScores.set(index-1, finalRoundScores.get(index-1)+eachRoundScores.get(index+1).get(0));
                }
                break;
            }

            if (i.get(0) == 10) { //Strike
                getBonus = 2;
            } else if ((i.get(0) + i.get(1)) == 10) { //Spare
                getBonus = 1;
            }

            if (index != 0) {
                finalRoundScores.add((i.get(0) + i.get(1) + finalRoundScores.get(index - 1)));
            } else {
                finalRoundScores.add(i.get(0) + i.get(1));
            }
            index++;
        }
        System.out.println(finalRoundScores);
    }
}