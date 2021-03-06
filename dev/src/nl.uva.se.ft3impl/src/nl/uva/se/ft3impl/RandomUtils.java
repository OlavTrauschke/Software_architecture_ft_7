package nl.uva.se.ft3impl;

import java.util.Random;

public class RandomUtils {
	private static final Random r = new Random();
	private static final String[] sentences = {
		"So this is Christmas",
		"And what have you done",
		"Another year over",
		"And a new one just begun",
		"And so this is Christmas",
		"I hope you have fun",
		"The near and the dear ones",
		"The old and the young",
		"A very merry Christmas",
		"And a happy New Year",
		"Let's hope it's a good one",
		"Without any fear",
		"And so this is Christmas War is over",
		"For weak and for strong If you want it",
		"For rich and the poor ones War is over",
		"The world is so wrong Now",
		"And so Happy Christmas War is over",
		"For black and for white If you want it",
		"For yellow and red ones War is over",
		"Let's stop all the fight Now",
		"A very merry Christmas",
		"And a happy New Year",
		"Let's hope it's a good one",
		"Without any fear",
		"And so this is Christmas War is over",
		"And what have we done If you want it",
		"Another year over War is over",
		"And a new one just begun Now",
		"And so Happy Christmas War is over",
		"I hope you have fun If you want it",
		"The near and the dear one War is over",
		"The old and the young Now",
		"A very merry Christmas",
		"And a happy New Year",
		"Let's hope it's a good one",
		"Without any fear",
		"War is over if you want it",
		"War is over now"
	};
	
	public static Random getGenerator() {
		return r;
	}
	
	public static String randomString() {
		return sentences[r.nextInt(sentences.length)];
	}
	
	public static <T extends Enum<?>> T randomEnum(Class<T> cls) {
		T[] values = cls.getEnumConstants();
		return values[r.nextInt(values.length)];
	}
	
	public static int randomInt(int incMax) {
		return r.nextInt(incMax) + 1;
	}
}
