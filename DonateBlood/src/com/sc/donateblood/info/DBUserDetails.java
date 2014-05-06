/**
 * 
 */
package com.sc.donateblood.info;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * @author Deepesh
 * 
 */
public class DBUserDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
	public String distance;
	public String bloodGroup;
	public String contactNumber;
	public Double latitude;
	public Double longitude;
	public String emailId;
	public String location;
	public byte[] userImage;

}
