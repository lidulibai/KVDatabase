package com.umeclub;

public class KeyObject {
    private byte[] hash;
    private long id;

    public KeyObject() {};

    public KeyObject(long id, byte[] hash) {
        this.id = id;
        this.hash = hash;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getHash() {
        return this.hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        if (hash == null) {
            return String.valueOf(this.id);
        }
        return String.valueOf(this.id) + ":" + new String(this.hash);
    }
}
