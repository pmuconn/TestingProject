package com;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestDateformattime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
		//mysql date() removes time
//		Query query = this.getEntityManager().createQuery(" select distinct o from MasterDocument o inner join o.publishedDocs p where p.purged=false and  DATE(p.expiryDate) < '"+dateFormat.format(Calendar.getInstance().getTime())+"'",MasterDocument.class);

	}

}
