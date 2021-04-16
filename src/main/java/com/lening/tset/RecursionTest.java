package com.lening.tset;
/**
 * 创建时间: 2021-04-14 8:56
 * 机关单位: 乐柠教育
 */

import org.junit.Test;

/**
 * 创建时间: 2021-04-14 8:56 
 * IT操作员: 陈港星
 */
public class RecursionTest {
    /**
     *  计算阶乘  factorial
     *  极限是8103   8104就溢出
     * @param x
     * @return
     */
    public Long factorial(Long x){
        if(x==1){
            return 1L;
        }
        return x*factorial(x-1);
    }
    @Test
    public void test1(){
        Long factorial = factorial(8103L);
        System.out.println(factorial);
    }

    /**
     * 费事数列
     * 1  1  2  3  5  8  13  21  34  55
     * @param x
     * @return
     */
    public Long getFeiShi(Long x){
        if(x==1 || x==2){
            return 1L;
        }
        return getFeiShi(x-1)+getFeiShi(x-2);
    }
    @Test
    public void test2(){
        Long feiShi = getFeiShi(4L);
        System.out.println(feiShi);
    }
}
