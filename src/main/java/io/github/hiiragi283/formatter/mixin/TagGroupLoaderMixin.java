package io.github.hiiragi283.formatter.mixin;

import io.github.hiiragi283.formatter.HTTagFormatRule;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TagGroupLoader.class)
public class TagGroupLoaderMixin<T> {

    @Redirect(method = "loadTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceFinder;toResourceId(Lnet/minecraft/util/Identifier;)Lnet/minecraft/util/Identifier;"))
    private Identifier ht_tag_formatter$toResourceId(ResourceFinder instance, Identifier path) {
        return HTTagFormatRule.convertId(instance.toResourceId(path));
    }

}