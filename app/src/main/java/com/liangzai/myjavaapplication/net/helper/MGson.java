package com.liangzai.myjavaapplication.net.helper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;

import java.lang.reflect.Field;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2019-12-23
 * 描述:
 *  * 1）MGson.newGson()得到Gson对象
 *  * 2)然后使用相对应的gson方法即可
 *  * 解决:
 *  * 1)要{}后端给[]返回实例对象
 *  * 2)要[]后端给{}返回空数组
 *  * 3)要int.class, Integer.class,short.class, Short.class,long.class, Long.class,double.class, Double.class,
 *  * float.class, Float.class后端给的非数字类型返回0
 *  * 4)要String后端给了[],{}等类型返回""
 */
public class MGson {

    /**
     * 生成注册自定义的对象处理器与集合处理器的Gson，方法
     *
     * @return
     */
    public static Gson newGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Class builder = gsonBuilder.getClass();
        Field f;
        try {
            //通过反射得到构造器
            f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            final java.util.Map<java.lang.reflect.Type, InstanceCreator<?>> val = (java.util.Map<java.lang.reflect.Type, InstanceCreator<?>>) f.get(gsonBuilder);//得到此属性的值
            //注册String类型处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, GsonTools.stringTypeAdapter()));
            //注册int.class, Integer.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, GsonTools.longAdapter(0)));
            //注册short.class, Short.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(short.class, Short.class, GsonTools.longAdapter(1)));
            //注册long.class, Long.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, GsonTools.longAdapter(2)));
            //注册double.class, Double.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, GsonTools.longAdapter(3)));
            //注册float.class, Float.class处理器
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, GsonTools.longAdapter(4)));
            //注册反射对象的处理器
            gsonBuilder.registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(new ConstructorConstructor(val), FieldNamingPolicy.IDENTITY, Excluder.DEFAULT));
            //注册集合的处理器
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonBuilder.create();
    }
}
