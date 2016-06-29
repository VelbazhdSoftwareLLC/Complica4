package eu.veldsoft.complica4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Alarm listener for network training.
 * 
 * @author Todor Balabanov
 */
public class NetworkTrainingReceiver extends BroadcastReceiver {
	/**
	 * Receiver constructor.
	 */
	public NetworkTrainingReceiver() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NetworkTrainingService.class));
	}
}
