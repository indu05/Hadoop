package java;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;

public class MyBean implements WritableComparable<MyBean>{ 
	

	 private String name; 
	 private Date today; 
	 private Long number; 
	 private BigDecimal dec;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	
	
	
	private int compareField(MyBean myBean, MyBean newMyBean) {
		return	 ifNameEquals(myBean.getName(), newMyBean.getName() ) && 
				ifNumberEquals(myBean.getNumber(), newMyBean.getNumber() )
				&& ifDecEquals(myBean.getDec(), newMyBean.getDec() ) 
				&& isDateEquals(myBean.getToday(), newMyBean.getToday()) ?
				  0 : 1;
		
	}
	
	
	private boolean ifNameEquals(String name2, String name3) {
		Boolean equals = Boolean.FALSE;
		
		if( (null == name2 )  && (null == name3)){
			equals = Boolean.TRUE;
		}else if( (null != name2 )  && (null != name3) && name2.equals(name3)){
			equals = Boolean.TRUE;
		}
		
		return equals;
	}
	private boolean ifNumberEquals(Long number2, Long number3) {
		Boolean equals = Boolean.FALSE;
		if( (null == number2 )  && (null == number3)){
			equals = Boolean.TRUE;
		}else if( (null != number2 )  && (null != number3) && number2.equals(number3)){
			equals = Boolean.TRUE;
		}
		return equals;
	}
	
	private boolean ifDecEquals(BigDecimal dec2, BigDecimal dec3) {
		Boolean equals = Boolean.FALSE;
		if( (null == dec2 )  && (null == dec3)){
			equals = Boolean.TRUE;
		}else if( (null != dec2 )  && (null != dec3) && dec2.equals(dec3)){
			equals = Boolean.TRUE;
		}
		return equals;
	}
	
	private boolean isDateEquals(Date today , Date newToday) {
		Boolean equals = Boolean.FALSE;
		if( (null == today )  && (null == newToday)){
			equals = Boolean.TRUE;
		}else if( (null != today )  && (null != newToday) && today.equals(newToday)){
			equals = Boolean.TRUE;
		}
		return equals;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		  this.name = in.readUTF(); 
		  this.number = in.readLong();
		  this.today = null !=  this.today ? new Date(in.readLong()) : null;
		  this.dec = null != in.readUTF() ? new BigDecimal(in.readUTF()) : null;
	}
	@Override
	public void write(DataOutput out) throws IOException {
		 out.writeUTF(name); 
		 out.writeLong(number);
		  if(null!=today){
			  out.writeLong(today.getTime());
		  }
		  if(null!=dec){
			  out.writeUTF(dec.toString());
		  }
		  
		
	}
	@Override
	public int compareTo(MyBean newMyBean) {
		return compareField(this,(MyBean)newMyBean);
	}
	
	 
	public String toString(){
	String str = 	(null != this.name ? this.name : "null") ;
			str = str + (null != this.number ? this.number.longValue() : "null");
			str = str +	(null != this.today ? this.today.toString() : "null");
			str = str	+ (	null != this.dec ? this.dec.toPlainString() : "null");
			return str;
	}
	 
	 

}
