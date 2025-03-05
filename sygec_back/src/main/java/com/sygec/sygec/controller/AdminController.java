package com.sygec.sygec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sygec.sygec.dto.AdminDto;
import com.sygec.sygec.dto.JwtRequest;
import com.sygec.sygec.service.AdminService;







//@CrossOrigin(origins = "*")
@RestController
@CrossOrigin
public class AdminController {

	
	@Autowired
	AdminService  adminService;
	
	@GetMapping("/admin/password/{password}")
	public AdminDto getPassword(@PathVariable String password) {
		return adminService.getMotDePass( password);
	}
	
	@PostMapping("/admin")
    public AdminDto newAdminDto(@RequestBody AdminDto newAdminDto) {
        return adminService.save(newAdminDto);
    }
	
	@PutMapping("/admin/{uuid}")
    public AdminDto updateAdminDto(@RequestBody AdminDto adminDto, @PathVariable String uuid) {
    	return adminService.updateAdmin(adminDto, uuid);       
    }
	
	@PostMapping("/admin/changePassword")
    public AdminDto changeEmailAndPassword(@RequestBody JwtRequest authenticationRequest) {
    	return adminService.changeEmailAndPassword(authenticationRequest.getUsername(), authenticationRequest.getPassword());       
    }
	
	
	@GetMapping("/admin/{uuid}")
	public AdminDto findById(@PathVariable String uuid){
		return adminService.findById(uuid);
	}
	
	@GetMapping("/admin/byEmail/{email}")
	public AdminDto findByEmail(@PathVariable String email){
		return adminService.getAdminByEmail(email);
	}
	
	
	@GetMapping("/admin/all")
	public List<AdminDto> findAll(){
		return adminService.findAll();
	}
	
//	@DeleteMapping("/Admin/delete/{uuid}")	
//	void deleteEntity(@PathVariable String uuid) {
//		adminService.deleteEntity(uuid);
//	}
	
	
}
