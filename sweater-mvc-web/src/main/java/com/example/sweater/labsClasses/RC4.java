package com.example.sweater.labsClasses;

public class RC4 {

    byte[] S;
    int _x = 0;
    int _y = 0;
    int _size;

    public RC4(byte[] key)
    {
        S = new byte[256];
        _size = key.length;
        int keyLength = key.length;

        for (int i = 0; i < 256; i++)
        {
            S[i] = (byte)i;
        }
        int j = 0;

        for (int i = 0; i < 256; i++)
        {
            System.out.println("here");
            j = Math.abs((j + S[i] + key[i % keyLength]) % 256);

            byte b = S[i];
            S[i]=S[j];
            S[j]=b;
        }
        System.out.println("out");
    }

    private byte KeyItem()
    {
        _x = Math.abs((_x + 1) % 256);
        _y = Math.abs((_y + S[_x]) % 256);
        byte b = S[_x];
        S[_x]=S[_y];
        S[_y]=b;
        return S[Math.abs((S[_x] + S[_y]) % 256)];
    }

    public byte[] Encode(byte[] dataB)
    {
        byte[] data =new byte[_size];
        for (int i=0;i<data.length;i++){
            data[i]=dataB[i];
        }
        byte[] cipher = new byte[data.length];
        for (int m = 0; m < data.length; m++)
        {
            cipher[m] = (byte)(data[m] ^ KeyItem());
        }
        return cipher;
    }

    public byte[] Decode(byte[] dataB)
    {
        return Encode(dataB);
    }

}
