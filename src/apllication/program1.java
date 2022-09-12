package apllication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class program1 {

	public static void main(String[] args) {
		
		
		Scanner leia = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentrDao();
		
		System.out.println("===== TEST 1: Department findById =====");
		Department department = departmentDao.findById(3);
		System.out.println(department);
	
		// ------------------------------------------------------------------------ //
		
		System.out.println("\n===== TEST 2: Department findByAll =====");
		List <Department> list = new ArrayList<>();
		list = departmentDao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}
	
		// ------------------------------------------------------------------------ //
		
		System.out.println("\n===== TEST 3: Department insert =====");
		Department newDepartment = new Department(null, "T.I");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id: " + newDepartment);
		
		// ------------------------------------------------------------------------ //
	
		System.out.println("\n===== TEST 4: Department update =====");
		department = departmentDao.findById(6);
		department.setName("RH");
		departmentDao.update(department);
		System.out.println("Update completed");
		 
		// ------------------------------------------------------------------------ //
		
		System.out.println("\n===== TEST 5: Department delete =====");
		System.out.println("Insert for id department: ");
		int id = leia.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete deparment!");             
	}
}
