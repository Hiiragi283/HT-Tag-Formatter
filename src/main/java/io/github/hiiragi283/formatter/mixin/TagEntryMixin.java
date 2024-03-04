package io.github.hiiragi283.formatter.mixin;

import io.github.hiiragi283.formatter.HTTagFormatRule;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TagEntry.class)
public class TagEntryMixin {

    @ModifyVariable(method = "createTag", at = @At("HEAD"), argsOnly = true)
    private static Identifier ht_tag_formatter$createTag(Identifier id) {
        return HTTagFormatRule.convertId(id);
    }

    @ModifyVariable(method = "createOptionalTag", at = @At("HEAD"), argsOnly = true)
    private static Identifier ht_tag_formatter$createOptionalTag(Identifier id) {
        return HTTagFormatRule.convertId(id);
    }

}