package ck.swing1.gui;

import java.util.EventListener;

public interface PrefsListener extends EventListener {
	
	public void preferencesSet(String user, String password, int portNumber);

}
