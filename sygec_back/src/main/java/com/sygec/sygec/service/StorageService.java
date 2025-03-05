package com.sygec.sygec.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sygec.sygec.model.StoredFile;
import com.sygec.sygec.repository.StorageRepository;
import com.sygec.sygec.serviceImpl.ImageUtils;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {
//        StoredFile imageData = repository.save(StoredFile.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .bytes(ImageUtils.compressImage(file.getBytes())).build());
    	StoredFile storedFile = new StoredFile();
    	storedFile.setName(file.getOriginalFilename());
    	storedFile.setBytes(ImageUtils.compressImage(file.getBytes()));
    	storedFile.setType(file.getContentType());
    	StoredFile imageData = repository.save(storedFile);
        if (imageData != null) 
        {
            return "Image enregistré avec succes " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<StoredFile> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getBytes());
        return images;
    }

    public byte[] searchImage(String uuid) {
        Optional<StoredFile> dbImageData = repository.findById(uuid);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getBytes());
        return images;
    }
}
