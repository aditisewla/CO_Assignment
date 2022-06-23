package fileio;
import java.util.*;
import java.io.*;

public class Assembler_updated {
	static int ctr,hlt,var,lab,ln;
    static boolean hlt_num=false,var_num2=false,var_num=false;
    static ArrayList<String> A = new ArrayList<String>(300);
    final static String[] R={"R0","R1","R2","R3","R4","R5","R6","FLAGS"};
    final static String[] V={"000","001","010","011","100","101","110","111"};
    final static String[] OP={"00000","00001","00010","00011","00100","00101","00110","00111","01000","01001","01010","01011","01100","01101","01110","01111","10000","10001","10010","10011"};


    public static String registers(String s1) {
        String a ="";
        if (s1.equals("R0")) {
            a+=V[0];
        }
        else if (s1.equals("R1")) {
            a+=V[1];
        }
        else if (s1.equals("R2")) {
            a+=V[2];
        }
        else if (s1.equals("R3")) {
            a+=V[3];
        }
        else if (s1.equals("R4")) {
            a+=V[4];
        }
        else if (s1.equals("R5")) {
            a+=V[5];
        }
        else if (s1.equals("R6")) {
            a+=V[6];
        }
        else {
            return "invalid case";
        }
        return a;
    }

    public static String registers_exmov(String s1) {
        String a ="";
        if (s1.equals("R0")) {
            a+=V[0];
        }
        else if (s1.equals("R1")) {
            a+=V[1];
        }
        else if (s1.equals("R2")) {
            a+=V[2];
        }
        else if (s1.equals("R3")) {
            a+=V[3];
        }
        else if (s1.equals("R4")) {
            a+=V[4];
        }
        else if (s1.equals("R5")) {
            a+=V[5];
        }
        else if (s1.equals("R6")) {
            a+=V[6];
        }
        else if(s1.equals("FLAGS")) {
            a+=V[7];
        }
        else {
            return "invalid case";
        }
        return a;
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
    public static void TypeA(String s,int ln){
        String[] ctr = s.split(" ");
        if (ctr.length != 4)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String f = s.split(" ")[0];
        String g = "";
        if(f.equals("add")) {
            g  += OP[0];
        }
        else if(f.equals("sub")) {
            g  += OP[1];
        }
        else if(f.equals("mul")) {
            g  += OP[6];
        }
        else if(f.equals("xor")) {
            g  += OP[10];
        }
        else if(f.equals("or")) {
            g  += OP[11];
        }
        else if(f.equals("and")) {
            g  += OP[12];
        }
        String a = g + "00";
        for(int i=1;i<=3;i++) {
            String s1 = s.split(" ")[i];
            if((registers(s1)).equals("invalid case") ) {
                System.out.println("Line no.: "+ln+" undefined register");
                return;
            }
            else {
                a += registers(s1);
            }
        }
        System.out.println(a);
    }
    public static void TypeB(String s,int ln){
        String[] ctr = s.split(" ");
        if (ctr.length != 3)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String b = s.split(" ")[2];
        String f = s.split(" ")[0];
        String g = "";
        if(f.equals("mov")) {
            g  += OP[2];
        }
        else if(f.equals("rs")) {
            g  += OP[8];
        }
        else if(f.equals("ls")) {
            g  += OP[9];
        }
        String a = g;

        String s1 = s.split(" ")[1];
        if((registers(s1)).equals("invalid case") ) {
            System.out.println("Line no.: "+ln+" undefined register");
            return;
        }
        else {
            a += registers(s1);
        }
        String c = ""+b.charAt(0);
        if(c.equals("$")) {
            String l="";
            for(int i=1;i<b.length();i++) {
                if(b.charAt(i)=='.'){
                    System.out.println("Line no.: "+ln+" error generated not an integer");
                    return;
                }
                l+=b.charAt(i);
            }
            int k=Integer.parseInt(l);
            if(!((Decimalto8bitbinary(k)).equals("invalid_num"))) {
                a += Decimalto8bitbinary(k);
            }
            else {
                System.out.println("Line no.: "+ln+" Imm value out of range/not valid");
                return;
            }
        }
        else {
            System.out.println("Line no.: "+ln+" Expecting $Imm");
            return;
        }
        System.out.println(a);
    }
    public static void TypeC(String s,int ln){
        String[] ctr = s.split(" ");
        if (ctr.length != 3)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String f = s.split(" ")[0];
        String g = "";
        if(f.equals("mov")) {
            g  += OP[3];
        }
        else if(f.equals("div")) {
            g  += OP[7];
        }
        else if(f.equals("not")) {
            g  += OP[13];
        }
        else if(f.equals("cmp")) {
            g  += OP[14];
        }
        String a = g + "00000";
        if(!(f.equals("mov")))
        {
            for(int i=1;i<=2;i++) {
                String s1 = s.split(" ")[i];
                if((registers(s1)).equals("invalid case") ) {
                    System.out.println("Line no.: "+ln+" undefined register");
                    return;
                }
                else {
                    a += registers(s1);
                }
            }
        }
        else
        {
            if(!((registers(s.split(" ")[1])).equals("invalid case")))
            {
                a+=registers(s.split(" ")[1]);
            }
            else
            {
                System.out.println("Line no.: "+ln+" undefined register");
                return;
            }

            if(!((registers_exmov(s.split(" ")[2])).equals("invalid case")))
            {
                a+=registers_exmov(s.split(" ")[2]);
            }
            else
            {
                System.out.println("Line no.: "+ln+" undefined register");
                return;
            }

        }

        System.out.println(a);
    }
    public static void TypeD(String s,int ln,String[] variable,String[] variable_position){
        String[] ctr = s.split(" ");
        if (ctr.length != 3)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String s2=s.split(" ")[2];
        int t = linearsearch_var(s2, variable);
        if((t!=-1) && !(s2.equals("")))
        {
            String f = s.split(" ")[0];
            String g = "";
            if(f.equals("ld")) {
                g  += OP[4];
            }
            else if(f.equals("st")) {
                g  += OP[5];
            }
            String a = g;

            String s1 = s.split(" ")[1];
            if((registers(s1)).equals("invalid case") ) {
                System.out.println("Line no.: "+ln+" undefined register");
                return;
            }
            else {
                a += registers(s1);
            }
            a+=variable_position[t];
            System.out.println(a);
        }
        else {
            System.out.println("Line no.: "+ln+" Error: given variable is not defined in file");
            return;
        }
    }
    public static void TypeE(String s,int ln,String[] label,String[] label_position){
        String[] ctr = s.split(" ");
        if (ctr.length != 2)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String s2=s.split(" ")[1];
        int t = linearsearch_lab(s2, label);
        if((t!=-1) && !(s2.equals("")))
        {
            String f = s.split(" ")[0];
            String g = "";
            if(f.equals("jmp")) {
                g  += OP[15];
            }
            else if(f.equals("jlt")) {
                g  += OP[16];
            }
            else if(f.equals("jgt")) {
                g  += OP[17];
            }
            else if(f.equals("je")) {
                g  += OP[18];
            }
            String a = g+"000";

            a+=label_position[t];
            System.out.println(a);

            return;
        }
        else {
            System.out.println("Line no.: "+ln+" Error: given label is not defined in file");
            return;
        }
    }
    public static void TypeF(String s,int ln){
        String[] ctr = s.split(" ");
        if(ctr.length !=1)
        {
            System.out.println("Line no.: "+ln+" invalid syntax (hlt should be alone)");
            return;
        }
        if(!((A.get(ln)).equals("null"))) {
            System.out.println("Line no.: "+ln+" hlt is not used as last instruction");
            return;
        }
        String a=OP[19]+"00000000000";
        System.out.println(a);
        hlt_num=true;
        return;
    }

    public static boolean funchar(char r) {
        if ((r >= 'A' && r <= 'Z')||(r >= 'a' && r <= 'z')||(r >= '0' && r <= '9')||(r=='_')) {
            return true;
        }
        return false;

    }

    public static int linearsearch_var(String s, String[] variable){
        int flag=0;
        int c =-1;
        for(int i=0;i<variable.length;i++)
        {
            if(variable[i]==null)
            { flag++;
                if(flag>10)
                {
                    break;
                }
            }
            else if(variable[i].equals(s))
            {

                c=i;
                break;
            }
        }
        return c;
    }

    public static int linearsearch_lab(String s, String[] label){
        int flag=0;
        int c =-1;
        for(int i=0;i<label.length;i++)
        {
            if(label[i]==null)
            { flag++;
                if(flag>10)
                {
                    break;
                }
            }
            else if(label[i].equals(s))
            {

                c=i;
                break;
            }
        }
        return c;
    }

    public static void mov_calling(String s,int ln)
    {
        String[] ctr = s.split(" ");
        if(ctr.length !=3)
        {
            System.out.println("Line no.: "+ln+" invalid syntax");
            return;
        }
        String b = s.split(" ")[2];
        String c = ""+b.charAt(0);
        if(c.equals("$"))
        {
            TypeB(s,ln);
        }
        else
        {
            TypeC(s,ln);
        }
    }


    public static void main(String[] args)  {

        //Take input to find the test file

        Scanner scn=new Scanner(System.in);
        while (scn.hasNextLine()) {

            A.add(scn.nextLine());
        }
	A.removeAll(Arrays.asList("", null));
        for(int i=0;i<2;i++)
        {
            A.add("null");
        }


//        FileWriter fw=new FileWriter("input.txt");
//        for(int m = 0; i<A.size(); i++)
//        {
//        	fw.write(A.get(i));
//        	System.out.println(A.get(i));
//        }
//        fw.close();
//
        String File= "input.txt";//input will be location of file by just running the code and entering the location of test file containing test cases


        String[] variable = new String[5000];
        String[] label = new String[5000];
        String[] variable_position = new String[5000];
        String[] label_position = new String[5000];

        File file = new File(File);

        try {

            for(int m = 0; m < (A.size()-2); m++)  {
                String line = A.get(m);
                if(line.trim().length()>0) {
//                    if(hlt_num2)
//                    {
//                        break;
//                    }
                    String result = line.trim().replaceAll("\\s+", " ");
                    String c = result.replace("\\t"," ");
                    String[] c_arr=c.split(" ");
                    String d = c.split(" ")[0];
                    if(d.equals("var") && var_num2 == true){
                        ctr++;
                    }
                    else if(d.equals("var")) {
                        if(c_arr.length==2) {
                            String f = c.split(" ")[1];
                            variable[var] = f;
                            var++;
                        }
                    }
//                    else if(d.equals("hlt"))
//                    {
//                        if(c_arr.length==1)
//                        {
//                            hlt_num2=true;
//                        }
//                        ctr++;
//                    }
                    else {
                        var_num2=true;
                        ctr++;
                    }
                    int k =0;
                    for(int i=0;i<c.length();i++) {
                        if(c.charAt(i)==':') {
                            k++;
                        }
                    }
                    int flag = 1;
                    String j = "";
                    if(k==1) {
                        for(int i=0;i<d.length()-1;i++) {
                            if(funchar(d.charAt(i))) {
                                j += d.charAt(i);
                            }
                            else {
                                flag=0;
                            }
                        }
                        if(flag==1) {
                            label[lab]=j;
                            label_position[lab]=String.valueOf(Decimalto8bitbinary(ctr-1));
                            lab++;
                        }
                    }
                }

            }

        } finally {}
        int i = 0;
        while(variable[i]!=null) {
            variable_position[i]=String.valueOf(Decimalto8bitbinary(ctr));
            i++;
            ctr++;
        }



        try  {
            for(int m = 0; m < (A.size()-2); m++)  {
                String line = A.get(m);
                if(line.trim().length()>0) {
                    ln++;
                    if(ln>256) {
                        System.out.println("Line no.: "+ln+" no. of lines exceeded");
                        break;
                    }
//		    	   if(hlt_num)
//		    	   {
//		    		   System.out.println("Line no.: "+ln+" Already one hlt instruction is found hence, Error");
//		    		   break;
//		    	   }
                    String c = line.trim().replaceAll("\\s+", " ");
                    String s = c.replace("\\t"," ");
                    String[] s_arr=s.split(" ");
                    String d = s.split(" ")[0];
                    String sf = "";
                    String s_final = "";
                    int z = 0;

                    if(d.equals("var") && var_num == true){
                       System.out.println("Line no.: "+ln+" given variable is not defined at the starting");
                       continue;
                    }

                    else if(d.equals("var")) {
                        if(s_arr.length==2) {
                            String fun = "";
                            int fun_flag = 1;
                            for(int a=0;a<s_arr[1].length();a++) {
                                if(funchar(s_arr[1].charAt(a))) {
                                    fun += s_arr[1].charAt(a);
                                }
                                else {
                                    fun_flag=0;
                                }
                            }
                            if(fun_flag==1) {
                                continue;
                            }
                            else {
                                System.out.println("Line no.: "+ln+" given variable name is wrong");
                                continue;
                            }
                        }
                        else {
                            System.out.println("Line no.: "+ln+" undefined variable instruction");
                            continue;
                        }
                    }
                    var_num=true;

                    int k =0;
                    for(int a=0;a<s.length();a++) {
                        if(s.charAt(a)==':') {
                            k++;
                        }
                    }
                    int flag = 1;
                    String j = "";
                    if(k==0)
                    {
                        //
                    }
                    else if(k==1) {
                        for(int a=0;a<d.length()-1;a++) {
                            if(funchar(d.charAt(a))) {
                                j += d.charAt(a);
                            }
                            else {
                                flag=0;
                            }
                        }
                        String[] sf_arr=s.split(" ");
                        if(flag==1) {
                            for(int a=1;a<sf_arr.length;a++) {
                                sf+=sf_arr[a]+" ";
                            }
                            z=1;
                        }
                        else {
                            System.out.println("Line no.: "+ln+" undefeined label");
                            continue;
                        }
                    }
                    else {
                        System.out.println("Line no.: "+ln+" undefeined label, more than one labels used");
                        continue;
                    }

                    String s0 = "";
                    if(z==1) {
                        s_final = sf;
                        s0 = sf.split(" ")[0];
                    }
                    else {
                        s_final = s;
                        s0 = s.split(" ")[0];
                    }

                    if(s0.equals("add") || s0.equals("sub") || s0.equals("mul") || s0.equals("xor") || s0.equals("or") || s0.equals("and")) {
                        TypeA(s_final,ln);
                        continue;
                    }
                    else if(s0.equals("rs") || s0.equals("ls")) {
                        TypeB(s_final,ln);
                        continue;
                    }
                    else if(s0.equals("not") || s0.equals("cmp")|| s0.equals("div")){
                        TypeC(s_final,ln);
                        continue;
                    }
                    else if(s0.equals("ld") || s0.equals("st")) {
                        TypeD(s_final,ln,variable,variable_position);
                        continue;
                    }
                    else if(s0.equals("jmp") || s0.equals("jlt") || s0.equals("jgt") || s0.equals("je")) {
                        TypeE(s_final,ln,label,label_position);
                        continue;
                    }
                    else if(s0.equals("mov")){
                        mov_calling(s_final,ln);
                    }
                    else if(s0.equals("hlt")){
                        TypeF(s_final,ln);
                        continue;
                    }
                    else {
                        System.out.println("Line no.: "+ln+" invalid instruction");
                        continue;
                    }

                }

            }
        }finally {}
        if(hlt_num == false) {
            System.out.println("Error: hlt not found");
        }
        
    }
    //
}

