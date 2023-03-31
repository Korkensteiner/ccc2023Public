package GeneticDrift;

import java.util.*;

/**
 * Created: 27.01.2023
 *
 * @author: Hannes Koppensteiner (Hannes)
 */
public class lvl1 {
    public static Set<List<Integer>> getPairs(List<Integer> arr) {
        Set<List<Integer>> finalArr = new TreeSet<>(new ComparePairs());

        for (int i = 0; i < arr.size(); i++) {
            int z = arr.get(i);
            if (z < 0) {
                for (int j = 0; j < arr.size(); j++) {
                    int x = arr.get(j);
                    if (x >= 0 && Math.abs(Math.abs(x) - Math.abs(z)) == 1) {
                        List<Integer> innerArr = new ArrayList<>();
                        if (i < j) {
                            innerArr.add(z);
                            innerArr.add(i);
                            innerArr.add(x);
                            innerArr.add(j);
                        } else {
                            innerArr.add(x);
                            innerArr.add(j);
                            innerArr.add(z);
                            innerArr.add(i);
                        }
                        finalArr.add(innerArr);
                    }
                }
            }
        }
        return finalArr;
    }

    public static List<Integer> inverted(List<Integer> permutation, List<Integer> pair){
        List<Integer> subArr;
        List<Integer> finalizedArr;
        if(pair.get(0) + pair.get(2) == 1){
            subArr = new ArrayList<>(permutation.subList(pair.get(1), pair.get(3)));
            finalizedArr = new ArrayList<>(permutation.subList(0,pair.get(1)));

            subArr.replaceAll(x -> -x);
            Collections.reverse(subArr);
            finalizedArr.addAll(subArr);

            finalizedArr.addAll(new ArrayList<>(permutation.subList(pair.get(3), permutation.size())));
        } else {
            subArr = new ArrayList<>(permutation.subList(pair.get(1)+1, pair.get(3)+1));
            finalizedArr = new ArrayList<>(permutation.subList(0,pair.get(1)+1));

            subArr.replaceAll(x -> -x);
            Collections.reverse(subArr);
            finalizedArr.addAll(subArr);

            List<Integer> list = new ArrayList<>(permutation.subList(pair.get(3)+1, permutation.size()));
            finalizedArr.addAll(list);
        }
        return finalizedArr;
    }


    private static class ComparePairs implements Comparator<List<Integer>> {
        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            return o1.get(0).compareTo(o2.get(0));
        }
    }

    public static int punkteZahl(List<Integer> permutation, List<Integer> pair) {
        List<Integer> invertedArr = inverted(permutation, pair);
        Set<List<Integer>> pairArr = getPairs(invertedArr);
        return pairArr.size();
    }


    public static int efficientAlgorithm(List<Integer> permutation){
        int max = 0;
        List<Integer> maxPair = new ArrayList<>();
        int amount = 0;
        Set<List<Integer>> pairs = getPairs(permutation);
        while(pairs.size() != 0){
            System.out.println(permutation);
            pairs = getPairs(permutation);
            Iterator<List<Integer>> iterator = pairs.iterator();
            while(iterator.hasNext()) {
                List<Integer> pair = iterator.next();
                int score = punkteZahl(permutation, pair);
                if (score >= max) {
                    max = score;
                    maxPair = pair;
                }
            }
            permutation = inverted(permutation, maxPair);
            max = 0;
            amount++;
        }
        return amount;
    }

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        List<String> list2 = Arrays.asList("0 3 1 6 5 -2 4 7".split(" "));
        list2.forEach(s -> {
            list.add(Integer.parseInt(s));
        });

        System.out.println(efficientAlgorithm(list)-1);

    }
}
