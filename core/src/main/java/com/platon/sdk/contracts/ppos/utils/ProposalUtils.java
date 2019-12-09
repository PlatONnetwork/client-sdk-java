package com.platon.sdk.contracts.ppos.utils;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProposalUtils {
	private static Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
  
	public static BigInteger versionStrToInteger(String version) {
		Matcher matcher = pattern.matcher(version);
	    if (matcher.find()) {
	      int ver = Byte.parseByte(matcher.group(1)) << 16 & 0x7fffffff;
	      int lite = Byte.parseByte(matcher.group(2)) << 8 & 0x7fffffff;
	      int patch = Byte.parseByte(matcher.group(3)) << 0 & 0x7fffffff;
	      int id = ver | lite | patch;
	      return BigInteger.valueOf(id);
	    } else {
	      throw new RuntimeException("version is invalid");
	    }
	}

	public static String versionInterToStr(BigInteger version) {
		int v = version.intValue();
		int ver = v >> 16 & 0x0000ffff;
	    int lite = v >> 8 & 0x000000ff;
	    int patch = v >> 0 & 0x000000ff;
	    return String.format("%s.%s.%s", ver, lite, patch);
	}
}
