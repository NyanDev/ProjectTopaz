package com.nyandev.projecttopaz;

import com.nyandev.projecttopaz.events.AllPurposeEvent;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 24/09/2017.
 */

public class UpdateDataService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        // send an event to update the weather cards
        EventBus.getDefault().postSticky(new AllPurposeEvent("fetchNewData"));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
