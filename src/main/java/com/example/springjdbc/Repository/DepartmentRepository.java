package com.example.springjdbc.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.springjdbc.domain.Department;

@Repository
public class DepartmentRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Department>DEPARTMENT_ROW_MAPPER =(rs, i) ->{
        Department department = new Department();
        department.setId(rs.getInt("id"));
        department.setName(rs.getString("name"));
        
        return department;
    };

    public Department load(Integer id){
        String sql = """
                SELECT
                id
                ,name
                FROM departments WHERE id =:id;
                """;
        SqlParameterSource param =new MapSqlParameterSource().addValue("id", id);

        Department department = template.queryForObject(sql, param, DEPARTMENT_ROW_MAPPER);
        return department;

    }

    public List<Department> findAll(){
        String sql ="""
                SELECT
                id
                ,name
                FROM departments
                """;
        
        List<Department> deparmentList = template.query(sql,DEPARTMENT_ROW_MAPPER);
        return deparmentList;
    }

    public Department save(Department department){
        SqlParameterSource param = new BeanPropertySqlParameterSource(department);
        
        if (department.getId() == null) {
            String insertSql = "INSERT INTO departments(name)" + "VALUES(:name)";
            template.update(insertSql, param);
        }else{
            String updateSql = "UPDATE departments SET name =:name" + "WHERE id =:id";
            template.update(updateSql,param);
        }
        return department;


    }

    public void deleteById(Integer id){
        String deleteSql = "DELETE FROM departments WHERE id = :id";
        
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        
        template.update(deleteSql, param);

    }


}
