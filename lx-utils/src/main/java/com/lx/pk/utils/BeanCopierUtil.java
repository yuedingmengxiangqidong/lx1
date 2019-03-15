package com.lx.pk.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 */
public class BeanCopierUtil extends BeanUtils{
	private static Logger logger = LoggerFactory.getLogger(BeanCopierUtil.class);

	/**
	 * convert input object to an object of output type
	 * no validation in side, so please make sure all arguments are ok
	 * before invoke this method
	 * @param inputObj
	 * @param outputType
	 * @return
	 */
	public static <I, O> O convert(I inputObj, Class<O> outputType) {
		try {
			if(inputObj == null){
				return null;
			}
			O k = outputType.newInstance();
			BeanUtils.copyProperties(inputObj, k);
			
//			BeanCopier copier = BeanCopier.create(inputObj.getClass(), outputType, false);
//			copier.copy(inputObj, k, null);
			return k;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * convert input list to a list of output type
	 * no validation in side, so please make sure all arguments are ok
	 * before invoke this method
	 * @param inputList
	 * @param outputType
	 * @return
	 */
	public static <I, O> List<O> convert(List<I> inputList, Class<O> outputType) {
		if(inputList == null){
			return null;
		}
		List<O> outputList = new ArrayList<>(inputList.size());
		for(I i: inputList){
			O convert = convert(i, outputType);
			outputList.add(convert);
		}
		return outputList;
	}
	
	/**
	 * convert input list to a list of output type
	 * no validation in side, so please make sure all arguments are ok
	 * before invoke this method
	 * @param inputMap
	 * @param outputType
	 * @return
	 */
	public static <I, O> Map<String, List<O>> convert(Map<String, List<I>> inputMap,  Class<O> outputType) {
		if(inputMap == null){
			return null;
		}
		Map<String, List<O>> map = new HashMap<String, List<O>>();
		Set<Entry<String, List<I>>> entrySet = inputMap.entrySet();
		for(Entry<String, List<I>> entry: entrySet){
			map.put(entry.getKey(), convert(entry.getValue(), outputType));
		}
		return map;
	}
	
	   public static <I, O> Map<Integer, O> convertMap(Map<Integer, I> inputMap,  Class<O> outputType) {
	        if(inputMap == null){
	            return null;
	        }
	        Map<Integer, O> map = new HashMap<Integer, O>();
	        Set<Entry<Integer, I>> entrySet = inputMap.entrySet();
	        for(Entry<Integer, I> entry: entrySet){
	            map.put(entry.getKey(), convert(entry.getValue(), outputType));
	        }
	        return map;
	    }

	/**
	 * 拷贝非空字段
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyNotNullProperties(Object source, Object target) throws BeansException {
		copyNotNullProperties(source, target, (Class) null, (String[]) null);
	}

	public static void copyNotNullProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyNotNullProperties(source, target, editable, (String[]) null);
	}

	public static void copyNotNullProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
		copyNotNullProperties(source, target, (Class) null, ignoreProperties);
	}


	/**
	 * 拷贝非空字段
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	private static void copyNotNullProperties(Object source, Object target,Class<?> editable, String... ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class actualEditable = target.getClass();
		if(editable != null) {
			if(!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
			}

			actualEditable = editable;
		}

		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List ignoreList = ignoreProperties != null?Arrays.asList(ignoreProperties):null;
		PropertyDescriptor[] var7 = targetPds;
		int var8 = targetPds.length;

		for(int var9 = 0; var9 < var8; ++var9) {
			PropertyDescriptor targetPd = var7[var9];
			Method writeMethod = targetPd.getWriteMethod();
			if(writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if(sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if(readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}

							Object ex = readMethod.invoke(source, new Object[0]);
							if(null != ex) {
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}

								writeMethod.invoke(target, new Object[]{ex});
							}
						} catch (Throwable var15) {
							throw new FatalBeanException("Could not copy property \'" + targetPd.getName() + "\' from source to target", var15);
						}
					}
				}
			}
		}

	}

}
