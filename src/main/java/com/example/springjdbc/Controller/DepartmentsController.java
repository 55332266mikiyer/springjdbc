package com.example.springjdbc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springjdbc.domain.Department;
import com.example.springjdbc.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentsController {
    @Autowired
    private DepartmentService service;

    @RequestMapping("/execute")
    public String excute(){
        Department department = new Department();
        department.setName("管理部");
        service.save(department);
        

        Department department2 = service.load(2);
        System.out.println(department2);
        service.deleteById(2);
        service.findAll().forEach(System.out::println);
        return "finished";
    }

}
