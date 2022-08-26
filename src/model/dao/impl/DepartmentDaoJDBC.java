package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	Scanner leia = new Scanner(System.in);
	private Connection conn;
	
	public DepartmentDaoJDBC (Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		  ResultSet rs = null;
		  
		  try {
			st = conn.prepareStatement(
					  "INSERT INTO department "
					  + "(Name) "
					  + "VALUES "
					  + "(?) ", 
					  Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs1 = st.getGeneratedKeys();
				if (rs1.next()) {
					int id = rs1.getInt(1);
					obj.setId(id);
					DB.closeResultSet(rs1);
				}
				else {
					throw new DbException("Unexpected error! No rows affected");
				}
			}
			
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE departmentId = ? ");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM department "
					+ "WHERE Id = ? "
					+ "ORDER BY Name ");
			
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs); 			
				return dep;
			}
			return null;
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * from Department order by Name ");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			if (rs.next()) {
				Department dep = new Department();
				list.add(dep);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	protected Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

}

