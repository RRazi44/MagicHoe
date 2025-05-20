package fr.razi.magichoe;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class XpData {
	private int current;
	private int required;
	
	private XpData(int current, int required) {
		this.current = current;
		this.required = required;
	}
	
	public static Optional<XpData> from(String line){
		String clean = ChatColor.stripColor(line);
        Pattern pattern = Pattern.compile("\\[(\\d+)/(\\d+)]");
        Matcher matcher = pattern.matcher(clean);

        if (matcher.find()) {
            int current = Integer.parseInt(matcher.group(1));
            int required = Integer.parseInt(matcher.group(2));
            return Optional.of(new XpData(current, required));
        }

        return Optional.empty();
	}
	
	public void add(int amount) {
		this.current += amount;
	}
	
	public boolean isLevelUp() {
		return current >= required;
	}
	
	public void reset() {
	    this.current = 0;
	}
	
	public void setRequired(int newRequired) {
		this.required = newRequired;
	}
	
	public String toLoreLine() {
		return "§8Expérience requis [" + current + "/" + required + "]";
	}
	
	public int getCurrent() {
		return current;
	}
	
	public int getRequired() {
		return required;
	}
}
