package com.example.sweater.labsClasses;

import java.io.*;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

//import jdk.internal.util.xml.impl.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Controller
public class DES {

    private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    private static final byte[] iv = {11, 22, 33, 44, 99, 88, 77, 66};

//    String cipherTextFile = "C:\\Users\\mi\\IdeaProjects\\sweater-mvc-web\\cipher.txt";
//    String clearTextNewFile = "C:\\Users\\mi\\IdeaProjects\\sweater-mvc-web\\source-new.txt";

    @RequestMapping(value = "/encodeDES", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileEncode(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("number1") String number1,
                            @RequestParam("number2") String number2,
                            @RequestParam("numberMode") String numberMode) {
        if (number1.length() != 8 || number2.length() != 8) {
            return "key and C0 must be 8 bytes";
        }

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // EncodeBlok(bytes);
                try {
                    SecretKey key = KeyGenerator.getInstance("DES").generateKey();

                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

                    // get Cipher instance and initiate in encrypt mode
                    if (numberMode.equals("CBC")) {
                        encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("ECB")) {
                        encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
                    } else if (numberMode.equals("OFB")) {
                        encryptCipher = Cipher.getInstance("DES/OFB32/PKCS5Padding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("CFB")) {
                        encryptCipher = Cipher.getInstance("DES/CFB8/NoPadding");
                        encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                    } else
                        return "метод не актуален";

                    // method to encrypt clear text file to encrypted file
                    System.out.println("here");
                    System.out.println(file);
                    encrypt((file.getInputStream()), new FileOutputStream(name + "-encoded"));
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

                return "Вы удачно загрузили DES" + name + " в " + name + "-encoded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить DES" + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

    }

    @RequestMapping(value = "/decodeDES", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileDecode(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("number1") String number1,
                            @RequestParam("number2") String number2,
                            @RequestParam("numberMode") String numberMode) {
        if (number1.length() != 8 || number2.length() != 8) {
            return "key and C0 must be 8 bytes";
        }

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
                        key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
                        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
                    if (numberMode.equals("CBC")) {
                        decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("ECB")) {
                        decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key);
                    } else if (numberMode.equals("OFB")) {
                        decryptCipher = Cipher.getInstance("DES/OFB32/PKCS5Padding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else if (numberMode.equals("CFB")) {
                        decryptCipher = Cipher.getInstance("DES/CFB8/NoPadding");
                        decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    } else
                        return "метод не актуален";

                    decrypt((file.getInputStream()), new FileOutputStream(name + "-decoded"));

                    System.out.println("DONE");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                        | InvalidAlgorithmParameterException | IOException e) {
                    e.printStackTrace();
                }

                return "Вы удачно загрузили DES" + name + " в " + name + "-decoded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
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

    private ArrayList<Long> KeySchedule(long key) {
        long p = Permute(key, PC1);
        long c = Left28(p);
        long d = Right28(p);

        ArrayList<Long> schedule = new ArrayList<Long>();

        ArrayList<Long> result = new ArrayList<>();
        struct struct = new struct();
        for (int i = 1; i <= LeftShifts.length; i++) {
            int finalI = i;

            long Left = LeftShift56(schedule.get(finalI - 1), LeftShifts[finalI - 1]);
            long Right = LeftShift56(schedule.get(finalI - 1), LeftShifts[finalI - 1]);

        }
        for (int i = 0; i < schedule.size(); i++) {
            long joined = Concat56(schedule.get(i), schedule.get(i));
            long permuted = Permute(joined, PC2);

            result.add(permuted);
        }
        return result;
    }

    private long Left28(long val) {
        return val & 0xFFFFFFF0;
    }

    private long Right28(long val) {
        return (val << 28) & 0xFFFFFFF0;
    }

    private long Concat56(long left, long right) {
        return (left & 0xFFFFFFF0) | ((right & 0xFFFFFFF0) >> 28);
    }

    long _key = 0;

    struct Pair(long Left, long Right) {
        long _Left = Left;
        long _Right = Right;
        return null;
    }

    public long EncodeBlok(byte[] blok) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.put(blok);
        buf.flip();
        long ulBlok = buf.getLong();
        long ip = Permute(ulBlok, IP);
        ArrayList<Long> schedule = KeySchedule(_key);

        long Left = ip & 0xFFFFFFFF;

        long Right = (ip & 0x00000000FFFFFFFF) << 32;

        for (int i = 0; i < 16; i++) {
            {
                 Left = Right;
                 Right = Left ^ F(Left, schedule.get(i + 1));
            }

        }

        long joined = Left | (Right >> 32);
        return (Permute(joined, IPINV));
    }

    private long LeftShift56(long val, int count) {
        for (int i = 0; i < count; i++) {
            long msb = val & 0x80000000;
            val = (val << 1) & 0xFFFFFFE0 | msb >> 27;
        }

        return val;
    }

    public long DecodeBlok(byte[] blok) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.put(blok);
        buf.flip();
        long ulBlok = buf.getLong();
        long ip = Permute(ulBlok, IP);
        ArrayList<Long> schedule = KeySchedule(_key);


        long Right = ip & 0xFFFFFFFF;
        long Left = (ip & 0x00000000FFFFFFFF) << 32;


        for (int i = 0; i < 16; i++) {

            Right = Left;
            Left = Right ^ F(Left, schedule.get(i + 1));

        }

        long joined = Left | (Right >> 32);


        return (Permute(joined, IPINV));
    }

    private long F(long right, long key) {
        long e = Permute(right, E);

        long x = e ^ key;

        ArrayList bs = Split(x);

        byte[] boxLookup = new byte[256];

        for (int i = 0; i < 8; i++) {
            boxLookup = SBoxLookup((byte) bs.get(i), i);
        }

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(boxLookup);
        buffer.flip();

        long result = Permute(buffer.getLong(), P);

        return result;
    }

    private byte[] SBoxLookup(byte val, int table) {
        int index = ((val & 0x80) >> 2) | ((val & 0x04) << 2) | ((val & 0x78) >> 3);
        return SBoxes[table];
    }

    private ArrayList<Byte> Split(long val) {
        ArrayList<Byte> result = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            result.add((byte) ((val & 0xFC00000) >> 56));

            val <<= 6;
        }

        return result;
    }

    private long GetBit(long a, int k) {
        return (a & (1L << k)) >> k;
    }

    private long Permute(long val, int[] changes) {
        int[] cloneChanges = (int[]) changes.clone();
        Collections.reverse(Arrays.asList(changes));

        for (int i = 0; i < cloneChanges.length; i++)
            cloneChanges[i]--;


        return TransposBits(val, cloneChanges);
    }

    private long TransposBits(long n, int[] tranpos) {
        long result = 0;
        int i = 0;

        int[] cloneTranspos = (int[]) tranpos.clone();
        Collections.reverse(Arrays.asList(cloneTranspos));
        for (int item : cloneTranspos) {
            result |= (BitOperation.GetBit((int) n, item) << i);
            i++;
        }

        return result;
    }

    public static int[] PC1 =
            {
                    57, 49, 41, 33, 25, 17, 9,
                    1, 58, 50, 42, 34, 26, 18,
                    10, 2, 59, 51, 43, 35, 27,
                    19, 11, 3, 60, 52, 44, 36,
                    63, 55, 47, 39, 31, 23, 15,
                    7, 62, 54, 46, 38, 30, 22,
                    14, 6, 61, 53, 45, 37, 29,
                    21, 13, 5, 28, 20, 12, 4
            };

    public static int[] PC2 =
            {
                    14, 17, 11, 24, 1, 5,
                    3, 28, 15, 6, 21, 10,
                    23, 19, 12, 4, 26, 8,
                    16, 7, 27, 20, 13, 2,
                    41, 52, 31, 37, 47, 55,
                    30, 40, 51, 45, 33, 48,
                    44, 49, 39, 56, 34, 53,
                    46, 42, 50, 36, 29, 32
            };

    public static int[] IP =
            {
                    58, 50, 42, 34, 26, 18, 10, 2,
                    60, 52, 44, 36, 28, 20, 12, 4,
                    62, 54, 46, 38, 30, 22, 14, 6,
                    64, 56, 48, 40, 32, 24, 16, 8,
                    57, 49, 41, 33, 25, 17, 9, 1,
                    59, 51, 43, 35, 27, 19, 11, 3,
                    61, 53, 45, 37, 29, 21, 13, 5,
                    63, 55, 47, 39, 31, 23, 15, 7
            };

    public static int[] IPINV =
            {
                    40, 8, 48, 16, 56, 24, 64, 32,
                    39, 7, 47, 15, 55, 23, 63, 31,
                    38, 6, 46, 14, 54, 22, 62, 30,
                    37, 5, 45, 13, 53, 21, 61, 29,
                    36, 4, 44, 12, 52, 20, 60, 28,
                    35, 3, 43, 11, 51, 19, 59, 27,
                    34, 2, 42, 10, 50, 18, 58, 26,
                    33, 1, 41, 9, 49, 17, 57, 25
            };

    public static int[] E =
            {
                    32, 1, 2, 3, 4, 5,
                    4, 5, 6, 7, 8, 9,
                    8, 9, 10, 11, 12, 13,
                    12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21,
                    20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29,
                    28, 29, 30, 31, 32, 1
            };

    public static int[] P =
            {
                    16, 7, 20, 21,
                    29, 12, 28, 17,
                    1, 15, 23, 26,
                    5, 18, 31, 10,
                    2, 8, 24, 14,
                    32, 27, 3, 9,
                    19, 13, 30, 6,
                    22, 11, 4, 25
            };

    public static byte[][] SBoxes =
            {
                    {
                            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
                            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
                    },
                    {
                            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
                            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
                    },
                    {
                            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
                            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
                    },
                    {
                            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
                            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
                    },
                    {
                            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
                            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
                    },
                    {
                            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
                            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
                    },
                    {
                            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
                            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
                    },
                    {
                            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
                            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
                    }
            };

    public static int[] LeftShifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    public class struct {
        public long _Left;
        public long _Right;

    }

}