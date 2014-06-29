/*
 * 
 * 
 */

package com.techapi.bus.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class Common {

    private static final String randChars = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();
    private static SimpleDateFormat sdf1=new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat sdf3=new SimpleDateFormat("MM月dd日HH时mm分");
    private static SimpleDateFormat sdf4=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
    private static SimpleDateFormat sdf5=new SimpleDateFormat("yyyy/MM/dd");
    private static Calendar calendar = Calendar.getInstance();
    /**
     * 描述：md5加密
     * @param arg0 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String arg0) {
        return Md5Util.encode(arg0);
    }

    /**
     * 描述：返回定长随机字符，如果isOnlyNum为真，则只随机数值，如果为false，则包括大小写字母和数值
     *
     * @param length
     *            位数
     * @param isOnlyNum
     *            是否为数值
     * @return 随机的字符串
     * @author:airgilbert
     */
    public static String getRandStr(int length, boolean isOnlyNum, boolean isOnlyLowCase) {
        int size = isOnlyNum ? 10 : 62;
        size = isOnlyLowCase ? (size-26) : size;
        StringBuffer hash = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            hash.append(randChars.charAt(random.nextInt(size)));
        }
        return hash.toString();
    }

    public static int byte2Int(byte[] res)
	{
		int len = res.length;
		if(len >= 4)
			return toInt(res);
		
		byte[] tmps = new byte[4];
		if(res[len-1] < 0)
		{
			for(int i = len; i < 4; i++)
			{
				tmps[i] = 0xffffffff;
			}
		}
		else
		{
			for(int i = len; i < 4; i++)
			{
				tmps[i] = 0x00000000;
			}
		}
		for(int i = 0; i < len; i++)
		{
			tmps[i] = res[i];
		}
		
		return toInt(tmps);
	}
	
	public static int toInt(byte[] bRefArr)
	{
		int iOutcome = 0;
		byte bLoop;
		int len = bRefArr.length;
		
		for(int i = 0; i < len; i++)
		{
			bLoop = bRefArr[i];
			iOutcome+= (bLoop & 0xFF) << (8 * i);
		}
		return iOutcome;
	}
	
	public static void writeData(String file, byte[] buffer)
	{
		OutputStream os = null;
		File fl = new File(file);
		try {
			if(!fl.exists() || fl.isDirectory()) {
				fl.createNewFile();
			}
			os = new FileOutputStream(fl);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if(os != null) {
			try {
				os.write(buffer);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int getDaysByMonth(long currentMillis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentMillis);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
     * 获取文件后缀名。
     * @param filename
     * @return
     */
    public static String getSuffixOfFilename(String filename) {
        String suffix = "";
        if(StringUtils.indexOf(filename, ".")>0) {
            suffix = StringUtils.substring(filename, StringUtils.lastIndexOf(filename, ".") + 1);
        }
        return suffix.toLowerCase();
    }
    
    public static void appendStringToResponse(HttpServletResponse response,String content) throws IOException {
    	PrintWriter printWriter = response.getWriter();
        printWriter.write(content);
        printWriter.flush();
        printWriter.close();
    }
    
    /**
     * 获取两个日期之间的天数差
     * @param exitDateFrom
     * @param exitDateTo
     * @return
     */
    public static int getIntervalDaysOfExitDate(Date exitDateFrom, Date exitDateTo){
        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();
        aCalendar.setTime(exitDateFrom);
        bCalendar.setTime(exitDateTo);
        int days = 0;
        while(aCalendar.before(bCalendar)){
        	if(bCalendar.getTimeInMillis() - aCalendar.getTimeInMillis() > DateUtils.MILLIS_PER_DAY) {
        		days++;
        	}
        	aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }
    
    /**
     * 根据时间获取毫秒数
     * @param
     */
    public static long getMillsByDateTime(String dateTime) {
    	long millsTime = 0;
    	try {
			Date date = sdf4.parse(dateTime);
			millsTime = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return millsTime;
    	
    }
    public static void main(String[] args) {

	}
    
}
