package koupah;

public class Check {
	
	Category category;
	int maxFlags;
	int flags = 0;
	String name;
	
	
	public Check(Category c, int maxFlags, String name) {
		this.category = c;
		this.maxFlags = maxFlags;
		this.name = name;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public void addFlags(int i) {
		if (Window.debug)
			System.out.println("Added " + i + " flag/s to " + name);
		flags += i;
	}
	
	public int getMaxFlags() {
		return maxFlags;
	}
	
	public boolean hasReachedMax() {
		return flags >= maxFlags;
	}
	
	public String getName() {
		return name;
	}
	
}
