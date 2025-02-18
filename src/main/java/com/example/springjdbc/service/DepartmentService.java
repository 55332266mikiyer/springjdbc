package com.example.springjdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjdbc.Repository.DepartmentRepository;
import com.example.springjdbc.domain.Department;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    private DepartmentRepository repository;

    public Department load(Integer id){
    return repository.load(id);
    }

    public List<Department> findAll(){
        return repository.findAll();
    }

    public Department save(Department department){
        return repository.save(department);
    }

    public void deleteById(Integer Id){
        repository.deleteById(Id);
    }



}
