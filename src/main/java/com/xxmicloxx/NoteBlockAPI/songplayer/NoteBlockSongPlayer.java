package com.xxmicloxx.NoteBlockAPI.songplayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.event.PlayerRangeStateChangeEvent;
import com.xxmicloxx.NoteBlockAPI.model.CustomInstrument;
import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Note;
import com.xxmicloxx.NoteBlockAPI.model.NotePitch;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.utils.CompatibilityUtils;
import com.xxmicloxx.NoteBlockAPI.utils.InstrumentUtils;

/**
 * SongPlayer created at a specified NoteBlock
 *
 */
public class NoteBlockSongPlayer extends RangeSongPlayer {

	private Block noteBlock;

	public NoteBlockSongPlayer(Song song) {
		super(song);
	}

	public NoteBlockSongPlayer(Song song, SoundCategory soundCategory) {
		super(song, soundCategory);
	}
	
	

	private NoteBlockSongPlayer(SongPlayer songPlayer) {
		super(songPlayer);
	}
	
	@Override
	void update(String key, Object value) {
		super.update(key, value);
		
		switch (key){
			case "noteBlock":
				noteBlock = (Block) value;
				break;
		}
	}

	/**
	 * Get the Block this SongPlayer is played at
	 * @return Block representing a NoteBlock
	 */
	public Block getNoteBlock() {
		return noteBlock;	
	}

	/**
	 * Set the Block this SongPlayer is played at
	 */
	public void setNoteBlock(Block noteBlock) {
		this.noteBlock = noteBlock;
		CallUpdate("noteBlock", noteBlock);
	}

	@Override
	public void playTick(Player player, int tick) {
		if (noteBlock.getType() != Material.NOTE_BLOCK) {
			return;
		}
		if (!player.getWorld().getName().equals(noteBlock.getWorld().getName())) {
			// not in same world
			return;
		}
		byte playerVolume = NoteBlockAPI.getPlayerVolume(player);

		for (Layer layer : song.getLayerHashMap().values()) {
			Note note = layer.getNote(tick);
			if (note == null) {
				continue;
			}
			player.playNote(noteBlock.getLocation(), InstrumentUtils.getBukkitInstrument(note.getInstrument()),
					new org.bukkit.Note(note.getKey() - 33));

			float volume = ((layer.getVolume() * (int) this.volume * (int) playerVolume) / 1000000F) 
					* ((1F / 16F) * getDistance());
			float pitch = NotePitch.getPitch(note.getKey() - 33);

			if (InstrumentUtils.isCustomInstrument(note.getInstrument())) {
				CustomInstrument instrument = song.getCustomInstruments()
						[note.getInstrument() - InstrumentUtils.getCustomInstrumentFirstIndex()];

				if (instrument.getSound() != null) {
					CompatibilityUtils.playSound(player, noteBlock.getLocation(), 
							instrument.getSound(), this.soundCategory, volume, pitch);
				} else {
					CompatibilityUtils.playSound(player, noteBlock.getLocation(), 
							instrument.getSoundFileName(), this.soundCategory, volume, pitch);
				}
			} else {
				CompatibilityUtils.playSound(player, noteBlock.getLocation(),
						InstrumentUtils.getInstrument(note.getInstrument()), this.soundCategory, volume, pitch);
			}

			if (isInRange(player)) {
				if (!this.playerList.get(player.getUniqueId())) {
					playerList.put(player.getUniqueId(), true);
					Bukkit.getPluginManager().callEvent(new PlayerRangeStateChangeEvent(this, player, true));
				}
			} else {
				if (this.playerList.get(player.getUniqueId())) {
					playerList.put(player.getUniqueId(), false);
					Bukkit.getPluginManager().callEvent(new PlayerRangeStateChangeEvent(this, player, false));
				}
			}
		}
	}
	
	/**
	 * Returns true if the Player is able to hear the current NoteBlockSongPlayer 
	 * @param player in range
	 * @return ability to hear the current NoteBlockSongPlayer
	 */	
	@Override
	public boolean isInRange(Player player) {
		if (player.getLocation().distance(noteBlock.getLocation()) > getDistance()) {
			return false;
		} else {
			return true;
		}
	}

	

}
