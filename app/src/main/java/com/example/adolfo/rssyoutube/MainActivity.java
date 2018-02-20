package com.example.adolfo.rssyoutube;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDGHAdgQQGyIf3xGMRJDzoEPtvObIhQF7I";
    private static final String YOUTUBE_PLAYLIST_ID = "RDBwb5D-ILhAQ";

    public String idVideo;
    public ListView mVideoList;
    public Videos video;
    public Button mBotonRss;
    private String mFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBotonRss = findViewById(R.id.downloadRss);
        mVideoList = findViewById(R.id.listViewVideos);


        mBotonRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseVideo parseVideo = new ParseVideo(mFileContent);
                parseVideo.process();


                Adapter adapterVideo = new Adapter(
                        MainActivity.this,
                        parseVideo.getVideos()
                );

                mVideoList.setAdapter(adapterVideo);

                mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int posi, long id) {
                        video = parseVideo.getVideos(posi);
                        idVideo = video.getId();
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                                MainActivity.this,
                                YOUTUBE_API_KEY,
                                idVideo
                        );
                        startActivity(intent);
                    }
                });

            }
        });

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://www.youtube.com/feeds/videos.xml?playlist_id="+YOUTUBE_PLAYLIST_ID);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings) {
            mFileContent = downloadXmlFile(strings[0]);

            if (mFileContent == null) {
                Log.d(TAG, "Se origino un problema en la descarga del archivo XML");
            }

            return mFileContent;
        }

        public String downloadXmlFile(String urlPath) {

            StringBuilder tempBuffer = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Codigo: " + response);

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int charsRead;
                char[] inputBuffer = new char[500];

                while (true) {
                    charsRead = inputStreamReader.read(inputBuffer);

                    if (charsRead <= 0) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();
            } catch (IOException e) {
                Log.d(TAG, "Se origino un problema al descargar el RSS: " + e.getMessage());

            }

            return null;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
