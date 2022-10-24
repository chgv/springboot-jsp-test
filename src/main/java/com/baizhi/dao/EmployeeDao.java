package com.baizhi.dao;

import com.baizhi.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    //查詢員工信息列表
    List<Employee> list();

    //添加員工信息
    void add(Employee employee);

    //根據id查詢員工信息
    Employee idByEmployee(Integer id);

    //更新員工信息
    void update(Employee employee);

    //根據id删除員工信息
    void delete(Integer id);
}
