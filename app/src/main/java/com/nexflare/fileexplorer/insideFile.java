package com.nexflare.fileexplorer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class insideFile extends AppCompatActivity {
    private static final String TAG = "ERR00s";
    public static final String TAGs="NEXTACT";
    String path;
    RV mRecyclerView;
    ArrayList<String> filearr;
    @Override
    protected void onStart() {
        super.onStart();
        filearr=new ArrayList<>();
        populatefile(path);
        mRecyclerView.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        filearr=new ArrayList<>();
        populatefile(path);
        mRecyclerView.notifyDataSetChanged();
    }


    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_file);
        rv= (RecyclerView) findViewById(R.id.rvInside);
        Intent intent=getIntent();
        path=intent.getStringExtra("path");
        Log.d(TAGs, "onCreate: "+path);
        filearr=new ArrayList<>();
        populatefile(path);
        mRecyclerView =new RV();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mRecyclerView);
    }
    public class VH extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        CardView cv;
        public VH(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.ivfile);
            tv= (TextView) itemView.findViewById(R.id.tvfile);
            cv= (CardView) itemView.findViewById(R.id.cv);
        }
    }
    public class RV extends RecyclerView.Adapter<VH>{


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();
            View v=inflater.inflate(R.layout.list_files,parent,false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(final VH holder, int position) {
            final String s=filearr.get(position);
            holder.tv.setText(s);
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        String filenameArray[] = s.split("\\.");
                        String extension = "";
                        extension = filenameArray[filenameArray.length - 1];
                        Log.d(TAG, "onClick EXTENSION: " + extension);

                        if (extension.equals("mp3")) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            Log.d(TAG, "onClick: PATH" + path + "/" + s);

                            File file = new File(path + "/" + holder.tv.getText());
                        /*intent.setData(Uri.parse("file://" + file.getPath()));
                        intent.setType("audio*//*");*/
                            intent.setDataAndType(Uri.fromFile(file),"audio/mp3");
//                            intent.setDataAndType(FileProvider.getUriForFile(insideFile.this, "com.nexflare.fileprovider", file), "audio/mp3");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);

                        } else if (extension.equals("pdf")) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
//                            Toast.makeText(insideFile.this, "Intent for pdf Called", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onClick: PATH" + path + "/" + s);
                            File file = new File(path + "/" + holder.tv.getText());
//                            Toast.makeText(insideFile.this, "File:" + file.getName(), Toast.LENGTH_SHORT).show();
                        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
//                            intent.setDataAndType(FileProvider.getUriForFile(insideFile.this, BuildConfig.APPLICATION_ID + ".provider", file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(insideFile.this, insideFile.class);
                            intent.putExtra("path", path + "/" + holder.tv.getText());
                            startActivity(intent);
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("EXC",e.getMessage());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return filearr.size();
        }
    }
    public void populatefile(String in) {
        Log.d(TAG, "populatefile:");
        Log.d("Files", "Path: " + path);

        File directory = new File(in);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            filearr.add(new String(files[i].getName()));
        }
    }
}
