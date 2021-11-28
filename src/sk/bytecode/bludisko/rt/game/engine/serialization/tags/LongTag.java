package sk.bytecode.bludisko.rt.game.engine.serialization.tags;

import java.nio.ByteBuffer;

public class LongTag extends Tag<Long> {

    public LongTag(Long data) {
        super(data);
    }

    @Override
    protected byte id() {
        return 4;
    }

    @Override
    protected int length() {
        return 8 + 1;
    }

    @Override
    public byte[] byteData() {
        return ByteBuffer.allocate(this.length())
                .put(this.id())
                .putLong(data)
                .array();
    }
}
