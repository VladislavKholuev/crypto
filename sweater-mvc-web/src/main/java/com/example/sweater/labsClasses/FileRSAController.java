package com.example.sweater.labsClasses;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FileRSAController {

//     public static void main(String[] args) {
//            int N = Integer.parseInt("10000");
//            System.out.println("start");
//            RSA key = new RSA(N);
//            System.out.println(key);
//
//            // create random message, encrypt and decrypt
////            BigInteger message = new BigInteger(N-1, random);
////            System.out.println(message);
//            //// create message by converting string to integer
//             String s = "text1234567890123456890\n";
//             byte[] bytes = s.getBytes();
//             BigInteger message = new BigInteger(bytes);
//
//            BigInteger encrypt = key.encrypt(message);
//
//            BigInteger decrypt = key.decrypt(encrypt);
//            System.out.println("message   = " + message);
//            System.out.println("encrypted = " + encrypt+"\n\n\n");
//            System.out.println(new String(encrypt.toByteArray(), StandardCharsets.UTF_8));
//            System.out.println("\n\n\n");
//            System.out.println("decrypted = " + decrypt);
//
//            System.out.println(new String(decrypt.toByteArray(), StandardCharsets.UTF_8));
//        }
    @RequestMapping(value="/encodeRSA", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileEncode(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file, Map<String, Object> model){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                RSA key = new RSA(bytes.length*9);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-encoded")));
                BigInteger message = new BigInteger(bytes);
                BigInteger encrypt = key.encrypt(message);

                ArrayList<byte[]> listBad = new ArrayList<>();
                byte[] byte256 = new byte[256];
                int i=0;
                for(byte b:bytes){
                    if(i<256) {
                        byte256[i] = bytes[i];
                        i++;
                    }else{
                        listBad.add(byte256.clone());
                        i=0;
                    }
                }
                BigInteger privateKey = key.getPrivateKey();
                BigInteger modulus = key.getModulus();
                stream.write(encrypt.toByteArray());
                stream.close();
                ArrayList<BigInteger> list = new ArrayList<>();
                list.add(privateKey);
                list.add(modulus);
                model.put("num", list);
                return "private key: {"+list.get(0)+"} \n\n  " +
                        "modulus {"+list.get(1)+"}";
            } catch (Exception e) {
                return "Вам не удалось загрузить RSA" + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }

    @RequestMapping(value="/decodeRSA", method=RequestMethod.POST)
    public @ResponseBody String handleFileDecode(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam String number,
                                                 @RequestParam String number1,
                                                 Map<String, Object> model){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                RSA key = new RSA(5000);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-decoded")));
                BigInteger encrypt = new BigInteger(bytes);
                BigInteger num_key = new BigInteger(number);
                BigInteger num_modulus = new BigInteger(number1);

                BigInteger decrypt = key.decrypt(encrypt,num_key,num_modulus);
                stream.write(decrypt.toByteArray());
                stream.close();
                return "Вы удачно загрузили RSA" + name + " в " + name + "-decoded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить RSA" + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }
}
