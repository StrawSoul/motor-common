package com.motor.common.utils;

import com.motor.common.paging.PageList;
import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * 实现各层VO-Entiy-Model之间的复制
 * @Author: zlj
 * @Description:
 */


public class BeanMapperUtil {

	private static DozerBeanMapper dozer = new DozerBeanMapper(Arrays.asList("META-INF/dozer-date.xml"));



	public static <T> T map(Object sourceObject, Class<T> destObjectclazz) {
		return sourceObject == null ? null : dozer.map(sourceObject, destObjectclazz);
	}

	public static <T, S> List<T> mapList(Collection<S> sourceList, Class<T> destObjectclazz) {
		if (sourceList == null) {
			return null;
		}
		List<T> destinationList = new ArrayList<T>();
		for (Iterator<S> it = sourceList.iterator(); it.hasNext();) {
			destinationList.add(map(it.next(), destObjectclazz));
		}
		return destinationList;
	}

	public static <T, S> PageList<T> mapList(PageList<S> sourceList, Class<T> destObjectclazz) {
		if (sourceList == null || sourceList.getData()==null) {
			return null;
		}
		List<T> dataList = new ArrayList<>();
		PageList<T> destinationList = new PageList<>(sourceList.getPaging(),dataList);
	
		for (Iterator<? extends S> it = sourceList.getData().iterator(); it.hasNext();) {
			destinationList.getData().add(map(it.next(), destObjectclazz));
		}
		return destinationList;
	}

	public static  <T extends Map> T cloneMap(T map){
		if(map instanceof Serializable){
			Serializable newMap = (Serializable) map;
			return (T)cloneObj(newMap);
		}
		throw new RuntimeException("object must be implements Serializable to clone");
	}
	public static  <T extends Serializable> T cloneObj(T obj){
		T anotherObj = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes = baos.toByteArray();

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

			ois = new ObjectInputStream(bais);
			anotherObj = (T)ois.readObject();
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} catch (StackOverflowError error) {
			error.printStackTrace();
		} finally {
			if (oos != null)
				try {
					oos.close();
				} catch (IOException localIOException3) {
				}
			if (ois != null)
				try {
					ois.close();
				} catch (IOException localIOException4) {
				}
		}
		return anotherObj;
	}

	public static String toStringForUrlParams(Object target) {
		StringBuffer params = new StringBuffer();
		forEachParams(target, (name,value)->params.append(name).append("=").append(value).append("&"));
		return params.toString();
	}
	public static Map<String, Object> toSimpleMap(Object target)   {
		Map<String,Object> params = new HashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		forEachParams(target, (name,value)->{
			if(value == null){
				return;
			}
			if(value instanceof Date){
				value = df.format(value);
			}
			params.put(name, value);
		});
		return params;
	}

	public static void  forEachParams(Object target, BiConsumer<String, Object> consumer){
		Method[] methods = target.getClass().getMethods();
		String methodName = null;
		for (Method method : methods) {
			methodName = method.getName();
			if ((methodName.startsWith("get") && method.getParameterCount() ==0)) {
				if(!"getClass".equals(methodName)){
					methodName = methodName.replace("get","");
					String c = methodName.substring(0,1);
					String name = methodName.replaceFirst(c, c.toLowerCase());
					try {
						consumer.accept(name, method.invoke(target));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}
	public static void fromMap(Object target, Map<String, ? extends Object> params) throws InvocationTargetException, IllegalAccessException {
		BeanUtils.populate(target, params);
	}
}
