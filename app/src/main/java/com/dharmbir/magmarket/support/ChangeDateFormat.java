package com.dharmbir.magmarket.support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangeDateFormat {
	// method to change time from milliseconds into sat, 24 Aug 2013 format.
	public static String getDate(String time) {
		long t = Long.parseLong(time);
		SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getDateDDMMMYYYY(String time) {
		long t = Long.parseLong(time);
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
		Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getYYYYMMDD_from_DDMMMYYYY(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// method to change time from milliseconds into 24 Aug 2013 format.
	public static String getDate2(String time) {
		long t = Long.parseLong(time);
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	// method to change time from milliseconds into 01:01 AM format.
	public static String getTime(String time) {
		long t = Long.parseLong(time) * 100;
		SimpleDateFormat f = new SimpleDateFormat("hh:mm aa", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	// method to change time from milliseconds into 15:01:00 format.
	public static String getTimeIn24(String time) {
		long t = Long.parseLong(time);
		SimpleDateFormat f = new SimpleDateFormat("kk:mm:ss", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	// method to get date from 2013-05-13 format to milliseconds.
	public static long getDateInMili(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date new_date;
		long time = 0L;
		try {
			new_date = df.parse(date);
			time = new_date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	// change date format from 2014-01-14 to 14-01-2014
	public static String getDateToAnother(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change date format from 23-9-2013 to 2013-09-23
	public static String getDateYYYYMMDD(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change date format from 23-9-2013 to 2013-09-23
	public static String getDateDDMMYYYY(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	/*
	// get day on date from milliseconds
	public static String getDay(String time) {
		long t = Long.parseLong(time);
		SimpleDateFormat f = new SimpleDateFormat("EEE", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}
	*/

	// change date format to 12 Aug 2013 from 2013-08-12 12:23:21
	public static String getDateFromDateTime(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change time format to 08:45 PM from 2013-08-12 12:23:21
	public static String getTimeFromDateTime(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("hh:mm aa", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change date format to 12 Aug 2013 from 2013-08-12
	public static String getDateMonthFromDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change time format to 12 Aug 2013, 08:45 PM from 2013-08-12 12:23:21
	public static String getNewDateTimeFromDateTime(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy, hh:mm aa",
				Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change time format to 12 Aug, 2013 from 2013-08-12
	public static String getNewDateTimeFromDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// method to change time from milliseconds into 23/03/2014 format.
	public static String getDateFromMili(String time) {
		long t = Long.parseLong(time) * 1000;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getDateFromMilisecondNew(String time) {
		long t = Long.parseLong(time) * 1000;
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getDateFromSec(String time) {
		long t = Long.parseLong(time) * 1000;
		SimpleDateFormat f = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getDateTimeFromSec(String time) {
		long t = Long.parseLong(time) * 1000;

//		SimpleDateFormat f = new SimpleDateFormat("dd MMM, yyyy, hh:mm aa", Locale.US);

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//2017-02-06 10:29:41

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(t);
		String s = f.format(calendar.getTime());
		return s;
	}

	// method get milliseconds from Date like 04/30/2014
	public static long getMilliSecFromDate(String date) {

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		Date new_date;
		long time = 0L;
		try {
			new_date = df.parse(date);
			time = new_date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;

	}

	// change date formatin portugues to 12,Aug 13 from 2013-08-12 12:23:21
	public static String getDateFromDateTimePortugues(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("dd, MMM yy, hh:mm", Locale.getDefault());
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change time format from 12:33:00 to 12:33 PM
	public static String getTimeForamteHHMMA(String time) {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("hh:mm aa", Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(time);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// change date time format in to 12,Aug 13 from 2013-08-12 12:23:21
	public static String getDateTimeMonthAM(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("MMM dd, yyyy",
				Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	// get difference in year between two date
	public static String getYearBetweenTwoDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date new_date = null;
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		int year = 0;
		try {
			new_date = df.parse(date);
			year = calendar.get(Calendar.YEAR);
			System.out.println("year current:- " + year);
			System.out.println("year:- "
					+ Integer.parseInt(simpleDateformat.format(new_date)));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ""
				+ (year - Integer.parseInt(simpleDateformat.format(new_date)));
	}

	public static String getTimeStamp(String date){

		long timeStamp = 0;

		try {
//			String str_date = date;
//			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//			Date date1 = (Date) formatter.parse(str_date);
//			System.out.println("Today is " + date1.getTime());
//			timeStamp = date1.getTime();


			String str_date = date;
			DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
			Date date1 = (Date)formatter.parse(str_date);
			long output=date1.getTime()/1000L;
			String str=Long.toString(output);
			timeStamp = Long.parseLong(str);
//			timeStamp = timeStamp / 1000;


		}catch (Exception e){
			e.printStackTrace();
		}

		return String.valueOf(timeStamp);
	}

	public static String getTimeStampFromDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",
				Locale.US);
		Date newDate;
		long timeStamp = 0L;
//		SimpleDateFormat f = new SimpleDateFormat("MMM",
//				Locale.US);
//		Calendar calendar = Calendar.getInstance();
		try {
			newDate = df.parse(date);
			timeStamp = newDate.getTime();
//			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return String.valueOf(timeStamp);
	}

	public static String getMonth(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("MMM",
				Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}

	public static String getYear(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",
				Locale.US);
		Date new_date;
		long t = 0L;
		SimpleDateFormat f = new SimpleDateFormat("yyyy",
				Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			new_date = df.parse(date);
			t = new_date.getTime();
			calendar.setTimeInMillis(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = f.format(calendar.getTime());
		return s;
	}


	public static String getDay(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",
				Locale.US);

		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = df.parse(date);
			calendar.setTime(date1);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		return s;
	}

	public static String getDayFromDate(String fromDate){
		String day = "";
		try {
			SimpleDateFormat inFormat = new SimpleDateFormat("dd MMM yyyy");
			Date date = inFormat.parse(fromDate);
			SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
			day = outFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return day;
	}

}
