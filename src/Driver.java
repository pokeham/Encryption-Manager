import java.util.Scanner;

/**
 * Driver Class: used to house the functionality of the UPCA and PostNet Classes
 */
public class Driver {

    /**
     * Main function within the Driver Class, prompts user for determining what barcode thewy want to encode/decode
      * @param args the arguement given to the main to run
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String choice;
        boolean POSTNET = false;
        do{
            System.out.println("What Barcode are you using(0-POSTNET, 1-UPC-A):");
            choice = scanner.nextLine();
            if(choice.equals("0")){
                POSTNET = true;
            }else if(choice.equals("1")){
                POSTNET = false;
            }else{
                System.out.println("please enter a valid input!");
            }

        }while(!choice.equals("0") && !choice.equals("1"));
        if(POSTNET){
            PostNet postNet = new PostNet();
            boolean Encode = false;
            do{
                System.out.println("Would you Like to Encode or Decode (0-Endcode, 1-Decode): ");
                choice = scanner.nextLine();
                if(choice.equals("0")){
                    Encode = true;
                }else if(choice.equals("1")){
                    Encode = false;
                }else{
                    System.out.println("Please Enter a Valid Input!");
                }
            }while (!choice.equals("0") && !choice.equals("1"));
            if(Encode){
                System.out.println("Please Enter your ZIP, ZIP+4 or ZIP+4+deliverycode: ");
                choice = scanner.nextLine();

                postNet.setZip(postNet.parseZip(choice));
                if(postNet.findCheckSum() != 10){

                    postNet.setZip((postNet.getZip()*10)+ postNet.findCheckSum());

                }else{
                    postNet.setZip(postNet.findSum());
                }
                postNet.convertToBinary();
                System.out.println("The BarCode is: " + postNet.convertBinaryToBarcode());
            }else{
                System.out.println("Please enter a Valid Barcode: ");
                choice = scanner.nextLine();
                postNet.convertBarcodeToBinary(choice);
                postNet.convertBinaryToZIP();
                System.out.println("The ZipCode is: " + postNet.getZip());
            }
        }else {
            UPCA upca = new UPCA();
            boolean Encode = false;
            do{
                System.out.println("Would you Like to Encode or Decode (0-Endcode, 1-Decode): ");
                choice = scanner.nextLine();
                if(choice.equals("0")){
                    Encode = true;
                }else if(choice.equals("1")){
                    Encode = false;
                }else{
                    System.out.println("Please Enter a Valid Input!");
                }
            }while (!choice.equals("0") && !choice.equals("1"));
            if(Encode){
                System.out.println("Please Enter a valid Product Code: ");
                choice = scanner.nextLine();
                upca.setProduct_code(choice);
                if(upca.getProductCode() == null){
                    System.out.println("Please enter a valid Product code!");
                }else{
                    upca.setCheckDigit();
                    upca.convertProductCodeToBarcode();
                }
            }else{
                System.out.println("Please Enter a valid UPC-A barcode: ");
                choice = scanner.nextLine();
                upca.setBarcode(choice);
                upca.convertBarcodeToProductCode();
                if(upca.valiateCheckDigit()){
                    System.out.println("Check Digit: " + upca.getCheckdigit()+" -Good");
                }else{
                    System.out.println("Invalid Check Digit");
                }
            }
        }
    }
}
