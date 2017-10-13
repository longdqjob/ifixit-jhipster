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

    public static final String UPLOAD_DIR_MATERIAL = "upload-dir" + File.separator + "material";

    public void setPath(Path path) {
        this.rootLocation = path;
    }

    public String getPath() {
        return this.rootLocation.toString();
    }

    public boolean createDirIfNotExits(String path) {
        String fullPath = this.rootLocation.toString() + File.separator + path;
        File theDir = new File(fullPath);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                log.info("---CREATE mkdir: " + fullPath + " success!");
                result = true;
            } catch (SecurityException se) {
                log.error("ERROR createDirIfNotExits: " + fullPath, se);
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

    //Return 0: fileName, 1: url
    public String[] store(MultipartFile file, String path) {
        try {
            String[] rtn = new String[2];
            createDirIfNotExits(path);
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            log.info("-------fileName getOriginalFilename: " + fileName);
            fileName = fileName.substring(0, fileName.lastIndexOf(ext) - 1);
            log.info("-------fileName: " + fileName);
            fileName = WebUtil.toPrettyURL(WebUtil.removeAscii(fileName));
            fileName += "." + ext;
            log.info("-------fileName ext: " + fileName);
            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
                //File Image
                Files.copy(file.getInputStream(), this.rootLocation.resolve(TMP_FILE + fileName));
                
                //resize Img
                ImageUtil.resize(getPath() + File.separator + StorageService.TMP_FILE + fileName, getPath() + File.separator + path + File.separator + fileName);
                rtn[0] = fileName;
                rtn[1] = ("/" + path + "/" + fileName).replaceAll("\\\\", "/");
                return rtn;
            } else {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
                rtn[0] = fileName;
                rtn[1] = "/" + fileName; //file.getOriginalFilename();
                return rtn;
            }
        } catch (Exception e) {
            log.error("ERROR store: ", e);
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
            log.error("ERROR loadFile: ", e);
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
