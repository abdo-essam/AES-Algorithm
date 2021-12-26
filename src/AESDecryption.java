// ADVANCED ENCRYPTION STANDARD (AES)

import java.util.HashMap;
import java.util.Scanner;


public class AESDecryption {


    private static void insert(String data, String[][] arr) {
        for (int i = 0; i < data.length(); ) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    arr[k][j] = data.substring(i, i + 2);
                    i = i + 2;
                }
            }
        }
    }


    private static void printArray(String[][] arr, String message) {
        System.out.println(message);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String hexToBinary(String hex) {

        String binary = "";
        hex = hex.toUpperCase();
        HashMap<Character, String> hashMap = new HashMap<>();
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;

        for (i = 0; i < hex.length(); i++) {
            ch = hex.charAt(i);
            if (hashMap.containsKey(ch))
                binary += hashMap.get(ch);
            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }
        return binary;
    }

    private static String binaryToHex(String binary) {

        HashMap<String, Character> hashMap = new HashMap<>();
        hashMap.put("0000", '0');
        hashMap.put("0001", '1');
        hashMap.put("0010", '2');
        hashMap.put("0011", '3');
        hashMap.put("0100", '4');
        hashMap.put("0101", '5');
        hashMap.put("0110", '6');
        hashMap.put("0111", '7');
        hashMap.put("1000", '8');
        hashMap.put("1001", '9');
        hashMap.put("1010", 'A');
        hashMap.put("1011", 'B');
        hashMap.put("1100", 'C');
        hashMap.put("1101", 'D');
        hashMap.put("1110", 'E');
        hashMap.put("1111", 'F');


        String bin;
        String hex = "";

        for (int i = 0; i < binary.length(); i = i + 4) {
            bin = binary.substring(i, i + 4);
            if (hashMap.containsKey(bin))
                hex += hashMap.get(bin);
            else {
                hex = "Invalid Binary String";
                return hex;
            }
        }
        return hex;
    }

/*    // function to xor two string arrays contain hex values
    private static String[][] xor2Arrays(String[][] arr, String[][] arr2) {
        String binaryNum = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // for 8 bits
                for (int k = 0; k < 8; k++) {
                    int bit = Integer.parseInt(String.valueOf(hexToBinary(arr[i][j]).charAt(k))) ^ Integer.parseInt(String.valueOf(hexToBinary(arr2[i][j]).charAt(k)));
                    binaryNum += Integer.valueOf(bit).toString();
                }
                arr[i][j] = binaryToHex(binaryNum);
                binaryNum = "";
            }
        }
        return arr;
    }
    */

    private static final String[][] inv_S_Box = {
            {"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"},
            {"7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"},
            {"54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"},
            {"08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"},
            {"72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"},
            {"6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"},
            {"90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"},
            {"d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"},
            {"3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"},
            {"96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"},
            {"47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"},
            {"fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"},
            {"1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"},
            {"60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"},
            {"a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"},
            {"17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"}
    };

    private static final String[][] S_BOX = {
            {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
            {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},
            {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
            {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
            {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
            {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
            {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
            {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
            {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
            {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
            {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
            {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
            {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
            {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
            {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
            {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}
    };


    private static String[][] invSubByte(String[][] input) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int row = -1;
                int column = -1;

                switch (input[i][j].charAt(0)) {
                    case '0':
                        row = 0;
                        break;
                    case '1':
                        row = 1;
                        break;
                    case '2':
                        row = 2;
                        break;
                    case '3':
                        row = 3;
                        break;
                    case '4':
                        row = 4;
                        break;
                    case '5':
                        row = 5;
                        break;
                    case '6':
                        row = 6;
                        break;
                    case '7':
                        row = 7;
                        break;
                    case '8':
                        row = 8;
                        break;
                    case '9':
                        row = 9;
                        break;
                    case 'A':
                        row = 10;
                        break;
                    case 'B':
                        row = 11;
                        break;
                    case 'C':
                        row = 12;
                        break;
                    case 'D':
                        row = 13;
                        break;
                    case 'E':
                        row = 14;
                        break;
                    case 'F':
                        row = 15;
                        break;
                }

                switch (input[i][j].charAt(1)) {
                    case '0':
                        column = 0;
                        break;
                    case '1':
                        column = 1;
                        break;
                    case '2':
                        column = 2;
                        break;
                    case '3':
                        column = 3;
                        break;
                    case '4':
                        column = 4;
                        break;
                    case '5':
                        column = 5;
                        break;
                    case '6':
                        column = 6;
                        break;
                    case '7':
                        column = 7;
                        break;
                    case '8':
                        column = 8;
                        break;
                    case '9':
                        column = 9;
                        break;
                    case 'A':
                        column = 10;
                        break;
                    case 'B':
                        column = 11;
                        break;
                    case 'C':
                        column = 12;
                        break;
                    case 'D':
                        column = 13;
                        break;
                    case 'E':
                        column = 14;
                        break;
                    case 'F':
                        column = 15;
                        break;
                }
                input[i][j] = inv_S_Box[row][column];

            }
        }

        return input;
    }

    private static String[][] invShiftRow(String[][] input) {
        String e;

        // i for loop for each row (0 row i don't need)
        for (int i = 1; i < 4; i++) {

            for (int repeat = 0; repeat < i; repeat++) { // i represent the repeats

                e = input[i][3]; // e is the element I need to move
                for (int j = 2; j >= 0; j--) {
                    input[i][j + 1] = input[i][j]; // in the same row I move between columns
                }

                input[i][0] = e;
            }

        }

        return input;
    }


    private static final String[][] inv_MIX_MATRICE = {
            {"0E", "0B", "0D", "09"},
            {"09", "0E", "0B", "0D"},
            {"0D", "09", "0E", "0B"},
            {"0B", "0D", "09", "0E"}
    };


    private static String[][] invMixColumn(String input[][]) {
        String result[][] = new String[4][4];
        String c;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c = "";
                for (int k = 0; k < 4; k++) {
                    c += getMulTable(inv_MIX_MATRICE[i][k], input[k][j]) + "+";
                }
                result[i][j] = getXORList(c);
            }
        }

        return result;
    }

    private static String getMulTable(String a, String b) {
        if (a.equals("0E")) {
            // x3+x2+x
            return shiftBit(b) + "+" + shiftBit(shiftBit(b)) + "+" + shiftBit(shiftBit(shiftBit(b)));
        }

        if (a.equals("0B")) {
            // x3+x+1
            return shiftBit(shiftBit(shiftBit(b))) + "+" + shiftBit(b) + "+" + b;
        }

        if (a.equals("0D")) {
            // x3+x2+1
            return shiftBit(shiftBit(shiftBit(b))) + "+" + shiftBit(shiftBit(b)) + "+" + b;
        }

        if (a.equals("09")) {
            // x3+1
            return shiftBit(shiftBit(shiftBit(b))) + "+" + b;
        }

        return null;
    }

    private static String shiftBit(String b) {

        //System.out.println("Hex : " + b);
        String binary = hexToBinary(b);
        // 1B is value of p(x) - 00011011
        boolean need1B = false;
        if (binary.charAt(0) == '1') {
            need1B = true;
        }
        // System.out.println("Substring : " + binary.substring(1));

        binary = (binary.substring(1) + "0");

        // System.out.println("Binary : " + binary);

        return (need1B) ? getXOR("1B", binaryToHex(binary)) : binaryToHex(binary);
    }


    private static String getXORList(String input) {
        String result = "";

        String[] inputTable = input.split("\\+");

        result = inputTable[0];

        for (int i = 1; i < inputTable.length; i++) {
            result = getXOR(result, inputTable[i]);
        }

        return result;

    }

    private static String getXOR(String a, String b) {
        String result = "";
        String a1 = hexToBinary(a);
        String b1 = hexToBinary(b);

        for (int i = 0; i < 8; i++) {
            result += (a1.charAt(i) == b1.charAt(i)) ? "0" : "1";
        }

        return binaryToHex(result);
    }

    private static String[][] addRoundKey(String[][] key, String[][] message) {
        String result[][] = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = getXOR(key[i][j], message[i][j]);
            }
        }
        return result;
    }

    // for 14 round
    private static final String[] rcon = {
            "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36", "6c", "d8", "ab", "4d"
    };

    private static String[][][] keyGeneration(String[][][] keys) {

        String[] newWord = new String[4];
        String[] firstColumn = new String[4]; // use it in xor to find the first word for the key


        // ( if 128 initial key length then key num is 10 = 40 Word )
        // ( if 192 initial key length then key num is 12 = 48 Word )
        // ( if 128 initial key length then key num is 14 = 56 Word )

        // the bigger step for generate keys
        for (int i = 1; i < keys.length; i++) {


            // step for each word in the key
            for (int row = 0; row < 4; row++) {
                newWord[row] = keys[i - 1][row][(keys.length - 8)]; // get last column of previous key - for key length 11 column is 3
            }
            rotWord(newWord);
            subByteArray(newWord);

            for (int row = 0; row < 4; row++) {
                firstColumn[row] = keys[i - 1][row][0]; // get first column of previous key
            }

            newWord = XOR2Arrays(newWord, firstColumn);


            newWord[0] = getXOR(newWord[0], rcon[i - 1]); // XOR the first value only

            for (int row = 0; row < 4; row++) {
                keys[i][row][0] = newWord[row];
            }

            // for key length 11 column is 4 and 13 is 6 and 15 is 8
            for (int c = 1; c < (keys.length - 7); c++) {
                for (int row = 0; row < 4; row++) {
                    keys[i][row][c] = getXOR(keys[i - 1][row][c], keys[i][row][c - 1]);
                }
            }


        }

        return keys;
    }

    private static String[] rotWord(String[] word) {
        String temp = word[0];
        for (int i = 0; i < word.length - 1; i++) {
            word[i] = word[i + 1];
        }
        word[word.length - 1] = temp;
        return word;
    }

    private static String[] subByteArray(String[] input) {


        for (int i = 0; i < 4; i++) {
            int row = -1;
            int column = -1;

            switch (input[i].charAt(0)) {
                case '0':
                    row = 0;
                    break;
                case '1':
                    row = 1;
                    break;
                case '2':
                    row = 2;
                    break;
                case '3':
                    row = 3;
                    break;
                case '4':
                    row = 4;
                    break;
                case '5':
                    row = 5;
                    break;
                case '6':
                    row = 6;
                    break;
                case '7':
                    row = 7;
                    break;
                case '8':
                    row = 8;
                    break;
                case '9':
                    row = 9;
                    break;
                case 'A':
                case 'a':
                    row = 10;
                    break;
                case 'B':
                case 'b':
                    row = 11;
                    break;
                case 'C':
                case 'c':
                    row = 12;
                    break;
                case 'D':
                case 'd':
                    row = 13;
                    break;
                case 'E':
                case 'e':
                    row = 14;
                    break;
                case 'F':
                case 'f':
                    row = 15;
                    break;
            }

            switch (input[i].charAt(1)) {
                case '0':
                    column = 0;
                    break;

                case '1':
                    column = 1;
                    break;

                case '2':
                    column = 2;
                    break;

                case '3':
                    column = 3;
                    break;

                case '4':
                    column = 4;
                    break;

                case '5':
                    column = 5;
                    break;

                case '6':
                    column = 6;
                    break;

                case '7':
                    column = 7;
                    break;

                case '8':
                    column = 8;
                    break;

                case '9':
                    column = 9;
                    break;

                case 'A':
                case 'a':
                    column = 10;
                    break;

                case 'B':
                case 'b':
                    column = 11;
                    break;

                case 'C':
                case 'c':
                    column = 12;
                    break;

                case 'D':
                case 'd':
                    column = 13;
                    break;

                case 'E':
                case 'e':
                    column = 14;
                    break;

                case 'F':
                case 'f':
                    column = 15;
                    break;
            }
            input[i] = S_BOX[row][column];

        }

        return input;
    }

    private static String[] XOR2Arrays(String[] arr, String[] arr2) {
        String[] result = new String[4];
        for (int i = 0; i < 4; i++) {
            result[i] = getXOR(arr[i], arr2[i]);
        }
        return result;
    }

    private static void printKeys(String[][][] keys, String message) {
        System.out.println(message);
        for (int i = 0; i < keys.length; i++) {
            printArray(keys[i], "Key " + (i + 1) + " Round " + i);
        }
    }

    public static void main(String[] args) {

        System.out.println("Overview of the AES Algorithm Decryption.\n" +
                "• It’s a Block Cipher. \n" +
                "• Encrypts blocks of size 128 bits where DES 64 bits.\n" +
                "• Uses a key of size 128, 192, and 256 bits\n");
        System.out.println("for test ");
        System.out.println("InitialKey = 2b7e151628aed2a6abf7158809cf4f3c");
        System.out.println("ciphertext = 3925841D02DC09FBDC118597196A0B32");
        System.out.println("Message must = 3243f6a8885a308d313198a2e0370734");


        //•Word is a 32 bits = 4 byte.
        //•For 128 key bit we will have from W0 to W43.
        //•For 192 key bit we will have from W0 to W51.
        //•For 256 key bit we will have from W0 to W59.


        System.out.print("Enter Key Length :");
        Scanner sc = new Scanner(System.in);
        int keyLength = sc.nextInt();
        int keyNum = 11;


        System.out.print("Enter The Key :");
        Scanner scString = new Scanner(System.in);
        String initialKey = scString.nextLine();
        switch (keyLength) {
            case 128:
                keyNum = 11; // 10 keys + initial key
                break;

            case 192:
                keyNum = 13; // 12 keys + initial key
                break;

            case 256:
                keyNum = 15; // 14 keys + initial key
                break;
            default:
        }

        // ciphertext :3925841D02DC09FBDC118597196A0B32
        System.out.print("Enter The ciphertext :");
        String c = scString.nextLine();

        // String initialKey = "2b7e151628aed2a6abf7158809cf4f3c";
        // String m = "3243f6a8885a308d313198a2e0370734";

        String[][][] keys = new String[keyNum][4][4];
        insert(initialKey, keys[0]);
        keyGeneration(keys);
        printKeys(keys, "Keys");
        System.out.println();


        String[][] cipher = new String[4][4];
        insert(c, cipher);
        printArray(cipher, "Cipher");

        int rounds = keyNum - 2;


        // Add Round Key
        // cipher xor Key with the last key

        System.out.println("Round " + (rounds + 1));
        cipher = addRoundKey(keys[rounds + 1], cipher);
        printArray(cipher, "Key XOR cipher");


        for (int r = rounds; r > 0; r--) {
            System.out.println("Round " + r);
            // invShiftRows
            cipher = invShiftRow(cipher);
            printArray(cipher, "After invShiftRow");

            // invSubBytes
            cipher = invSubByte(cipher);
            printArray(cipher, "After invSubBytes");

            // AddRoundKey
            cipher = addRoundKey(keys[r], cipher);
            printArray(cipher, "After AddRoundKey");

            // invMixColumns
            cipher = invMixColumn(cipher);
            printArray(cipher, "After invMixColumns");


        }

        System.out.println("Round " + 0);
        // InvShiftRows
        cipher = invShiftRow(cipher);
        printArray(cipher, "After invShiftRow");

        // invSubBytes
        cipher = invSubByte(cipher);
        printArray(cipher, "After invSubBytes");

        // AddRoundKey
        cipher = addRoundKey(keys[0], cipher);
        printArray(cipher, "After AddRoundKey");

        System.out.print("PlainText :");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(cipher[j][i]);
            }
        }


    }
}

