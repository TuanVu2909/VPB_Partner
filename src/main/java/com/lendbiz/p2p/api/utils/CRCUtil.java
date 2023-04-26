package com.lendbiz.p2p.api.utils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CRCUtil {

	private static final int[] TABLE = {
			0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50A5, 0x60C6, 0x70E7,
			0x8108, 0x9129, 0xA14A, 0xB16B, 0xC18C, 0xD1AD, 0xE1CE, 0xF1EF
	};

	public static String getCRC(String data) {
		try {
			int crc = calculate(data.getBytes());
			String res = Integer.toHexString(crc).toUpperCase();
			return res.substring(res.length() - 4);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public static int calculate(byte[] data) {
		int crc = 0xFFFF;

		for (int i = 0; i < data.length; i++) {
			crc = (crc << 4) ^ TABLE[((crc >> 12) ^ (data[i] >> 4)) & 0x0F];
			crc = (crc << 4) ^ TABLE[((crc >> 12) ^ (data[i] & 0x0F)) & 0x0F];
		}

		return crc;
	}

}
