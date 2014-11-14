package cci.cert.model;

public interface ProductIterator {
    public Product first();
    public boolean hasNext();
    public Product next();
    public Product prev();
    public void reset();
}