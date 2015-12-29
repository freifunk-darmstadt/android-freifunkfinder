package common;

import android.content.Context;

/**
 * Created by govind on 12/26/2015.
 * sets the app context to be used by
 * other layers of the app
 */

public class ApplicationContext {

    private Context applicationContext = null;

    public ApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

}
