package com.example.sweater.labsClasses;

import java.math.BigInteger;
import java.util.ArrayList;

public class Algebra {
    public static ArrayList<Long> GetPrimeNumbers(long m)
    {
        ArrayList<Long> result = new ArrayList<>();
        result.add((long) 2);
        for (long i = 3; i < (m + 1); i += 2)
        {
            if ((i > 10) && (i % 10 == 5))
                continue;

            boolean f = false;
            for (int j = 0; j < result.size(); j++)
            {
                if (result.get(j) * result.get(j) - 1 > i)
                {
                    f = true;
                    result.add(i);
                    break;
                }

                if (i % result.get(j) == 0)
                {
                    f = true;
                    break;
                }
            }

            if (!f)
                result.add(i);
        }

        return result;
    }

    public static long Euler(int n)
    {
        int result = n;

        for (int i = 2; i * i <= n; ++i)
            if (n % i == 0)
            {
                while (n % i == 0)
                    n /= i;
                result -= result / i;
            }
        if (n > 1)
            result -= result / n;

        return result;
    }

    public static long Euclid(long a, long b)
    {
        while (b > 0)
        {
            a %= b;
            long c = a;
            a = b;
            b=c;
//            (a, b) = (b, a);
        }

        return a;
    }

    public static BigInteger PowMod(BigInteger a, BigInteger b, BigInteger m)
    {
        BigInteger res = BigInteger.valueOf(1);
        while (b.compareTo(BigInteger.ZERO) > 0)
        {
            if ((b.and(BigInteger.ONE)).compareTo(BigInteger.ONE) == 0)
                res = (res.multiply((a))).remainder((m));
            a = (a.multiply(a)).remainder(m);
            b = b.shiftRight(1);
        }
        return res;
    }

    public static ArrayList<Long> Factorization(long n)
    {
        ArrayList<Long> ans = new ArrayList<>();

        for (long i = 2; i * i <= n; ++i)
            if (n % i == 0)
            {
                while (n % i == 0)
                {
                    n /= i;
                    ans.add(i);
                }
            }
        if (n > 1)
        {
            ans.add(n);
        }

        return ans;
    }
//  вычеты по модулю
    public static ArrayList<Long> RDS(long m)
    {
        ArrayList<Long> result = new ArrayList<>();

        for (long i = 2; i < m; i++)
            if (IsCoprime(m, i))
                result.add(i);

        return result;
    }

//    public static ArrayList<Long> EuclidExBin(long a, long b, long x,long y) {
//        long g = 1;
//
//        while (((a & 1) == 0) && ((b & 1) == 0)) {
//            a >>= 1;
//            b >>= 1;
//            g <<= 1;
//        }
//
//        long u = a;
//        long v = b;
//        long A = 1;
//        long B = 0;
//        long C = 0;
//        long D = 1;
//
//        while (u != 0) {
//            while ((u & 1) == 0) {
//                u >>= 1;
//                if (((A & 1) == 0) && ((B & 1) == 0)) {
//                    A >>= 1;
//                    B >>= 1;
//                } else {
//                    A = (A + b) >> 1;
//                    B = (B - a) >> 1;
//                }
//            }
//
//            while ((v & 1) == 0) {
//                v >>= 1;
//                if (((C & 1) == 0) && ((D & 1) == 0)) {
//                    C >>= 1;
//                    D >>= 1;
//                } else {
//                    C = (C + b) >> 1;
//                    D = (D - a) >> 1;
//                }
//            }
//
//            if (u >= v) {
//                u -= v;
//                A -= C;
//                B -= D;
//            } else {
//                v -= u;
//                C -= A;
//                D -= B;
//            }
//        }
//
//        x = C;
//        y = D;
//        ArrayList<Long> list = new ArrayList<>();
//        list.add(g * v);
//        list.add(x);
//        list.add(y);
//        return list;
//    }


    public static ArrayList<BigInteger> EuclidExBin(BigInteger a, BigInteger b, BigInteger x, BigInteger y)
    {
        BigInteger g = BigInteger.valueOf(1);

        while (((a.and(BigInteger.ONE)).compareTo(BigInteger.ZERO))==0 && ((b.and(BigInteger.ONE)).compareTo(BigInteger.ZERO))==0)
        {
            a = a.shiftRight(1);
            b = b.shiftRight(1);
            g = g.shiftLeft(1);
        }

        BigInteger u = a;
        BigInteger v = b;
        BigInteger A = BigInteger.valueOf(1);
        BigInteger B = BigInteger.valueOf(0);
        BigInteger C = BigInteger.valueOf(0);
        BigInteger D = BigInteger.valueOf(1);

        while (u.compareTo(BigInteger.ZERO) != 0)
        {
            while ((u.and(BigInteger.ONE)).compareTo(BigInteger.ZERO)==0)
            {
                u = u.shiftRight(1);
                if (((A.and(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0) && ((B.and(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0))
                {
                    A = A.shiftRight(1);
                    B = B.shiftRight(1);
                }
                else
                {
                    A = (A.add(b)).shiftRight(1);
                    B = (B.subtract(a)).shiftRight(1);
                }
            }

            while ((v.and(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0)
            {
                v = v.shiftRight(1);
                if (((C.and(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0) && ((D.and(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0))
                {
                    C = C.shiftRight(1);
                    D = D.shiftRight(1);
                }
                else
                {
                    C = (C.add(b)).shiftRight(1);
                    D = (D.subtract(a)).shiftRight(1);
                }
            }

            if(u.compareTo(v)>-1)
            {
                u = u.subtract(v);
                A = A.subtract(C);
                B = B.subtract(D);
            }

            else
            {
                v = v.subtract(u);
                C = C.subtract(A);
                D = D.subtract(B);
            }
        }

        x = C;
        y = D;
        ArrayList<BigInteger> list = new ArrayList<>();
        list.add(g.multiply(v));
        list.add(x);
        list.add(y);
        return list;
    }

    public static BigInteger MultInverse(BigInteger a, BigInteger m) throws Exception {
        BigInteger x=BigInteger.ZERO, y=BigInteger.ZERO;
//        BigInteger g = EuclidExBin(a, m,  x,  y);
        ArrayList<BigInteger> list = EuclidExBin(a, m,  x,  y);
        BigInteger g = list.get(0);
        x = list.get(1);
        y = list.get(2);
        if (g.compareTo(BigInteger.ONE) != 0)
            throw new Exception("Not exist");

        x = x.remainder(m);

        return x;
    }

    private static boolean IsCoprime(long a, long b)
    {
        return a == b
                ? a == 1
                : a > b
                ? IsCoprime(a - b, b)
                : IsCoprime(b - a, a);
    }
}
