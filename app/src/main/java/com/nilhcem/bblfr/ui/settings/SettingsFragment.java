package com.nilhcem.bblfr.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.core.utils.AppUtils;
import com.nilhcem.bblfr.core.utils.IntentUtils;

import javax.inject.Inject;

import timber.log.Timber;

public class SettingsFragment extends PreferenceFragment {

    @Inject Preferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BBLApplication.get(getActivity()).inject(this);

        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(Preferences.PREFS_NAME);
        manager.setSharedPreferencesMode(Context.MODE_PRIVATE);
        addPreferencesFromResource(R.xml.preferences);

        initMode();

        initVersion();
        initRateApp();
        initPreferenceLink("prefs_dev_link");
        initPreferenceLink("prefs_bblfr_link");
        initPreferenceLink("prefs_sources_link");
    }

    private void initMode() {
        Preference modePref = findPreference("prefs_mode");
        if (mPrefs.isUsingHrMode()) {
            modePref.setTitle(getString(R.string.settings_data_hr_leave_title));
            modePref.setSummary(getString(R.string.settings_data_hr_leave_summary));
        } else {
            modePref.setTitle(getString(R.string.settings_data_hr_enter_title));
            modePref.setSummary(getString(R.string.settings_data_hr_enter_summary));
        }

        modePref.setOnPreferenceClickListener(preference -> {
            Timber.d("Change mode");
            mPrefs.toggleMode();
            Activity context = getActivity();
            context.finish();
            IntentUtils.restartApp(context);
            return true;
        });
    }

    private void initVersion() {
        findPreference("prefs_app_version").setSummary(AppUtils.getVersion());
    }

    private void initRateApp() {
        Preference ratePref = findPreference("prefs_rate_link");
        if (AppUtils.wasInstalledFromGooglePlay(getActivity())) {
            ratePref.setOnPreferenceClickListener(preference -> {
                IntentUtils.startGooglePlayIntent(getActivity());
                return true;
            });
        } else {
            ((PreferenceCategory) findPreference("prefs_about")).removePreference(ratePref);
        }
    }

    private void initPreferenceLink(String prefName) {
        findPreference(prefName).setOnPreferenceClickListener(preference -> {
            IntentUtils.startSiteIntent(getActivity(), preference.getSummary().toString());
            return true;
        });
    }
}
