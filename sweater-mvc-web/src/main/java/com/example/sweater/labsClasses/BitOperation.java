package com.example.sweater.labsClasses;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class BitOperation {



    static public int GetBit(int a, int k)
    {
//        System.out.println((a.and(BigInteger.ONE.shiftLeft(k))).shiftRight(k));
        return (a & (1 << (int)k)) >> (int)k;
    }

    static public BigInteger ChangeBit(BigInteger a, int k)
    {
        return a.xor(BigInteger.ONE.shiftLeft(k));
    }

    static public int SwapBit(int a, int i, int j)
    {
        int bi = GetBit(a, i);
        int bj = GetBit(a, j);
        return (a & ~(1 << (int)j) & ~(1 << (int)i)) | (bi << (int)j) | (bj << (int)i);
    }

    static public int ZeroSmallBits(int a, int m)
    {
        return a & ~((1 << (int)m) - 1);
    }

    static public Integer GlueBits(int a, int i, int bitDepth)
    {
        System.out.println(bitDepth);
        return (a >> (bitDepth - i) << i) | (a & ((1 << i) - 1));
    }

    static public Integer MiddleBits(int a, int i, int bitDepth)
    {
        return (a & ZeroSmallBits(a & ((1 << (int)(bitDepth - i + 1)) - 1), i)) >> (int)i;
    }

    static public BigInteger SwapBytes(BigInteger a, int i, int j)
    {
        BigInteger bi = (a.and ((BigInteger.ONE.shiftLeft(8 * (i + 1))).subtract(BigInteger.ONE) )) .shiftRight (8 * i);
        BigInteger bj = (a.and((BigInteger.ONE .shiftLeft (8 * (j + 1))).subtract(BigInteger.ONE) )).shiftRight (8 * j);
        a = a .xor (bi.shiftLeft (i * 8));
        a = a .xor (bj.shiftLeft (j * 8));

        return (a .or (bi .shiftLeft (j * 8)) .or (bj .shiftLeft (i * 8)));
    }

    static public BigInteger MaxDegreeBin(BigInteger n)
    {
        return n.and(n.negate());
    }

    static public BigInteger InsideDiapason(BigInteger x)
    {
        BigInteger p = BigInteger.valueOf(0);

        while (x.compareTo( BigInteger.ONE)>0)
        {
            x = x.shiftRight(1);
            p=p.add(BigInteger.ONE);
        }

        return p;
    }

    static public int whithInRange(int x)
    {
        int p = 0;

        while (x>1)
        {
            x = x>>(1);
            p+=1;
        }

        return p;
    }

    static public int AutoXOR(int a, int depthBit)
    {
        depthBit = 1 << (whithInRange((a))+((1)));
        while (depthBit > 1) {
            depthBit >>= 1;
            a = (a >> depthBit) ^ (a & ((1 << (depthBit)) - 1));
        }

        return a;
//        int result = 0;
//
//        for (int i = 0; i < depthBit; i++)
//        {
//            result ^= GetBit(a, 1);
//            a >>= 1;
//        }
//        if(result==0)
//            return result+1;
//        else
//        return result-1;
    }

    static public int CycleShiftLeft(int x, int p, int n)
    {
        n = n - n / p * p;

        return ((x << (int)n) | (x >> (int)(p - n))) & ((1 << (int)p) - 1);
    }

    static public int CycleShiftRight(int x, int p, int n)
    {

        n = n - n / p * p;

        return ((x >> (int)n) | (x << (int)(p - n))) & ((1 << (int)p) - 1);
    }

    static public int TransposBits(int n, int[] tranpos)
    {
        int result = 0;
        int i = 0;

        int[] cloneTranspos = (int[])tranpos.clone();

        Collections.reverse(Arrays.asList(cloneTranspos.clone()));

        for (int item : cloneTranspos)
        {
            result |= (GetBit(n, item) << i);
            i++;
        }
        System.out.println(Integer.toBinaryString(result));
        return result;
    }

    static public byte[] BytesXOR(byte[] first, byte[] second)
    {
        byte[] result = new byte[first.length];

        for (int i = 0; i < first.length; i++)
            result[i] = (byte)(first[i] ^ second[i]);

        return result;
    }

    static public int AXOR(int a, int depthBit)
    { AutoXOR(2,1);
        int result = 0;

        for (int i = 0; i < depthBit; i++)
        {
            result ^= GetBit(a, 1);
            a >>= 1;
        }
        if(result==0)
            return result+1;
        else
        return result-1;
    }
}
