import java.util.*;

/**
 * UPCA Barcode class which is capable of encoding/decoding UPCA barcodes.
 */
public class UPCA {
    /**
     * String product_code: stores the product_code of UPCA barcodes as a String.
     */
    private String product_code;
    /**
     * String barcode: stores UPCA barcodes as 1's and 0's in a String.
     */
    private String barcode;
    /**
     * int checkdigit: stores the check digit of a UPCA product code as an int.
     */
    private int checkdigit;
    /**
     *  map_encoding_first_six: used for encoding the first six digits
     * of a product code into a UPCA barcode.
     */
    private static final Map<Integer,String> map_encoding_first_six = new HashMap<>(){{
        put(0,"0001101");
        put(1,"0011001");
        put(2,"0010011");
        put(3,"0111101");
        put(4,"0100011");
        put(5,"0110001");
        put(6,"0101111");
        put(7,"0111011");
        put(8,"0110111");
        put(9,"0001011");
    }};
    /**
     * map_encoding_last_six: used for encoding the last six digits of a
     * product code into a UPCA barcode
     */
    private static final Map<Integer,String> map_encoding_last_six = new HashMap<>(){{
        put(0,"1110010");
        put(1,"1100110");
        put(2,"1101100");
        put(3,"1000010");
        put(4,"1011100");
        put(5,"1001110");
        put(6,"1010000");
        put(7,"1000100");
        put(8,"1001000");
        put(9,"1110100");
    }};
    /**
     map_deconding_first_six: used for decoding the first six digits of a product
     code from a UPCA barcode.
     */
    private static final Map<String, Integer> map_deconding_first_six = new HashMap<>(){{
        put("0001101",0);
        put("0011001",1);
        put("0010011",2);
        put("0111101",3);
        put("0100011",4);
        put("0110001",5);
        put("0101111",6);
        put("0111011",7);
        put("0110111",8);
        put("0001011",9);
    }};
    /**
     *  map_deconding_last_six: used for decoing the last six digits of a product
     * code from a UPCA barcode
     */
    private static final Map<String, Integer> map_deconding_last_six = new HashMap<>(){{
        put("1110010",0);
        put("1100110",1);
        put("1101100",2);
        put("1000010",3);
        put("1011100",4);
        put("1001110",5);
        put("1010000",6);
        put("1000100",7);
        put("1001000",8);
        put("1110100",9);
    }};

    /**
     * void setProduct_code: sets the product code if it is valid otherwise it sets the product to null.
     * @param product_code: a string to be set to the private member product_code.
     */
    public void setProduct_code(String product_code) {
        if(product_code.length() == 11) {
            this.product_code = product_code;
        }else{
            this.product_code = null;
        }

    }

    /**
     * void convertProductCodeToBarcode: uses the product code member variable and using the appropiate encoding map,
     * converts the product code to a barcode and stores the result in the barcode member.
     */
    public void convertProductCodeToBarcode(){
        StringBuilder sb_barcode = new StringBuilder();
        sb_barcode.append("101");
        for(int i = 0; i < 12; i++){
            if(i <=5){
                sb_barcode.append(map_encoding_first_six.get(Integer.parseInt(String.valueOf(product_code.charAt(i)))));
            }else{
                sb_barcode.append(map_encoding_last_six.get(Integer.parseInt(String.valueOf(product_code.charAt(i)))));
            }
            if(i == 5){
                sb_barcode.append("01010");
            }
        }
        sb_barcode.append("101");
        barcode = sb_barcode.toString();
        System.out.println(barcode);

    }

    /**
     * void setCheckDigit: Finds the check digit for a given product code and assigns it to the checkdigit member.
     */
    public void setCheckDigit(){
        int sum = 0;
        for(int i = 0; i < 11;i++){
            if(i % 2 == 0) {
                sum += 3*(Character.getNumericValue(product_code.charAt(i)));
            }else{
                sum += Character.getNumericValue(product_code.charAt(i));
            }
        }
        checkdigit = 10 - sum%10;
        if(checkdigit == 10){
            product_code += 0;
        }else{
            product_code += checkdigit;
        }
    }

    /**
     * void convertBarcodeToProductCode: uses the barcode member and the appropriate decoding map member to
     * convert the barcode member to a product code, then assigns it to the product_code member.
     */
    public void convertBarcodeToProductCode(){
        String temp = barcode.substring(3,barcode.length()-3);
        int i =0;
        StringBuilder sb = new StringBuilder();
        while(i < temp.length()){
            if(i<42){
                sb.append(map_deconding_first_six.get(temp.substring(i,i+7)));
                i+=7;
            }else if(i == 42){
                i+=5;
            }else{
                sb.append(map_deconding_last_six.get(temp.substring(i,i+7)));
                i+=7;
            }
        }
        product_code = sb.toString();
        checkdigit = Integer.valueOf(product_code.substring(product_code.length()-1));
        product_code = product_code.substring(0,product_code.length()-1);
        System.out.println("Product Code: " + product_code);
    }

    /**
     * boolean valiateCheckDigit: uses the member product code generated from the decoding process and gets resulting
     * valid check digit and compares that to the previously assigned checkdigit.
     * @return returns a boolean expression comparing equality between the temp check digit and the member check digit.
     */
    public boolean valiateCheckDigit() {
        String temp = product_code;
        int sum = 0;
        for (int i = 0; i < temp.length(); i++) {
            if (i % 2 == 0) {
                sum += 3 * (Character.getNumericValue(temp.charAt(i)));
            } else {
                sum += Character.getNumericValue(temp.charAt(i));
            }
        }
        int check = 10 - sum % 10;
        return check == checkdigit;
    }

    /**
     * int getCheckdigit: returns the private member checkdigit.
     * @return returns checkdigit
     */
    public int getCheckdigit() {
        return checkdigit;
    }

    /**
     * String getProductCode: returns the private member product_code.
     * @return returns product_code
     */
    public String getProductCode(){
        return product_code;
    }

    /**
     * void setBarcode: sets the private member variable barcode to the passed String.
     * @param barcode: a string input being set to the private member barcode.
     */
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }
}
