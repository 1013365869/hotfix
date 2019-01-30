package study.jll.com.library.utils;

import java.lang.reflect.Array;

/**
 * Created by jll on 2019/1/29.
 */

public class ArraryUtils {
   public static Object combineArray(Object arrayLhs,Object arrayRhs){
       //获取一个数组的class对象，通过array.new Instance()可以反射生成数组对象
       Class<?> localClass =arrayLhs.getClass().getComponentType();
       //前数组长度
       int i= Array.getLength(arrayLhs);
       //新数组总长度 = 前数组长度+后数组长度
       int j =i+Array.getLength(arrayRhs);
       //生成数组对象
       Object result =Array.newInstance(localClass,j);
       for (int k = 0; k < j; k++) {
           if (k<i){
               //从0开始遍历，如果前数组有值，添加到新数组的第一个位置
               Array.set(result,k,Array.get(arrayLhs,k));
           }else {
               //添加完前数组，再添加后数组，合并完成
               Array.set(result,k,Array.get(arrayRhs,k-i));
           }
       }
       return result;
   }
}

