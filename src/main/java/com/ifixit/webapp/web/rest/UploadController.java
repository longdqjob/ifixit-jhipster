/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifixit.webapp.web.rest;

import com.google.gson.Gson;
import com.ifixit.webapp.service.StorageService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 *
 * @author thuyetlv
 */
@RestController
@RequestMapping("/api")
public class UploadController {

    private final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    StorageService storageService;
    List<String> files = new ArrayList<String>();

    @Autowired
    ServletContext context;

//    @PostMapping("/post")
    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {

            //Save File
            Path path = Paths.get(context.getRealPath("/"));
            log.info("-----path: " + path.toString());
            storageService.setPath(path);

            String[] store = storageService.store(file, StorageService.UPLOAD_DIR_MATERIAL);
            String fileName = store[0];
            String fileUrl = store[1];
            files.add(fileName);

            JSONObject jSONObject = new JSONObject();
            jSONObject.put("fileName", fileName);
            jSONObject.put("url", fileUrl);
//            Resource fileSave = storageService.loadFile(fileName);
//            jSONObject.put("path", fileSave.getURI());

            Gson gson = new Gson();

            ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(jSONObject),
                    new HttpHeaders(),
                    HttpStatus.OK);
            return responseEntity;

//            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(jSONObject));
        } catch (Exception e) {
            log.error("ERROR handleFileUpload: ", e);
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
                    new HttpHeaders(),
                    HttpStatus.BAD_GATEWAY);
            return responseEntity;
        }
    }

    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(UploadController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
