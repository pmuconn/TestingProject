package com;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;


public class PhoneFormatter {

    public String formatPhoneNumber(String phone) {
        DecimalFormat phoneFormatD = new DecimalFormat("0000000000");
        DecimalFormat phoneFormatD2 = new DecimalFormat("0000000");
//        MessageFormat phoneFormatM = new MessageFormat("({0}) {1}-{2}");
        MessageFormat phoneFormatM = new MessageFormat("{0}-{1}-{2}");
        MessageFormat phoneFormatM2 = new MessageFormat("{1}-{2}");
        String r = "";
        if (StringUtils.isNotEmpty(phone)) {
            boolean area = false;
            int p = 0;

            phone = phone.replaceAll("[^0-9]", "");
            p = Integer.valueOf(phone);

            if (p == 0 || String.valueOf(p) == "" || String.valueOf(p).length() < 7) {
                return "";
            }

            //format change if area code exists
            String fot = "";
            if (String.valueOf(p).length() == 7) {
                fot = phoneFormatD2.format(p);
            } else {
                fot = phoneFormatD.format(p);
                area = true;
            }
            fot = phoneFormatD.format(p);

            String extra = fot.length() > 10 ? fot.substring(0, fot.length() - 10) : "";
            fot = fot.length() > 10 ? fot.substring(fot.length() - 10, fot.length()) : fot;

            String[] arr = {
                    (fot.charAt(0) != '0') ? fot.substring(0, 3) : (fot.charAt(1) != '0') ? fot.substring(1, 3) : fot.substring(2, 3),
                    fot.substring(3, 6),
                    fot.substring(6)
            };
            if (area) {
                r = phoneFormatM.format(arr);
            } else {
                r = phoneFormatM2.format(arr);
            }
            r = (r.contains("(0)")) ? r.replace("(0) ", "") : r;
            r = (extra != "") ? ("+" + extra + " " + r) : r;


        }

        return r;
    }

    public static void main(String[] args) {
        String p1 = "123-444-5555";
        String p2 = "";
        String p3 = null;
        String p4 = "(123)111-1111";
        String p5 = "1112223333";
        String p6 = "123-2345";
        String p7 = "1232345";

        PhoneFormatter u = new PhoneFormatter();
        System.out.println(u.formatPhoneNumber(p1));
        System.out.println(u.formatPhoneNumber(p2));
        System.out.println(u.formatPhoneNumber(p3));
        System.out.println(u.formatPhoneNumber(p4));
        System.out.println(u.formatPhoneNumber(p5));
        System.out.println(u.formatPhoneNumber(p6));
        System.out.println(u.formatPhoneNumber(p7));
    }	
}
