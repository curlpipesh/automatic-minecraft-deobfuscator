package me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_9_X.client.renderer.entity;

import me.curlpipesh.mcdeobf.Main;
import me.curlpipesh.mcdeobf.deobf.ClassDef;
import me.curlpipesh.mcdeobf.deobf.Deobfuscator;
import me.curlpipesh.mcdeobf.deobf.net.minecraft.v1_9_X.entity.EntityLivingBase;
import me.curlpipesh.mcdeobf.util.AccessHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by TehNeon on 2016-02-27.
 */
public class RendererLivingEntity extends Deobfuscator {
    public RendererLivingEntity() {
        super("RendererLivingEntity");
    }

    @Override
    public boolean deobfuscate(final byte[] classData) {
        List<String> constants = dumpConstantPoolStrings(new ClassReader(classData));
        return constants.containsAll(Arrays.<String>asList("Grumm",
                "Dinnerbone",
                "Couldn't render entity"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassDef getClassDefinition(final byte[] classData) {
        ClassReader cr = new ClassReader(classData);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        ClassDef def = new ClassDef(this);

        for (MethodNode m : (List<MethodNode>) cn.methods) {
            if (AccessHelper.isPublic(m.access)) {

                if (m.desc.equals("(" + Main.getInstance().getVersion().getDeobfuscator(EntityLivingBase.class).getObfuscatedDescription() + "DDD)V")) {
                    def.addMethod("passSpecialRender", m);
                }


            }
        }

        return def;
    }
}
