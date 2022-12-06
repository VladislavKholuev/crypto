package com.example.sweater;

import com.example.sweater.labsClasses.Algebra;
import com.example.sweater.labsClasses.BitOperation;
import com.example.sweater.labsClasses.GF256;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("/lab1.1")
    public String greeting(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "firstFirst";
    }

    @GetMapping("/lab1.2")
    public String second(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");

        return "firstSecond";
    }

    @GetMapping("/lab1.3")
    public String third(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "firstThird";
    }

    @GetMapping("/lab1.4")
    public String fourth(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "firstFourth";
    }

    @GetMapping("/lab2a")
    public String SecondALab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "secondA";
    }

    @GetMapping("/lab2b")
    public String SecondBLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "secondB";
    }

    @GetMapping("/lab3")
    public String thirdLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "third";
    }

    @GetMapping("/lab4")
    public String fourthLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "fourth";
    }

    @GetMapping("/lab5")
    public String fivethLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "fiveth";
    }
    @GetMapping("/lab6")
    public String sixthLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "sixth";
    }
    @GetMapping("/lab7")
    public String seventhLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "seventh";
    }

    @GetMapping("/lab8")
    public String eighthLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "eighth";
    }

    @GetMapping("/vernam")
    public String vernamLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "vernam";
    }

    @GetMapping("/RC4")
    public String RC4Lab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "RC4";
    }

    @GetMapping("/primeNumbers")
    public String primeNumbersLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "primeNumbersLab";
    }

    @GetMapping("/euler")
    public String eulerLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "Euler";
    }

    @GetMapping("/powMod")
    public String powModLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "powMod";
    }

    @GetMapping("/rds")
    public String RdsLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "RDS";
    }

    @GetMapping("/canonPrimeNums")
    public String canonPrimeNumsLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "canonPrimeNums";
    }

    @GetMapping("/euclid")
    public String euclidLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "euclid";
    }

    @GetMapping("/euclidBig")
    public String euclidBinLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "euclidBig";
    }

    @GetMapping("/RSA")
    public String RSALab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "rsa";
    }

    @GetMapping("/DES")
    public String DESLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "DES";
    }

    @GetMapping("/lab3.1")
    public String PolyFormLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "polynomForm";
    }

    @GetMapping("/lab3.2")
    public String MulGFLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "mulGF";
    }

    @GetMapping("/lab3.3")
    public String InvGFLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "InvGF";
    }

    @GetMapping("/rijndael")
    public String RijndaelLab(@RequestParam(name="name", required=false) String name, Map<String, Object> model) {
        model.put("num", "");
        return "rijndael";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("some", "hello, letsCode!");
        return "main";
    }

    @PostMapping("/lab1.1")
    public String calculate1_1(@RequestParam String number,@RequestParam String bitOfNumber, Map<String, Object> model){
        if((number!= null && !number.isEmpty()) && (bitOfNumber!=null && !bitOfNumber.isEmpty())) {
            try
            {
                int num1_1 = (Integer.parseInt(number));
                int bit1_1 = Integer.parseInt(bitOfNumber);
                model.put("num", BitOperation.GetBit(num1_1,bit1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "firstFirst";
        }
        else {
            model.put("num", "0");
        }
        return "firstFirst";
    }

    @PostMapping("/lab1.2")
    public String calculate(@RequestParam String number,@RequestParam String bitOfNumber, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumber!=null && !bitOfNumber.isEmpty())) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Long.parseLong(number));
                int bit1_1 = Integer.parseInt(bitOfNumber);
                model.put("num", BitOperation.ChangeBit(num1_1,bit1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "firstSecond";
        }
        else {
            model.put("num", "0");
        }
        return "firstSecond";
    }

    @PostMapping("/lab1.3")
    public String calculate3(@RequestParam String number,@RequestParam String bitOfNumberI,@RequestParam String bitOfNumberJ, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumberI!=null && !bitOfNumberI.isEmpty())&& (bitOfNumberJ!=null && !bitOfNumberJ.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number);
                int bitI = Integer.parseInt(bitOfNumberI);
                int bitJ = Integer.parseInt(bitOfNumberJ);
                model.put("num", BitOperation.SwapBit(num1_1,bitI,bitJ));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "firstThird";
        }
        else {
            model.put("num", "0");
        }
        return "firstThird";
    }

    @PostMapping("/lab1.4")
    public String calculate4(@RequestParam String number,@RequestParam String bitOfNumber, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumber!=null && !bitOfNumber.isEmpty())) {
            try
            {
                int num1_1 = (Integer.parseInt(number));
                int bit1_1 = Integer.parseInt(bitOfNumber);
                model.put("num", BitOperation.ZeroSmallBits(num1_1,bit1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "firstFourth";
        }
        else {
            model.put("num", "0");
        }
        return "firstFourth";
    }

    @PostMapping("/lab2a")
    public String calculateLab2A(@RequestParam String number,@RequestParam String bitOfNumberI , Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumberI!=null && !bitOfNumberI.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                int bitI = Integer.parseInt(bitOfNumberI);
                model.put("num", Integer.toBinaryString(BitOperation.GlueBits(num1_1,bitI,number.length())));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "secondA";
        }
        else {
            model.put("num", "0");
        }
        return "secondA";
    }

    @PostMapping("/lab2b")
    public String calculateLab2B(@RequestParam String number,@RequestParam String bitOfNumberI, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumberI!=null && !bitOfNumberI.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                int bitI = Integer.parseInt(bitOfNumberI);
                model.put("num", Integer.toBinaryString(BitOperation.MiddleBits(num1_1,bitI,number.length())));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "secondB";
        }
        else {
            model.put("num", "0");
        }
        return "secondB";
    }

    @PostMapping("/lab3")
    public String calculateLab3(@RequestParam String number,@RequestParam String bitOfNumberI,@RequestParam String bitOfNumberJ, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (bitOfNumberI!=null && !bitOfNumberI.isEmpty())&& (bitOfNumberJ!=null && !bitOfNumberJ.isEmpty())) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Long.parseLong(number));
                int bitI = Integer.parseInt(bitOfNumberI);
                int bitJ = Integer.parseInt(bitOfNumberJ);
                model.put("num", BitOperation.SwapBytes(num1_1,bitI,bitJ));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "third";
        }
        else {
            model.put("num", "-");
        }
        return "third";
    }

    @PostMapping("/lab4")
    public String calculateLab4(@RequestParam String number, Map<String, Object> model){
        if(number!= null && !number.isEmpty()) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Long.parseLong(number));
                model.put("num", BitOperation.MaxDegreeBin(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "fourth";
        }
        else {
            model.put("num", "0");
        }
        return "fourth";
    }

    @PostMapping("/lab5")
    public String calculateLab5(@RequestParam String number, Map<String, Object> model){
        if(number!= null && !number.isEmpty()) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Long.parseLong(number));
                model.put("num", BitOperation.InsideDiapason(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "fiveth";
        }
        else {
            model.put("num", "0");
        }
        return "fiveth";
    }

    @PostMapping("/lab6")
    public String calculateLab6(@RequestParam String number, Map<String, Object> model){
        if(number!= null && !number.isEmpty()) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                model.put("num", BitOperation.AutoXOR(num1_1,number.length()));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "sixth";
        }
        else {
            model.put("num", "0");
        }
        return "sixth";
    }

    @PostMapping("left")
    public String calculateLab7left(@RequestParam String number,@RequestParam String num_p,@RequestParam String num_n, Map<String, Object> model){
        if(number!= null && !number.isEmpty() &&(num_n!= null && !num_n.isEmpty())&&(num_p!= null && !num_p.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                int p = Integer.parseInt(num_p);
                int n = Integer.parseInt(num_n);
                model.put("num", Integer.toBinaryString(BitOperation.CycleShiftLeft(num1_1,p,n)));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "seventh";
        }
        else {
            model.put("num", "0");
        }
        return "seventh";
    }

    @PostMapping("right")
    public String calculateLab7right(@RequestParam String number,@RequestParam String num_p,@RequestParam String num_n, Map<String, Object> model){
        if(number!= null && !number.isEmpty() &&(num_n!= null && !num_n.isEmpty())&&(num_p!= null && !num_p.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                int p = Integer.parseInt(num_p);
                int n = Integer.parseInt(num_n);
                model.put("num", Integer.toBinaryString(BitOperation.CycleShiftRight(num1_1,p,n)));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "seventh";
        }
        else {
            model.put("num", "0");
        }
        return "seventh";
    }

    @PostMapping("/lab8")
    public String calculateLab8(@RequestParam String number,@RequestParam String num_p, Map<String, Object> model){
        if(number!= null && !number.isEmpty() &&(num_p!= null && !num_p.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number,2);
                ArrayList<String> list = new ArrayList<>(Arrays.asList(num_p.split(",")));
                int[] mas = new int[list.size()];
                for (int i=0;i<list.size(); i++){
                    mas[i]=Integer.parseInt(list.get(i));
                }
                String num = Integer.toBinaryString(BitOperation.TransposBits(num1_1,mas));
                StringBuilder builder = new StringBuilder();
                builder.append(num);
                model.put("num", builder.reverse());
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "eighth";
        }
        else {
            model.put("num", "0");
        }
        return "eighth";
    }

    @PostMapping("/primeNumbers")
    public String calculateLabPrimeNumbers(@RequestParam String number, Map<String, Object> model){
        if(number!= null && !number.isEmpty()) {
            try
            {
                long num1_1 = Long.parseLong(number);
                model.put("num", Algebra.GetPrimeNumbers(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "primeNumbersLab";
        }
        else {
            model.put("num", "0");
        }
        return "primeNumbersLab";
    }

    @PostMapping("/euler")
    public String calculateLabEuler(@RequestParam String number, Map<String, Object> model){
        if(number!= null && !number.isEmpty()) {
            try
            {
                int num1_1 = Integer.parseInt(number);
                if(num1_1>0)
                model.put("num", Algebra.Euler(num1_1));
                else model.put("num", "число должно быть больше 0");
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "Euler";
        }
        else {
            model.put("num", "0");
        }
        return "Euler";
    }

    @PostMapping("/powMod")
    public String calculateLabPowMod(@RequestParam String number,@RequestParam String step,@RequestParam String mod, Map<String, Object> model){

        if((number!= null && !number.isEmpty()) && (step!=null && !step.isEmpty())&& (mod!=null && !mod.isEmpty())) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Long.parseLong(number));
                BigInteger stepen = BigInteger.valueOf((Long.parseLong(step)));
                BigInteger modul = BigInteger.valueOf((Long.parseLong(mod)));
                model.put("num", Algebra.PowMod(num1_1,stepen,modul));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "powMod";
        }
        else {
            model.put("num", "-");
        }
        return "powMod";
    }

    @PostMapping("/rds")
    public String calculateLabRDS(@RequestParam String number, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                long num1_1 = (Long.parseLong(number));
                model.put("num", Algebra.RDS(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "RDS";
        }
        else {
            model.put("num", "-");
        }
        return "RDS";
    }

    @PostMapping("/canonPrimeNums")
    public String calculateLabCanonPrimeNums(@RequestParam String number, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                long num1_1 = Math.abs(Long.parseLong(number));
                model.put("num", Algebra.Factorization(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "canonPrimeNums";
        }
        else {
            model.put("num", "-");
        }
        return "canonPrimeNums";
    }

    @PostMapping("/euclid")
    public String calculateLabEuclid(@RequestParam String number,@RequestParam String number2, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                long num1_1 = Math.abs(Long.parseLong(number));
                long num1_2 = Math.abs(Long.parseLong(number2));
                model.put("num", Algebra.Euclid(num1_1,num1_2));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "euclid";
        }
        else {
            model.put("num", "-");
        }
        return "euclid";
    }

    @PostMapping("/euclidBig")
    public String calculateLabEuclidBig(@RequestParam String number,@RequestParam String number2, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                BigInteger num1_1 = BigInteger.valueOf(Math.abs(Long.parseLong(number)));
                BigInteger num1_2 = BigInteger.valueOf(Math.abs(Long.parseLong(number2)));
                model.put("num", Algebra.EuclidExBin(num1_1,num1_2,BigInteger.ZERO,BigInteger.ZERO));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "euclidBig";
        }
        else {
            model.put("num", "-");
        }
        return "euclidBig";
    }

    @PostMapping("/lab3.1")
    public String calculateLabPolyFormGF(@RequestParam String number, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number);
                model.put("num", GF256.toPolynomForm(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "polynomForm";
        }
        else {
            model.put("num", "-");
        }
        return "polynomForm";
    }

    @PostMapping("/lab3.2")
    public String calculateLabMulGF(@RequestParam String number,@RequestParam String number1, Map<String, Object> model){

        if((number!= null && !number.isEmpty())&& (number1!= null && !number1.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number);
                int num1_2 = Integer.parseInt(number1);
                model.put("num", GF256.mul(num1_1,num1_2));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "mulGF";
        }
        else {
            model.put("num", "-");
        }
        return "mulGF";
    }

    @PostMapping("/lab3.3")
    public String calculateLabInvGF(@RequestParam String number, Map<String, Object> model){

        if((number!= null && !number.isEmpty())) {
            try
            {
                int num1_1 = Integer.parseInt(number);
//                int num1_2 = Integer.parseInt(number1);
                model.put("num", GF256.inv(num1_1));
            }
            catch (NumberFormatException nfe)
            {
                model.put("num", "Ваши данные некорректны");
            }
            return "InvGF";
        }
        else {
            model.put("num", "-");
        }
        return "InvGF";
    }

}
