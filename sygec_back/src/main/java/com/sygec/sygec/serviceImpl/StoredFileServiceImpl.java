package com.sygec.sygec.serviceImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sygec.sygec.model.StoredFile;
import com.sygec.sygec.repository.StoredFilesRepository;
import com.sygec.sygec.service.StoredFileService;

@Service
public class StoredFileServiceImpl implements StoredFileService {

	Logger logger = LoggerFactory.getLogger(StoredFileServiceImpl.class);
	
    @Autowired
    private StoredFilesRepository fileDBRepository;
    
    @Override
    public StoredFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());       
        StoredFile StoredFile = new StoredFile(fileName, file.getContentType(), file.getBytes(),"");
        return fileDBRepository.save(StoredFile);
    }
    
    @Override
    public StoredFile getFile(String uuid) {
        return fileDBRepository.findById(uuid).get();
    }
    
    @Override
    public StoredFile getFileById(String uuid) {
        return fileDBRepository.findByUuid(uuid);
    }

	@Override
	public StoredFile storeAsDataUrl(String dataUrl) {
		logger.info("\n------------------------------\n");
//		logger.info(storedFileDto.getName());
		logger.info("\n-------------------------------\n");
		StoredFile StoredFile = new StoredFile("photo"+new Date(), "image/jpeg", null, dataUrl);
        return fileDBRepository.save(StoredFile);
	}

	@Override
	public StoredFile getAsDataUrl(String uuid) {
		StoredFile storeFile = fileDBRepository.findByUuid(uuid);
		return storeFile;
	}

	


}
