package com.example.util;


import com.sun.management.OperatingSystemMXBean;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author:DarenSu
 * @Date: 2021/04/21
 * @Time: 14:42
 * @LinkedOfCode: http://cache.baiducontent.com/c?m=fu09jpSOBwNRbQPEkv5OZBVV5sXxvEDifFd2hX08_Uza7fsa_UCaRDlx4NqASPrs2lfF_E3n_W4fDxzrwKPM99OVUI8rwzCvgdhC5oh_d6Qdh5KtMjpc7_e5X17_Njz0T_23wgzlcleuy1rOCNnR6q15n1rfVa0OHQ5cK-BB8mbFU_ssmnOiK05kmwxS8vaqWWkNiTbP0huNg4fWUJrzjl57RWPGJslmYndycMHP0OK&p=882a914e8bd919fc57ef8f274951&newp=882a914e92904ead1ea4cb29130e92695d0fc20e3ad1d501298ffe0cc4241a1a1a3aecbf2c251604d8cf7b6404a84e5ceef033763d0034f1f689df08d2ecce7e6b&s=ec8ce6abb3e952a8&user=baidu&fm=sc&query=JAVA%BB%F1%C8%A1Lunix%B5%C4CPU%BA%CD%C4%DA%B4%E6%D0%C5%CF%A2&qid=c1532ed6000143ca&p1=5
 * @LinkedOfMaven: https://blog.csdn.net/qq_39704682/article/details/84787634
 *
 */


public class ComputerMonitorUtil {

    private static String osName = System.getProperty("os.name");
    private static final int CPUTIME = 500;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;
    private static final Logger logger = Logger.getLogger(ComputerMonitorUtil.class);

