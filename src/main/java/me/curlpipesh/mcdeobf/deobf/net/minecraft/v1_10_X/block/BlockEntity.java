package me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_10_X.block;

import me.curlpipesh.mcdeobf.Main;
import me.curlpipesh.mcdeobf.deobf.ClassDef;
import me.curlpipesh.mcdeobf.deobf.Deobfuscator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * @author audrey
 * @since 8/24/15.
 */
public class BlockEntity extends Deobfuscator {
    public BlockEntity() {
        super("BlockEntity");
    }

    @Override
    public boolean deobfuscate(final byte[] classData) {
        return dumpConstantPoolStrings(new ClassReader(classData)).contains("Skipping BlockEntity with id {}");
    }

    @Override
    @SuppressWarnings({"unchecked", "Duplicates"})
    public ClassDef getClassDefinition(final byte[] classData) {
        final ClassReader cr = new ClassReader(classData);
        final ClassNode cn = new ClassNode();
        final ClassDef def = new ClassDef(this);
        cr.accept(cn, 0);
        final Optional<Entry<Deobfuscator, byte[]>> blockPos = Main.getInstance().getDataToMap().entrySet().stream()
                .filter(d -> d.getKey().getDeobfuscatedName().equals("BlockPos")).findFirst();
        if(!blockPos.isPresent()) {
            Main.getInstance().getLogger().severe("[BlockEntity] Couldn't find BlockPos, bailing out.");
            return null;
        }
        ((List<FieldNode>) cn.fields).stream().filter(f -> f.desc.contains(blockPos.get().getKey().getObfuscatedDescription()))
                .forEach(f -> def.addField("blockPos", f.name));
        return def;
    }
}
