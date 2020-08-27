package com.motor.common.utils;

import com.motor.common.paging.PageList;
import org.dozer.DozerBeanMapper;

import java.io.*;
import java.util.*;

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
}
