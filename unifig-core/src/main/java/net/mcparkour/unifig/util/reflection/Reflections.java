/*
 * MIT License
 *
 * Copyright (c) 2019 MCParkour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mcparkour.unifig.util.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import sun.reflect.ReflectionFactory;

public final class Reflections {

	@SuppressWarnings("UseOfSunClasses")
	private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();
	private static final Constructor<Object> OBJECT_CONSTRUCTOR = getConstructor(Object.class);

	private Reflections() {
		throw new UnsupportedOperationException("Cannot create instance of this class");
	}

	public static List<Class<?>> getGenericTypes(Field field) {
		Type type = field.getGenericType();
		if (!(type instanceof ParameterizedType)) {
			throw new IllegalArgumentException("Field generic type is not ParametrizedType");
		}
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		return Arrays.stream(typeArguments)
			.map(typeArgument -> (Class<?>) typeArgument)
			.collect(Collectors.toUnmodifiableList());
	}

	public static boolean isStatic(Member member) {
		int modifiers = member.getModifiers();
		return Modifier.isStatic(modifiers);
	}

	public static <T extends AccessibleObject> T accessible(T accessible) {
		accessible.trySetAccessible();
		return accessible;
	}

	public static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	public static Field getField(Class<?> fieldClass, String fieldName) {
		try {
			Field field = fieldClass.getDeclaredField(fieldName);
			field.trySetAccessible();
			return field;
		} catch (NoSuchFieldException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	@Nullable
	public static Object getFieldValue(Field field, @Nullable Object instance) {
		try {
			return field.get(instance);
		} catch (IllegalAccessException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	public static void setFieldValue(Field field, @Nullable Object instance, @Nullable Object value) {
		try {
			field.set(instance, value);
		} catch (IllegalAccessException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	public static Method getMethod(Class<?> methodClass, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = methodClass.getDeclaredMethod(methodName, parameterTypes);
			method.trySetAccessible();
			return method;
		} catch (NoSuchMethodException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	public static Object invokeMethod(Method method, @Nullable Object instance, Object... parameters) throws Throwable {
		try {
			return method.invoke(instance, parameters);
		} catch (IllegalAccessException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		} catch (InvocationTargetException exception) {
			throw exception.getCause();
		}
	}

	public static <T> Constructor<T> getConstructor(Class<T> constructorClass, Class<?>... parameterTypes) {
		try {
			Constructor<T> constructor = constructorClass.getDeclaredConstructor(parameterTypes);
			constructor.trySetAccessible();
			return constructor;
		} catch (NoSuchMethodException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getSerializationConstructor(Class<T> constructorClass) {
		Constructor<?> constructor = REFLECTION_FACTORY.newConstructorForSerialization(constructorClass, OBJECT_CONSTRUCTOR);
		return (Constructor<T>) constructor;
	}

	public static <T> T newInstance(Constructor<T> constructor, Object... parameters) {
		try {
			return constructor.newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
			throw new UncheckedReflectiveOperationException(exception);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(Class<T> interfaceClass, InvocationHandler handler) {
		ClassLoader interfaceClassLoader = interfaceClass.getClassLoader();
		Class<?>[] interfaceClassArray = new Class[] {interfaceClass};
		Object proxyObject = Proxy.newProxyInstance(interfaceClassLoader, interfaceClassArray, handler);
		return (T) proxyObject;
	}
}
