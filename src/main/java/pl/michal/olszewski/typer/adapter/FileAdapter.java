package pl.michal.olszewski.typer.adapter;

import java.io.Closeable;

public interface FileAdapter extends Closeable, Iterable<FileAdapterRow> {

}