    /**
     * 功能：Linux & Window
     *  CPU
     * */
    public static double getCpuUsage() {
        // window
        if (osName.toLowerCase().contains("windows")
                || osName.toLowerCase().contains("win")) {
            try {
                String procCmd = System.getenv("windir")
                        + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
                // Fetch process information
                long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));//The first read of CPU information
                Thread.sleep(CPUTIME);//Sleep 500 ms
                long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));//Read the CPU information a second time
                if (c0 != null && c1 != null) {
                    long idletime = c1[0] - c0[0];
                    long busytime = c1[1] - c0[1];
                    Double cpusage = Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime));
                    BigDecimal b1 = new BigDecimal(cpusage);
                    double cpuUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    return cpuUsage;
                } else {
                    return 0.0;
                }
            } catch (Exception ex) {
                logger.debug(ex);
                return 0.0;
            }

        } else {

            try {
                Map<?, ?> map1 = ComputerMonitorUtil.cpuinfo();
                Thread.sleep(CPUTIME);
                Map<?, ?> map2 = ComputerMonitorUtil.cpuinfo();

                long user1 = Long.parseLong(map1.get("user").toString());
                long nice1 = Long.parseLong(map1.get("nice").toString());
                long system1 = Long.parseLong(map1.get("system").toString());
                long idle1 = Long.parseLong(map1.get("idle").toString());

                long user2 = Long.parseLong(map2.get("user").toString());
                long nice2 = Long.parseLong(map2.get("nice").toString());
                long system2 = Long.parseLong(map2.get("system").toString());
                long idle2 = Long.parseLong(map2.get("idle").toString());

                long total1 = user1 + system1 + nice1;
                long total2 = user2 + system2 + nice2;
                float total = total2 - total1;

                long totalIdle1 = user1 + nice1 + system1 + idle1;
                long totalIdle2 = user2 + nice2 + system2 + idle2;
                float totalidle = totalIdle2 - totalIdle1;

                float cpusage = (total / totalidle) * 100;

                BigDecimal b1 = new BigDecimal(cpusage);
                double cpuUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return cpuUsage;
            } catch (InterruptedException e) {
                logger.debug(e);
            }
        }
        return 0;
    }

    /**
     * 功能：Linux CPU
     * */
    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            logger.debug(e);
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                logger.debug(e2);
            }
        }
        return map;
    }

    /**
     * Linux & Window
     * Memory usage
     * */
    public static double getMemUsage() {
        if (osName.toLowerCase().contains("windows")
                || osName.toLowerCase().contains("win")) {

            try {
                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                        .getOperatingSystemMXBean();
                // Total physical memory + virtual memory
                long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
                // Remaining physical memory
                long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
                Double usage = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
                BigDecimal b1 = new BigDecimal(usage);
                double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return memoryUsage;
            } catch (Exception e) {
                logger.debug(e);
            }
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            InputStreamReader inputs = null;
            BufferedReader buffer = null;
            try {
                inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
                buffer = new BufferedReader(inputs);
                String line = "";
                while (true) {
                    line = buffer.readLine();
                    if (line == null)
                        break;
                    int beginIndex = 0;
                    int endIndex = line.indexOf(":");
                    if (endIndex != -1) {
                        String key = line.substring(beginIndex, endIndex);
                        beginIndex = endIndex + 1;
                        endIndex = line.length();
                        String memory = line.substring(beginIndex, endIndex);
                        String value = memory.replace("kB", "").trim();
                        map.put(key, value);
                    }
                }

                long memTotal = Long.parseLong(map.get("MemTotal").toString());
                long memFree = Long.parseLong(map.get("MemFree").toString());
                long memused = memTotal - memFree;
                long buffers = Long.parseLong(map.get("Buffers").toString());
                long cached = Long.parseLong(map.get("Cached").toString());

                double usage = (double) (memused - buffers - cached) / memTotal * 100;
                BigDecimal b1 = new BigDecimal(usage);
                double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return memoryUsage;
            } catch (Exception e) {
                logger.debug(e);
            } finally {
                try {
                    buffer.close();
                    inputs.close();
                } catch (Exception e2) {
                    logger.debug(e2);
                }
            }

        }
        return 0.0;
    }

    /**
     * Window &  Linux
     * Disk utilization
     *
     * @return
     * @throws Exception
     */
    public static double getDiskUsage() throws Exception {
        double totalHD = 0;
        double usedHD = 0;
        if (osName.toLowerCase().contains("windows")
                || osName.toLowerCase().contains("win")) {
            long allTotal = 0;
            long allFree = 0;
            for (char c = 'A'; c <= 'Z'; c++) {
                String dirName = c + ":/";
                File win = new File(dirName);
                if (win.exists()) {
                    long total = (long) win.getTotalSpace();
                    long free = (long) win.getFreeSpace();
                    allTotal = allTotal + total;
                    allFree = allFree + free;
                }
            }
            Double precent = (Double) (1 - allFree * 1.0 / allTotal) * 100;
            BigDecimal b1 = new BigDecimal(precent);
            precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return precent;
        } else {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("df -hl");// df -hl View hard disk space
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String str = null;
                String[] strArray = null;
                while ((str = in.readLine()) != null) {
                    int m = 0;
                    strArray = str.split(" ");
                    for (String tmp : strArray) {
                        if (tmp.trim().length() == 0)
                            continue;
                        ++m;
                        if (tmp.indexOf("G") != -1) {
                            if (m == 2) {
                                if (!tmp.equals("") && !tmp.equals("0"))
                                    totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                            }
                            if (m == 3) {
                                if (!tmp.equals("none") && !tmp.equals("0"))
                                    usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                            }
                        }
                        if (tmp.indexOf("M") != -1) {
                            if (m == 2) {
                                if (!tmp.equals("") && !tmp.equals("0"))
                                    totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            }
                            if (m == 3) {
                                if (!tmp.equals("none") && !tmp.equals("0"))
                                    usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.debug(e);
            } finally {
                in.close();
            }
            // Keep two decimal places
            double precent = (usedHD / totalHD) * 100;
            BigDecimal b1 = new BigDecimal(precent);
            precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return precent;
        }

    }

    // window cpu
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;//读取物理设备时间
            long usertime = 0;//执行代码占用时间
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
// Order of field occurrence：Caption,CommandLine,KernelModeTime,ReadOperationCount
                String caption = substring(line, capidx, cmdidx - 1).trim();
// System.out.println("caption:"+caption);
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
// System.out.println("cmd:"+cmd);
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                List<String> digitS1 = getDigit(s1);
                List<String> digitS2 = getDigit(s2);

// System.out.println("s1:"+digitS1.get(0));
// System.out.println("s2:"+digitS2.get(0));

                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0) {
                        if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
                            idletime += Long.valueOf(digitS1.get(0)).longValue();
                        }
                    }
                    if (s2.length() > 0) {
                        if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
                            idletime += Long.valueOf(digitS2.get(0)).longValue();
                        }
                    }
                    continue;
                }
                if (s1.length() > 0) {
                    if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
                        kneltime += Long.valueOf(digitS1.get(0)).longValue();
                    }
                }

                if (s2.length() > 0) {
                    if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
                        kneltime += Long.valueOf(digitS2.get(0)).longValue();
                    }
                }
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;

            return retn;
        } catch (Exception ex) {
            logger.debug(ex);
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                logger.debug(e);
            }
        }
        return null;
    }

    /**
     * Get the number from the string text
     *
     * @param text
     * @return
     */
    private static List<String> getDigit(String text) {
        List<String> digitList = new ArrayList<String>();
        digitList.add(text.replaceAll("\\D", ""));
        return digitList;
    }

    /**
     * Due to the problem of string.substring handling Chinese characters (treating a Chinese character as a byte),
     * there are hidden dangers when containing Chinese characters.
     * The adjustment is as follows:
     *
     * @param src
     * The string to be intercepted
     * @param start_idx
     * Start coordinate (inclusive)
     * @param end_idx
     * Cutoff coordinates (inclusive)
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }

    public static void main(String[] args) throws Exception {

        //The CPU usage of the current system
        double cpuUsage = ComputerMonitorUtil.getCpuUsage();
//The memory usage of the current system
        double memUsage = ComputerMonitorUtil.getMemUsage();
//The disk usage of the current system
        double diskUsage = ComputerMonitorUtil.getDiskUsage();

        System.out.println("cpuUsage:"+cpuUsage);
        System.out.println("memUsage:"+memUsage);
        System.out.println("diskUsage:"+diskUsage);


    }

}

