package com.kaicom.api.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

/**
 * json工具类
 * @author scj
 * 
 */
public class JsonUtils {

	public static String toJson(Object obj) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String json = gson.toJson(obj);
		return json;
	}

	/**
	 * When create json string, the null field will not include in string
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonIgnoreNull(Object obj) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		return json;
	}

	/**
	 * Object to json string. Could ignore some fields
	 * 
	 * @param obj
	 * @param ignores
	 *            Ignore fields
	 * @return Json String
	 */
	public static String toJson(Object obj, String... ignores) {
		IgnoreStrategy ignoreStratege = new IgnoreStrategy(ignores);
		Gson gson = new GsonBuilder().serializeNulls()
				.setExclusionStrategies(ignoreStratege).create();
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {

		Gson gson = new Gson();

		T result = gson.fromJson(json, classOfT);

		return result;
	}
	
	/**
	 * 
	 * @param json
	 *            the string from which the object is to be deserialized
	 * @param typeOfT
	 *            The specific genericized type of src. You can obtain this type
	 *            by using the {@link com.zltd.utils.JsonUtils.TypeProxy} class.
	 *            For example, to get the type for {@code Collection<Foo>}, you
	 *            should use:
	 * 
	 *            <pre>
	 * Type typeOfT = new TypeProxy&lt;Collection&lt;Foo&gt;&gt;() {
	 * }.getType();
	 * </pre>
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfT);
	}

	private static class IgnoreStrategy implements ExclusionStrategy {

		private String[] mIgnoreFields;

		IgnoreStrategy(String... ignores) {
			mIgnoreFields = ignores;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			for (String filed : mIgnoreFields) {
				Log.d("jsonutis", f.getName());
				if (filed.equals(f.getName())) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {

			return false;
		}

	}

	public static class TypeProxy<T> {
		final Type type;

		public TypeProxy() {
			type = getSuperclassTypeParameter(getClass());
		}

		public Type getType() {
			return type;
		}

		Type getSuperclassTypeParameter(Class<?> subclass) {
			Type superclass = subclass.getGenericSuperclass();
			if (superclass instanceof Class) {
				throw new RuntimeException("Missing type parameter.");
			}
			ParameterizedType parameterized = (ParameterizedType) superclass;
			return $Gson$Types.canonicalize(parameterized
					.getActualTypeArguments()[0]);
		}
	}
}
