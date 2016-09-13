package com.lzokks04.myutilbox.utils.iputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Liu on 2016/9/9.
 */
public class IpUtils {

    private static IpUtils instance = new IpUtils();

    private IpUtils() {

    }

    public static IpUtils getInstance() {
        return instance;
    }


    public String Ping(String str) {
        InputStream input = null;
        BufferedReader in = null;
        Process p;
        try {
            p = Runtime.getRuntime().exec("ping -c 5 -w 100 " + str);
            p.waitFor();
            input = p.getInputStream();
            in = new BufferedReader(new InputStreamReader(input));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
//                buffer.append(line + "\n");
                if (MatcheIpAddress.isIpAddress(line) && MatcheIpAddress.isMs(line)) {
                    sb.append(MatcheIpAddress.match(line) + "\t\t\t" + MatcheIpAddress.matchMs(line) + "\n");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public String HostToIp(String str) {
        Process p;
        InputStream input = null;
        BufferedReader in = null;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + str);
            p.waitFor();
            input = p.getInputStream();
            in = new BufferedReader(new InputStreamReader(input));
            String line = "";
            while ((line = in.readLine()) != null) {
                if (MatcheIpAddress.isIpAddress(line)) {
                    return MatcheIpAddress.match(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String readSteam(InputStream is) {
        InputStreamReader isr = null;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public String getInfo(String str) {
        try {
            String body = readSteam(new URL("http://freeapi.ipip.net/" + str).openStream());
            return body;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBody(String str) {
        try {
            String body = readSteam(new URL(str).openStream());
            return MatcheIpAddress.match(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

