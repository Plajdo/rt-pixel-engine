package sk.bytecode.bludisko.rt.game.engine.serialization.tags;

import java.nio.ByteBuffer;

public final class BooleanTag extends Tag<Boolean> {

    public BooleanTag(Boolean data) {
        super(data);
    }

    @Override
    public byte id() {
        return 9;
    }

    @Override
    public int length() {
        return 1 + 1;
    }

    @Override
    public byte[] byteData() {
        return ByteBuffer.allocate(this.length())
                .put(this.id())
                .put(this.data ? (byte) 1 : (byte) 0)
                .array();
    }
}
