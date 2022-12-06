package com.example.sweater.labsClasses;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "Вы можете загружать файл с использованием того же URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                byte[] keyByte= new byte[bytes.length];
                for(int i=0;i<keyByte.length;i++){
                    keyByte[i]=(byte)i;
                }
                Vernam ver = new Vernam(keyByte);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(ver.Decode(ver.Encode(bytes)));
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }

    @RequestMapping(value="/encode", method=RequestMethod.POST)
    public @ResponseBody String handleFileEncode(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("key") String key){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                byte[] keyByte= key.getBytes();
//                for(int i=0;i<keyByte.length;i++){
//                    keyByte[i]=(byte)i;
//                }
                Vernam ver = new Vernam(keyByte);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-encoded")));
                stream.write(ver.Encode(bytes));
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-encoded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }

    @RequestMapping(value="/decode", method=RequestMethod.POST)
    public @ResponseBody String handleFileDecode(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("key") String key){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                byte[] keyByte= key.getBytes();
//                for(int i=0;i<keyByte.length;i++){
//                    keyByte[i]=(byte)i;
//                }
                Vernam ver = new Vernam(keyByte);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-decoded")));
                stream.write(ver.Decode(bytes));
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-decoded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }
}