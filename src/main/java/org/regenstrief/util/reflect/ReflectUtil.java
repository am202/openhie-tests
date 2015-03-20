/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */
package org.regenstrief.util.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.regenstrief.util.Util;

/**
 * <p>
 * Title: Reflect Utility
 * </p>
 * <p>
 * Description: Utility methods for accessing Java Objects by reflection
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Regenstrief Institute
 * </p>
 * 
 * @author Andrew Martin
 * @version 1.0
 */
public class ReflectUtil {
    
    public final static Class<?>[] EMPTY_CLASS_ARRAY = {};
    
    public final static Object[] EMPTY_OBJECT_ARRAY = {};
    
    public final static Method[] EMPTY_METHOD_ARRAY = {};
    
    private final static Set<ClassLoader> classLoaders = new LinkedHashSet<ClassLoader>();
    
    //private final static Map<Class<?>, List<Method>> methodLists = new HashMap<Class<?>, List<Method>>();
    
    /**
     * Retrieves the Class for the given class name
     * 
     * @param className the class name
     * @return the Class
     **/
    public final static Class<?> forName(final String className) {
        Exception e = null;
        for (final ClassLoader classLoader : getClassLoaders()) {
            try {
                final Class<?> c = forName(className, classLoader);
                if (c != null) {
                    return c;
                }
            } catch (final Exception ex) {
                if (e == null) {
                    e = ex;
                }
            }
        }
        if (e == null) {
            return null;
        }
        throw Util.toRuntimeException(e);
    }
    
    private final static Class<?> forName(final String className, final ClassLoader classLoader) throws Exception {
        try {
            //return Class.forName(className);
            return Class.forName(className, false, classLoader);
        } catch (final Exception e) {
            // className might be like "org.package.Class.InnerClass".
            // Class.forName doesn't support that, so we'll support it here.
            int i = className.lastIndexOf('.');
            if (i < 0) {
                throw e;
            }
            // Attempt to get the "org.package.Class" Class.
            final Class<?> baseClass;
            try {
                baseClass = forName(className.substring(0, i));
            } catch (final Exception b) {
                /*
                https://tools.regenstrief.org/jira/browse/CORE-1029
                Don't just throw the Exception for the base class.
                That leads to messages like "ClassNotFoundException: org".
                */
                throw e;
            }
            final String innerClassName = className.substring(i + 1);
            // See if any of its inner classes match "org.package.Class.InnerClass".
            for (i = 0; i < 2; i++) {
                final Class<?>[] innerClasses = (i == 0) ? baseClass.getClasses() : baseClass.getDeclaredClasses();
                final Class<?> innerClass = getInnerClass(innerClasses, innerClassName);
                if (innerClass != null) {
                    return innerClass;
                }
            }
            throw e;
        }
    }
    
    private final static Class<?> getInnerClass(final Class<?>[] innerClasses, final String innerClassName) {
        final int size = Util.length(innerClasses);
        for (int i = 0; i < size; i++) {
            final Class<?> innerClass = innerClasses[i];
            if (innerClassName.equals(innerClass.getSimpleName())) {
                return innerClass;
            }
        }
        return null;
    }
    
    /**
     * Retrieves whether the Class with the given name is available
     * 
     * @param className the Class name
     * @return whether the Class is available
     **/
    public final static boolean isClassAvailable(final String className) {
        Class<?> clazz;
        
        try {
            clazz = forName(className);
        } catch (final Exception e) {
            clazz = null;
        }
        
        return clazz != null;
    }
    
    /**
     * Retrieves a new instance of the given class name
     * 
     * @param className the class name
     * @return the instance Object
     **/
    public final static Object newInstance(final String className) {
        return newInstance(forName(className));
    }
    
    /**
     * Retrieves a clone of the given Object
     * 
     * @param o the Object
     * @return the clone Object
     **/
    public final static Object clone(final Object o) {
        return invoke(o, getMethod(o, "clone", EMPTY_CLASS_ARRAY), EMPTY_OBJECT_ARRAY);
    }
    
