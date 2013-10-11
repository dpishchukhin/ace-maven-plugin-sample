package org.knowhowlab.osgi.workshop2012.firealarm.fogsensor;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.knowhowlab.osgi.workshop2012.firealarm.api.FireAppliance;
import org.knowhowlab.osgi.workshop2012.firealarm.api.common.AbstractSensorAppliance;
import org.osgi.service.component.ComponentContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dpishchukhin
 */
@Component(specVersion = "1.2", name = "firealarm.smoke.sensor", label = "Smoke Sensor", description = "Smoke Sensor",
        metatype = false, configurationFactory = true, immediate = true)
@Service(FireAppliance.class)
public class SmokeSensorComponent extends AbstractSensorAppliance implements FireAppliance {
    private ScheduledExecutorService executorService;

    @Override
    protected void activate(ComponentContext ctx) {
        super.activate(ctx);
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(new CheckEnvironmentStatusRunnable(), 1, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void deactivate(ComponentContext ctx) {
        executorService.shutdown();
        executorService.shutdownNow();

        super.deactivate(ctx);
    }

    private class CheckEnvironmentStatusRunnable implements Runnable {
        @Override
        public void run() {
            try {
                boolean hasFog = getRoom().hasSmoke();
                if (hasFog && !activated) {
                    activateAlarm(null);
                } else if (activated && !hasFog) {
                    deactivateAlarm();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
