import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.*;

/**
 * PostNet Class: The PostNet Class is capable of decoding and encoding PostNet barcodes/ZipCodes.
 */
public class PostNet {
    /**
     * int Zip: private member variable Zip is used to store the ZipCode to be encoded/decoded.
     */
    private int Zip;
    /**
     * int count: the private member variable count keeps track of the length of the Zip member.
     * And is initialized to 0.
     */
    private int count = 0;
    /**
     * String binary: the private member varibale is used to store the binary representation of a barcode to be decoded/encoded.
     */
    private String binary;
    /**
     * map: is used to encode ZipCodes into a binary representation of a barcode.
     */
    private static final Map<Integer,String> map = new HashMap<>(){{
        put(0,"11000");
        put(1,"00011");
        put(2,"00101");
        put(3,"00110");
        put(4,"01001");
        put(5,"01010");
        put(6,"01100");
        put(7,"10001");
        put(8,"10010");
        put(9,"10100");
    }};
    /**
     * map_decode: is used to decode binary representations of barcodes into ZipCodes.
     */
    private static final Map<String,Integer> map_decode = new HashMap<>(){{
        put("11000",0);
        put("00011",1);
        put("00101",2);
        put("00110",3);
        put("01001",4);
        put("01010",5);
        put("01100",6);
        put("10001",7);
        put("10010",8);
        put("10100",9);
    }};

    /**
     * void setZip: is used to assign the private member Zip to a passed String.
     * @param zip: a string zip to be assigned to the private member Zip
     */
    public void setZip(int zip) {
        Zip = zip;
    }

    /**
     * int getZip: return the private member Zip.
     * @return returns the private member Zip
     */
    public int getZip(){
        return this.Zip;
    }

    /**
     * int parseZip: is used to convert a String Zip (user input) into an Int and remove the '-'.
     * @param Zip: A string to be converted nto its Integer representation.
     * @return returns an int that is equivalent to the String passed into the function.
     */
    public int parseZip(String Zip){
        int int_zip = 0;
        for(int i = 0; i <=Zip.length()-1 ; i++){
            if(Zip.charAt(i) == '-'){

            }else{
                int_zip += Integer.parseInt(String.valueOf(Zip.charAt(i)));
                int_zip *=10;
            }
        }
        return int_zip/10;
    }

    /**
     * int findSum: returns the sum of the Zip digits.
     * @return returns the sum of the Zip digits.
     */
    public int findSum(){
        int sum = 0;
        int num = Zip;
        while(num > 0){
            int temp = num%10;
            sum += temp;
            num -= temp;
            num /=10;

        }
        return sum;
    }

    /**
     * int findCheckSum: finds the checkSum of a ZipCode.
     * @return returns the checksum of a ZipCode.
     */
    public int findCheckSum(){
        return 10-(findSum()%10);
    }

    /**
     * void addCheckSum: appends a passed int to the private member variable Zip.
     * @param num: a number to be appended to the private member Zip.
     */
    public void addCheckSum(int num){
        this.Zip *= 10;
        this.Zip += num;
    }

    /**
     * void convertToBinary: reverses the Zip then uses the private member map to encode a Zipcode into Binary
     * updates the count member variable and assigns the resulting binary representation of a barcode to the private member binary.
     */
    public void convertToBinary(){
        int num = Zip;
        StringBuilder string = new StringBuilder();
        int temp = num;
        int reversed = 0;
        while(temp != 0){
            reversed = reversed*10 + temp%10;
            temp /= 10;
        }
        num = reversed;
        string.append('1');
        while(num > 0){
            int digit = num%10;
            if(map.containsKey(digit)){
                string.append(map.get(digit));
            }
            num /= 10;
            count++;
        }
        string.append('1');
        binary = string.toString();
    }

    /**
     * String convertBinaryToBarcode: converts the binary representation of a barcode into a String barcode.
     * @return retunrs a String representation of a barcode.
     */
    public String convertBinaryToBarcode(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i < (count*5)+2;i++){
            if(binary.charAt(i)=='1'){
                sb.append('|');
            }else{
                sb.append('.');
            }
        }
        return sb.toString();
    }

    /**
     * void convertBarcodeToBinary: converts a passed String barcode into a binary representation of a barcode and assigns
     * the private member binary to the result.
     * @param barcode: a string barcode to be converted into a binary representation of a barcode.
     */
    public void convertBarcodeToBinary(String barcode){
        StringBuilder string = new StringBuilder();
        for(int i = 1; i < barcode.length()-1;i++){
            if(barcode.charAt(i) == '|'){
                string.append(1);
            }else{
                string.append(0);
            }
        }
        binary = string.toString();
    }

    /**
     * void convertBinaryToZIP: converts the private member binary to a Zip code using the private member map_decode.
     * Then assigns the result to the private member Zip.
     */
    public void convertBinaryToZIP(){
        if(binary.length()%5 != 0){
            throw new InvalidParameterException();
        }
        int num =0;
        for(int i = 0; i < binary.length();i+=5){
            if(map_decode.containsKey(binary.substring(i,i+5))){
                num += map_decode.get(binary.substring(i,i+5));
                num*=10;
            }
        }
        Zip = num/100;
    }




}
