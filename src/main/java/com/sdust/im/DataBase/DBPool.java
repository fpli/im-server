package com.sdust.im.DataBase;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用数据库连接池加大响应速度
 *
 */
public class DBPool {

	private static DataSource ds;

	private DBPool() {}

	static {
		try {
			InputStream in = DBPool.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			Properties pro = new Properties();
			pro.load(in);
			ds = BasicDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("获取数据库连接失败....");
			e.printStackTrace();
		}
		return con;
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
