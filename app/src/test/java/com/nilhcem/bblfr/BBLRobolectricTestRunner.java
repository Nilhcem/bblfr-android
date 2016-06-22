package com.nilhcem.bblfr;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import java.io.File;

public class BBLRobolectricTestRunner extends RobolectricTestRunner {

    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 21;

    public BBLRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String root = getAppPath();
        String buildVariant = BuildConfig.FLAVOR + "/" + BuildConfig.BUILD_TYPE;

        String manifestProperty = root + "src/main/AndroidManifest.xml";
        String assetsProperty = root + "build/intermediates/assets/" + buildVariant;
        String resProperty = root + "build/intermediates/res/merged/" + buildVariant;
        return new AndroidManifest(
                Fs.fileFromPath(manifestProperty),
                Fs.fileFromPath(resProperty),
                Fs.fileFromPath(assetsProperty)) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
        };
    }

    private String getAppPath() {
        // Using Android-Studio: ./app/
        if (new File("./app/").exists()) {
            return "./app/";
        }
        // Using gradle: ../app/
        return "../app/";
    }
}
