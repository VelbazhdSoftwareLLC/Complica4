package eu.veldsoft.complica4;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import eu.veldsoft.complica4.model.Util;

/**
 * Boot receiver for alarm events registration.
 * 
 * @author Todor Balabanov
 */
public class BootReceiver extends BroadcastReceiver {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
		JobInfo jobInfo = new JobInfo.Builder(Util.TRAINING_JOB_ID,
				new ComponentName(context, NetworkTrainingJobService.class)).setOverrideDeadline(0).build();
		jobScheduler.schedule(jobInfo);
	}
}
