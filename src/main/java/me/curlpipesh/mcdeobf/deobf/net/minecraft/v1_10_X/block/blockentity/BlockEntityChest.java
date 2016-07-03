package me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_10_X.block.blockentity;

import me.curlpipesh.mcdeobf.deobf.ClassDef;
import me.curlpipesh.mcdeobf.deobf.Deobfuscator;
import org.objectweb.asm.ClassReader;

import java.util.List;

/**
 * @author audrey
 * @since 8/25/15.
 */
public class BlockEntityChest extends Deobfuscator {
    public BlockEntityChest() {
        super("BlockEntityChest");
    }

    @Override
    public boolean deobfuscate(byte[] classData) {
        List<String> constantPool = dumpConstantPoolStrings(new ClassReader(classData));
        return constantPool.contains("container.chest") && constantPool.contains("minecraft:chest");
    }

    @Override
    public ClassDef getClassDefinition(byte[] classData) {
        return new ClassDef(this);
    }
}
