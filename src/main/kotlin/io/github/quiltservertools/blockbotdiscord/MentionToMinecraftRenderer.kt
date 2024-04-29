package io.github.quiltservertools.blockbotdiscord

import com.kotlindiscord.kord.extensions.ExtensibleBot
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.supplier.EntitySupplyStrategy
import dev.vankka.mcdiscordreserializer.renderer.implementation.DefaultMinecraftRenderer
import io.github.quiltservertools.blockbotdiscord.config.config
import io.github.quiltservertools.blockbotdiscord.config.getGuild
import io.github.quiltservertools.blockbotdiscord.config.getMentionColor
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
class MentionToMinecraftRenderer(
    private val bot: ExtensibleBot,
) : DefaultMinecraftRenderer() {
    override fun appendChannelMention(component: Component, id: String): Component {
        return runBlocking {
            val channel = bot.getKoin().get<Kord>()
                .getChannel(Snowflake(id), EntitySupplyStrategy.cacheWithRestFallback)
            val name = channel?.data?.name?.value ?: "deleted-channel"

            component.append(
                Component.text(
                    "#$name"
                ).color(config.getMentionColor())
            )
        }
    }

    override fun appendUserMention(component: Component, id: String): Component {
        return runBlocking {
            val member = config.getGuild(bot).getMemberOrNull(Snowflake(id))
            val name = member?.effectiveName ?: "unknown-user"

            component.append(
                Component.text("@$name")
            ).color(config.getMentionColor())
        }
    }

    override fun appendRoleMention(component: Component, id: String): Component {
        return runBlocking {
            val role = config.getGuild(bot).getRoleOrNull(Snowflake(id))
            val name = role?.name ?: "deleted-role"
            val color = if (role != null && role.color.rgb != 0) role.color.rgb else config.getMentionColor().value()

            component.append(
                Component.text(
                    "@${name}"
                ).color(TextColor.color(color))
            )
        }
    }
}
