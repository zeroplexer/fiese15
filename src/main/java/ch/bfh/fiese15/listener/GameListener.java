package ch.bfh.fiese15.listener;

public interface GameListener {

	public void onGameStateChanged();
	
	public void onPlayerChanged();
	
	public void onGameEndedMultiplayer();
	
	public void onGameEndedSingleplayer();
}
