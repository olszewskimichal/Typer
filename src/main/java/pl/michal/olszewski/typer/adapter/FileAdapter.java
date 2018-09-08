package pl.michal.olszewski.typer.adapter;

import java.io.Closeable;

interface FileAdapter extends Closeable, Iterable<FileAdapterRow> {

}