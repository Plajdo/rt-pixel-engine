package sk.bytecode.bludisko.rt.game.engine.serialization.tags;

import java.nio.ByteBuffer;

public final class IntTag extends Tag<Integer> {

    public IntTag(Integer data) {
        super(data);
    }

    @Override
    protected byte id() {
        return 3;
    }

    @Override
    protected int length() {
        return 4 + 1;
    }

    @Override
    public byte[] byteData() {
        return ByteBuffer.allocate(this.length())
                .put(this.id())
                .putInt(data)
                .array();
    }

}