package com.petpet.c3po.gatherer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petpet.c3po.api.gatherer.MetaDataGatherer;
import com.petpet.c3po.api.model.helper.MetadataStream;
import com.petpet.c3po.common.Constants;

public class FileSystemGatherer implements MetaDataGatherer {

  private static final Logger LOG = LoggerFactory.getLogger(FileSystemGatherer.class);

  private Map<String, String> config;

  private List<String> files;

  private long count;

  private long remaining;

  private int pointer;

  public FileSystemGatherer(Map<String, String> config) {
    this.config = config;
    this.init();
  }

  public void setConfig(Map<String, String> config) {
    this.config = config;

  }

  public long getCount() {
    return this.count;
  }

  @Override
  public long getRemaining() {
    return this.remaining;
  }

  public List<MetadataStream> getNext(int nr) {
    List<MetadataStream> next = new ArrayList<MetadataStream>();

    if (nr <= 0) {
      return next;
    }

    while (this.pointer < this.files.size() && nr > 0) {
      try {
        nr--;
        this.remaining--;

        String fileName = this.files.get(pointer++);
        MetadataStream dos = new MetadataStream(fileName, new FileInputStream(fileName));
        next.add(dos);

      } catch (FileNotFoundException e) {
        LOG.warn("File '{}' not found: {}", this.files.get(this.pointer), e.getMessage());
      }
    }

    return next;
  }

  private void init() {
    this.files = new ArrayList<String>();
    this.pointer = 0;
    this.count = -1;
    this.remaining = -1;
    String path = (String) this.config.get(Constants.CNF_COLLECTION_LOCATION);
    boolean recursive = Boolean.valueOf(this.config.get(Constants.CNF_RECURSIVE));

    if (path == null) {
      LOG.error("No path config provided");
      return;
    }

    File dir = new File(path);

    if (!dir.exists() || !dir.isDirectory()) {
      LOG.error("Directory '{}' does not exist, or is not a directory", path);
      return;
    }

//    final XMLFileFilter filter = new XMLFileFilter(recursive);

    this.count = this.traverseFiles(dir, null);
    this.remaining = this.count;

  }

  private long traverseFiles(File file, FileFilter filter) {
    long sum = 0;

    if (file.isDirectory()) {
      File[] files = file.listFiles(filter);
      for (File f : files) {
        sum += traverseFiles(f, filter);
      }
    } else {
      this.files.add(file.getAbsolutePath());
      sum++;
    }

    return sum;
  }

  private class XMLFileFilter implements FileFilter {

    private boolean recursive;

    public XMLFileFilter(boolean recursive) {
      this.recursive = recursive;
    }

    @Override
    public boolean accept(File pathname) {
      boolean accept = false;

      if ((pathname.isDirectory() && this.recursive))
        accept = true;

      return accept;
    }

  }

  @Override
  public MetadataStream getNext() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public boolean hasNext() {
    // TODO Auto-generated method stub
    return false;
  }
}