package com.example.sweater.labsClasses;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class rijndael {
    private byte[] SBox = new byte[256];
    private byte[] InvSBox = new byte[256];
    private byte[] RCON = RCon();
    private int Nb;//размер блока в словах
    private int Nk;//размер ключа в словах
    private int Nr;//кол-во раундов одно слово - 4 байта
    private byte[][] w;

    public rijndael(byte[] key, int lenBlock) {
        Nb = lenBlock / 4;
        Nk = key.length / 4;

        if (Nb == 8 || Nk == 8)
            Nr = 14;
        else if (Nb == 6 || Nk == 6)
            Nr = 12;
        else
            Nr = 10;

        GenS();
        GenInvS();
        w = KeyExpansion(key);
    }


    public byte[] EncodeBlok(byte[] block) {
        byte[][] state = ToState(block);

        byte[][] w1 = new byte[Nb][];
        for (int i = 0; i < Nb; ++i) {
            w1[i] = w[i];
        }

        AddRoundKey(state, w1);

        for (int i = 1; i < Nr; i++) {
            SubBytesEncode(state);
            ShiftRowsEncode(state);
            MixColumnsEncode(state);
            byte[][] w2 = new byte[Nb][];
            for (int j = Nb * i; j < Nb * i + 1; ++j) {
                w2[j] = w[j];
            }
            AddRoundKey(state, w2);
        }
        SubBytesEncode(state);
        ShiftRowsEncode(state);

        byte[][] w3 = new byte[w.length][];
        for (int i = Nb * Nr; i < w.length; ++i) {
            w3[i] = w[i];
        }

        AddRoundKey(state, w3);

        return ToBlock(state);
    }


    public byte[] DecodeBlok(byte[] block) {
        byte[][] state = ToState(block);

        byte[][] w1 = new byte[w.length][];
        for (int i = Nb * Nr; i < w.length; ++i) {
            w1[i] = w[i];
        }
        AddRoundKey(state, w1);

        for (int i = Nr - 1; i > 0; i--) {
            ShiftRowsDecode(state);
            SubBytesDecode(state);
            byte[][] w2 = new byte[Nb][];
            for (int j = Nb * i; j < Nb * i + 1; ++j) {
                w2[j] = w[j];
            }
            AddRoundKey(state, w2);
            MixColumnsDecode(state);
        }

        ShiftRowsDecode(state);
        SubBytesDecode(state);
        byte[][] w3 = new byte[Nb][];
        for (int i = 0; i < Nb; ++i) {
            w3[i] = w[i];
        }
        AddRoundKey(state, w3);

        return ToBlock(state);
    }

    //расширение ключа
    private byte[][] KeyExpansion(byte[] key) {
        byte[][] keys = new byte[Nb * (Nr + 1)][];

        for (int i = 0; i < Nk; i++) {
            keys[i] = new byte[4];
            for (int j = 0; j < 4; j++)
                keys[i][j] = key[4 * i + j];
        }

        for (int i = Nk; i < Nb * (Nr + 1); i++) {
            byte[] word = keys[i - 1];
            byte[] tmp = new byte[keys[0].length];

            if (i % Nk == 0) {
                byte[] X = CycleShift(word, -1);
                for (int j = 0; j < X.length; j++)
                    tmp[j] = SBox[X[j]];

                tmp[0] ^= RCON[i / Nk];
            } else if ((Nk > 6) && (i % Nk == 4)) {
                for (int j = 0; j < tmp.length; j++)
                    tmp[j] = SBox[tmp[j]];
            }
            byte[] Y = new byte[4];
            for (int j = 0; j < 4; j++)
                Y[j] = (byte) (keys[i - Nk][j] ^ tmp[j]);

            keys[i] = Y;
        }

        return keys;
    }

    //применение sblocк-ов к state
    private void SubBytesEncode(byte[][] state) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < Nb; j++)
                state[i][j] = SBox[state[i][j]];
    }

    private void SubBytesDecode(byte[][] state) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < Nb; j++)
                state[i][j] = InvSBox[state[i][j]];
    }

    //сдвиг строк
    private void ShiftRowsEncode(byte[][] state) {
        int[] c = new int[]{1, 2, 3};
        for (int i = 1; i < 4; i++)
            state[i] = CycleShift(state[i], c[i - 1]);
    }

    private void ShiftRowsDecode(byte[][] state) {
        int[] c = new int[]{-1, -2, -3};
        for (int i = 1; i < 4; i++)
            state[i] = CycleShift(state[i], c[i - 1]);
    }

    //перемешивание столбцов
    private void MixColumnsDecode(byte[][] state) {
        byte[] b = new byte[4];

        for (int i = 0; i < Nb; i++) {
            b[0] = (byte) (GMul((byte) 0x0E, state[0][i]) ^ GMul((byte) 0x0B, state[1][i]) ^
                    GMul((byte) 0x0D, state[2][i]) ^ GMul((byte) 0x09, state[3][i]));
            b[1] = (byte) (GMul((byte) 0x09, state[0][i]) ^ GMul((byte) 0x0E, state[1][i]) ^
                    GMul((byte) 0x0B, state[2][i]) ^ GMul((byte) 0x0D, state[3][i]));
            b[2] = (byte) (GMul((byte) 0x0D, state[0][i]) ^ GMul((byte) 0x09, state[1][i]) ^
                    GMul((byte) 0x0E, state[2][i]) ^ GMul((byte) 0x0B, state[3][i]));
            b[3] = (byte) (GMul((byte) 0x0B, state[0][i]) ^ GMul((byte) 0x0D, state[1][i]) ^
                    GMul((byte) 0x09, state[2][i]) ^ GMul((byte) 0x0E, state[3][i]));
            for (int j = 0; j < 4; j++)
                state[j][i] = b[j];
        }
    }

    //перемешивание столбцов
    private void MixColumnsEncode(byte[][] state) {
        byte[] b = new byte[4];

        for (int i = 0; i < Nb; i++) {
            b[0] = (byte) (GMul((byte) 0x02, state[0][i]) ^ GMul((byte) 0x03, state[1][i]) ^ state[2][i] ^ state[3][i]);
            b[1] = (byte) (state[0][i] ^ GMul((byte) 0x02, state[1][i]) ^ GMul((byte) 0x03, state[2][i]) ^ state[3][i]);
            b[2] = (byte) (state[0][i] ^ state[1][i] ^ GMul((byte) 0x02, state[2][i]) ^ GMul((byte) 0x03, state[3][i]));
            b[3] = (byte) (GMul((byte) 0x03, state[0][i]) ^ state[1][i] ^ state[2][i] ^ GMul((byte) 0x02, state[3][i]));
            for (int j = 0; j < 4; j++)
                state[j][i] = b[j];
        }
    }

    //state xor с ключом
    private void AddRoundKey(byte[][] state, byte[][] w) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < Nb; j++)
                state[i][j] ^= w[j][i];
    }

    //циклический сдвиг влево
    private byte[] CycleShift(byte[] row, int shift) {
        ArrayList<Byte> list = new ArrayList<>();
        byte[] allByteArray = new byte[row.length];
        ByteBuffer buff = ByteBuffer.wrap(allByteArray);

        if (shift > 0) {
            byte[] a = new byte[shift];
            for (int i = row.length; i > row.length - shift; i--)
                list.add(row[i]);
            for (int i = 0; i < list.size(); i++)
                a[i] = list.get(i);
            byte[] b = new byte[row.length - shift];
            for (int i = 0; i < row.length - shift; ++i)
                b[i] = row[i];
            buff.put(a).put(b);
        } else {
            byte[] b = new byte[shift];
            for (int i = row.length; i > row.length - shift; i--)
                list.add(row[i]);
            for (int i = 0; i < list.size(); i++)
                b[i] = list.get(i);

            byte[] a = new byte[row.length - shift];
            for (int i = 0; i < row.length - shift; ++i) {
                b[i] = row[i];
            }
            buff.put(b).put(a);
        }

        return buff.array();
    }

    //из 1 в 2 мерный
    private byte[][] ToState(byte[] block) {
        byte[][] state = new byte[4][Nb];
        for (int i = 0; i < 4; i++) {
            state[i] = new byte[Nb];
            for (int j = 0; j < Nb; j++)
                state[i][j] = block[i + 4 * j];
        }
        return state;
    }

    //двумерный массив к одномерному
    private byte[] ToBlock(byte[][] state) {
        byte[] block = new byte[4 * Nb];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < Nb; j++)
                block[i + 4 * j] = state[i][j];
        return block;
    }

    //произведение в поле галуа 256
    private byte GMul(byte a, byte b) {
        byte p = 0;

        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0)
                p ^= a;
            boolean is_high = (a & 0x80) != 0;
            a <<= 1;
            a &= 0xFF;
            if (is_high)
                a ^= 0x1B;
            b >>= 1;
        }
        return p;
    }

    private void GenS() {
        byte p = 1, q = 1;

        while (true) {
            if ((p & 0x80) == 0x80)
                p ^= (byte) (((p << 1) ^ 0x1B) & 0xFF);
            else
                p ^= (byte) ((p << 1) & 0xFF);

            for (int i = 1; i <= 4; i <<= 1) {
                q ^= (byte) (q << i);
            }
            if ((q & 0x80) == 0x80)
                q ^= 0x09;
            q &= 0xFF;
            byte b = q;
            for (int i = 0; i < 4; i++) {
                b ^= CycleByte(q, i);
            }
            SBox[p] = (byte) ((b ^ 0x63) & 0xFF);

            if (p == 1)
                break;
        }
        SBox[0] = 0x63;
    }

    private byte CycleByte(byte x, int shift) {
        return (byte) (((x << shift) | (x >> (8 - shift))) & 0xFF);
    }

    private void GenInvS() {
        for (int i = 0; i < 256; i++)
            InvSBox[SBox[i]] = (byte) i;
    }

    private byte[] RCon() {
        byte[] result = new byte[32];
        result[0] = 1;
        for (int i = 1; i < 32; i++)
            result[i] = GMul(result[i - 1], (byte) 0x02);
        return result;
    }
}
