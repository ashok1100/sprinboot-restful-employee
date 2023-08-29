package net.springboot.restful.webservices.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.springboot.restful.webservices.dto.EmployeeDto;
import net.springboot.restful.webservices.exception.EmailAlreadyExistsException;
import net.springboot.restful.webservices.exception.ResourceNotFoundException;
import net.springboot.restful.webservices.model.Employee;
import net.springboot.restful.webservices.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EmployeeDto createEmployee(EmployeeDto employeeDto) {

		Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
		if (optionalEmployee.isPresent()) {
			throw new EmailAlreadyExistsException(
					"Given email already exists in the db... please try with different email");
		}
		// converting EmployeeDto into JPA entity
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		Employee savedEmployee = employeeRepository.save(employee);

		// converting JPA entity to EmployeeDto
		EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);
		return savedEmployeeDto;
	}

	public List<EmployeeDto> findAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map((employee) -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	public EmployeeDto getEmployeeByid(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
		return modelMapper.map(employee, EmployeeDto.class);
	}

	public EmployeeDto updateEmployee(EmployeeDto employee) {
		Employee existingEmployee = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employee.getId()));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		return modelMapper.map(updatedEmployee, EmployeeDto.class);
	}

	public void deleteEmployeeById(Long employeId) {
		Employee existingEmployee = employeeRepository.findById(employeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeId));
		employeeRepository.deleteById(employeId);
		System.out.println(existingEmployee);
	}
}
