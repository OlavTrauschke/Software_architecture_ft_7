package nl.uva.se.ft3impl.log;

import java.time.LocalDateTime;

/**
 * A log message
 */
public class LogMessage {
	protected String message;
	protected int priority;
	protected Site site;
	protected LocalDateTime timestamp;
	
	public LogMessage(String message, Site site) {
		this(message, site, 3);
	}
	
	public LogMessage(String message, Site site, int priority) {
		this(message, site, priority, LocalDateTime.now());
	}
	
	public LogMessage(String message, Site site, int priority, LocalDateTime timestamp) {
		this.message = message;
		this.priority = priority;
		this.site = site;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return timestamp + ": " + message + ", site: " + site + ", prio " + priority;
	}
}
