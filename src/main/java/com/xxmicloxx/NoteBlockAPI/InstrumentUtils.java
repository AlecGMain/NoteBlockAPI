package com.xxmicloxx.NoteBlockAPI;

/**
 * Various methods for working with instruments
 */
public class InstrumentUtils {

	/**
	 * Returns the org.bukkit.Sound enum for the current server version
	 * @param instrument
	 * @see Sound
	 * @return Sound enum (for the current server version)
	 */
	public static org.bukkit.Sound getInstrument(byte instrument) {
		return org.bukkit.Sound.valueOf(getInstrumentName(instrument));
	}

	/**
	 * Returns the name of the org.bukkit.Sound enum for the current server version
	 * @param instrument
	 * @see Sound
	 * @return Sound enum name (for the current server version)
	 */
	public static String getInstrumentName(byte instrument) {
		switch (instrument) {
			case 0:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_HARP").name();
			case 1:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_BASS").name();
			case 2:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_BASEDRUM").name();
			case 3:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_SNARE").name();
			case 4:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_HAT").name();
			case 5:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_GUITAR").name();
			case 6:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_FLUTE").name();
			case 7:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_BELL").name();
			case 8:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_CHIME").name();
			case 9:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_XYLOPHONE").name();
			default:
				return Sound.getFromBukkitName("BLOCK_NOTE_BLOCK_HARP").name();
		}
	}

	/**
	 * Returns the name of the org.bukkit.Instrument enum for the current server version
	 * @param instrument
	 * @return Instrument enum (for the current server version)
	 */
	public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
		switch (instrument) {
			case 0:
				return org.bukkit.Instrument.PIANO;
			case 1:
				return org.bukkit.Instrument.BASS_GUITAR;
			case 2:
				return org.bukkit.Instrument.BASS_DRUM;
			case 3:
				return org.bukkit.Instrument.SNARE_DRUM;
			case 4:
				return org.bukkit.Instrument.STICKS;
			case 5:
				return org.bukkit.Instrument.GUITAR;
			case 6:
				return org.bukkit.Instrument.FLUTE;
			case 7:
				return org.bukkit.Instrument.BELL;
			case 8:
				return org.bukkit.Instrument.CHIME;
			case 9:
				return org.bukkit.Instrument.XYLOPHONE;
			default:
				return org.bukkit.Instrument.PIANO;
		}
	}

	/**
	 * If true, the byte given represents a custom instrument
	 * @param instrument
	 * @return
	 */
	public static boolean isCustomInstrument(byte instrument) {
		if (CompatibilityUtils.isPost1_12()) {
			if (instrument > 9) {
				return true;
			}
			return false;
		}
		if (instrument > 4) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the first index in which a custom instrument 
	 * can be added to the existing list of instruments
	 * @return instrument as a byte
	 */
	public static byte getCustomInstrumentFirstIndex() {
		if (CompatibilityUtils.isPost1_12()) {
			return 10;
		}
		return 5;
	}

}