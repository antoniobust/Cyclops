package com.mdmobile.cyclopes.utils;

import android.support.annotation.WorkerThread;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Parse the xml file provided from user to import client id secret and address automatically
 * Format expected
 * <server Name="serverName">
 *     <secret>XXXX</secret>
 *     <clientId>XXXX</clientId>
 *     <address>http://xxx</address>
 * </server>
 */

@WorkerThread
public class ServerXmlConfigParser {

    private final String nameSpace = null;
    public interface ServerXmlParse {
        void xmlParseComplete();
    }

    public ArrayList<ServerInfo> parseXml(InputStream inputStream) throws XmlPullParserException, IOException {

            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.nextTag();
            return readServer(xmlPullParser);

    }

    private ArrayList<ServerInfo> readServer(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
        ArrayList<ServerInfo> serverInfo = new ArrayList<>();
        xmlParser.require(XmlPullParser.START_TAG, nameSpace, "server");
        String serverName = xmlParser.getAttributeValue(0);

        while (xmlParser.next() != XmlPullParser.END_DOCUMENT) {
            if (xmlParser.getEventType() == XmlPullParser.START_DOCUMENT || xmlParser.getEventType() == XmlPullParser.END_TAG ) {
                continue;
            }
            if(xmlParser.getEventType() == XmlPullParser.START_TAG) {
                    serverInfo.add(readServerProperties(serverName,xmlParser));
            }
        }
        return serverInfo;
    }

    private ServerInfo readServerProperties(String name,XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String secret = "",clientId="",address="";

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
            }
        }
        return new ServerInfo(name,secret,clientId,address);
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
        if (parser.next()== XmlPullParser.TEXT) {
            value = parser.getText();
            parser.nextTag();
        }
        return value;
    }

    public static class ServerInfo {
        private String apiSecret, clientId, serverAddress, serverName;

        public ServerInfo(String name, String apiSecret, String clientId, String serverAddress) {
            this.apiSecret = apiSecret;
            this.clientId = clientId;
            this.serverAddress = serverAddress;
            this.serverName = name;
        }

        public String getApiSecret() {
            return apiSecret;
        }

        public String getClientId() {
            return clientId;
        }

        public String getServerAddress() {
            return serverAddress;
        }

        public String getServerName() {
            return serverName;
        }
    }
}