    /**
     * Retrieves a new instance of the given Class
     * 
     * @param c the Class
     * @param <T> the Class type
     * @return the instance Object
     **/
    public final static <T> T newInstance(final Class<T> c) {
        final Constructor<T> constructor;
        
        try {
            //return c.newInstance(); // Is there a reason not to do this?  Does this fail for private constructors?
            constructor = c.getDeclaredConstructor(EMPTY_CLASS_ARRAY);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        
        return newInstance(constructor, EMPTY_OBJECT_ARRAY);
    }
    
    public final static <T> T newInstance(final Constructor<T> constructor, final Object... args) {
        try {
            constructor.setAccessible(true);
            
            return constructor.newInstance(args); // throws InvocationTargetException, IllegalAccessException
        } catch (final Exception e) {
            throw Util.toRuntimeException(e);
        }
    }
    
    public final static <T> T newPropertyInstance(final String key, final T defaultInstance) {
        final String value = Util.getProperty(key);
        if (value == null) {
            return defaultInstance;
        }
        return Util.cast(newInstance(value));
    }
    
    /**
     * Retrieves a new array of the given component Class
     * 
     * @param c the component Class
     * @param length the array length
     * @param <E> the element type
     * @return the array
     **/
    public final static <E> E[] newArray(final Class<E> c, final int length) {
        return Util.cast(Array.newInstance(c, length));
    }
    
    // List<String> list = newArray(ArrayList.class, 5);
    ///**	Retrieves a new array of the given component Class
    //@param c	the component Class
    //@param length	the array length
    //@param <E>	the element type
    //@param <T>	the return type
    //@return	the array
    //**/
    //public final static <E, T extends E> T[] newArray(Class<E> c, int length)
    //{	return Util.cast(Array.newInstance(c, length));
    //}
    
    /**
     * Retrieves the desired Method from the given Object
     * 
     * @param o the Object containing the Method
     * @param methodName the name of the desired Method
     * @param parameterTypes the parameter types of the desired Method
     * @return the Method
     **/
    public final static Method getMethod(final Object o, final String methodName, final Class<?>... parameterTypes) {
        return getMethod(o.getClass(), methodName, parameterTypes);
    }
    
    /**
     * Retrieves all Methods of the given Class of any visibility, including inherited Methods
     * 
     * @param c the Class
     * @return the List<Method>
     **/
    public final static List<Method> getMethodList(Class<?> c) {
        /*final List<Method> cached = methodLists.get(c); // CORE-1266
        if (cached != null) {
            return cached;
        }*/
        
        // Class.getDeclaredMethods() retrieves Methods of any visibility, but not those inherited from super Classes.
        // Class.getMethods() retrieves inherited Methods, but only public Methods.
        // This retrieves all Methods.
        final List<Method> methodList = new ArrayList<Method>();
        final Set<Object[]> methodSignatureSet = new HashSet<Object[]>();
        
        do {
            for (final Method method : c.getDeclaredMethods()) {
                final Object[] methodSignature = new Object[] { method.getName(), method.getParameterTypes() };
                // Can't just check methodList itself.
                // The equals() and hashCode() methods depend on the Class.
                // If a Class overrides a parent's method, it would show up twice.
                if (!methodSignatureSet.contains(methodSignature)) {
                    methodSignatureSet.add(methodSignature);
                    methodList.add(method);
                }
            }
        } while ((c = c.getSuperclass()) != null);
        
        //methodLists.put(c, methodList);
        return methodList;
    }
    
    /**
     * Retrieves all Methods of the given Class of any visibility, including inherited Methods
     * 
     * @param c the Class
     * @return the Method[] array
     **/
    public final static Method[] getMethods(final Class<?> c) {
        return getMethodList(c).toArray(EMPTY_METHOD_ARRAY);
    }
    
    private static interface FunctionAccessor<T extends AccessibleObject> {
        
        public Collection<T> getList(final Class<?> c);
        
        public boolean equals(final T func, final String name);
        
        public Class<?>[] getParameterTypes(final T func);
    }
    
    private final static class MethodAccessor implements FunctionAccessor<Method> {
        
        @Override
        public final Collection<Method> getList(final Class<?> c) {
            return getMethodList(c);
        }
        
        @Override
        public final boolean equals(final Method func, final String name) {
            return func.getName().equals(name);
        }
        
        @Override
        public final Class<?>[] getParameterTypes(final Method func) {
            return func.getParameterTypes();
        }
    }
    
    private final static MethodAccessor methAcc = new MethodAccessor();
    
    private final static class ConstructorAccessor implements FunctionAccessor<Constructor<?>> {
        
        @Override
        public final Collection<Constructor<?>> getList(final Class<?> c) {
            return Arrays.asList(c.getDeclaredConstructors());
        }
        
        @Override
        public final boolean equals(final Constructor<?> func, final String name) {
            return true;
        }
        
        @Override
        public final Class<?>[] getParameterTypes(final Constructor<?> func) {
            return func.getParameterTypes();
        }
    }
    
    private final static ConstructorAccessor consAcc = new ConstructorAccessor();
    
    /**
     * Retrieves the desired Method from the given Class
     * 
     * @param c the Class containing the Method
     * @param methodName the name of the desired Method
     * @param parameterTypes the parameter types of the desired Method
     * @return the Method
     **/
    public final static Method getMethod(final Class<?> c, final String methodName, final Class<?>... parameterTypes) {
        return getFunction(methAcc, c, methodName, parameterTypes);
    }
    
    /**
     * Retrieves the desired Method from the given Class
     * 
     * @param c the Class containing the Method
     * @param parameterTypes the parameter types of the desired Method
     * @param <T> the type
     * @return the Method
     **/
    public final static <T> Constructor<T> getConstructor(final Class<T> c, final Class<?>... parameterTypes) {
        return Util.cast(getFunction(consAcc, c, c.getName(), parameterTypes));
    }
    
    private final static <T extends AccessibleObject> T getFunction(final FunctionAccessor<T> acc, final Class<?> c,
                                                                    final String methodName,
                                                                    final Class<?>... parameterTypes) {
        List<T> candidates = null;
        
        for (final T method : acc.getList(c)) {
            final Class<?>[] methodParameterTypes = acc.getParameterTypes(method);
            if (acc.equals(method, methodName) && areAssignableFrom(methodParameterTypes, parameterTypes)) {
                if (candidates == null) {
                    candidates = new ArrayList<T>();
                    candidates.add(method);
                } else {
                    boolean needAdd = true;
                    for (int i = candidates.size() - 1; i >= 0; i--) {
                        final Class<?>[] candidateParameterTypes = acc.getParameterTypes(candidates.get(i));
                        if (areAssignableFrom(candidateParameterTypes, methodParameterTypes)) {
                            if (needAdd) {
                                candidates.set(i, method);
                                needAdd = false;
                            } else {
                                candidates.remove(i);
                            }
                        } else if (areAssignableFrom(methodParameterTypes, candidateParameterTypes)) {
                            needAdd = false;
                            break;
                        }
                    }
                    if (needAdd) {
                        candidates.add(method);
                    }
                }
            }
        }
        
        if (candidates == null) {
            return null;
        }
        
        if (candidates.size() > 1) {
            throw new RuntimeException("The method " + methodName + '(' + toString(parameterTypes)
                    + ") is ambiguous for the type " + c.getName());
        }
        
        final T candidate = candidates.get(0);
        candidate.setAccessible(true);
        
        return candidate;
        
        /*
        for (Method method : getMethodList(c))
        {
        	if(method.getName().equals(methodName) && Util.equals(method.getParameterTypes(), parameterTypes))
        	{
        		method.setAccessible(true);
        		return method;
        	}
        }

        return null;
        */
    }
    
    private final static String toString(final Class<?>... classes) {
        final int size = Util.length(classes);
        if (size == 0) {
            return "";
        }
        
        final StringBuilder sb = new StringBuilder();
        for (final Class<?> c : classes) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(c.getName());
        }
        
        return sb.toString();
    }
    
