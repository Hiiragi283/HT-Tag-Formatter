package io.github.hiiragi283.formatter

import net.minecraft.util.Identifier
import org.apache.logging.log4j.Level

fun buildTagFormatRule(name: String, builderAction: HTTagFormatRule.Builder.() -> Unit): HTTagFormatRule =
    HTTagFormatRule.Builder(name).apply(builderAction).let(::HTTagFormatRule)

class HTTagFormatRule internal constructor(builder: Builder) {
    val name: String = builder.name
    private val prefix: String? = builder.prefix
    private val suffix: String? = builder.suffix

    val hasPrefix: Boolean = prefix != null
    val hasSuffix: Boolean = suffix != null
    val hasBoth: Boolean = hasPrefix && hasSuffix
    val hasEither: Boolean = hasPrefix || hasSuffix

    init {
        check(hasEither) { "Rule; $name must have either prefix or suffix!" }
        if (hasBoth) {
            if (!bothRules.add(this)) HTTagFormatter.LOGGER.warn("TagFormatRule named $name overridden!")
        } else {
            if (!eitherRules.add(this)) HTTagFormatter.LOGGER.warn("TagFormatRule named $name overridden!", Level.WARN)
        }
    }

    //    Conventional Tag -> Part Tag    //

    fun getResult(id: Identifier): Result = getResult(id.path)

    fun getResult(t: String): Result = when {
        hasBoth -> Result.BOTH.takeIf { t.startsWith(prefix!!) && t.endsWith(suffix!!) }
        hasPrefix -> Result.PREFIX.takeIf { t.startsWith(prefix!!) }
        hasSuffix -> Result.SUFFIX.takeIf { t.endsWith(suffix!!) }
        else -> null
    } ?: Result.NONE

    fun convert(id: Identifier): Identifier = Identifier(id.namespace, convert(id.path))

    fun convert(path: String): String = when (getResult(path)) {
        Result.BOTH -> path.removePrefix(prefix!!).removeSuffix(suffix!!)
        Result.PREFIX -> path.removePrefix(prefix!!)
        Result.SUFFIX -> path.removeSuffix(suffix!!)
        Result.NONE -> path
    }.let { "$name/$it" }.apply { HTTagFormatter.LOGGER.debug("Converted; $path -> $this") }

    fun canConvert(id: Identifier): Boolean = canConvert(id.path)

    fun canConvert(path: String): Boolean = getResult(path) != Result.NONE

    //    Material + Rule -> Part Tag    //

    fun combine(name: String): String = StringBuilder().apply {
        prefix?.let(this::append)
        append(name)
        suffix?.let { removeSuffix("s") }?.let(this::append)
    }.toString()

    fun combineToId(name: String, namespace: String = "part"): Identifier = Identifier(namespace, combine(name))

    //    Any    //

    override fun equals(other: Any?): Boolean = (other as? HTTagFormatRule)?.name == this.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Builder    //

    class Builder internal constructor(val name: String) {
        var prefix: String? = null
        var suffix: String? = null
    }

    //    Result    //

    enum class Result {
        BOTH,
        PREFIX,
        SUFFIX,
        NONE,
    }

    //    Companion    //

    companion object {
        @JvmStatic
        val registry: Map<String, HTTagFormatRule>
            get() = registry1
        private val registry1: MutableMap<String, HTTagFormatRule> = mutableMapOf()

        private val bothRules: MutableSet<HTTagFormatRule> = hashSetOf()

        private val eitherRules: MutableSet<HTTagFormatRule> = hashSetOf()

        private val customRules: MutableMap<String, String> = hashMapOf()

        @JvmStatic
        fun registerCustomRule(before: String, after: String) {
            customRules[before] = after
        }

        @JvmStatic
        fun findRule(id: Identifier): HTTagFormatRule? = findRule(id.path)

        @JvmStatic
        fun findRule(path: String): HTTagFormatRule? =
            bothRules.firstOrNull { it.canConvert(path) } ?: eitherRules.firstOrNull { it.canConvert(path) }

        @JvmStatic
        fun convertId(id: Identifier): Identifier = findRule(id.path)?.convert(id)?.takeIf { id.namespace == "c" }
            ?: customRules[id.path]?.let { Identifier(id.namespace, it) }
            ?: id
    }
}
