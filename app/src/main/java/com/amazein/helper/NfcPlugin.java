package com.amazein.helper;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NfcPlugin {
    private static final String TAG = "NfcPlugin";
    public static final List<IntentFilter> intentFilters = new ArrayList<>();
    public static  final ArrayList<String[]> techLists = new ArrayList<>();

    public static  NdefMessage p2pMessage = null;
    public static PendingIntent pendingIntent = null;
    
    public static void startNfc(Activity activity) {
        createPendingIntent(activity); // onResume can call startNfc before execute

        activity.runOnUiThread(() -> {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

            if (nfcAdapter != null && !activity.isFinishing()) {
                try {
                    IntentFilter[] intentFilters = getIntentFilters();
                    String[][] techLists = getTechLists();
                    // don't start NFC unless some intent filters or tech lists have been added,
                    // because empty lists act as wildcards and receives ALL scan events
                    if (intentFilters.length > 0 || techLists.length > 0) {
                        nfcAdapter.enableForegroundDispatch(activity, getPendingIntent(), intentFilters, techLists);
                    }

                    if (p2pMessage != null) {
                        nfcAdapter.setNdefPushMessage(p2pMessage, activity);
                    }
                } catch (IllegalStateException e) {
                    // issue 110 - user exits app with home button while nfc is initializing
                    Log.w(TAG, "Illegal State Exception starting NFC. Assuming application is terminating.");
                }

            }
        });
    }
    public static void createPendingIntent(Activity act) {
        if (pendingIntent == null) {
            Activity activity = act;
            Intent intent = new Intent(activity, activity.getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        }
    }
    private void stopNfc(Activity activity) {

        Log.d(TAG, "stopNfc");
        activity.runOnUiThread(() -> {

            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

            if (nfcAdapter != null) {
                try {
                    nfcAdapter.disableForegroundDispatch(activity);
                } catch (IllegalStateException e) {
                    // issue 125 - user exits app with back button while nfc
                    Log.w(TAG, "Illegal State Exception stopping NFC. Assuming application is terminating.");
                }
            }
        });
    }

    public static PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    private IntentFilter createIntentFilter(String mimeType) throws IntentFilter.MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilter.addDataType(mimeType);
        return intentFilter;
    }
    private void addToTechList(String[] techs) {
        techLists.add(techs);
    }

    public static IntentFilter[] getIntentFilters() {
        return intentFilters.toArray(new IntentFilter[intentFilters.size()]);
    }

    public static String[][] getTechLists() {
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return techLists.toArray(new String[0][0]);
    }
}
