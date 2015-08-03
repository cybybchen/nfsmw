package com.ea.eamobile.nfsmw.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 */
public class NumberUtil {

    private static final NumberUtil instance = new NumberUtil();
    private static final Random rand = new Random();

    private NumberUtil() {
    }

    public static NumberUtil getInstance() {
        return instance;
    }

    /**
	 * 
	 */
    public static final int TRUE = 1;
    /**
	 * 
	 */
    public static final int FALSE = 0;

    /**
	 * 
	 */
    public static int randomRate(int[] rate, int[] returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue[i];
            }
        }
        return 0;
    }

    public static int randomRate(Integer[] rate, Integer[] returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue[i];
            }
        }
        return 0;
    }

    /**
     * 
     * 
     * @param map
     *            <value,ratio>
     * @return
     */
    public static int randomRate(Map<Integer, Integer> map) {
        Integer[] temp = new Integer[map.size()];
        return randomRate(map.values().toArray(temp), map.keySet().toArray(temp));
    }

    public static int randomRate(List<Integer> rate, List<Integer> returnValue) {
        int total = 0;
        for (int i = 0; i < rate.size(); i++) {
            total += rate.get(i);
        }
        if(total>0){
            int t = rand.nextInt(total);
            for (int i = 0; i < rate.size(); i++) {
                t = t - rate.get(i);
                if (t < 0) {
                    return returnValue.get(i);
                }
            }
        }
        return 0;
    }

    /**
	 * 
	 */
    public static int randomAverageRate(int[] returnValue) {
        int rate[] = new int[returnValue.length];
        Arrays.fill(rate, 1);
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue[i];
            }
        }
        return 0;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public static int randomRate(int[] rate, int[][] returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }

        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                int[] subRate = new int[returnValue[i].length];
                Arrays.fill(subRate, 1);
                return randomRate(subRate, returnValue[i]);
            }
        }

        return 0;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public static <T> T randomRate(int[] rate, T[] returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);

        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue[i];
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public static <T> T randomRate(int[] rate, List<T> returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);

        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue.get(i);
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public static <T> T randomAverageRate(T[] returnValue) {
        int rate[] = new int[returnValue.length];
        Arrays.fill(rate, 1);

        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }

        int t = rand.nextInt(total);

        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return returnValue[i];
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public <T> T randomRate(int[] rate, T[][] returnValue) {
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }

        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                int[] subRate = new int[returnValue[i].length];
                Arrays.fill(subRate, 1);
                return randomRate(subRate, returnValue[i]);
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @param rate
     * @param returnValue
     * @return
     */
    public <T> T randomAverageRate(T[][] returnValue) {
        int rate[] = new int[returnValue.length];
        Arrays.fill(rate, 1);

        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }

        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                int[] subRate = new int[returnValue[i].length];
                Arrays.fill(subRate, 1);
                return randomRate(subRate, returnValue[i]);
            }
        }

        return null;
    }

    public static int randomRange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public static int randomNumber(int x) {
        return rand.nextInt(x);
    }

    public static double randomDouble(){
        return rand.nextDouble();
    }
    
    public static boolean isRandomHit(int x) {
        return rand.nextInt(100) + 1 < x;
    }
    
    public static <T>T randomList(List<T> list){
        T result=null;
        if(list!=null&&list.size()>0){
            result=(T) list.get(randomNumber(list.size()));
        }
        return result;
        
    }
    
}
