package co.hackinout.www.inout_decoders;

import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

/**
 * Created by meet on 31/10/15.
 */

public class ConfigHelper {

    private ParseConfig config;
    private long configLastFetchedTime;

    public void fetchConfigIfNeeded() {
        final long configRefreshInterval = 60 * 60; // 1 hour

        if (config == null ||
                System.currentTimeMillis() - configLastFetchedTime > configRefreshInterval) {
            // Set the config to current, just to load the cache
            config = ParseConfig.getCurrentConfig();

            // Set the current time, to flag that the operation started and prevent double fetch
            ParseConfig.getInBackground(new ConfigCallback() {
                @Override
                public void done(ParseConfig parseConfig, ParseException e) {
                    if (e == null) {
                        // Yay, retrieved successfully
                        config = parseConfig;
                        configLastFetchedTime = System.currentTimeMillis();
                    } else {
                        // Fetch failed, reset the time
                        configLastFetchedTime = 0;
                    }
                }
            });
        }
    }
}
