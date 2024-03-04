package io.github.hiiragi283.formatter.mixin;

import io.github.hiiragi283.formatter.HTTagFormatRule;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TagKey.class)
public class TagKeyMixin<T> {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Identifier ht_tag_formatter$modifyId(Identifier id) {
        return HTTagFormatRule.convertId(id);
    }
}