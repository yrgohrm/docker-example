package se.yrgo.deb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MimeTypeParser {
    private MimeTypeParser() {}

    /**
     * Parse a mime type value from Accept or Content-Type header
     * and return a list of mime types in their preferred order.
     * Most preferred comes first.
     * 
     * @param mimeTypeString list of mime types
     * @return a sorted list of mime types
     */
    public static List<String> parse(final String mimeTypeString) {
        if (mimeTypeString == null || mimeTypeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Mime Types can't be null or empty");
        }

        List<MimeType> mimeTypes = new ArrayList<>();

        final String[] mimeElements = mimeTypeString.split(",");
        for (String element : mimeElements) {
            if (element.trim().isEmpty()) {
                continue;
            }

            mimeTypes.add(getMimeType(element));
        }

        mimeTypes.sort(Comparator.comparingDouble(MimeType::getQuality).reversed());
        return mimeTypes.stream().map(MimeType::getMimeType).collect(Collectors.toList());
    }

    private static MimeType getMimeType(String mimeElement) {
        final String[] parts = mimeElement.split(";");
        final String mimeType = parts[0];
        final String[] tokens = Arrays.copyOfRange(parts, 1, parts.length);
        double quality = getQualityValue(tokens);
        return new MimeType(mimeType.trim(), quality);
    }

    private static double getQualityValue(final String[] tokens) {
        for (String token : tokens) {
            if (isQualityToken(token)) {
                return getQualityValue(token);
            }
        }

        return 1.0;
    }

    private static double getQualityValue(String token) {
        String qualityValue = token.substring(2);
        try {
            double potentialQuality = Double.parseDouble(qualityValue);
            if (validQualityRange(potentialQuality)) {
                return potentialQuality;
            }

            throw new IllegalArgumentException("Quality value not in range: " + potentialQuality);
        }
        catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Illegal quality value: " + token);
        }
    }

    private static boolean isQualityToken(String token) {
        return token.startsWith("q=");
    }

    private static boolean validQualityRange(double q) {
        return q >= 0.0 && q <= 1.0;
    }

    private static class MimeType {
        private String mime;
        private double quality;

        public MimeType(String mime, double quality) {
            this.mime = mime;
            this.quality = quality;
        }

        public String getMimeType() {
            return mime;
        }

        public double getQuality() {
            return quality;
        }

        @Override
        public String toString() {
            return "MimeType [mimeType=" + mime + ", quality=" + quality + "]";
        }
    }
}
