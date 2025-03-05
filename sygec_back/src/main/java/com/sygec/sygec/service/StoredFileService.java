package com.sygec.sygec.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.sygec.sygec.model.StoredFile;






public interface StoredFileService {

    StoredFile store(MultipartFile file) throws IOException;
    

    StoredFile getFile(String uuid);

    StoredFile getFileById(String uuid);

	StoredFile storeAsDataUrl(String dataUrl);

	StoredFile getAsDataUrl(String id);
    
}