    public final static boolean areAssignableFrom(final Class<?>[] superClasses, final Class<?>[] subClasses) {
        final int size = superClasses.length;
        
        if (size != subClasses.length) {
            return false;
        }
        
        for (int i = 0; i < size; i++) {
            //if (!superClasses[i].isAssignableFrom(subClasses[i]))
            if (!isAssignableFrom(superClasses[i], subClasses[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves the desired Method from the given Object
     * 
     * @param o the Object containing the Method
     * @param methodName the name of the desired Method
     * @param parameterTypes the parameter types of the desired Method
     * @return the Method
     **/
    public final static Method getMethod(final Object o, final String methodName, final Type... parameterTypes) {
        return getMethod(o.getClass(), methodName, parameterTypes);
    }
    
    /**
     * Retrieves the desired Method from the given Class
     * 
     * @param c the Class containing the Method
     * @param methodName the name of the desired Method
     * @param parameterTypes the parameter types of the desired Method
     * @return the Method
     **/
    public final static Method getMethod(final Class<?> c, final String methodName, final Type... parameterTypes) {
        for (final Method method : getMethodList(c)) {
            if (method.getName().equals(methodName) && Util.equals(method.getGenericParameterTypes(), parameterTypes)) {
                method.setAccessible(true);
                return method;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves all Fields of the given Class of any visibility, including inherited Fields
     * 
     * @param c the Class
     * @return the List<Field>
     **/
    public final static List<Field> getFieldList(Class<?> c) {
        final List<Field> fieldList = new ArrayList<Field>();
        final Set<String> fieldNameSet = new HashSet<String>();
        
        do {
            for (final Field field : c.getDeclaredFields()) {
                final String fieldName = field.getName();
                if (!fieldNameSet.contains(fieldName)) {
                    fieldNameSet.add(fieldName);
                    fieldList.add(field);
                }
            }
        } while ((c = c.getSuperclass()) != null);
        
        return fieldList;
    }
    
    /**
     * Retrieves the desired Field from the given Object
     * 
     * @param o the Object containing the Field
     * @param name the Field name
     * @return the Field
     **/
    public final static Field getField(final Object o, final String name) {
        return getField(o.getClass(), name);
    }
    
    /**
     * Retrieves the desired Field from the given Class
     * 
     * @param c the Class containing the Field
     * @param name the Field name
     * @return the Field
     **/
    public final static Field getField(final Class<?> c, final String name) {
        for (final Field f : getFieldList(c)) {
            if (f.getName().equals(name)) {
                f.setAccessible(true);
                return f;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves the Field representing the given field Object in the given container Object
     * 
     * @param container the container Object
     * @param field the field Object
     * @return the Field
     **/
    public final static Field getContainerField(final Object container, final Object field) {
        return getContainerField(container.getClass(), container, field);
    }
    
    /**
     * Retrieves the Field representing the given field Object in the given container
     * 
     * @param containerClass the container Class
     * @param field the field Object
     * @return the Field
     **/
    public final static Field getContainerField(final Class<?> containerClass, final Object field) {
        return getContainerField(containerClass, null, field);
    }
    
    /**
     * Retrieves the Field representing the given field Object in the given container
     * 
     * @param containerClass the container Class
     * @param container the container Object
     * @param field the field Object
     * @return the Field
     **/
    private final static Field getContainerField(final Class<?> containerClass, final Object container, final Object field) {
        if (field == null) {
            throw new NullPointerException();
        }
        
        for (final Field f : getFieldList(containerClass)) {
            f.setAccessible(true);
            if ((container == null) && !Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            final Object o;
            try {
                o = f.get(container);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (o == field) {
                return f;
            }
        }
        
        throw new RuntimeException("Could not find " + field + " in " + container);
    }
    
    /**
     * Invokes the desired Method of the given Object
     * 
     * @param o the Object
     * @param method the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Method's return value
     **/
    public final static Object invoke(final Object o, final Method method, final Object... parameters) {
        method.setAccessible(true);
        
        try {
            return method.invoke(o, parameters);
        } catch (final Exception e) {
            throw Util.toRuntimeException(Util.nvl(e.getCause(), e));
        }
    }
    
    /**
     * Invokes the desired static Method
     * 
     * @param method the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Method's return value
     **/
    public final static Object invoke(final Method method, final Object... parameters) {
        return invoke(null, method, parameters);
    }
    
    private final static Class<?>[] getClasses(final Object... parameters) {
        final int size = parameters.length;
        final Class<?>[] parameterTypes = new Class<?>[size];
        
        for (int i = 0; i < size; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        
        return parameterTypes;
    }
    
    /**
     * Invokes the desired Method of the given Object
     * 
     * @param o the Object
     * @param name the name of the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Object returned by the Method
     **/
    public final static Object invoke(final Object o, final String name, final Object... parameters) {
        return invoke(o, name, getClasses(parameters), parameters);
    }
    
    /**
     * Invokes the desired Method of the given Class
     * 
     * @param c the Class
     * @param name the name of the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Object returned by the Method
     **/
    public final static Object invoke(final Class<?> c, final String name, final Object... parameters) {
        return invoke(c, name, getClasses(parameters), parameters);
    }
    
    private final static void assertMethod(final Method method, final String name, final Class<?>[] parameterTypes,
                                           final Class<?> c) {
        if (method == null) {
            throw new RuntimeException("The method " + name + '(' + toString(parameterTypes)
                    + ") could not be found in the type " + c.getName());
        }
    }
    
    /**
     * Invokes the desired Method of the given Object
     * 
     * @param o the Object
     * @param name the name of the desired Method
     * @param parameterTypes the parameter Classes of the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Object returned by the Method
     **/
    public final static Object invoke(final Object o, final String name, final Class<?>[] parameterTypes,
                                      final Object[] parameters) {
        final Method method = getMethod(o, name, parameterTypes);
        
        assertMethod(method, name, parameterTypes, o.getClass());
        
        return invoke(o, method, parameters);
    }
    
    /**
     * Invokes the desired Method of the given Class
     * 
     * @param c the Class
     * @param name the name of the desired Method
     * @param parameterTypes the parameter Classes of the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Object returned by the Method
     **/
    public final static Object invoke(final Class<?> c, final String name, final Class<?>[] parameterTypes,
                                      final Object[] parameters) {
        final Method method = getMethod(c, name, parameterTypes);
        
        assertMethod(method, name, parameterTypes, c);
        
        return invoke(null, method, parameters);
    }
    
    /**
     * Invokes the desired Method of the given Object
     * 
     * @param o the Object
     * @param name the name of the desired Method
     * @param parameterTypes the parameter Classes of the desired Method
     * @param parameters the Objects to use as parameters when invoking the Method
     * @return the Object returned by the Method
     **/
    public final static Object invokeIfFound(final Object o, final String name, final Class<?>[] parameterTypes,
                                             final Object[] parameters) {
        final Method method = getMethod(o, name, parameterTypes);
        
        return method == null ? null : invoke(o, method, parameters);
    }
    
    public final static Class<?> getWrapper(final Class<?> primitiveClass) {
        if (primitiveClass.equals(Boolean.TYPE)) {
            return Boolean.class;
        } else if (primitiveClass.equals(Byte.TYPE)) {
            return Byte.class;
        } else if (primitiveClass.equals(Character.TYPE)) {
            return Character.class;
        } else if (primitiveClass.equals(Double.TYPE)) {
            return Double.class;
        } else if (primitiveClass.equals(Float.TYPE)) {
            return Float.class;
        } else if (primitiveClass.equals(Integer.TYPE)) {
            return Integer.class;
        } else if (primitiveClass.equals(Long.TYPE)) {
            return Long.class;
        } else if (primitiveClass.equals(Short.TYPE)) {
            return Short.class;
        } else {
            return null;
            //throw new RuntimeException(primitiveClass + " is not primitive");
        }
    }
    
    public final static Class<?> getPrimitive(final Class<?> wrapperClass) {
        if (wrapperClass.equals(Boolean.class)) {
            return Boolean.TYPE;
        } else if (wrapperClass.equals(Byte.class)) {
            return Byte.TYPE;
        } else if (wrapperClass.equals(Character.class)) {
            return Character.TYPE;
        } else if (wrapperClass.equals(Double.class)) {
            return Double.TYPE;
        } else if (wrapperClass.equals(Float.class)) {
            return Float.TYPE;
        } else if (wrapperClass.equals(Integer.class)) {
            return Integer.TYPE;
        } else if (wrapperClass.equals(Long.class)) {
            return Long.TYPE;
        } else if (wrapperClass.equals(Short.class)) {
            return Short.TYPE;
        } else {
            return null;
            //throw new RuntimeException(primitiveClass + " is not a wrapper");
        }
    }
    
    /**
     * Retrieves whether the parent Class is assignable from the child Class, treating primitives as
     * assignable from their wrapper
     * 
     * @param parent the parent Class
     * @param child the child Class
     * @return whether the parent Class is assignable from the child Class
     **/
    public final static boolean isAssignableFrom(final Class<?> parent, final Class<?> child) {
        if (parent.isAssignableFrom(child)) {
            return true;
        }
        
        if (parent.equals(Boolean.TYPE)) {
            if (child.equals(Boolean.class)) {
                return true;
            }
        } else if (parent.equals(Byte.TYPE)) {
            if (child.equals(Byte.class)) {
                return true;
            }
        } else if (parent.equals(Character.TYPE)) {
            if (child.equals(Character.class)) {
                return true;
            }
        } else if (parent.equals(Double.TYPE)) {
            if (child.equals(Double.class) || child.equals(Float.TYPE)) {
                return true;
            }
        } else if (parent.equals(Float.TYPE)) {
            if (child.equals(Float.class)) {
                return true;
            }
        } else if (parent.equals(Integer.TYPE)) {
            if (child.equals(Integer.class) || child.equals(Short.TYPE) || child.equals(Byte.TYPE)) {
                return true;
            }
        } else if (parent.equals(Long.TYPE)) {
            if (child.equals(Long.class) || child.equals(Integer.TYPE) || child.equals(Short.TYPE)
                    || child.equals(Byte.TYPE)) {
                return true;
            }
        } else if (parent.equals(Short.TYPE)) {
            if (child.equals(Short.class) || child.equals(Byte.TYPE)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retrieves null or a null-like default if the given Class is primitive
     * 
     * @param c the Class
     * @return the null marker
     **/
    public final static Object getNull(final Class<?> c) {
        if (c.equals(Boolean.TYPE)) {
            return Boolean.FALSE;
        } else if (c.equals(Byte.TYPE)) {
            return Byte.valueOf((byte) 0);
        } else if (c.equals(Character.TYPE)) {
            return Character.valueOf(' ');
        } else if (c.equals(Double.TYPE)) {
            return Double.valueOf(0);
        } else if (c.equals(Float.TYPE)) {
            return Float.valueOf(0);
        } else if (c.equals(Integer.TYPE)) {
            return Integer.valueOf(0);
        } else if (c.equals(Long.TYPE)) {
            return Long.valueOf(0);
        } else if (c.equals(Short.TYPE)) {
            return Short.valueOf((short) 0);
        }
        
        return null;
    }
    
    public final static void registerClassLoader(final ClassLoader classLoader) {
        getClassLoaders(); // Initializes Set
        addClassLoader(classLoader);
    }
    
    private final static void addClassLoader(final ClassLoader classLoader) {
        if (classLoader != null) {
            classLoaders.add(classLoader);
        }
    }
    
    private final static Set<ClassLoader> getClassLoaders() {
        addClassLoader(ReflectUtil.class.getClassLoader());
        addClassLoader(Thread.currentThread().getContextClassLoader());
        addClassLoader(ClassLoader.getSystemClassLoader());
        return classLoaders;
    }
}
