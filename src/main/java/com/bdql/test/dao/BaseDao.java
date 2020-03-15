package com.bdql.test.dao;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @AUTHOR: 小于
 * @DATE: [2020/3/14  21:48]
 * @DESC:
 */


public class BaseDao<T> {

    private static Logger LOGGER = Logger.getLogger(BaseDao.class);
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;
    private static final Queue<Connection> connIdleQueue = new ConcurrentLinkedQueue<Connection>();
    private static final List<Connection> workList = new ArrayList<Connection>();
    protected static final Integer maxPool = 5;
    protected static final Integer minPool = 3;
    // 单位秒
    protected static final Integer idleTime = 5;

    protected Entry entry;

    static {

        Properties properties = new Properties();
        try {

            properties.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("db.properties").getFile()));

            driverClass = properties.getProperty("jdbc.stu.driver.class");
            url = properties.getProperty("jdbc.stu.url");
            username = properties.getProperty("jdbc.stu.username");
            password = properties.getProperty("jdbc.stu.password");


            try {
                Class.forName(driverClass);
                while (connIdleQueue.size() < minPool) {
                    connIdleQueue.add(DriverManager.getConnection(url, username, password));
                }
            } catch (ClassNotFoundException e) {
                LOGGER.error("jdbc驱动类加载异常", e);
            } catch (SQLException e) {
                LOGGER.error("jdbc获取连接异常", e);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            Thread.sleep(idleTime * 1000);
                        } catch (InterruptedException e) {
                            LOGGER.error("jdbc工具类休眠异常", e);
                        }
                        if (connIdleQueue.size() > minPool) {
                            synchronized (BaseDao.class) {
                                while (connIdleQueue.size() > minPool) {
                                    try {
                                        connIdleQueue.poll().close();
                                    } catch (SQLException e) {
                                        LOGGER.error("jdbc回收空闲连接异常", e);
                                    }
                                }
                            }

                        }
                    }
                }
            }).start();

        } catch (IOException e) {

            LOGGER.error("database db.properties load failure!!!");

        }
    }

    protected final Connection getConnection() throws Exception {
        Connection conn = connIdleQueue.poll();
        if (conn == null) {
            synchronized (BaseDao.class) {
                if (workList.size() > maxPool) {
                    throw new Exception("jdbc连接池中的连接已用完,请稍后再试");
                } else {
                    try {
                        conn = DriverManager.getConnection(url, username, password);
                        workList.add(conn);
                    } catch (SQLException e) {
                        LOGGER.error("jdbc获取连接异常", e);
                    }
                }
            }
        }
        return conn;
    }

    protected final void release(Connection conn) {
        workList.remove(conn);
        connIdleQueue.add(conn);
    }


    protected final void update(String sql, Object... args) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof Integer) {
                        pst.setInt(i + 1, (Integer) arg);
                    } else if (arg instanceof String) {
                        pst.setString(i + 1, String.valueOf(arg));
                    } else {
                        pst.setObject(i + 1, arg);
                    }
                }
            }
            pst.execute();
        } catch (Exception e) {
            LOGGER.error("获取连接失败", e);
            throw e;
        } finally {
            release(conn);
        }
    }


    protected final List<T> query(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof Integer) {
                        pst.setInt(i + 1, (Integer) arg);
                    } else if (arg instanceof String) {
                        pst.setString(i + 1, String.valueOf(arg));
                    } else {
                        pst.setObject(i + 1, arg);
                    }
                }
            }

            ResultSet rs = pst.executeQuery();
            if (entry == null) {
                fillEntry();
            }

            List<T> list = new ArrayList<>();
            Class clazz = entry.getClazz();
            Map<Field, Method> map = entry.getSetMethodMap();
            while (rs.next()) {
                T t = (T) clazz.getConstructor().newInstance();
                for (Field field : map.keySet()) {
                    if (field.getType() == Integer.class)
                        map.get(field).invoke(t, rs.getInt(field.getName()));
                    else if (field.getType() == String.class)
                        map.get(field).invoke(t, rs.getString(field.getName()));
                    else
                        map.get(field).invoke(t, rs.getObject(field.getName()));
                }
                list.add(t);
            }
            return list;

        } catch (Exception e) {
            LOGGER.error("获取连接失败", e);
            return new ArrayList<>();
        } finally {
            release(conn);
        }
    }

    private void fillEntry() {
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) superClass;
        Type[] actualTypes = p.getActualTypeArguments();
        Class clazz = (Class) actualTypes[0];
        entry = new Entry();
        Field[] fields = clazz.getDeclaredFields();
        Map<Field, Method> methodMap = new HashMap<>();
        for (Field field : fields) {
            if (field.getAnnotation(TableFiled.class) != null) {
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                try {
                    methodMap.put(field, clazz.getMethod("set" + name, field.getType()));
                } catch (NoSuchMethodException e) {
                    LOGGER.error("获取实体类的方法失败", e);
                }
            }
        }
        entry.setSetMethodMap(methodMap);
        entry.setClazz(clazz);
    }


}
