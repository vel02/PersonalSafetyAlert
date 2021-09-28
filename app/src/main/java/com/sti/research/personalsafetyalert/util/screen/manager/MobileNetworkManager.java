package com.sti.research.personalsafetyalert.util.screen.manager;

import java.util.HashMap;
import java.util.Map;

interface MobileNetwork {

    void validate(String number);

    String getNetwork();

}

public class MobileNetworkManager implements MobileNetwork {

    private final Map<String, String> prefixes;
    private String network;

    public MobileNetworkManager() {
        prefixes = networkPrefixes();
    }

    @Override
    public void validate(String number) {
        for (String prefix : prefixes.keySet()) {
            if (number.startsWith(prefix)) {
                this.network = prefixes.get(prefix);
                return;
            }
        }
    }

    @Override
    public String getNetwork() {
        return network;
    }

    private Map<String, String> networkPrefixes() {

        Map<String, String> prefixes = new HashMap<>();
        prefixes.put("0907", "TNT");
        prefixes.put("0909", "TNT");
        prefixes.put("0910", "TNT");
        prefixes.put("0912", "TNT");
        prefixes.put("0930", "TNT");
        prefixes.put("0938", "TNT");
        prefixes.put("0948", "TNT");
        prefixes.put("0950", "TNT");

        prefixes.put("0908", "SMART");
        prefixes.put("0918", "SMART");
        prefixes.put("0919", "SMART");
        prefixes.put("0920", "SMART");
        prefixes.put("0921", "SMART");
        prefixes.put("0928", "SMART");
        prefixes.put("0929", "SMART");
        prefixes.put("0939", "SMART");
        prefixes.put("0946", "SMART");
        prefixes.put("0947", "SMART");
        prefixes.put("0949", "SMART");
        prefixes.put("0951", "SMART");
        prefixes.put("0961", "SMART");
        prefixes.put("0998", "SMART");
        prefixes.put("0999", "SMART");

        prefixes.put("0905", "GLOBE");
        prefixes.put("0906", "GLOBE");
        prefixes.put("0915", "GLOBE");
        prefixes.put("0916", "GLOBE");
        prefixes.put("0917", "GLOBE");
        prefixes.put("0926", "GLOBE");
        prefixes.put("0927", "GLOBE");
        prefixes.put("0935", "GLOBE");
        prefixes.put("0936", "GLOBE");
        prefixes.put("0937", "GLOBE");
        prefixes.put("0945", "GLOBE");
        prefixes.put("0953", "GLOBE");
        prefixes.put("0954", "GLOBE");
        prefixes.put("0955", "GLOBE");
        prefixes.put("0956", "GLOBE");
        prefixes.put("0965", "GLOBE");
        prefixes.put("0966", "GLOBE");
        prefixes.put("0967", "GLOBE");
        prefixes.put("0975", "GLOBE");
        prefixes.put("0976", "GLOBE");
        prefixes.put("0977", "GLOBE");
        prefixes.put("0978", "GLOBE");
        prefixes.put("0979", "GLOBE");
        prefixes.put("0994", "GLOBE");
        prefixes.put("0995", "GLOBE");
        prefixes.put("0996", "GLOBE");
        prefixes.put("0997", "GLOBE");

        return prefixes;
    }
}
