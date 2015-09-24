package nl.uva.se.ft3impl;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class PrintFilter extends Filter<String, String> {

	protected final String prefix;
	
	public PrintFilter(String prefix, Pipe<String> in, Pipe<String> out) {
		super(in, out);
		this.prefix = prefix;
	}

	@Override
	public String transformOne(String obj) {
		System.out.println(prefix + " - " + obj);
		return obj;
	}

}
