import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class Main {
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static byte[] getHMACKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] byteKey = new byte[32];
        secureRandom.nextBytes(byteKey);
        return byteKey;
    }
    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static byte[] getHmac(String message,byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        Mac sha256Hmac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_SHA256);
        sha256Hmac.init(keySpec);
        return sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hmac;
        String hmacKey;
        byte[] byteHmacKey;
        byte[] byteHmac;
        int choice = -1;
        int compChoice = -1;
        String move = "";
        String compMove = "";
        Scanner scanner = new Scanner(System.in);
        Set<String> check = new TreeSet<>();
        check.addAll(Arrays.asList(args.clone()));
        if(check.size()!= args.length || args.length % 2 != 1 || args.length < 3){
            System.out.println("Input ERROR!\nCheck your args(3 and more, but only odd, NO duplicates) ");
            return;
        }
        LoopList<String> list = new LoopList<>();
        list.add(args);

        while(true){
            try {
                compChoice = new SecureRandom().nextInt(args.length);
                compMove = list.get(compChoice).getData();
                byteHmacKey = Main.getHMACKey();
                byteHmac = getHmac(compMove,byteHmacKey);
                hmac = bytesToHex(byteHmac);
                System.out.println("HMAC: " + hmac);

                printMenu(args);
                choice = Integer.valueOf(scanner.nextLine());
                while(choice < 0 || choice > list.getSize()){
                    System.out.println("Enter wright move!");
                    choice = Integer.valueOf(scanner.nextLine());
                }
                if(choice == 0) break;
                choice--;
                move = list.get(choice).getData();
                System.out.println("Your move: " + move);
                System.out.println("Comp move: " + compMove);

                if(list.distanceBetween(list.get(compChoice),list.get(choice)) <= list.getSize()/2){
                    System.out.println("You WIN!");
                }
                else {
                    System.out.println("You LOSE!");
                }
                hmacKey = bytesToHex(byteHmacKey);
                System.out.println("HMAC Key: " + hmacKey);
                System.out.println("___________________________________________");
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMenu(String[] arg) {
        System.out.println("0 - EXIT");
        for (int i = 0; i < arg.length; i++) {
            System.out.println(i+1 + " - " + arg[i]);
        }
        System.out.println("Enter your move: ");
    }
}