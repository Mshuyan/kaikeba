package com.kkb.spring.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.kkb.spring.po.User;

public class UserDaoImpl implements UserDao {

	// 数据源对象
	private DataSource dataSource;

	// setter方法注入DataSource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// 初始化方法
	public void init() {
		System.out.println("初始化方法被调用");
	}

	@Override
	public List<User> queryUserList(Map<String, Object> param) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			// 通过数据源获取连接
			connection = dataSource.getConnection();

			// 定义sql语句 ?表示占位符
			String sql = "select * from user where username = ?";

			// 获取预处理 statement
			preparedStatement = connection.prepareStatement(sql);

			// 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
			preparedStatement.setObject(1, param.get("username"));

			// 向数据库发出 sql 执行查询，查询出结果集
			rs = preparedStatement.executeQuery();

			// 遍历查询结果集
			List<User> results = new ArrayList<User>();
			User result = null;
			Class<?> clazz = User.class;
			while (rs.next()) {
				// 遍历一行数据，则创建一个User对象
				result = (User) clazz.newInstance();
				// 获取结果集的元数据
				ResultSetMetaData metaData = rs.getMetaData();
				// 获取每一行包含的列的数量
				int columnCount = metaData.getColumnCount();
				// 遍历所有列
				for (int i = 0; i < columnCount; i++) {
					// 获取每一列的名称
					String columnName = metaData.getColumnName(i + 1);
					// 通过反射获取指定属性名称的Field对象（保证列名和属性名一致）
					Field field = clazz.getDeclaredField(columnName);
					// 设置暴力破解
					field.setAccessible(true);

					// 给私有属性赋值
					field.set(result, rs.getObject(i + 1));
				}

				results.add(result);
			}

			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		return null;
	}

}
