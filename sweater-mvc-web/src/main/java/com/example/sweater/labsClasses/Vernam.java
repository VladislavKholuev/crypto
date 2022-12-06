package com.example.sweater.labsClasses;

public class Vernam {
    byte[] _key;

    public Vernam(byte[] key) {
        _key = key;
    }

    public byte[] Encode(byte[] data) {
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++)
            result[i] = (byte) (data[i] ^ _key[i%_key.length]);

        return result;
    }

    public byte[] Decode(byte[] data) {
        return Encode(data);
    }
}
