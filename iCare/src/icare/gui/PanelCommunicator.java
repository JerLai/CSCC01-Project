package icare.gui;

import java.beans.PropertyChangeListener;

public interface PanelCommunicator {

	public String getPanelType();
	public void setPanelType();
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
