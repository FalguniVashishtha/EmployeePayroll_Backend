package com.bridgelabz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegisterDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Model;
import com.bridgelabz.repository.IEmpRepository;
import com.bridgelabz.utility.TokenUtil;


@Service
public class EmployeeService implements IEmpService{

	@Autowired
	IEmpRepository repo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	TokenUtil tokenUtil;
	
	@Override
	public RegisterDto register(RegisterDto employeeDto) {
		Optional<Model> empModel = repo.findByemployeeName(employeeDto.getEmployeeName());
		if (empModel.isPresent()) {
			throw new UserException("Username already exists!!");
		}
		Model registeredEmployee = modelMapper.map(employeeDto, Model.class);
		repo.save(registeredEmployee);

		System.out.println("Successfully registered");
		return employeeDto;
	}
	
	@Override
	public List<RegisterDto> getAllEmployee() {
//		if(role.equals("Admin")) {
			return repo.findAll().stream().map(employee -> modelMapper.map(employee, RegisterDto.class))
					.collect(Collectors.toList());
//		}
//		else {
//			throw new UserException("You are not Admin");
//		}
	}
	
	@Override
	public RegisterDto deleteEmployee(int id) throws UserException {

		if(repo.findById(id).isPresent()) {

			Optional<Model> emp = repo.findById(id);
			RegisterDto empDto = modelMapper.map(emp.get(), RegisterDto.class);
			repo.deleteById(id);
			return empDto;

		} else {
			throw new UserException("Employee not found");
		}
	}
	
	@Override
	public RegisterDto getEmployeeByName(String name) {
		Optional<Model> findByName = repo.findByEmployeeName(name);
		if (findByName.isEmpty()) {
			throw new UserException("User does not exist");
		}
		RegisterDto employeeDto = modelMapper.map(findByName.get(), RegisterDto.class);
		return employeeDto;

	}
	@Override
	public RegisterDto getEmployee(int id) {
		Optional<Model> emp = repo.findById(id);
		RegisterDto empDto = modelMapper.map(emp.get(),RegisterDto.class);
		System.out.println("get employee : "+id+" "+empDto.getEmployeeName());
		return empDto;
	}
	
	@Override
	public RegisterDto update(RegisterDto registerDto, String token) {
		LoginDto loginUser = tokenUtil.decode(token);
		Model empModel = modelMapper.map(registerDto, Model.class);

		if (repo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).isPresent()
				&& repo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).get().isLogin()) {
			empModel.setEmployeeId(repo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).get().getEmployeeId());
			//empModel.setIsVerified(true);
			empModel.setLogin(true);
			repo.save(empModel);
			System.out.println("Updated Successfully");

			return registerDto;
		} else {
			throw new UserException("Please Login!");
		}
	}
	@Override
	public Optional<Model> deleteEmployeeName(String employeeName) {
		Optional<Model> empModel = repo.findByEmployeeName(employeeName);
		if (empModel.isEmpty()) {
			throw new UserException("Employee doesn't exist!!!");
		}
		repo.deleteByEmployeeName(employeeName);
		return empModel;

	}
	
	@Override
	public String loginByToken(LoginDto loginDto) {
		Optional<Model> user = repo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		if (user.isEmpty()) {
			Optional<Model> userEmail = repo.findByEmail(loginDto.getEmail());
			Optional<Model> userPassword = repo.findByPassword(loginDto.getPassword());
			if (userEmail.isEmpty()) {
				throw new UserException("Email is incorrect");
			} else if (userPassword.isEmpty()) {
				throw new UserException("Password is incorrect");
			}
		}
		String token = tokenUtil.generateToken(loginDto);

		user.get().setLogin(true);
		repo.save(user.get());
		System.out.println("Check the user is login or not " + user.get().isLogin());

		return token;
	}

	@Override
	public String logoutByToken(String token) {
		LoginDto loginDTO = tokenUtil.decode(token);
		Optional<Model> checkUserDetails = repo.findByEmailAndPassword(loginDTO.getEmail(),
				loginDTO.getPassword());
		LoginDto logout = modelMapper.map(checkUserDetails, LoginDto.class);
		checkUserDetails.get().setLogin(false);
		repo.save(checkUserDetails.get());
		return "logout successful";
	}

	
	
	
}
