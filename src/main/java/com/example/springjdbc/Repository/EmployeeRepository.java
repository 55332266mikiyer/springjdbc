package com.example.springjdbc.Repository;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.springjdbc.domain.Employee;

@Repository
public class EmployeeRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final org.springframework.jdbc.core.RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setAge(rs.getInt("age"));
        employee.setGender(rs.getString("gender"));
        employee.setDepartmentId(rs.getInt("department_id"));
        return employee;
    };

    public Employee load(Integer id){
        String sql = "SELECT id, name, age, gender, department_id FROM employees WHERE id =:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

        return employee;
    }

    // public Employee load(Integer id){
    //     System.out.println("Repositoryのloadが呼ばれました");
    //     return null;
    // }

    public List<Employee> findAll(){
        // System.out.println("RepositoryのfindAll()が呼ばれました");
        String sql = """
           select
             id
             , name
             , age
             , gender
             , department_id
             FROM employees ORDER BY age

                """;
        List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
        return employeeList;
    }

    public Employee save(Employee employee){
        // System.out.println("Repositoryのsave()が呼ばれました");
        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

        if(employee.getId() == null){
            String insertSql = "INSERT INTO employees(name,age,gender,department_id)" + "VALUES(:name,:age,:gender,:departmentId)";
            template.update(insertSql, param);
        }else{
            String updateSql = "UPDATE employees SET name=:name, age=:age," + "gender=:gender, department_id=:departmentId" + "WHERE id =:id" ;//returning id
            template.update(updateSql, param);
        }
        return employee;
        }
        
    

    public void deleteById (Integer id){
        String deleteSql = "DELETE FROM employees WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(deleteSql, param);
        // System.out.println("RepositoryのdeleteById()が呼ばれました");
    }

}
