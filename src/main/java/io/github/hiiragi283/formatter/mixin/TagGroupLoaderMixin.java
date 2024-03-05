package io.github.hiiragi283.formatter.mixin;

import io.github.hiiragi283.formatter.HTTagFormatRule;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {

    @SuppressWarnings("unchecked")
    @Redirect(method = "buildGroup", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getKey()Ljava/lang/Object;"))
    private <K, V> K ht_tag_formatter$getKey(Map.Entry<K, V> instance) {
        K key = instance.getKey();
        return key instanceof Identifier ? (K) HTTagFormatRule.convertId((Identifier) key) : key;
    }

}