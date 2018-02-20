package com.example.adolfo.rssyoutube;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class ParseVideo {
    private static final String TAG = "ParseVideo";

    private String xmlData;
    private ArrayList<Videos> videos;

    public ParseVideo(String xmlData) {
        this.xmlData = xmlData;
        videos = new ArrayList<>();
    }

    public ArrayList<Videos> getVideos() {
        return videos;
    }

    public boolean process() {
        boolean status = true;
        Videos currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new Videos();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                videos.add(currentRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("title")) {
                                currentRecord.setTitle(textValue);
                            } else if (tagName.equalsIgnoreCase("videoId")) {
                                currentRecord.setId(textValue);
                            } else if (tagName.equalsIgnoreCase("thumbnail")) {
                                String url = xpp.getAttributeValue(null, "url");
                                currentRecord.setMiniaturaURL(url);
                            } else if (tagName.equalsIgnoreCase("stadistics")) {
                                String views = xpp.getAttributeValue(null, "views");
                                currentRecord.setViews(views);
                            }
                        }

                        break;
                    default:
                }
                eventType = xpp.next();
            }
        } catch (
                Exception e)

        {
            Log.d(TAG, "Problema parseando el RSS" + e.getMessage());
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public Videos getVideos(int i) {
        return videos.get(i);
    }
}
