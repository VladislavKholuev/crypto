package com.example.sweater.labsClasses;



import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Modes {

    public static int convertByteToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++)
            value = (value << 8) | b[i];
        return value;
    }


    private static int _bytes_to_block(byte[] b_arr) {
        return Modes.convertByteToInt(b_arr);
    }

    public static ArrayList<Integer> bytes_to_blocks(ArrayList<Byte> b_arr) {
        ArrayList<Integer> res = new ArrayList<>();
        int s = b_arr.size() % 8;
        if (s != 0)
            for (int i = 0; i < s; i++)
                b_arr.add((byte) 0);
        for (int i = 0; i < b_arr.size() - 8; i = i + 8) {
            byte[] cur_bytes = new byte[8]; //b_arr[i:i + 8]
            for (int j = i; j < i + 8; j++) {
                cur_bytes[j] = b_arr.get(j);
            }
            res.add(Modes._bytes_to_block(cur_bytes));
        }
        return res;
    }

    public static byte[] _block_to_bytes(int block) {
        byte[] bytes = ByteBuffer.allocate(8).putInt(block).array();
        return bytes;
    }

    public static byte[] blocks_to_bytes(ArrayList<Integer> l_arr) {
        ArrayList<Byte> b_arr = new ArrayList<>();
        for (int i = 0; i < l_arr.size(); i++) {
            for (byte num : Modes._block_to_bytes(l_arr.get(i))) {
                b_arr.add(num);
            }
        }
        byte[] res = new byte[b_arr.size()];
        for (int i = 0; i < b_arr.size(); i++) {
            res[i] = b_arr.get(i);
        }
        return res;
    }


    public class CBC implements ICoder {
        private ICoderBlok _algorithm;
        private byte[] _c0;

        public CBC(ICoderBlok algorithm, byte[] c0)
        {
            _c0 = c0;
            _algorithm = algorithm;
        }

        public ArrayList Encode(byte[] message)
        {
            byte[] m = (byte[])message.clone();
            ArrayList c = new ArrayList<Byte>();

            ArrayList<Byte> prev = c;
            for (int i = 0; i < message.length; i += _algorithm.Size)
            {
                for(int j = 0; j < _algorithm.Size; j++)
                    m[i+j] = (byte) (m[i+j] ^ prev.get(j));

                c.add(_algorithm.EncodeBlok(message));
                prev = c;
            }

            return c;
        }

        public ArrayList Decode(byte[] code)
        {
            byte[] c = (byte[])code.clone();
            ArrayList m = new ArrayList<Byte>();


            ArrayList prev = m;
            for (int i = 0; i < code.length; i += _algorithm.Size)
            {
                for (int j = 0; j < _algorithm.Size; j++)
                    c[i + j] = (byte) (c[i + j] ^ (byte)prev.get(j));

                m.add(_algorithm.DecodeBlok(code));
                prev = m;
            }

            return m;
        }
    }

    public class CFB implements ICoder {
        private ICoderBlok _algorithm;
        private byte[] _c0;

        public CFB(ICoderBlok algorithm, byte[] c0) {
            _c0 = c0;
            _algorithm = algorithm;
        }

        public ArrayList Encode(byte[] message) {
            byte[] m = (byte[]) message.clone();
            ArrayList c = new ArrayList<Byte>();

            ArrayList prev = new ArrayList<Byte>();

            for (int i = 0; i < message.length; i += _algorithm.Size) {
                c.add(_algorithm.EncodeBlok(prev));
                for (int j = 0; j < _algorithm.Size; j++)
                    c.set(i + j, (byte) c.get(i + j) ^ m[i + j]);

                prev = c;
            }

            return c;
        }

        public ArrayList<Byte> Decode(byte[] code) {
            byte[] c = (byte[]) code.clone();
            ArrayList m = new ArrayList<Byte>();

            ArrayList prev = new ArrayList<Byte>();
            for (int i = 0; i < code.length; i += _algorithm.Size) {
                m.add(_algorithm.DecodeBlok(prev));
                for (int j = 0; j < _algorithm.Size; j++)
                    m.set(i + j, (byte) m.get(i + j) ^ c[i + j]);

                prev = m;
            }

            return m;
        }
    }


    public class ECB implements ICoder {

        private ICoderBlok _algorithm;

        public ECB(ICoderBlok algorithm) {
            _algorithm = algorithm;
        }

        public ArrayList Encode(byte[] message) {
            ArrayList c = new ArrayList<Byte>();

            for (int i = 0; i < message.length; i += _algorithm.Size)
                c.add(_algorithm.EncodeBlok(message));

            byte[] result = new byte[c.size()];
            for (int i = 0; i < c.size(); i++) {
                result[i] = (byte) c.get(i);
            }
            return c;
        }

        public ArrayList Decode(byte[] bloks) {
            ArrayList m = new ArrayList<Byte>();

            for (int i = 0; i < bloks.length; i += _algorithm.Size)
                m.add(_algorithm.DecodeBlok(bloks));

            return m;
        }
    }

    public interface ICoder {
        ArrayList Encode(byte[] message);

        ArrayList Decode(byte[] message);
    }

    public interface ICoderBlok {
        int Size = 0;

        byte[] EncodeBlok(byte[] message);

        byte[] DecodeBlok(byte[] message);

//    class Mode:
//    def encode(self, message):
//    pass
//
//    def decode(self, code):
//    pass
//
//    @staticmethod
//    def _bytes_to_block(b_arr):
//            return int.from_bytes(b_arr, 'little')
//
//    @staticmethod
//    def bytes_to_blocks(b_arr):
//    res = []
//
//            for i in range(0, len(b_arr), 8):
//    cur_bytes = b_arr[i:i + 8]
//            res.append(Mode._bytes_to_block(cur_bytes))
//
//            return res
//
//    @staticmethod
//    def _block_to_bytes(block):
//            return block.to_bytes(8, byteorder='little')
//
//    @staticmethod
//    def blocks_to_bytes(l_arr):
//    b_arr = bytearray()
//
//        for long in l_arr:
//            b_arr.extend(Mode._block_to_bytes(long))
//
//            return b_arr


    //    class ECB(Mode):
//    def __init__(self, alg):
//    self._alg = alg
//
//    def encode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//
//            for i in range(len(blocks)):
//            l_arr.append(self._alg.encode_block(blocks[i]))
//
//            return self.blocks_to_bytes(l_arr)
//
//    def decode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//
//            for i in range(len(blocks)):
//            l_arr.append(self._alg.decode_block(blocks[i]))
//
//            return self.blocks_to_bytes(l_arr)
//
//
//    class CBC(Mode):
//    def __init__(self, alg, c0):
//    self._alg = alg
//    self._c0 = c0
//
//    def encode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//    prev = self._c0
//
//        for i in range(len(blocks)):
//            l_arr.append(self._alg.encode_block(blocks[i] ^ prev))
//    prev = l_arr[i]
//
//            return self.blocks_to_bytes(l_arr)
//
//    def decode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//    prev = self._c0
//
//        for i in range(len(blocks)):
//            l_arr.append(self._alg.decode_block(blocks[i]) ^ prev)
//    prev = blocks[i]
//
//            return self.blocks_to_bytes(l_arr)
//
//
//    class OFB(Mode):
//    def __init__(self, alg, c0):
//    self._alg = alg
//    self._c0 = c0
//
//    def encode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//    prev = self._c0
//
//        for i in range(len(blocks)):
//            l_arr.append(self._alg.encode_block(prev))
//    prev = l_arr[i]
//    l_arr[i] ^= blocks[i]
//
//            return self.blocks_to_bytes(l_arr)
//
//    def decode(self, b_arr):
//            return self.encode(b_arr)
//
//
//    class CFB(Mode):
//    def __init__(self, alg, c0):
//    self._alg = alg
//    self._c0 = c0
//
//    def encode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//    prev = self._c0
//
//        for i in range(len(blocks)):
//            l_arr.append(self._alg.encode_block(prev) ^ blocks[i])
//    prev = l_arr[i]
//
//            return self.blocks_to_bytes(l_arr)
//
//    def decode(self, b_arr):
//    blocks = self.bytes_to_blocks(b_arr)
//    l_arr = []
//    prev = self._c0
//
//        for i in range(len(blocks)):
//            l_arr.append(self._alg.encode_block(prev) ^ blocks[i])
//    prev = blocks[i]
//
//            return self.blocks_to_bytes(l_arr)}
    Object EncodeBlok(ArrayList prev);
    Object DecodeBlok(ArrayList prev);
    }

    public static class activate{
        public ECB e;
        public CFB c;
        public CBC cbc;
        public ICoder block;
        public rijndael rijndael;
        public void ex(){
            e.Decode(new byte[]{1,2});
            e.Encode(new byte[]{1,2});
            c.Decode(new byte[]{1,2});
            c.Encode(new byte[]{1,2});
            block.Decode(new byte[]{1,2});
            block.Encode(new byte[]{1,2});
            rijndael.DecodeBlok(new byte[]{1,2});
            rijndael.EncodeBlok(new byte[]{1,2});
        }
    }
}