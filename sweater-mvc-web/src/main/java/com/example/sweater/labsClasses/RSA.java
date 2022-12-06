package com.example.sweater.labsClasses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
//неизменяемый класс, для работы с целыми числами
import java.math.BigInteger;
//класс, беспечивающий криптостойкую генерацию случайных чисел
import java.security.SecureRandom;
//для работы с файлом
import java.io.*;
import java.awt.Desktop;
/**
 * Реализация алгоритма шифрования RSA.
 */
public class RSA {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N / 2, random);
        BigInteger q = BigInteger.probablePrime(N / 2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }


    BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    BigInteger decrypt(BigInteger encrypted, BigInteger privateK, BigInteger modul) {
        return encrypted.modPow(privateK, modul);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }
}