import java.util.*;
import java.io.*;
public class Simulator {


	static ArrayList<String> A = new ArrayList<String>(256);
    static ArrayList<String> ctr = new ArrayList<String>(256);
    static ArrayList<String> pc = new ArrayList<String>(256);
    static String[] R = {"0000000000000000","0000000000000000","0000000000000000","0000000000000000","0000000000000000","0000000000000000","0000000000000000","0000000000000000"};
    static int cycles=0, ptr=0;

    public static int decimal(String n)
    {
        int d = 0;
        int p = 1;
        for(int i = n.length() -1; i>=0;i--) {
            String c = ""+n.charAt(i);
            d+=(Integer.parseInt(c))*p;
            p*=2;
        }
        return d;
    }

    public static String bitbinary(int n) {
        String a=String.format("%16s", Integer.toBinaryString(n)).replaceAll(" ", "0");
        String d="";
        if (a.length()>16){
            for(int x=a.length()-1;x>a.length()-17;x--){

                d+=a.charAt(x);
            }
            StringBuilder sb=new StringBuilder(d);
            sb.reverse();
            String si = sb.toString();
            return si;
        }
        return a;
    }

    public static int rgs(String s1) {

        if (s1.equals("000")) {
            return 0;
        }
        else if (s1.equals("001")) {
            return 1;
        }
        else if (s1.equals("010")) {
            return 2;
        }
        else if (s1.equals("011")) {
            return 3;
        }
        else if (s1.equals("100")) {
            return 4;
        }
        else if (s1.equals("101")) {
            return 5;
        }
        else if (s1.equals("110")) {
            return 6;
        }
        else if(s1.equals("111")) {
            return 7;
        }

        return -1;
    }


    public static String Decimalto8bitbinary(int n)
    {
        if(n>255 || n<0) {
            return "invalid_num";
        }
        int d =n;
        String bin= "00000000";
        for(int i = bin.length()-1;d!=0;i--)
        {
            char[] a=bin.toCharArray();
            a[i]= String.valueOf(d%2).charAt(0);
            bin=new String(a);
            d=d/2;

        }
        return bin;
    }

