/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifixit.webapp.service;

import com.ifixit.webapp.web.rest.util.ImageUtil;
import com.ifixit.webapp.web.rest.util.WebUtil;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.activation.MimetypesFileTypeMap;
import liquibase.util.file.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thuyetlv
 */
@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public static final String UPLOAD_DIR = "upload-dir";
    public static final String TMP_FILE = "tmp_";
//    private final Path rootLocation = Paths.get(UPLOAD_DIR);
    private Path rootLocation = Paths.get(UPLOAD_DIR);

    public void setPath(Path path) {
        this.rootLocation = path;
    }

    public String getPath() {
        return this.rootLocation.toString();
    }

    public boolean createDirIfNotExits() {
        File theDir = new File(this.rootLocation.toString() + File.separator + UPLOAD_DIR);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                log.error("ERROR createDirIfNotExits: ", se);
                return false;
            }
            return result;
        }
        return true;
    }
    
    public String getPathFile(String fileName) {
        return this.rootLocation.toString() + File.separator + fileName;
    }

    public boolean isImageFile(File f) {
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    public String store(MultipartFile file) {
        try {
            String fileName = WebUtil.removeAscii(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
                //File Image
                Files.copy(file.getInputStream(), this.rootLocation.resolve(TMP_FILE + fileName));

                //resize Img
                ImageUtil.resize(getPath() + File.separator + StorageService.TMP_FILE + fileName, getPath() + File.separator + fileName);
                return fileName;
            } else {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
                return file.getOriginalFilename();
            }
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}
