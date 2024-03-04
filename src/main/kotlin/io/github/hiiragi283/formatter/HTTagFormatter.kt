package io.github.hiiragi283.formatter

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object HTTagFormatter : PreLaunchEntrypoint {
    private const val MOD_NAME: String = "HT Tag Formatter"
    private val logger: Logger = LogManager.getLogger(MOD_NAME)

    @JvmStatic
    fun log(message: String, level: Level = Level.INFO) {
        logger.log(level, "[$MOD_NAME] $message")
    }

    override fun onPreLaunch() {
        Fabric
        Forge
        log("HT Tag Formatter initialized!")
    }

    object Fabric {
        //    Block    //

        @JvmField
        val BLOCK = buildTagFormatRule("block") { suffix = "_blocks" }

        @JvmField
        val ORES = buildTagFormatRule("ore") { suffix = "_ores" }

        @JvmField
        val RAW_BLOCK = buildTagFormatRule("raw_block") {
            prefix = "raw_"
            suffix = "_blocks"
        }

        //    Item    //

        @JvmField
        val DUST = buildTagFormatRule("dust") { suffix = "_dusts" }

        @JvmField
        val SMALL_DUST = buildTagFormatRule("small_dust") { suffix = "_small_dusts" }

        @JvmField
        val GEAR = buildTagFormatRule("gear") { suffix = "_gears" }

        @JvmField
        val GEM = buildTagFormatRule("gem") { suffix = "_gems" }

        @JvmField
        val INGOT = buildTagFormatRule("ingot") { suffix = "_ingots" }

        @JvmField
        val NUGGET = buildTagFormatRule("nugget") { suffix = "_nuggets" }

        @JvmField
        val PLATE = buildTagFormatRule("plate") { suffix = "_plates" }

        @JvmField
        val RAW_ORE = buildTagFormatRule("raw_ore") {
            prefix = "raw_"
            suffix = "_ores"
        }
    }

    object Forge {
        //    Block    //

        @JvmField
        val BLOCK = buildTagFormatRule("block/forge") { prefix = "storage_blocks/" }

        @JvmField
        val ORE = buildTagFormatRule("ore/forge") { prefix = "ores/" }

        @JvmField
        val RAW_BLOCK = buildTagFormatRule("raw_block/forge") { prefix = "storage_blocks/raw_" }

        //    Fabric    //

        @JvmField
        val DUST = buildTagFormatRule("dust/forge") { prefix = "dusts/" }

        @JvmField
        val INGOT = buildTagFormatRule("ingot/forge") { prefix = "ingots/" }

        @JvmField
        val GEAR = buildTagFormatRule("gear/forge") { suffix = "gears/" }

        @JvmField
        val GEM = buildTagFormatRule("gem/forge") { suffix = "gems/" }

        @JvmField
        val NUGGET = buildTagFormatRule("nugget/forge") { prefix = "nuggets/" }

        @JvmField
        val PLATE = buildTagFormatRule("plate/forge") { prefix = "plates/" }

        @JvmField
        val RAW_ORE = buildTagFormatRule("raw_ore") { prefix = "raw_materials/" }
    }
}
