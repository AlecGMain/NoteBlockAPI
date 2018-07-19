package com.xxmicloxx.NoteBlockAPI;

import org.bukkit.entity.Player;

public class RadioSongPlayer extends SongPlayer {

	public RadioSongPlayer(Song song) {
		super(song);
	}

	public RadioSongPlayer(Song song, SoundCategory soundCategory) {
		super(song, soundCategory);
	}

	@Override
	public void playTick(Player player, int tick) {
		byte playerVolume = NoteBlockPlayerMain.getPlayerVolume(player);

		for (Layer layer : song.getLayerHashMap().values()) {
			Note note = layer.getNote(tick);
			if (note == null) {
				continue;
			}

			float volume = (layer.getVolume() * (int) this.volume * (int) playerVolume) / 1000000F;
			float pitch = NotePitch.getPitch(note.getKey() - 33);

			if (Instrument.isCustomInstrument(note.getInstrument())) {
				CustomInstrument instrument = song.getCustomInstruments()
						[note.getInstrument() - Instrument.getCustomInstrumentFirstIndex()];

				if (instrument.getSound() != null) {
					CompatibilityUtils.playSound(player, player.getEyeLocation(),
							instrument.getSound(),
							this.soundCategory, volume, pitch);
				} else {
					CompatibilityUtils.playSound(player, player.getEyeLocation(),
							instrument.getSoundFileName(),
							this.soundCategory, volume, pitch);
				}
			} else {
				CompatibilityUtils.playSound(player, player.getEyeLocation(),
						Instrument.getInstrument(note.getInstrument()), this.soundCategory, volume, pitch);
			}
		}
	}

}
