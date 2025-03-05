package com.sygec.sygec.service;

import java.util.List;

import com.sygec.sygec.dto.AdminDto;




public interface AdminService {
	
	AdminDto save(AdminDto adminDto);
	AdminDto updateAdmin(AdminDto userDto, String uuid);
	AdminDto changeEmailAndPassword(String email, String password);
	AdminDto findById(String uuid);
	List<AdminDto> findAll();
	void deleteEntity(String uuid);
	AdminDto getMotDePass(String newPassowd);
	AdminDto getAdminByEmail(String email);
	AdminDto updatePassword(String email, String newPassword);
	void  deconnecterAdmin();
	List<AdminDto> getByUserOnLigne();


}
