package com.mdmobile.pocketconsole.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Uri matcher for MobiControlDB uris
 */

class McUriMatcher {

    private UriMatcher uriMatcher;
    private SparseArray<McEnumUri> mEnumsMap = new SparseArray<>();

    McUriMatcher() {
        //Instantiate uri matcher
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //Add uris and uri codes
        addUrisToMatch();

    }

    private void addUrisToMatch() {
        final String authority = McContract.CONTENT_AUTHORITY;
        McEnumUri[] enumValues = McEnumUri.values();

        //Iterate over enums values and add the uris and respective codes to the matcher
        for (McEnumUri enumValue : enumValues) {
            uriMatcher.addURI(authority, enumValue.path, enumValue.matcherCode);
        }
        //Rebuild the enum map to index every enum to his matcher code
        buildEnumsMap(enumValues);
    }

    private void buildEnumsMap(McEnumUri[] uris) {
        for (McEnumUri enumElement : uris) {
            mEnumsMap.put(enumElement.matcherCode, enumElement);
        }
    }


    public McEnumUri matchUri(Uri uri) {
        int code = uriMatcher.match(uri);

        McEnumUri mcEnumUri = mEnumsMap.get(code);
        if (mcEnumUri != null) {
            return mcEnumUri;
        } else {
            throw new UnsupportedOperationException("Unsupported uri: " + uri + " code: " + code);
        }
    }

}
