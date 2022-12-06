package com.example.sweater.labsClasses;

public class GF256 {

//    public static void main(String args[]){
//        System.out.println(toPolynomForm(7));
//        System.out.println(inv(750%256));
//        System.out.println(mul(750%256,30));
//    }

    public static String toPolynomForm(int b){
        StringBuilder s = new StringBuilder();
        for(int i=7;i>=0;i--){
            if (getBit(b, i) == 1)
                s.append( String.format("x^%d+", i));
        }
        String res = s.toString();
        res = res.replace("x^0","1");
        res = res.replace("x^1", "x");

        return res.substring(0,res.length()-1);
    }

    public static int getBit(int a, int k){
        return (a >> k) & 1;
    }

    public static int mul(int a, int b){
        int aa=a, bb =b , r= 0, t=0;
        while (aa != 0){
            if ((aa & 1) != 0)
                r ^= bb;
            t = bb & 0x80;
            bb <<= 1;

            if (t != 0){
            bb ^= 0x1B;
            }
            aa >>= 1;
        }
        return r & 0xFF;
    }

    public static int inv(int a){
        return pow(a, 254);
    }

    private static int pow(int a, int n){
        int res = a;
        while(n>1){
            n-=1;
            res = mul(res,a);
        }
        return res;
    }

}
