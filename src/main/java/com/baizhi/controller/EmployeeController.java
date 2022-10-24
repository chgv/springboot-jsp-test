package com.baizhi.controller;

import com.baizhi.entity.Employee;
import com.baizhi.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用來開發員工功能
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /**
     * 根據id刪除員工信息
     * @return
     */
    @RequestMapping("/delete")
    public String deleteEmployee(Integer id){
        log.debug("删除的id: {}",id);
        //1.根據id刪除員工信息
        employeeService.delete(id);
        //2.跳轉到列表頁面
        return "redirect:/employee/list";
    }

    /**
     * 更新員工信息
     *
     * @return
     */
    @RequestMapping("update")
    public String updateEmployee(Employee employee) {
        log.debug("員工id: {}", employee.getId());
        log.debug("員工名稱: {}", employee.getName());
        log.debug("員工工資: {}", employee.getSalary());
        log.debug("員工生日: {}", employee.getBirthday());
        log.debug("員工性別: {}", employee.getGender());
        //1.更新員工信息
        employeeService.update(employee);
        //2.跳轉到員工列表
        return "redirect:/employee/list";
    }

    /**
     * 根據id查詢員工信息
     *
     * @return
     */
    @RequestMapping("detail")
    public String detailEmployee(Integer id, Model model) {
        log.debug("接收id: {}", id);
        //1.根據id查詢一個員工
        Employee employee = employeeService.idByEmployee(id);
        //2.存入request session application
        model.addAttribute("employee", employee);
        return "updateEmp";//跳轉頁面
    }


    /**
     * 添加員工信息
     *
     * @return
     */
    @RequestMapping("add")
    public String addEmployee(Employee employee) {
        log.debug("員工名稱: {}", employee.getName());
        log.debug("員工工資: {}", employee.getSalary());
        log.debug("員工生日: {}", employee.getBirthday());
        log.debug("員工性别: {}", employee.getGender());
        //1.保存員工信息
        employeeService.add(employee);
        return "redirect:/employee/list";//跳轉到列表
    }

    /**
     * 員工列表
     *
     * @return
     */
    @RequestMapping("list")
    public String listEmployee(HttpServletRequest request, Model model) {
        //1.獲取員工列表
        List<Employee> employees = employeeService.list();
        //request.setAttribute("employees",employees);
        model.addAttribute("employees", employees);
        return "emplist";
    }
}
