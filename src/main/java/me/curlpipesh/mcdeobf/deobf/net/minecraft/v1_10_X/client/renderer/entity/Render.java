package me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_10_X.client.renderer.entity;

import me.curlpipesh.mcdeobf.Main;
import me.curlpipesh.mcdeobf.deobf.ClassDef;
import me.curlpipesh.mcdeobf.deobf.Deobfuscator;
import me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_10_X.entity.Entity;
import me.curlpipesh.mcdeobf.util.AccessHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by TehNeon on 2016-02-27.
 */
public class Render extends Deobfuscator {
    public Render() {
        super("Render");
    }

    @Override
    public boolean deobfuscate(final byte[] classData) {
        final List<String> constants = dumpConstantPoolStrings(new ClassReader(classData));
        return constants.containsAll(Arrays.<String>asList("deadmau5",
                "minecraft:blocks/fire_layer_0",
                "minecraft:blocks/fire_layer_1"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassDef getClassDefinition(final byte[] classData) {
        final ClassReader cr = new ClassReader(classData);
        final ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        final ClassDef def = new ClassDef(this);

        //noinspection Convert2streamapi
        for (final MethodNode m : (List<MethodNode>) cn.methods) {
            if (AccessHelper.isPublic(m.access) || AccessHelper.isProtected(m.access)) {
                if (m.desc.equals('(' + Main.getInstance().getVersion().getDeobfuscator(Entity.class).getObfuscatedDescription() + "Ljava/lang/String;DDDI)V")) {
                    def.addMethod("renderLivingLabel", m);
                }
            }
        }

        return def;
    }
}