    public static void add(String[] s) {
        String l = s[7]+s[8]+s[9];
        if(decimal(R[rgs(s[10]+s[11]+s[12])]) + decimal(R[rgs(s[13]+s[14]+s[15])]) > 65535) {
            R[7] = "0000000000001000";
	    R[rgs(l)] = bitbinary(decimal(R[rgs(s[10]+s[11]+s[12])]) + decimal(R[rgs(s[13]+s[14]+s[15])])) ;	
        }
        else {
            if(rgs(l)!=-1) {
                R[7] = "0000000000000000";
                R[rgs(l)] = bitbinary(decimal(R[rgs(s[10]+s[11]+s[12])]) + decimal(R[rgs(s[13]+s[14]+s[15])]));

            }
        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void sub(String[] s) {
        String l = s[7]+s[8]+s[9];
        if(decimal(R[rgs(s[10]+s[11]+s[12])]) - decimal(R[rgs(s[13]+s[14]+s[15])]) < 0) {
            R[7] = "0000000000001000";
	    R[rgs(l)] = "0000000000000000";

        }
        else {
            if(rgs(l)!=-1) {
                R[7] = "0000000000000000";
                R[rgs(l)] = bitbinary(decimal(R[rgs(s[10]+s[11]+s[12])]) - decimal(R[rgs(s[13]+s[14]+s[15])]));

            }
        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void mul(String[] s) {
        String l = s[7]+s[8]+s[9];
        if(decimal(R[rgs(s[10]+s[11]+s[12])]) * decimal(R[rgs(s[13]+s[14]+s[15])]) > 65535) {
            R[7] = "0000000000001000";
	    R[rgs(l)] = bitbinary(decimal(R[rgs(s[10]+s[11]+s[12])]) * decimal(R[rgs(s[13]+s[14]+s[15])])) ;
        }
        else {
            if(rgs(l)!=-1) {
                R[7] = "0000000000000000";
                R[rgs(l)] = bitbinary(decimal(R[rgs(s[10]+s[11]+s[12])]) * decimal(R[rgs(s[13]+s[14]+s[15])]));

            }
        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void xor(String[] s) { //(a ^ b)
        String l = s[7]+s[8]+s[9];
        if(rgs(l)!=-1) {
            R[7] = "0000000000000000";
            R[rgs(l)] = bitbinary(decimal(R[(rgs(s[10]+s[11]+s[12]))]) ^ decimal(R[(rgs(s[13]+s[14]+s[15]))]));

        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void or(String[] s) {
        String l = s[7]+s[8]+s[9];
        if(rgs(l)!=-1) {
            R[7] = "0000000000000000";
            R[rgs(l)] = bitbinary(decimal(R[(rgs(s[10]+s[11]+s[12]))]) | decimal(R[(rgs(s[13]+s[14]+s[15]))]));

        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;

    }
    public static void and(String[] s) {
        String l = s[7]+s[8]+s[9];
        if(rgs(l)!=-1) {
            R[7] = "0000000000000000";
            R[rgs(l)] = bitbinary(decimal(R[(rgs(s[10]+s[11]+s[12]))]) & decimal(R[(rgs(s[13]+s[14]+s[15]))]));

        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void movR(String[] s) {
        R[rgs(s[10]+s[11]+s[12])]=R[rgs(s[13]+s[14]+s[15])];
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void rs(String[] s) {
        R[rgs(s[5]+s[6]+s[7])]=bitbinary(decimal((R[rgs(s[5]+s[6]+s[7])])) >>  (decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15])));
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void ls(String[] s) {
        R[rgs(s[5]+s[6]+s[7])]=bitbinary(decimal((R[rgs(s[5]+s[6]+s[7])])) <<  (decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15])));
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void movI(String[] s) {
        R[rgs(s[5]+s[6]+s[7])]= bitbinary(decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]));
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void div(String[] s) {
        if(decimal(R[rgs(s[13]+s[14]+s[15])])==0) {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
            return;
        }
        else {
            R[0]=bitbinary((decimal(R[rgs(s[10]+s[11]+s[12])]))/(decimal(R[rgs(s[13]+s[14]+s[15])])));
            R[1]=bitbinary((decimal(R[rgs(s[10]+s[11]+s[12])]))%(decimal(R[rgs(s[13]+s[14]+s[15])])));
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
            return;
        }
    }
    public static void not(String[] s) {
        String l = s[10]+s[11]+s[12];
        if(rgs(l)!=-1) {
            R[7] = "0000000000000000";
            R[rgs(l)] = bitbinary(~ decimal(R[rgs(s[13]+s[14]+s[15])]));
        }
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void cmp(String[] s) {
        if(decimal(R[(rgs(s[10]+s[11]+s[12]))]) > decimal(R[(rgs(s[13]+s[14]+s[15]))])) {
            R[7] = "0000000000000010";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
        }
        else if(decimal(R[(rgs(s[10]+s[11]+s[12]))]) < decimal(R[(rgs(s[13]+s[14]+s[15]))])) {

            R[7] = "0000000000000100";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;

        }
        else {
            R[7] = "0000000000000001";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
        }


    }
    public static void ld(String[] s){
        R[rgs(s[5]+s[6]+s[7])] = A.get(decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]));
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void st(String[] s) {
        A.set(decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]), R[rgs(s[5]+s[6]+s[7])]);

        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }
    public static void jmp(String[] s) {
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr = decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]);
        cycles++;
    }
    public static void jlt(String[] s) {
        if(R[7].equals("0000000000000100")) {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr = decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]);
            cycles++;
        }
        else {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
        }
    }
    public static void jgt(String[] s) {
        if(R[7].equals("0000000000000010")) {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr = decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]);
            cycles++;
        }
        else {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
        }
    }
    public static void je(String[] s) {
        if(R[7].equals("0000000000000001")) {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr = decimal(s[8]+s[9]+s[10]+s[11]+s[12]+s[13]+s[14]+s[15]);
            cycles++;
        }
        else {
            R[7] = "0000000000000000";
            pc.add(ctr.get(ptr));
            System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
            ptr++;
            cycles++;
        }
    }
    public static void hlt(String[] s) {
        R[7] = "0000000000000000";
        pc.add(ctr.get(ptr));
        System.out.println(pc.get(cycles)+" "+R[0]+" "+R[1]+" "+R[2]+" "+R[3]+" "+R[4]+" "+R[5]+" "+R[6]+" "+R[7]);
        ptr++;
        cycles++;
    }


    public static void main(String[] args) throws IOException  {
        for(int q=0;q<256;q++){
            A.add("0000000000000000");
            ctr.add("00000000");
        }
        Scanner scn=new Scanner(System.in);
        int b = 0;
        while (scn.hasNextLine()) {

            A.set(b,scn.nextLine());
            ctr.set(b,Decimalto8bitbinary(b));
            b++;
        }

        for(;ptr<b;)
        {
            String f=A.get(ptr);
            String[] s=f.split("");
            String l="";

            for(int j=0;j<5;j++)
            {
                l+=s[j];
            }


            if(l.equals("00000"))
            {
                add(s);

                continue;

            }
            else if(l.equals("00001"))
            {
                sub(s);

                continue;
            }
            else if(l.equals("00010"))
            {
                movI(s);

                continue;
            }
            else if(l.equals("00011"))
            {
                movR(s);

                continue;
            }
            else if(l.equals("00100"))
            {
                ld(s);

                continue;
            }
            else if(l.equals("00101"))
            {
                st(s);

                continue;
            }
            else if(l.equals("00110"))
            {
                mul(s);

                continue;
            }
            else if(l.equals("00111"))
            {
                div(s);

                continue;
            }
            else if(l.equals("01000"))
            {
                rs(s);

                continue;
            }
            else if(l.equals("01001"))
            {
                ls(s);

                continue;
            }
            else if(l.equals("01010"))
            {
                xor(s);

                continue;
            }
            else if(l.equals("01011"))
            {
                or(s);

                continue;
            }
            else if(l.equals("01100"))
            {
                and(s);

                continue;
            }
            else if(l.equals("01101"))
            {
                not(s);

                continue;
            }
            else if(l.equals("01110"))
            {
                cmp(s);

                continue;
            }
            else if(l.equals("01111"))
            {
                jmp(s);
                continue;
            }
            else if(l.equals("10000"))
            {
                jlt(s);
                continue;
            }
            else if(l.equals("10001"))
            {
                jgt(s);
                continue;
            }
            else if(l.equals("10010"))
            {
                je(s);
                continue;
            }
            else if(l.equals("10011"))
            {
                hlt(s);
                continue;
            }
        }

        for(int a=0;a<A.size();a++) {
            System.out.println(A.get(a));
        }
        FileWriter fw=new FileWriter("input.txt");
        int c=0;
        for(int i=0;i<pc.size();i++)
        {
            fw.write(c+","+decimal(pc.get(i))+"\n");
            c++;
        }
        fw.close();

    }
    }