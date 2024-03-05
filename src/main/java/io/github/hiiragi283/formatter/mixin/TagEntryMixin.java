package io.github.hiiragi283.formatter.mixin;

import io.github.hiiragi283.formatter.HTTagFormatRule;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Tag.TagEntry.class)
public abstract class TagEntryMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Identifier ht_rag_formatter$init(Identifier id) {
        return HTTagFormatRule.convertId(id);
    }

}