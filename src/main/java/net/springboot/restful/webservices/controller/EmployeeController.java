package net.springboot.restful.webservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.springboot.restful.webservices.dto.EmployeeDto;
import net.springboot.restful.webservices.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/create")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
		return new ResponseEntity<EmployeeDto>(savedEmployee, HttpStatus.CREATED);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<EmployeeDto> employee = employeeService.findAllEmployees();
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("id") Long empId) {
		EmployeeDto employee = employeeService.getEmployeeByid(empId);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable("id") Long empId,
			@RequestBody EmployeeDto employeeDto) {
		employeeDto.setId(empId);
		EmployeeDto savedEmployee = employeeService.updateEmployee(employeeDto);
		return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long empId) {
		employeeService.deleteEmployeeById(empId);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}
}
