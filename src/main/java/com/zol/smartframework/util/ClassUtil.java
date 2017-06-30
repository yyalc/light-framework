package com.zol.smartframework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建时间：2017年6月29日
 * 
 * @author suzhihui 类操作工具类
 */
public final class ClassUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClassUtil.class);

	// 获取类加载器
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	// 加载类
	public static Class<?> loadClass(String classname, boolean isInitialized) {
		Class<?> cls;
		try {
			cls = Class.forName(classname, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure", e);
			throw new RuntimeException(e);
		}
		return cls;
	}

	// 加载类(默认初始化)
	public static Class<?> loadClass(String classname) {
	   return loadClass(classname, true);
	}

	// 获取指定包名下的所有类
	public static Set<Class<?>> getClassSet(String packagename) {
		Set<Class<?>> classSet = new HashSet<>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(
					packagename.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String packagePath = url.getPath().replaceAll("%20", "");
					addClass(classSet, packagePath, packagename);
				} else if ("jar".equals(protocol)) {
					JarURLConnection connection = (JarURLConnection) url
							.openConnection();
					if (null != connection) {
						JarFile jarFile = connection.getJarFile();
						Enumeration<JarEntry> entrys = jarFile.entries();
						while (entrys.hasMoreElements()) {
							JarEntry jarEntry = entrys.nextElement();
							String jarEntryName = jarEntry.getName();
							if (jarEntryName.endsWith(".class")) {
								String classname = jarEntryName.substring(0,
										jarEntryName.lastIndexOf("."))
										.replaceAll("/", ".");
								doAddClass(classSet, classname);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("get class set failure", e);
			throw new RuntimeException(e);
		}
		return classSet;
	}

	private static void addClass(Set<Class<?>> set, String packagePath,
			String packagename) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class"))
						|| file.isDirectory();
			}
		});
		for (File f : files) {
			String filename = f.getName();
			if (f.isFile()) {
				String classname = filename.substring(0,
						filename.lastIndexOf("."));
				if (StringUtil.isNotEmpty(classname)) {
					classname = packagename + "." + classname;
				}
				doAddClass(set, classname);
			} else {
				String subPackagePath = filename;
				if (StringUtil.isNotEmpty(subPackagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}
				String subPackageName = filename;
				if (StringUtil.isNotEmpty(packagename)) {
					subPackageName = packagename + "." + subPackageName;
				}
				addClass(set, subPackagePath, subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> set, String classname) {
		Class<?> cls = loadClass(classname, false);
		set.add(cls);
	}

}
