package study.jll.com.library.utils;

import java.lang.reflect.Field;

/**
 * Created by jll on 2019/1/29.
 */

public class ReflectUtils {

    /**
     * 通过反射获取某对象，并使用私有可访问
     * @param obj 该属性所属类的对象
     * @param clazz 改属性所属类
     * @param field 属性名
     * @return 该属性对象
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object obj,Class<?> clazz,String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField =clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 给某属性复制
     * @param obj
     * @param clazz
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void  setField (Object obj,Class<?> clazz,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field loaclField =clazz.getDeclaredField("dexElements");
        loaclField.setAccessible(true);
        loaclField.set(obj,value);
    }

    /**
     * 通过反射获取baseDexClassLoader对象中的pathlist对象
     * @param baseDexClassLoader
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getPathList(Object baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    /**
     * 通过反射获取baseDexClassLoader对象中的pathlist对象,在获取dexElements对象
     * @param paramObject
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getDexElements(Object paramObject) throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }
}
