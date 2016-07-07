package com;

public class TokenTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a = "S9V99.";
		String b = "9(01).";
		String c = "X.";
		String d = "99.";
		String d1 = "9(08).";
		String e = "X(01).";

//		getlen1(a);
//		getlen1(b);
//		getlen1(c);
//		getlen1(d);
//		getlen1(d1);
//		getlen1(e);
		
		System.out.println(" next run ... ");
		
		getlen2(a);
		getlen2(b);
		getlen2(c);
		getlen2(d);
		getlen2(d1);
		getlen2(e);
		
		
		
	}
	
	private static void getlen1(String str) {
	       String[] expl = str.split("\\(|\\)");
	       String len;
	        if(expl.length < 2) {
	            int num = expl[0].replace(".","").length() ;
	            len = String.valueOf(num);
	        }else{
	            len = expl[1];
	            if(expl[0].substring(0,1).equals("-")){
	               int newLen = Integer.parseInt(len) + 1;
	                len = String.valueOf(newLen);
	                System.out.println("+1!");
	            }
	            if(expl[2].charAt(0) == 'V'){
	                String newS = expl[2].substring(1).replace(".","");
	                int newLength = Integer.parseInt(len) + newS.length();
	                len = String.valueOf(newLength);
	            }
	        }
	        System.out.println("Length of " + str + " is " + len);
	}
	
	private static void getlen2(String str) {
		String characterString = str;
		
		characterString = characterString.replace(".", "");
		
//		curItem.element.setAttribute("picture", characterString);
		if (characterString.charAt(0) == 'S') {
//			curItem.element.setAttribute("signed", "true");
			characterString = characterString.substring(1);
		}
		int displayLength = 0, storageLength = 0;
            /* change "length" to "display-length" - bm  ??*/
//		if (curItem.element.hasAttribute("display-length")) {
//			displayLength = Integer.parseInt(curItem.element.getAttribute("display-length"));
//		}
//		if (curItem.element.hasAttribute("storage-length")) {
//			storageLength = Integer.parseInt(curItem.element.getAttribute("storage-length"));
//		}
		int decimalPos = -1;
		boolean isNumeric = false;
		boolean isFirstCurrencySymbol = true;
        String ucCharacterString = characterString.toUpperCase();
		for (int i = 0; i < characterString.length(); i++) {
			char c = ucCharacterString.charAt(i);
			switch (c) {
			case 'A':
			case 'B':
			case 'E':
				storageLength++;
			case 'G':
			case 'N':
				storageLength++;
				displayLength++;
				break;
			//==========================================
			case '.':
				displayLength++;
			case 'V':
				isNumeric = true;
				decimalPos = displayLength;
				break;
			//==========================================
			case 'P':
				if (characterString.charAt(0) == 'P') {
					decimalPos = 0;
				}
				isNumeric = true;
				displayLength++;
				break;
			//==========================================
			case '$':
				if (isFirstCurrencySymbol) {
					isFirstCurrencySymbol = false;
					isNumeric = true;
				} else {
					displayLength++;
				}
				break;
			//==========================================
			case 'C': // CR
			case 'D': // DR
				i++;  // skip R
			case 'Z':
			case '9':
			case '0':
			case '+':
			case '-':
			case '*':				
				isNumeric = true;
			case '/':
			case ',':			
			case 'X':
				displayLength++;
				break;
			case '(':
				int endParenPos = characterString.indexOf(')', i + 1);
				int count = Integer.parseInt(characterString.substring(i + 1,
						endParenPos));
				i = endParenPos;
				displayLength = displayLength + count - 1;
			}
		}
		
        System.out.println("Length of " + str + " is " + displayLength );

	}

}
