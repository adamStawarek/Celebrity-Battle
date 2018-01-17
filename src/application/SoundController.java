package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.*;

import javafx.fxml.Initializable;

public class SoundController implements Initializable{
	
		private String url;
		private int volume;
		Clip clip;
		
	  
		public SoundController(String u, int volume) {
			super();
			this.url = u;
			this.volume = volume;
		}
		
		public void Play() {
			 AudioInputStream stream = null;
				try {
					stream = AudioSystem.getAudioInputStream(getClass().getResource(url));
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
					    AudioFormat format = stream.getFormat();
					    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					      format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format
					          .getSampleRate(), format.getSampleSizeInBits() * 2, format
					          .getChannels(), format.getFrameSize() * 2, format.getFrameRate(),
					          true); // big endian
					      stream = AudioSystem.getAudioInputStream(format, stream);
					    }

					    DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
					        ((int) stream.getFrameLength() * format.getFrameSize()));
					    
						try {
							clip = (Clip) AudioSystem.getLine(info);
						} catch (LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					    try {
							clip.open(stream);
						} catch (LineUnavailableException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    
					    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					    
					    double gain;
					    switch (volume) {
					    	case 0:
					    		 gain = -70;
					    		 break;
					    	case 1:
					    		 gain = -60;
					    		 break;
					    	case 2:
					    		 gain = -50;
					    		 break;
					    	case 3:
					    		 gain = -40;
					    		 break;
					    	case 4:
					    		 gain = -30;
					    		 break;
					    	case 5:
					    		 gain = -20;					    		 
					    		 break;
					    	case 6:
					    		 gain = -15;
					    		 break;
					    	case 7:
					    		 gain = -10;
					    		 break;
					    	case 8:
					    		 gain = -5;
					    		 break;
					    	case 9:
					    		 gain = 0;
					    		 break;
					    	case 10:
					    		 gain = 5;
					    		 break;	 
					    	default:
					    		gain=0;
					    	
					    }
					    
					    //float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
					    gainControl.setValue((float) gain);

					    BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
					    muteControl.setValue(true);

					    muteControl.setValue(false);

					    clip.start();
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
		
			clip = null;
		   
		    
		} 
}
