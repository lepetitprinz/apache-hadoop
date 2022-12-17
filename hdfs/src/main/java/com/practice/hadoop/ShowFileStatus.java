package com.practice.hadoop;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;



public class ShowFileStatus {
    public static void main(String[] args) throws IOException {
        String uri =args[0];//w  w  w . j a  v  a2 s . c  om
        Path file = new Path(uri);
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
    }

    public void fileStatusForFile(FileSystem fs) throws IOException {
        Path file  = new Path("/dir/file");
        FileStatus stat = fs.getFileStatus(file);
        // stat.getPath().toUri().getPath();
        // stat.isDirectory();
        // stat.getLen();
        // stat.getReplication();
        // stat.getBlockSize();
        // stat.getOwner();
        // stat.getGroup();
        // stat.getPermission();
    }

}
