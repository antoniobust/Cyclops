package com.mdmobile.cyclops.util;

import androidx.annotation.WorkerThread;
import android.util.Xml;

import com.mdmobile.cyclops.dataModel.Instance;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Parse the xml file provided from user to import client id secret and address automatically
 * Format expected
 * <server Name="instanceName">
 * <secret>XXXX</secret>
 * <clientId>XXXX</clientId>
 * <address>http://xxx</address>
 * </server>
 */

@WorkerThread
public class ServerXmlConfigParser {

    private final String nameSpace = null;

    public ArrayList<Instance> parseXml(InputStream inputStream) throws XmlPullParserException, IOException {

        ArrayList<Instance> instanceList = new ArrayList<>();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(inputStream, null);
        xmlPullParser.nextTag();
        if (!xmlPullParser.getName().equals("McServer")) {
            throw new XmlPullParserException("Wrong root tag name: " + xmlPullParser.getName());
        }
        while (xmlPullParser.nextTag() != XmlPullParser.END_TAG) {
            instanceList.add(readServer(xmlPullParser));
        }
        return instanceList;
    }

    private Instance readServer(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
//        xmlParser.require(XmlPullParser.START_TAG, nameSpace, "server");
        String serverName = xmlParser.getAttributeValue(0);
        Instance instance;

        while (xmlParser.next() != XmlPullParser.END_TAG) {
            if (xmlParser.getEventType() == XmlPullParser.START_DOCUMENT || xmlParser.getEventType() == XmlPullParser.END_TAG) {
                continue;
            }
            if (xmlParser.getEventType() == XmlPullParser.START_TAG) {
                instance = readServerProperties(serverName, xmlParser);
                return instance;
            }
        }
        throw new XmlPullParserException("No server found, error reading serverInfo file");
    }

    private Instance readServerProperties(String name, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String secret = "", clientId = "", address = "";

        while (xmlPullParser.getEventType() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (xmlPullParser.getName()) {
                case "secret":
                    secret = readSecret(xmlPullParser);
                    xmlPullParser.nextTag();
                    break;
                case "clientId":
                    clientId = readClientId(xmlPullParser);
                    xmlPullParser.nextTag();
                    break;
                case "address":
                    address = readServerAddress(xmlPullParser);
                    xmlPullParser.nextTag();
                    break;
                default:
                    throw new XmlPullParserException("Error reading xml file:" + xmlPullParser.getName());
            }
        }
        return new Instance(name, secret, clientId, address, -1, -1);
    }

    private String readSecret(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "secret");
        String summary = readValue(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "secret");
        return summary;
    }

    private String readClientId(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "clientId");
        String summary = readValue(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "clientId");
        return summary;
    }

    private String readServerAddress(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "address");
        String summary = readValue(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "address");
        return summary;
    }

    private String readServerName(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "Name");
        String summary = readValue(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "Name");
        return summary;
    }

    private String readValue(XmlPullParser parser) throws XmlPullParserException, IOException {
        String value = "";
        if (parser.next() == XmlPullParser.TEXT) {
            value = parser.getText();
            parser.nextTag();
        }
        return value;
    }

    public interface ServerXmlParse {
        void xmlParseComplete(ArrayList<Instance> instanceInfo);
    }
}
