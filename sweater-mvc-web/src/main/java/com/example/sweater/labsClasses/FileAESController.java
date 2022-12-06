package com.example.sweater.labsClasses;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

@Controller
public class FileAESController {
    private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    private static final byte[] iv = {11, 22, 33, 44, 99, 88, 77, 66,11, 22, 33, 44, 99, 88, 77, 66};

    @RequestMapping(value = "/encodeAES", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileEncode(//@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("number1") String number1,
                            @RequestParam("number2") String number2,
                            @RequestParam("numberMode") String numberMode) {


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                try {
                    SecretKey key = KeyGenerator.getInstance("AES").generateKey();

                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);


                    if (numberMode.equals("CBC")) {
                        encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("ECB")) {
                        encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
                    } else if (numberMode.equals("OFB")) {
                        encryptCipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("CFB")) {
                        encryptCipher = Cipher.getInstance("AES/CFB/NoPadding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else
                        return "метод не актуален";

                    encrypt((file.getInputStream()), new FileOutputStream("encode AES" + file.getOriginalFilename()));
//                    System.out.println(key.getEncoded());
                    // method to decrypt encrypted file to clear text file
//                    decrypt(new FileInputStream(cipherTextFile), new FileOutputStream(clearTextNewFile));

                    try (FileWriter writer = new FileWriter(number1+"file.txt", false)) {
                        writer.write(Base64.getEncoder().encodeToString(key.getEncoded()));
                        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
                        writer.flush();
                    }
                    System.out.println(key.getEncoded());
                    System.out.println("DONE");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                        | InvalidAlgorithmParameterException | IOException e) {
                    e.printStackTrace();
                }

                return "Вы удачно загрузили AES" + file.getOriginalFilename() ;
            } catch (Exception e) {
                return "Вам не удалось загрузить AES" + file.getOriginalFilename()  + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename()  + " потому что файл пустой.";
        }

    }

    @RequestMapping(value = "/decodeAES", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileDecode(//@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("number1") String number1,
                            @RequestParam("number2") String number2,
                            @RequestParam("numberMode") String numberMode) {


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                try {
                    SecretKey key = null;
                    //DecodeBlok(bytes);

                    try {
                        File fileKey = new File(number1+"file.txt");
                        FileReader fr = new FileReader(fileKey);
                        BufferedReader reader = new BufferedReader(fr);
                        String line = reader.readLine();
                        System.out.println(line);
                        byte[] decodedKey = Base64.getDecoder().decode(line);
                        key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
                    if (numberMode.equals("CBC")) {
                        decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("ECB")) {
                        decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key);
                    } else if (numberMode.equals("OFB")) {
                        decryptCipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("CFB")) {
                        decryptCipher = Cipher.getInstance("AES/CFB/NoPadding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else
                        return "метод не актуален";

                    decrypt((file.getInputStream()), new FileOutputStream("decoded AES"+file.getOriginalFilename() ));

                    System.out.println("DONE");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                        | InvalidAlgorithmParameterException | IOException e) {
                    e.printStackTrace();
                }

                return "Вы удачно загрузили AES" + file.getOriginalFilename()  ;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + file.getOriginalFilename()  + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename()  + " потому что файл пустой.";
        }

    }

    private static void encrypt(InputStream is, OutputStream os) throws IOException {

        // create CipherOutputStream to encrypt the data using encryptCipher
        os = new CipherOutputStream(os, encryptCipher);
        writeData(is, os);
    }

    private static void decrypt(InputStream is, OutputStream os) throws IOException {

        // create CipherOutputStream to decrypt the data using decryptCipher
        is = new CipherInputStream(is, decryptCipher);
        writeData(is, os);
    }

    // utility method to read data from input stream and write to output stream
    private static void writeData(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int numRead = 0;
        // read and write operation
        while ((numRead = is.read(buf)) >= 0) {
            os.write(buf, 0, numRead);
        }
        os.close();
        is.close();
    }
}
