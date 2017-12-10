package application;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class AudioPlayer {
	private URL url;
	private java.applet.AudioClip audioclip;
	public AudioPlayer(URL u) {		
		url = u;
        audioclip = Applet.newAudioClip(url);
	}
	public void Play() {
		audioclip.play();
	}
}
