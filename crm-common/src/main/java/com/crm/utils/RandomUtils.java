package com.crm.utils;

public class RandomUtils {

    public static Integer generateNumber(Integer numberWei)
    {
       /* Random rand =new Random(System.currentTimeMillis());
        int i = rand.nextInt(maxNumber);
        return  i;*/

        int base = (int)Math.pow(10,(numberWei-1));
        //System.out.println("base:" + base);

        int nRet = (int)((Math.random()*9+1)*base);
        System.out.println("nRet:" + nRet);

        return  nRet;

    }
}
