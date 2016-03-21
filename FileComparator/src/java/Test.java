package java;

import java.math.BigDecimal;
import java.util.Date;

public class Test {
	
	private Date today; 
	 private Long number; 
	 private BigDecimal dec;
	 
	 
	 
	 public Date getToday() {
		return today;
	}



	public void setToday(Date today) {
		this.today = today;
	}



	public Long getNumber() {
		return number;
	}



	public void setNumber(Long number) {
		this.number = number;
	}



	public BigDecimal getDec() {
		return dec;
	}



	public void setDec(BigDecimal dec) {
		this.dec = dec;
	}



	public static void main(String[] args) {
		Test test = new Test();
		test.setDec(new BigDecimal("5.01"));
		test.setNumber(5l);
		test.setToday(new Date());
		
		System.out.println(test.getDec().toString());
		System.out.println(test.getToday().toString());
		
	}

}
