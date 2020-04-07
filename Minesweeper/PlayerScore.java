package Minesweeper;

import java.util.Objects;


public class PlayerScore implements Comparable<PlayerScore> {

	private String name;
	private long time;

	public PlayerScore(String name, long time) {

		this.name = name;
		this.time = time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, time);
	}

	
	public long getTime() {
		return time;
	}

	@Override
	public int compareTo(PlayerScore that) {
		// TODO Auto-generated method stub
		return (int) (this.getTime() - that.getTime());
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PlayerScore)) {
			return false;
		}
		PlayerScore other = (PlayerScore) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		
		long millis = time % 1000;
		long second = (time / 1000) % 60;
		long minute = (time/ (1000 * 60)) % 60;
		long hour = (time / (1000 * 60 * 60)) % 24;
		String printedTime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
		
		return this.name + " - " + printedTime;
	}

}
