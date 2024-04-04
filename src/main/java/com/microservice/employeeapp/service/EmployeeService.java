package com.microservice.employeeapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.employeeapp.employeeRepo.EmployeeRepo;
import com.microservice.employeeapp.entity.Employee;
import com.microservice.employeeapp.response.AddressResponse;
import com.microservice.employeeapp.response.EmployeeResponse;

@Service
// Instead of returning entity we have to return model
public class EmployeeService {
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @Value("${addressservice.base.url}") private String addressBaseUrl;
	 */
	

	// initializing rest template inside default constructor using RestTemplateBuilder class
	public EmployeeService(@Value("${addressservice.base.url}") String addressBaseUrl,RestTemplateBuilder builder) {
		this.restTemplate=builder
				.rootUri(addressBaseUrl)
				.build();
	}
	public EmployeeResponse getEmployeeById(int id) {
		
		
		AddressResponse addressResponse = new AddressResponse();
		Employee employee = employeeRepo.findById(id).get();
		EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
		
		addressResponse = restTemplate.getForObject("/address/{id}", AddressResponse.class, id);
		employeeResponse.setAddressResponse(addressResponse);

		/* 
		 * EmployeeResponse employeeResponse = new EmployeeResponse();
		 * employeeResponse.setId(employee.getId());
		 * employeeResponse.setName(employee.getName());
		 * employeeResponse.setEmail(employee.getEmail());
		 * employeeResponse.setBloodgroup(employee.getBloodgroup());
		 */

		return employeeResponse;

	}

}
